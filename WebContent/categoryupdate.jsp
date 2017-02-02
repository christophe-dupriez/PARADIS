<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:if test="${not empty readonly}"><div class="info-block without-tabs"></c:if>
<c:if test="${empty readonly}">
<form role="form" method="POST" action="CategoryMan" class="form-horizontal without-tabs" id="toBeValidated"
	  data-bv-message="This value is not valid"
	  data-bv-live="enabled"
	  data-bv-container="tooltip"
	  data-bv-submitButtons="#toBeValidatedSubmit">
  <fieldset>
	<input type="hidden" name="action" value="update"/>
	<input type="hidden" name="subaction" value="save"/>
	<input type="hidden" name="id" value="${category.PK}"/>
	<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	<input type="hidden" name="currenttab" id="currentTab" value=""/>
</c:if>
	<c:forEach var="lang" items="${supportedLocales}">
	<div class="row form-group">
	    <label class="col-sm-2 text-right" for="name_${lang}"><fmt:message key="name.name" /> ${lang}</label>
	    <div class="col-sm-10">
			<input${readonly} type="text" class="form-control" name="name_${lang}" id="name_${lang}"
				value="${fn:escapeXml(category.namesMap[lang])}" maxlength="63"
				width="63" />
		</div>
	</div>
	</c:forEach>
	<div class="row form-group">
		<label class="col-sm-2 text-right" for="colorhex0"><fmt:message key="category.color"/></label>
		<div class="col-sm-10">
			<div id="colorPick0">
				<input${readonly} type="text" pattern='\#[0-9a-fA-F]{6}' class='form-control'
					name='color' id="colorhex0" value="#${category.webColor}"
					maxlength="7" width="7" onfocus="showPicker(0)"/>
			</div>
		</div>
    </div>
<c:if test="${empty readonly}">
    <div class="row form-group">
      <div class="col-xs-offset-2 col-xs-10">
		<button type="submit" class="btn btn-success" name="action" value="interface" id="toBeValidatedSubmit"><fmt:message key="action.record"/></button>
	  </div>
    </div>
  </fieldset>
</form>
</c:if>
<%--
<div class="row">
   <div class="col-lg-12">
   	  <c:set var="nbdtf" value="${fn:length(category.dataTypeField)}"/>
   	  <c:if test="${nbdtf gt 0}">${nbdtf } <fmt:message key="objs.rawdatafield"/>:
      <dl class="dl-horizontal">
		<c:forEach var="datafield" items="${category.dataTypeField}">
		   	<dt>${datafield.description}</dt>
		   	<dd>(<fmt:message key="rawtab.${datafield.inheader}"/><c:if test="${datafield.fixedCategory}">: <strong><fmt:message key="rawdatafield.fixed"/></strong></c:if>) <strong>${datafield.precision}</strong> bits
		   	<c:forEach var="rawtable" items="${datafield.tables }">, ${rawtable.rawtype}-<i>${rawtable.description}</i></c:forEach>
		   	</dd>
		</c:forEach>
	  </dl>
	  </c:if>
	</div>
</div>
<div class="row">
   <div class="col-lg-12">
      <dl class="dl-horizontal">
		<c:forEach var="subtype" items="${category.subtype}">
			<c:set var="machine" value="${subtype.subtypeMachine}"/>
			<dt id="machine-${machine.PK}" style='padding-bottom:10px;<c:if test="${machine.active==false}">background-color:#CCCCCC;</c:if>'>
			    <a href="MachineMan?id=${machine.PK}&breadcrumb=${breadcrumb}">
				<strong><span style="color:${machine.colorHex};">${machine.symbolBlack}</span> ${machine.nodeid} / ${machine.hexMachineMAC}</strong>
				<fmt:message key="icon.machine"/></a>
			</dt>
			<dd>
			  <c:if test="${userMayUpdate}"> &nbsp; 
			    <a href="MachineMan?id=${machine.PK}&rawfieldid=${subtype.subtypeField.PK}&categoryid=${category.PK}&action=interface&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonInterface.jsp" %></a>
			  </c:if>
			  ${machine.getBestName(pageContext.request)}<c:if test="not empty machine.description">: ${machine.description }</c:if><br/>&nbsp;
 			</dd>
		</c:forEach>
	  </dl>
	</div>
</div> --%>
<c:if test="${userMayDelete}">
<form class="form-horizontal" action="CategoryMan" role="form">
  <fieldset>
		<input type="hidden" name="action" value="delete"/>
		<input type="hidden" name="id" value="${category.PK}"/>
		<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	  <div class="row form-group create-block">
	    <label class="col-xs-2 text-right" for="action.delete"><fmt:message key="action.confirm"/></label>
	    <div class="col-xs-1">
		  <input type="checkbox" class="form-control" name="confirm" value="confirmed" id="action.delete"/>
		</div>
	    <div class="col-xs-9">
	      <span class="input-group-btn"><button type="submit" class="btn btn-danger btn-xs"><fmt:message key="action.delete"/><strong><fmt:message key="icon.category"/>${category.getBestName(pageContext.request)}</strong></button></span>
	    </div>
	   </div>	   
   </fieldset>
</form>
</c:if>
<c:if test="${not empty readonly}"></div></c:if>

   	<script type="text/javascript">
   	    var colorhexId = '';
		var colorhex="${category.color}";
		<c:set var="colorHex" value="${category.color}"/>
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
