<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:forEach var="place" items="${places}">
<div class="row active">
  <div class="col-xs-2" style="background-color:${place.colorHex};"> &nbsp; &nbsp; ${place.symbolBlack} &nbsp; &nbsp; </div>
  <div class="col-xs-8"><strong><a href="PlaceMan?action=display&id=${place.PK}">${place.getBestName(pageContext.request)}</a></strong></div>
  <div class="col-xs-2">
  	  <c:if test="${not empty place.latitude and not empty place.longitude}"><a href="https://maps.google.com/maps?q=${place.latitude},${place.longitude}" target="_blank"><%@include file="inc/buttonGoogleMap.jsp"%><%--${place.latLongElev}--%></a> </c:if>
  </div>
</div>
<div class="row active end-block">
  <div class="col-xs-12 text-right">
  <%@include file="placerel.jsp" %>
  <span class="button-spacer">&nbsp;</span>
  <span class="button-holder">
	  <c:if test="${userMayUpdate}">
	    <a href="PlaceMan?id=${place.PK}&action=update&breadcrumb=${breadcrumb}"><%@include file="inc/buttonUpdate.jsp" %></a>
	  </c:if>
  </span>
  </div>
</div>
</c:forEach>
<c:if test="${userMayCreate}">
<form class="form-horizontal" action="PlaceMan" role="form">
  <fieldset>
    <input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
    <input type="hidden" name="action" value="create"/>
	<div class="row form-group create-block">
    	<label class="col-xs-2 text-right" for="placename"><fmt:message key="name.name"/> ${auxil.currentLanguage}</label>
	    <div class="col-xs-6">
		    <input type="text" maxlength="127" class="form-control" id="placename" name="name"/>
		</div>
		<div class="col-xs-4">
		    <span class="input-group-btn">
		    	<button type="submit" class="btn btn-default"><fmt:message key="action.create"/> <fmt:message key="icon.place"/><br/><fmt:message key="obj.place"/></button>
		    </span>
	    </div>
 	 </div>
   </fieldset>
</form>
</c:if>
<%@ include file="footer.jsp"%>

