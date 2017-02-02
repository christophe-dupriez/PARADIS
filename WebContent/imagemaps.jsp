<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:forEach var="imagemap" items="${imagemaps}">
<div class="row active end-block">
  <div class="col-xs-4"><img src="imagemap/${imagemap.PK}.png" width="100%"/></div>
  <div class="col-xs-6"><strong><a href="ImagemapMan?action=display&id=${imagemap.PK}&datatypeid=1">${imagemap.getBestName(pageContext.request)}</a></strong></div>
  <div class="col-xs-2">
	<c:if test="${fn:length(imagemap.mapPlace) gt 0}">
  	  <span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.place"/>"><a href="PlaceMan?action=list&imagemapid=${imagemap.PK}&breadcrumb=${breadcrumb}">${fn:length(imagemap.mapPlace)}<fmt:message key="icon.place"/></a></span>
    </c:if>
	
  </div>
  <div class="col-xs-1">
  <c:if test="${userMayUpdate}"> 
    <a href="ImagemapMan?id=${imagemap.PK}&action=update&breadcrumb=${breadcrumb}"><%@include file="inc/buttonUpdate.jsp" %></a>
  </c:if>
  </div>
</div>			      
</c:forEach>
<c:if test="${userMayCreate}">
<form class="form-horizontal" action="ImagemapMan" role="form">
  <fieldset>
    <input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
    <input type="hidden" name="action" value="create"/>
	<div class="row form-group create-block">
    	<label class="col-xs-2 text-right" for="imagemapname"><fmt:message key="name.name"/> ${auxil.currentLanguage}</label>
	    <div class="col-xs-6">
		    <input type="text" maxlength="127" class="form-control" id="imagemapname" name="name"/>
		</div>
		<div class="col-xs-4">
		    <span class="input-group-btn">
		    	<button type="submit" class="btn btn-default"><fmt:message key="action.create"/> <fmt:message key="icon.imagemap"/><br/><fmt:message key="obj.imagemap"/></button>
		    </span>
	    </div>
 	 </div>
   </fieldset>
</form>
</c:if>
<%@ include file="footer.jsp"%>
