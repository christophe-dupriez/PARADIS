<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%>
<c:set var="datatypes" value="${machine.datatypes}"/>
<span class="button-holder">
  <c:if test="${fn:length(datatypes) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.datatype"/>"><a href="DataTypeMan?action=list&machineid=${machine.PK}&breadcrumb=${breadcrumb}">${fn:length(datatypes)}<fmt:message key="icon.datatype"/></a></span> &nbsp;
  </c:if>
</span><span class="button-spacer"></span>
<%--
<span class="button-holder">
  <c:set var="scalings" value="${machine.scalings}"/>
  <c:if test="${fn:length(scalings) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.scaling"/>"><a href="ScalingMan?action=list&machineid=${machine.PK}&breadcrumb=${breadcrumb}">${fn:length(scalings)}<fmt:message key="icon.scaling"/></a></span> &nbsp;
  </c:if></span><span class="button-spacer"></span>--%>
<span class="button-holder">
  <c:set var="places" value="${machine.places}"/>
  <c:if test="${fn:length(places) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.place"/>"><a href="PlaceMan?action=list&machineid=${machine.PK}&breadcrumb=${breadcrumb}">${fn:length(places)}<fmt:message key="icon.place"/></a></span> &nbsp;
  </c:if>
</span>
