<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%>
<span class="button-holder">
<c:set var="datatypes" value="${datatype.machines}"/>
  <c:if test="${fn:length(machines) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.machine"/>"><a href="MachineMan?action=list&datatypeid=${datatype.PK}&breadcrumb=${breadcrumb}">${fn:length(datatypes)}<fmt:message key="icon.machine"/></a></span> &nbsp;
  </c:if>
</span><span class="button-spacer"></span>
<span class="button-holder">  <c:set var="graphs" value="${datatype.typeGraph}"/>
  <c:if test="${fn:length(graphs) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.graphconfig"/>"><a href="GraphConfigMan?action=list&datatypeid=${datatype.PK}&breadcrumb=${breadcrumb}">${fn:length(graphs)}<fmt:message key="icon.scaling"/></a></span> &nbsp;
  </c:if>
</span><span class="button-spacer"></span>
<span class="button-holder">  <c:set var="assertions" value="${datatype.typeCondition}"/>
  <c:if test="${fn:length(assertions) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.assertion"/>"><a href="AssertionMan?action=list&datatypeid=${datatype.PK}&breadcrumb=${breadcrumb}">${fn:length(graphs)}<fmt:message key="icon.assertion"/></a></span> &nbsp;
  </c:if>
</span><span class="button-spacer"></span>
<span class="button-holder">
  <c:set var="events" value="${datatype.events}"/>
  <c:if test="${fn:length(events) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.event"/>"><a href="EventMan?action=list&datatypeid=${datatype.PK}&breadcrumb=${breadcrumb}">${fn:length(events)}<fmt:message key="icon.event"/></a></span> &nbsp;
  </c:if>
</span>
<%--public static final String SUBTYPE_PROPERTY = "subtype"--%>

