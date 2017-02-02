<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:if test="${not empty readonly}"><div class="info-block without-tabs"></c:if>
<c:if test="${empty readonly}">
<form role="form" method="POST" action="NetworkMan" class="form-horizontal without-tabs">
  <fieldset>
	<input type="hidden" name="action" value="update"/>
	<input type="hidden" name="subaction" value="save"/>
	<input type="hidden" name="id" value="${network.PK}"/>
	<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
</c:if>
	<c:forEach var="lang" items="${supportedLocales}">
	<div class="row form-group">
	    <label class="col-xs-2 text-right" for="name_${lang}"><fmt:message key="name.name" /> ${lang}</label>
	    <div class="col-xs-10">
			<input${readonly} type="text" class="form-control" name="name_${lang}" id="name_${lang}"
				value="${fn:escapeXml(network.namesMap[lang])}" maxlength="63"
				width="63" />
		</div>
	</div>
	</c:forEach>
  <%--<div class="form-group">
    <label class="col-xs-2 text-right" for="network.description">
	    <fmt:message key="network.description"/>
    </label>
    <div class="col-xs-10">
	    <input${readonly} type="text" maxlength="127" id="network.description" class="form-control" name="description" value="${fn:escapeXml(network.description)}" width="63"/>
    </div>
  </div> --%>
  <div class="row form-group">
  	<div class="col-xs-12">
	  <table class="table table-condensed table-striped">
		   <tr><td><fmt:message key="network.basetempo"/></td><th>${network.basetempo }</th></tr>
		   <tr><td><fmt:message key="network.beatgsm"/></td><th>${network.beatgsm }</th></tr>
		   <tr><td><fmt:message key="network.beatmeasure"/></td><th>${network.beatmeasure }</th></tr>
		   <tr><td><fmt:message key="network.sleeptime"/></td><th>${network.sleeptime }</th></tr>
		   <tr><td><fmt:message key="network.timeslice"/></td><th>${network.timeslice }</th></tr>
		   <tr><td><fmt:message key="network.warmup"/></td><th>${network.warmup }</th></tr>
	  </table>
  	</div>
  </div>
<c:if test="${empty readonly}">
    <div class="row form-group">
      <div class="col-xs-offset-2 col-xs-1">
		<button type="submit" class="btn btn-success" name="action" value="interface" id="toBeValidatedSubmit"><fmt:message key="action.record"/></button>
	  </div>
    </div>
</fieldset>
</form>
</c:if>
<c:if test="${not empty readonly}"></div></c:if>
<c:if test="${userMayDelete}">
<form class="form-horizontal" action="NetworkMan" role="form">
  <fieldset>
		<input type="hidden" name="action" value="delete"/>
		<input type="hidden" name="id" value="${network.PK}"/>
		<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	  <div class="row form-group create-block">
	    <label class="col-xs-2 text-right" for="action.delete"><fmt:message key="action.confirm"/></label>
	    <div class="col-xs-1">
		  <input type="checkbox" class="form-control" name="confirm" value="confirmed" id="action.delete"/>
		</div>
	    <div class="col-xs-4">
	      <span class="input-group-btn"><button type="submit" class="btn btn-danger btn-xs"><fmt:message key="action.delete"/><strong><fmt:message key="icon.network"/>${network.getBestName(pageContext.request)}</strong></button></span>
	    </div>
	   </div>	   
   </fieldset>
</form>
</c:if>
<%@ include file="footer.jsp"%>
