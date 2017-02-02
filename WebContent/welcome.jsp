<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
      <div class="row">
        <div class="col-sm-12">
        <c:if test="${not empty requestScope['javax.servlet.error.status_code']}">
	      	<h2>Error ${requestScope['javax.servlet.error.status_code']}:${requestScope['javax.servlet.error.message']}</h2>
      	</c:if>	
      </div>
      <div class="row">
        <div class="col-sm-12">
          <h2>Dernières alertes:</h2>
		<div class="table-responsive">
		  <table class="table">
          <c:forEach var="measure" items="${lastAlerts}">
          <tr><td>${measure.whenmeasured }</td>
              <th><a href="MachineUpdate?action=list&id=${measure.measureMachine.PK }#machine-${measure.measureMachine.PK }">${measure.measureMachine.nodeid }</a></th>
              <th><a href="TypeUpdate?action=list&id=${measure.measureType.PK }#type-${measure.measureType.PK}">${measure.measureType.name }</a></th>
              <td>${measure.rule.min} &lt;</td><th>${measure.value } ${measure.measureType.unit }</th><td>&lt; ${measure.rule.max}</td></tr>
          </c:forEach>
          </table>
        </div>
      </div>
      </div>
    <c:if test="${not empty places and not empty types }">
      <div class="row-responsive">
        <div class="col-lg-12">
          <h2>Mesures:</h2>
          <form method="GET" action="MeasureUpdate">
           <input type="hidden" name="from" value="welcome"/>
           <select name="place"><c:forEach var="place" items="${places }">
           <option value="${place.PK }"<c:if test="${place.PK==1234567}"> selected</c:if>>${place.getBestName(pageContext.request)}</option>
           </c:forEach></select>
<%--
           <select name="type"><c:forEach var="type" items="${types }">
           <option value="${type.PK }">${type.name }</option>
           </c:forEach></select>
           --%>
	       <button type="submit" class="btn btn-default btn-sm" name="action" value="list">Lister</button>
        </form>
      </div>
      </div>      
    </c:if>
<%@ include file="footer.jsp"%>
