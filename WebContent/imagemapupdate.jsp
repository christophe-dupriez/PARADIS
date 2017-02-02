<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:if test="${not empty readonly}"><div class="info-block"></c:if>
<c:if test="${empty readonly}">
<form role="form" method="POST" action="ImagemapMan" class="form-horizontal" id="toBeValidated"
	  data-bv-message="This value is not valid"
	  data-bv-live="enabled"
	  data-bv-container="tooltip"
	  data-bv-submitButtons="#toBeValidatedSubmit">
  <fieldset>
	<input type="hidden" name="action" value="update"/>
	<input type="hidden" name="subaction" value="save"/>
	<input type="hidden" name="id" value="${imagemap.PK}"/>
	<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	<input type="hidden" name="currenttab" id="currentTab" value=""/>
</c:if>
<ul class="nav nav-tabs">
  <li${empty currentTab or currentTab=='#profile'?' class="active"':''}><a href="#profile" data-toggle="tab"><fmt:message key="obj.imagemap"/></a></li>
  <li${currentTab=='#place'?' class="active"':''}><a href="#place" data-toggle="tab"><fmt:message key="objs.place"/></a></li>
</ul>
<!-- Tab panes -->
<div class="tab-content">
  <div class="tab-pane${empty currentTab or currentTab=='#profile'?' active':''}" id="profile">

	<c:forEach var="lang" items="${supportedLocales}">
	<div class="row form-group">
	    <label class="col-sm-2 text-right" for="name_${lang}"><fmt:message key="name.name" /> ${lang}</label>
	    <div class="col-sm-10">
			<input${readonly} type="text" class="form-control" name="name_${lang}" id="name_${lang}"
				value="${fn:escapeXml(imagemap.namesMap[lang])}" maxlength="63"
				width="63" />
		</div>
	</div>
	</c:forEach>
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="imagemap.xmax">
	    <fmt:message key="imagemap.xmax"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="number" min="0" max="10000" step="1" class="form-control" name="xmax" id="imagemap.xmax" value="${imagemap.xmaxInt}" maxlength="6" width="10"/>
		<c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.imagemap.xmax"/></span></c:if>
    </div>
  </div>
  
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="imagemap.ymax">
	    <fmt:message key="imagemap.ymax"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="number" min="0" max="100000" step="1" class="form-control" name="ymax" id="imagemap.ymax" value="${imagemap.ymaxInt}" maxlength="6" width="10"/>
		<c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.imagemap.ymax"/></span></c:if>
    </div>
  </div>
  </div>
  <div class="tab-pane${currentTab=='#place'?' active':''}" id="place">
	<c:forEach var="mapplace" items="${imagemap.mapPlace}">
	<c:set var="place" value="${mapplace.mapplace}"/>
	  <div class="row form-group">
	    <label class="col-sm-2 text-right" for="xpos-${place.PK}">
		    ${place.getBestName(pageContext.request)}
	    </label>
	    <div class="col-sm-4">
			<div class="input-group">
				<span class="input-group-btn">
				    <button class="btn btn-default" type="button">X:</button>
				</span>
				<input${readonly} type="number" min="0" max="9999" class="form-control" name="xpos${place.PK}" id="xpos-${place.PK}" value="${mapplace.xposInt}" maxlength="6" width="10"/>
			</div>
	    </div>
	    <div class="col-sm-4">
			<div class="input-group">
				<span class="input-group-btn">
				    <button class="btn btn-default" type="button">Y:</button>
				</span>
				<input${readonly} type="number" min="0" max="9999" class="form-control" name="ypos${place.PK}" id="ypos-${place.PK}" value="${mapplace.yposInt}" maxlength="6" width="10"/>
			</div>
	    </div>
	  </div>
	</c:forEach>
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
<c:if test="${not empty readonly}"></div></c:if>
<div class="row"><div class="col-xs-12"><%@ include file="splashgraph.jsp"%></div></div>
<c:if test="${userMayDelete}">
<form class="form-horizontal" action="ImagemapMan" role="form">
  <fieldset>
		<input type="hidden" name="action" value="delete"/>
		<input type="hidden" name="id" value="${imagemap.PK}"/>
		<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	  <div class="row form-group create-block">
	    <label class="col-xs-2 text-right" for="action.delete"><fmt:message key="action.confirm"/></label>
	    <div class="col-xs-1">
		  <input${readonly} type="checkbox" class="form-control" name="confirm" value="confirmed" id="action.delete"/>
		</div>
	    <div class="col-xs-9">
	      <span class="input-group-btn"><button type="submit" class="btn btn-danger btn-xs"><fmt:message key="action.delete"/><strong><fmt:message key="icon.imagemap"/>${imagemap.getBestName(pageContext.request)}</strong></button></span>
	    </div>
	   </div>	   
   </fieldset>
</form>
</c:if>
<%@ include file="footer.jsp"%>
