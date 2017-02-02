<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<div class="row active">
  <div class="col-sm-12">
      <dl class="dl-horizontal">
		<c:forEach var="scaling" items="${scalings}">
			<dt id="scaling-${scaling.PK}" style='padding-bottom:10px;'>
			    <a href="ScalingMan?id=${scaling.PK}&breadcrumb=${breadcrumb}"><strong>${scaling.unit}</strong></a>
			</dt>
			<dd style='margin-bottom:10px;border-bottom: #666 dotted 1px;'>
			  <c:if test="${userMayUpdate}"> &nbsp; 
			    <a href="ScalingMan?id=${scaling.PK}&action=update&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonUpdate.jsp" %></a>
			    <a href="ScalingMan?id=${scaling.PK}&action=interface&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonInterface.jsp" %></a>
			  </c:if>
			  ${scaling.description }<br/>  &nbsp;
			  <%@include file="scalingrel.jsp" %>
 			</dd>
		</c:forEach>
	 </dl>
  </div>
</div>
<c:if test="${userMayCreate}">
	<form class="form-horizontal" action="ScalingMan" role="form">
	  <fieldset>
	    <input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	    <input type="hidden" name="action" value="create"/>
		<div class="row form-group create-block">
		    <label class="col-xs-2 text-right" for="scalingunit">
		    	<fmt:message key="scaling.unit"/>
		    	<span class="help-block"><fmt:message key="help.scaling.unit"/></span>
		    </label>
		    <div class="col-xs-2 input-group">
			    <input type="text" maxlength="6" class="form-control" id="scalingunit" name="scalingunit"/>
			    <span class="input-group-btn">
			    	<button type="submit" class="btn btn-default"><fmt:message key="action.create"/> <fmt:message key="icon.scaling"/> <fmt:message key="obj.scaling"/></button>
			    </span>
		    </div>
        </div>
	   </fieldset>
	</form>
</c:if>
<%@ include file="footer.jsp"%>
