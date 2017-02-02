<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:set var="currentMachines" value="${datatype.machines}"/>
<c:set var="possibleMachines" value="${datatype.singleBitMachines.removeAll(currentMachines)}"/>
 <div class="row">
   <div class="col-lg-12">
	  <dl class="dl-horizontal">
	    <c:forEach var="machine" items="${currentMachines}">
			<dt id="machine-${machine.PK}" style='padding-bottom:10px;<c:if test="${machine.active==false}">background-color:#CCCCCC;</c:if>'>
			    <a href="MachineMan?id=${machine.PK}&breadcrumb=${breadcrumb}">
				<strong>${machine.nodeid} / ${machine.hexMachineMAC}</strong></a>
				<br/><a href="NetworkMan?id=${machine.machineNetwork.PK}&breadcrumb=${breadcrumb}"><fmt:message key="icon.network"/>${machine.machineNetwork.getBestName(pageContext.request)}</a>
			</dt>
			<dd style='margin-bottom:10px;border-bottom: #666 dotted 1px;'>
			  <c:if test="${userMayUpdate}"> &nbsp; 
			    <a href="MachineMan?id=${machine.PK}&action=update&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonUpdate.jsp" %></a>
			    <a href="MachineMan?id=${machine.PK}&datatypeid=${datatype.PK}&action=interface&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonInterface.jsp" %></a>
			  </c:if>
			  ${machine.getBestName(pageContext.request)} <c:if test="not empty machine.description">: ${machine.description }</c:if><br/>  &nbsp;
			  <%@include file="machinerel.jsp" %>
			  <c:if test="${not empty machine.gsm}">GSM:${machine.gsm}  &nbsp; </c:if>
			  <c:if test="${not empty machine.latitude and not empty machine.longitude}"><br/><a href="https://maps.google.com/maps?q=${machine.latitude},${machine.longitude}" target="_blank">${machine.latLongElev}</a> </c:if>
 			</dd>
		</c:forEach>
		<hr/>
	    <c:forEach var="machine" items="${currentMachines}">
			<dt id="machine-${machine.PK}" style='padding-bottom:10px;<c:if test="${machine.active==false}">background-color:#CCCCCC;</c:if>'>
			    <a href="MachineMan?id=${machine.PK}&breadcrumb=${breadcrumb}">
				<strong>${machine.nodeid} / ${machine.hexMachineMAC}</strong></a>
				<br/><a href="NetworkMan?id=${machine.machineNetwork.PK}&breadcrumb=${breadcrumb}"><fmt:message key="icon.network"/>${machine.machineNetwork.getBestName(pageContext.request)}</a>
			</dt>
			<dd style='margin-bottom:10px;border-bottom: #666 dotted 1px;'>
			  <c:if test="${userMayUpdate}"> &nbsp; 
			    <a href="MachineMan?id=${machine.PK}&action=update&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonUpdate.jsp" %></a>
			    <a href="MachineMan?id=${machine.PK}&datatypeid=${datatype.PK}&action=interface&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonInterface.jsp" %></a>
			  </c:if>
			  ${machine.getBestName(pageContext.request)} <c:if test="not empty machine.description">: ${machine.description }</c:if><br/>  &nbsp;
			  <%@include file="machinerel.jsp" %>
			  <c:if test="${not empty machine.gsm}">GSM:${machine.gsm}  &nbsp; </c:if>
			  <c:if test="${not empty machine.latitude and not empty machine.longitude}"><br/><a href="https://maps.google.com/maps?q=${machine.latitude},${machine.longitude}" target="_blank">${machine.latLongElev}</a> </c:if>
 			</dd>
		</c:forEach>
	  </dl><%--No Assignable Datatypes for last tabs...  --%>
	</div>
  </div>
</div>
<c:if test="${userMayUpdate}"> 
<div class="row">
  <div class="col-lg-12">
	    <a href="DataTypeMan?id=${datatype.PK}&action=update&breadcrumb=${breadcrumb}"><%@include file="inc/buttonUpdate.jsp" %></a>
  </div>
</div>
</c:if>
<%@ include file="footer.jsp"%>
