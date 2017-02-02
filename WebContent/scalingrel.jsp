<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><c:set var="machines" value="${scaling.machines}"/>
  <c:if test="${fn:length(machines) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.machine"/>"><a href="MachineMan?action=list&scalingid=${scaling.PK}&breadcrumb=${breadcrumb}">${fn:length(machines)}<fmt:message key="icon.machine"/></a></span> &nbsp;
  </c:if>
  <c:set var="datatypes" value="${scaling.datatypes}"/>
  <c:if test="${fn:length(datatypes) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.datatype"/>"><a href="DataTypeMan?action=list&scalingid=${scaling.PK}&breadcrumb=${breadcrumb}">${fn:length(datatypes)}<fmt:message key="icon.datatype"/></a></span> &nbsp;
  </c:if>

