<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:if test="${not empty readonly}"><div class="info-block"></c:if>
<c:if test="${empty readonly}">
<form role="form" method="POST" action="PlaceMan" class="form-horizontal" id="toBeValidated"
	  data-bv-message="This value is not valid"
	  data-bv-live="enabled"
	  data-bv-container="tooltip"
	  data-bv-submitButtons="#toBeValidatedSubmit">
  <fieldset>
	<input type="hidden" name="action" value="update"/>
	<input type="hidden" name="subaction" value="save"/>
	<input type="hidden" name="id" value="${place.PK}"/>
	<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	<input type="hidden" name="currenttab" id="currentTab" value=""/>
</c:if>
<ul class="nav nav-tabs">
  <li${empty currentTab or currentTab=='#graphs'?' class="active"':''}><a href="#graphs" data-toggle="tab"><fmt:message key="obj.place"/></a></li>
  <li${currentTab=='#position'?' class="active"':''}><a href="#position" data-toggle="tab"><fmt:message key="tab.position"/></a></li>
</ul>
<!-- Tab panes -->
<div class="tab-content">
  <div class="tab-pane${empty currentTab or currentTab=='#graphs'?' active':''}" id="graphs">
	<c:forEach var="lang" items="${supportedLocales}">
	<div class="row form-group">
	    <label class="col-sm-2 text-right" for="name_${lang}"><fmt:message key="name.name" /> ${lang}</label>
	    <div class="col-sm-10">
			<input${readonly} type="text" class="form-control" name="name_${lang}" id="name_${lang}"
				value="${fn:escapeXml(place.namesMap[lang])}" maxlength="63"
				width="63" />
		</div>
	</div>
	</c:forEach>
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="place.symbol">
    	<fmt:message key="place.symbol"/>
    </label>
    <div class="col-sm-10">
    	<select${readonly} class="form-control" name="symbol" id="place.symbol">
    	<c:forEach var="symb" items="${symbols}">
    		<option value="${symb.ordinal}" <c:if test="${place.symbol==symb.ordinal}">selected</c:if>>${symb} ${symb.whiteEntity} ${symb.blackEntity}</option>
   		</c:forEach>
    	</select>
    </div>
  </div>
	<div class="row form-group">
		<label class="col-sm-2 text-right" for="colorhex0"><fmt:message key="place.color"/></label>
		<div class="col-sm-10">
			<div id="colorPick0">
				<input${readonly} type="text" pattern='\#[0-9a-fA-F]{6}' class='form-control'
					name='color' id="colorhex0" value="#${place.webColor}"
					maxlength="7" width="7" onfocus="showPicker(0)"/>
			</div>
		</div>
    </div>
	<div class="row form-group">
		<label class="col-sm-2 text-right" for="colorhex0"><fmt:message key="objs.imagemap"/></label>
		<div class="col-sm-10">
			<select${readonly} multiple="multiple" class="form-control">
			<c:forEach var="imagemap" items="${auxil.allImagemaps}">
				<option value="${imagemap.PK}"<c:if test="${imagemap.places.contains(place)}"> selected</c:if>>${imagemap.getBestName(pageContext.request)}</option>
			</c:forEach>
			</select>
		</div>
    </div>
  </div>
  <div class="tab-pane${currentTab=='#position'?' active':''}" id="position">
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="place.latitude">
	    <fmt:message key="place.latitude"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="number" min="-90.000000" max="90.000000" step="0.000001" class="form-control" name="latitude" id="place.latitude" value="${fn:escapeXml(place.latitude)}" maxlength="31" width="31"
	       separator="."/>
		<c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.place.latitude"/></span></c:if>
    </div>
  </div>
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="place.longitude">
	    <fmt:message key="place.longitude"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="number" min="-180.000000" max="180.000000" step="0.000001" class="form-control" name="longitude" id="place.longitude" value="${fn:escapeXml(place.longitude)}" maxlength="31" width="31"
	       separator="."/>
		<c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.place.longitude"/></span></c:if>
    </div>
  </div>
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="place.elevation">
	    <fmt:message key="place.elevation"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="number" min="-4000.0" max="8000.0" step="0.1" class="form-control" name="elevation" id="place.elevation" value="${fn:escapeXml(place.elevation)}" maxlength="31" width="31"
	       separator="."/>
		<c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.place.elevation"/></span></c:if>
    </div>
  </div>
  </div>
</div>
<c:if test="${empty readonly}">
    <div class="row form-group">
      <div class="col-sm-offset-2 col-sm-10">
		<button type="submit" class="btn btn-success" name="action" value="interface" id="toBeValidatedSubmit"><fmt:message key="action.record"/></button>
	  </div>
    </div>
  </fieldset>
</form>
</c:if>
<c:if test="${not empty readonly}"></div></c:if>
<div class="row buttons-list">
  <div class="col-sm-12 text-right">
  <%@include file="placerel.jsp" %>
  <span class="button-spacer">&nbsp;</span>
  <span class="button-holder">
	  <c:if test="${userMayUpdate}">
	    <a href="PlaceMan?id=${place.PK}&action=interface&breadcrumb=${breadcrumb}"><%@include file="inc/buttonInterface.jsp" %></a>
	  </c:if>
  </span><span class="button-spacer">&nbsp;</span>
  <span class="button-holder">
	  <c:if test="${userMayUpdate and not empty readonly}">
	    <a href="PlaceMan?id=${place.PK}&action=update&breadcrumb=${breadcrumb}"><%@include file="inc/buttonUpdate.jsp" %></a>
	  </c:if>
  </span>
  </div>
</div>
<c:if test="${userMayDelete}">
<form class="form-horizontal" action="PlaceMan" role="form">
  <fieldset>
		<input type="hidden" name="action" value="delete"/>
		<input type="hidden" name="id" value="${place.PK}"/>
		<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	  <div class="row form-group create-block">
	    <label class="col-sm-2 text-right" for="action.delete"><fmt:message key="action.confirm"/></label>
	    <div class="col-sm-1">
		  <input${readonly} type="checkbox" class="form-control" name="confirm" value="confirmed" id="action.delete"/>
		</div>
	    <div class="col-sm-9">
	      <span class="input-group-btn"><button type="submit" class="btn btn-danger btn-xs"><fmt:message key="action.delete"/><strong><fmt:message key="icon.place"/>${place.getBestName(pageContext.request)}</strong></button></span>
	    </div>
	   </div>	   
   </fieldset>
</form>
</c:if>

   	<script type="text/javascript">
   	    var colorhexId = '';
		var colorhex="${place.color}";
		<c:set var="colorHex" value="${place.color}"/>
   	</script>
	<%@include file="colorpicker.jsp"%>
   	<script type="text/javascript">
		function showPicker(noCateg) {
			colorhexId = 'colorhex'+noCateg;
			colorhex = $('#'+colorhexId).val();
			var pick = $('#colorPicker');
			pick.detach();
			$('#colorPick'+noCateg).append(pick);
			pick.show();
			mouseOutMap();
			return true;
		}
		function hidePicker(noCateg) {
			//alert($('#'+colorhexId).val());
			$("#colorPicker").hide();
			return true;
		}
		$('#colorPicker').hide();
	</script>

<%@ include file="footer.jsp"%>
