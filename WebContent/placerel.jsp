<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%>
<c:set var="machines" value="${place.placemachine}"/>
<span class="button-holder">
  <c:if test="${fn:length(machines) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.machine"/>"><a href="MachineMan?action=list&placeid=${place.PK}&breadcrumb=${breadcrumb}">${fn:length(machines)}<fmt:message key="icon.machine"/></a></span> &nbsp;
  </c:if>
</span><span class="button-spacer"></span>
<span class="button-holder">
	<c:if test="${fn:length(place.placesubtype) gt 0}">
  	  <span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.subtype"/>"><a href="SubTypeMan?action=list&placeid=${place.PK}&breadcrumb=${breadcrumb}">${fn:length(place.placesubtype)}<fmt:message key="icon.subtype"/></a></span>
    </c:if>
</span><span class="button-spacer"></span>
<c:set var="machines" value="${place.machines}"/>
<span class="button-holder">
  <c:if test="${fn:length(machines) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.machine"/>"><a href="MachineMan?action=list&placeid=${place.PK}&breadcrumb=${breadcrumb}">${fn:length(machines)}<fmt:message key="icon.machine"/></a></span> &nbsp;
  </c:if>
</span><span class="button-spacer"></span>

<span class="button-holder">
  <c:set var="imagemaps" value="${place.imagemaps}"/>
  <c:if test="${fn:length(imagemaps) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.imagemap"/>"><a href="ImagemapMan?action=list&placeid=${place.PK}&breadcrumb=${breadcrumb}">${fn:length(imagemaps)}<fmt:message key="icon.imagemap"/></a></span> &nbsp;
  </c:if>
</span><span class="button-spacer"></span>
<span class="button-holder">
  <c:set var="assertions" value="${place.assertions}"/>
  <c:if test="${fn:length(assertions) gt 0}">
  	<span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.assertion"/>"><a href="AssertionMan?action=list&placeid=${place.PK}&breadcrumb=${breadcrumb}">${fn:length(assertions)}<fmt:message key="icon.assertion"/></a></span> &nbsp;
  </c:if>
</span>
