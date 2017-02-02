<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:forEach var="network" items="${networks}">
<div class="row active">
  <div class="col-xs-8"><strong>${network.getBestName(pageContext.request)}</strong></div>
  <div class="col-xs-2">
	<c:if test="${fn:length(network.networkMachine) gt 0}">
  	  <span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="objs.machine"/>"><a href="MachineMan?action=list&networkid=${network.PK}&breadcrumb=${breadcrumb}">${fn:length(network.networkMachine)}<fmt:message key="icon.machine"/></a></span>
    </c:if>	
  </div>
  <div class="col-xs-2">
  <c:if test="${userMayUpdate}"> 
    <a href="NetworkMan?id=${network.PK}&action=update&breadcrumb=${breadcrumb}"><%@include file="inc/buttonUpdate.jsp" %></a>
  </c:if>
  </div>
</div>
<div class="row active end-block">
  <div class="col-xs-offset-2 col-xs-10">
	  <table class="table table-condensed table-striped">
	   <tr><td><fmt:message key="network.basetempo"/></td><th>${network.basetempo }</th></tr>
	   <tr><td><fmt:message key="network.beatgsm"/></td><th>${network.beatgsm }</th></tr>
	   <tr><td><fmt:message key="network.beatmeasure"/></td><th>${network.beatmeasure }</th></tr>
	   <tr><td><fmt:message key="network.sleeptime"/></td><th>${network.sleeptime }</th></tr>
	   <tr><td><fmt:message key="network.timeslice"/></td><th>${network.timeslice }</th></tr>
	   <tr><td><fmt:message key="network.warmup"/></td><th>${network.warmup }ms</th></tr>
	  </table>
  </div>
</div>
</c:forEach>
<c:if test="${userMayCreate}">
<form class="form-horizontal" action="NetworkMan" role="form">
  <fieldset>
    <input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
    <input type="hidden" name="action" value="create"/>
	<div class="row form-group create-block">
    	<label class="col-xs-2 text-right" for="networkname"><fmt:message key="name.name"/> ${auxil.currentLanguage}</label>
	    <div class="col-xs-6">
		    <input type="text" maxlength="127" class="form-control" id="networkname" name="name"/>
		</div>
		<div class="col-xs-4">
		    <span class="input-group-btn">
		    	<button type="submit" class="btn btn-default"><fmt:message key="action.create"/> <fmt:message key="icon.network"/> <fmt:message key="obj.network"/></button>
		    </span>
	    </div>
 	 </div>
   </fieldset>
</form>
</c:if>
<%@ include file="footer.jsp"%>
<%--
network.basetempo   = basic network tempo
network.beatgsm     = beats between GPRS messages
network.beatmeasure = beats between measures
network.sleeptime   = sleep time (ms, DigiMesh only)
network.timeslice   = time slice for each module (ms)
network.warmup      = warm-up time (ms)
 --%>