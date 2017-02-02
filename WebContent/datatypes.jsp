<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:forEach var="currType" items="${datatypes}">
<div class="row <c:if test="${currType.active==false}">in</c:if>active">
  <div class="col-xs-6">
	    <a href="DataTypeMan?id=${currType.PK}&breadcrumb=${breadcrumb}"><strong> ${currType.symbolBlack}</strong>
		  ${not empty currType.getBestName(pageContext.request) ? currType.getBestName(pageContext.request) : currType.description}</a>
  </div>
  <div class="col-xs-6 text-right"><c:if test="${not empty currType.isaletter}"><strong><big>${currType.isaletter}</big>:${isaletters[currType.isaletter]}</strong></c:if>
	    <strong><c:choose>
	    <c:when test="${currType.presentation==1}"><fmt:message>presentation.1</fmt:message></c:when>
	    <c:when test="${currType.presentation==2}"><fmt:message>presentation.2</fmt:message></c:when>
	    <c:otherwise>${currType.precision ne '1'?currType.precision:''}${currType.unit}</c:otherwise>
	    </c:choose></strong>
  </div>
</div>
<div class="row <c:if test="${currType.active==false}">in</c:if>active">
  <div class="col-xs-12">
<%--
    <span style="display:inline-block;border:solid 1px #666;padding:0px;">
    	<c:set var="sep" value="&nbsp;"/>
	  	<c:forEach var="interv" items="${currType.intervals}"><span style="white-space: nowrap;">${sep} ${interv.minString}:<span class="category-holder" style="background-color:#${interv.webColor};display:inline-block;padding:2px;margin:0px;">
		  			&nbsp;<c:if test="${not empty interv.label}"><strong>${interv.label.getBestName(pageContext.request)}</strong></c:if></span></span><c:set var="sep" value="&lt;"/></c:forEach>${sep} ${currType.maxString}&nbsp;</span>
 --%>
 	<table width="100%">
 	<tr><c:forEach var="interv" items="${currType.intervals}"><th${interv.tableWidthPercent}><c:if test="${not empty interv.label}"><strong>${interv.label.getBestName(pageContext.request)}</strong></c:if>&nbsp;</th>
 	</c:forEach><td> &nbsp; </td></tr>
 	<tr><c:forEach var="interv" items="${currType.intervals}"><th class="category-holder" style="background-color:${interv.colorHex};padding:2px;margin:0px;"> &nbsp; </th>
 	</c:forEach><td> &nbsp; </td></tr>
 	<tr><c:forEach var="interv" items="${currType.intervals}"><td>${interv.minString}${currType.unit}&nbsp; </td>
 	</c:forEach><td>${currType.maxString}${currType.unit}&nbsp;</td></tr>
 	</table>
  </div>
</div>
<div class="row <c:if test="${currType.active==false}">in</c:if>active end-block">
  <div class="col-xs-12 text-right">
	  <%@include file="datatyperel.jsp" %>
	  <c:if test="${userMayUpdate}">
	    <span class="button-spacer"></span>
		<span class="button-holder">
	    <a href="DataTypeMan?id=${currType.PK}&action=interface&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonInterface.jsp" %></a>
	    </span><span class="button-spacer"></span>
		<span class="button-holder">
	    <a href="DataTypeMan?id=${currType.PK}&action=update&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonUpdate.jsp" %></a>
	    </span>
	  </c:if>
  </div>
</div>
</c:forEach>
<c:if test="${userMayCreate}">
	<form class="form-horizontal" action="DataTypeMan" role="form">
	  <fieldset>
	    <input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	    <input type="hidden" name="action" value="create"/>
		<div class="row form-group create-block">
		    <label class="col-xs-2 text-right" for="datatypecreate">
		    	<fmt:message key="name.name"/>
		    </label>
		    <div class="col-xs-6">
			    <input type="text" maxlength="127" class="form-control" id="datatypecreate" name="name"/>
		    	<span class="help-block"><fmt:message key="help.datatype.name"/> ${auxil.currentLanguage}</span>
		    </div>
		    <div class="col-xs-4">
			    <span class="input-group-btn">
			    	<button type="submit" class="btn btn-default"><fmt:message key="action.create"/><fmt:message key="icon.datatype"/><br/><fmt:message key="obj.datatype"/></button>
			    </span>
		    </div>
        </div>
	   </fieldset>
	</form>
</c:if>
<%@ include file="footer.jsp"%>
