<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:forEach var="category" items="${categories}">
<div class="row active end-block">
  <div class="col-xs-2" style="background-color:${category.colorHex};"> &nbsp; &nbsp; &nbsp; </div>
  <div class="col-xs-6"><strong>${category.getBestName(pageContext.request)}</strong></div>
  <div class="col-xs-1">
	<c:if test="${fn:length(category.datatypes) gt 0}">
  	  <span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.datatype"/>"><a href="DataTypeMan?action=list&categoryid=${category.PK}&breadcrumb=${breadcrumb}">${fn:length(category.datatypes)}<fmt:message key="icon.datatype"/></a></span>
    </c:if>
	
  </div>
  <div class="col-xs-1">
  <c:if test="${userMayUpdate}"> 
    <a href="CategoryMan?id=${category.PK}&action=update&breadcrumb=${breadcrumb}"><%@include file="inc/buttonUpdate.jsp" %></a>
  </c:if>
  </div>
</div>			      
</c:forEach>
<c:if test="${userMayCreate}">
<form class="form-horizontal" action="CategoryMan" role="form">
  <fieldset>
    <input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
    <input type="hidden" name="action" value="create"/>
	<div class="row form-group create-block">
    	<label class="col-xs-2 text-right" for="categoryname"><fmt:message key="name.name"/> ${auxil.currentLanguage}</label>
	    <div class="col-xs-6">
		    <input type="text" maxlength="127" class="form-control" id="categoryname" name="name"/>
		</div>
		<div class="col-xs-4">
		    <span class="input-group-btn">
		    	<button type="submit" class="btn btn-default"><fmt:message key="action.create"/> <fmt:message key="icon.category"/><br/><fmt:message key="obj.category"/></button>
		    </span>
	    </div>
 	 </div>
   </fieldset>
</form>
</c:if>
<%@ include file="footer.jsp"%>
