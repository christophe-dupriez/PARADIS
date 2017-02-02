<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:if test="${userMayUpdate}"> 
<form role="form" method="POST" action="MachineMan" class="form-horizontal" id="toBeValidated"
	  data-bv-message="This value is not valid"
	  data-bv-live="enabled"
	  data-bv-container="tooltip"
	  data-bv-submitButtons="#toBeValidatedSubmit">
  <fieldset>
	<input type="hidden" name="action" value="interface"/>
	<input type="hidden" name="subaction" value="save"/>
	<input type="hidden" name="id" value="${machine.PK}"/>
	<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
</c:if>
	<!-- Nav tabs -->
<ul class="nav nav-tabs">
  <li<c:if test="${(empty selTab or (selTab == '1'))}"> class="active"</c:if>><a href="#1" data-toggle="tab"><fmt:message key="rawtab.1"/></a></li>
  <li<c:if test="${selTab == '2'}"> class="active"</c:if>><a href="#2" data-toggle="tab"><fmt:message key="rawtab.2"/></a></li>
  <li<c:if test="${selTab == '6'}"> class="active"</c:if>><a href="#6" data-toggle="tab"><fmt:message key="rawtab.6"/></a></li>
  <li<c:if test="${selTab == '7'}"> class="active"</c:if>><a href="#7" data-toggle="tab"><fmt:message key="rawtab.7"/></a></li>
  <li<c:if test="${selTab == '8'}"> class="active"</c:if>><a href="#8" data-toggle="tab"><fmt:message key="rawtab.8"/></a></li>
  <li<c:if test="${selTab == '9'}"> class="active"</c:if>><a href="#9" data-toggle="tab"><fmt:message key="rawtab.9"/></a></li>
</ul>
<!-- Tab panes -->
<c:set var="curTab" value=""/>
<div class="tab-content">
	    <c:forEach var="tablefield" items="${rawtablefields}">
	    <c:if test="${not (tablefield.tableField.inheader==curTab)}">
	      <c:if test="${not empty curTab}">
				</dl>
			  </div>
			</div>
			<c:if test="${not isFixed}">
			  <c:if test="${userMayUpdate and not empty notUsed}">
		        <div class="row">
		          <div class="col-lg-12">
			        <dl class="dl-horizontal">
			    <c:forEach var="datatype" items="${notUsed}">
			    	<dt><big>${datatype.isaletter}</big> ${datatype.unit} ${datatype.symbolBlack}</dt><dd><input type="radio" name="datatypeid" value="${datatype.PK}"/></dd>
				</c:forEach>
					</dl>
				  </div>
				</div>
			  </c:if>
			</c:if>
		  </div>
	      </c:if>
		  <div class="tab-pane<c:if test="${(empty curTab and empty selTab) or (selTab == tablefield.tableField.inheader)}"> active</c:if>" id="${tablefield.tableField.inheader}">
		      <div class="row">
		        <div class="col-lg-12">
			    <dl class="dl-horizontal">
		      <c:set var="curTab" value="${tablefield.tableField.inheader}"/>
		      <c:set var="isFixed" value="${tablefield.tableField.fixedDataType}"/>
		      <c:set var="notUsed" value="${auxil.findDataTypesByHeader(curTab)}"/>
	    </c:if>
	    <dt>${tablefield.tableField.description}</dt><dd>${tablefield.tableField.precision}bits 
	    	<c:set var="someSub" value=""/>
		    <c:forEach var="subtype" items="${tablefield.fieldSubtype}">
		    	<c:if test="${subtype.subtypeMachine==machine}">
			    	<c:set var="someSub" value="${subtype}"/>
		    		<c:choose>
		    		<c:when test="${subtype.active and subtype.owner.active}"><fmt:message key="icon.active"/></c:when>
		    		<c:otherwise>&nbsp;</c:otherwise>
		    		</c:choose> 
		    		${subtype.owner.getBestName(pageContext.request)}
		    		<big>${subtype.owner.isaletter}</big> ${subtype.owner.unit}  (${subtype.owner.nbcategories} <fmt:message key="datatype.categories"/>)
		    		${subtype.owner.description}
			    	<a href="MachineMan?action=interface&id=${machine.PK}&rawfieldid=${tablefield.PK}&datatypeid=${subtype.owner.PK}&breadcrumb=${breadcrumb}">
			    	<fmt:message key="action.update"/></a>
			    	<c:set var="wasThere" value="${auxil.removeAnItem(notUsed,subtype.owner)}"/>
		    	</c:if>
		    </c:forEach>
		    <c:if test="${empty someSub}">
		    	<button type="submit" name="rawfieldid" value="${tablefield.PK}"><fmt:message key="action.create"/></button>
		    </c:if>
	    </dd>
	    </c:forEach>
		</dl><%--No Assignable Datatypes for last tabs...  --%>
	  </div>
	</div>
  </div>
</div>
<c:if test="${userMayUpdate}"> 
</fieldset>
</form>
<div class="row">
  <div class="col-lg-12">
	    <a href="MachineMan?id=${machine.PK}&action=update&breadcrumb=${breadcrumb}"><%@include file="inc/buttonUpdate.jsp" %></a>
  </div>
</div>
</c:if>
<%@ include file="footer.jsp"%>
