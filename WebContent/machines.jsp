<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:set var="precNet" value=""/>
<c:forEach var="machine" items="${machines}">
<c:if test="${machine.machineNetwork.PK != precNet}">
	<div class="row section">
	  <div class="col-sm-12">
	  <a href="NetworkMan?id=${machine.machineNetwork.PK}&breadcrumb=${breadcrumb}"><fmt:message key="icon.network"/> <strong>${machine.machineNetwork.getBestName(pageContext.request)}</strong></a>
	  </div>
	</div>
	<c:set var="precNet" value="${machine.machineNetwork.PK}"/>
</c:if>
<div class="row <c:if test="${machine.active==false}">in</c:if>active">
  <div class="col-xs-2">
    <a href="MachineMan?id=${machine.PK}&breadcrumb=${breadcrumb}">
		<strong>${machine.hexMachineMAC}</strong><%-- - <strong>${machine.nodeid}</strong>--%>
	</a>
	<c:if test="${not empty machine.gsm}"><div style="display:inline;font-size:66%;line-height:70%"><%--<fmt:message key="icon.gsm"/> --%><a href="tel:${machine.gsm}">${machine.gsm}</a></div></c:if>
  </div>
  <div class="col-xs-1">A
  </div>
  <div class="col-xs-4">
	  <strong>${machine.getBestName(pageContext.request)}</strong>
	  <c:if test="not empty machine.description"><br/>${machine.description }</c:if>
  </div>
  <div class="col-xs-5">
	  <c:set var="place" value="${machine.machinePlace}"/>
	  <c:if test="${not empty place}"> <a href="PlaceMan?id=${place.PK}"><fmt:message key="icon.place"/>${place.getBestName(pageContext.request)}</a></c:if>
  </div>
</div>
<div class="row <c:if test="${machine.active==false}">in</c:if>active end-block">
  <div class="col-xs-12 text-right">
  <%@include file="machinerel.jsp" %>
  <span class="button-spacer">&nbsp;</span>
  <span class="button-holder">
	  <c:if test="${userMayUpdate}">
	    <a href="MachineMan?id=${machine.PK}&action=interface&breadcrumb=${breadcrumb}"><%@include file="inc/buttonInterface.jsp" %></a>
	  </c:if>
  </span><span class="button-spacer">&nbsp;</span>
  <span class="button-holder">
	  <c:if test="${userMayUpdate}">
	    <a href="MachineMan?id=${machine.PK}&action=update&breadcrumb=${breadcrumb}"><%@include file="inc/buttonUpdate.jsp" %></a>
	  </c:if>
  </span>
  </div>
</div>
</c:forEach>
<c:if test="${userMayCreate}">
<form class="form-horizontal" action="MachineMan" role="form">
  <fieldset>
    <input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
    <input type="hidden" name="action" value="create"/>
    <c:if test="not empty network">
    	<input type="hidden" name="networkid" value="${network.PK}"/>
    </c:if>
	<div class="row create-block">
	    <div class="col-xs-12">
		    <h3><fmt:message key="action.create"/> <fmt:message key="icon.machine"/> <fmt:message key="obj.machine"/></h3>
		</div>
    </div>
	<div class="row create-block">
	    <label class="col-xs-2" for="machinemac">
	    	<fmt:message key="machine.machinemac"/>
	    </label>
	    <div class="col-xs-10">
			<div class="input-group">
			    <input type="text" <%--maxlength="6"--%> class="form-control" id="machinemac" name="machinemac"/>
			    <span class="input-group-btn">
			    <button type="submit" class="btn btn-success"><fmt:message key="action.create"/></button>
			    </span>
	    	</div>
	    	<span class="help-block text-center"><fmt:message key="help.machine.machinemac"/></span>
	    </div>
 	 </div>
   </fieldset>
</form>
</c:if>
<%@ include file="footer.jsp"%>
