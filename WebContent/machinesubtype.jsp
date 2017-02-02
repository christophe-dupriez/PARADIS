<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<div class="row">
  <div class="col-xs-12">
   <h2> - <fmt:message key="icon.datatype"/>  <fmt:message key="obj.datatype"/> ${datatype.getBestName(pageContext.request)} : ${datatype.description}</h2>
  </div>
</div>
<div class="row">
    <div class="col-xs-10 col-xs-offset-2">
	<a href="MachineMan?id=${machine.PK}&datatypeid=${datatype.PK}&rawfieldid=${rawtablefield.PK}&action=interface&subaction=reinit&breadcrumb=${breadcrumb}"><button type="button" class="btn btn-default"><fmt:message key="subtype.reinit"/></button></a>
	</div>
</div>

<form role="form" method="POST" action="MachineMan" class="form-horizontal without-tabs" id="toBeValidated"
	  data-bv-message="This value is not valid"
	  data-bv-live="enabled"
	  data-bv-container="tooltip"
	  data-bv-submitButtons="#toBeValidatedSubmit">
  <fieldset>
	<input type="hidden" name="action" value="interface"/>
	<input type="hidden" name="subaction" value="save"/>
	<input type="hidden" name="id" value="${machine.PK}"/>
	<input type="hidden" name="rawfieldid" value="${rawtablefield.PK}"/>
	<input type="hidden" name="datatypeid" value="${datatype.PK}"/>
	<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
  <c:set var="noCat" value="1"/>

	<div class="row">
	    <div class="col-xs-10 col-xs-offset-2"><span class="help-block"><fmt:message key="help.datatype.min"/></span></div>
	</div>  
  <c:forEach var="categ" items="${subtype.categories}">
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.timeslot">
    	#${noCat}:
    </label>
    <div class="col-sm-4" style="background-color:${categ.label.colorHex};padding-top:4px;padding-bottom:4px;">
	    <input type="number" step="any" maxlength="31" width="31" id="datatype.min${noCat>1?noCat-1:''}" class="form-control" style="border: solid 1px black;" name="min${noCat>1?noCat-1:''}" value="${categ.min}" separator="."/>
	    <div class="text-center" style="margin:3px;"><span style="font-family:monospace;background-color:white">${categ.label.colorHex} :  ${categ.label.getBestName(pageContext.request)}</span></div>
    </div>
  </div>
    <c:set var="noCat" value="${noCat+1}"/>
  </c:forEach>
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.max">
    	<fmt:message key="datatype.max"/>:
    </label>
    <div class="col-sm-4">
	    <input type="number" step="any" maxlength="31" width="31" id="datatype.max" class="form-control" name="max" value="${subtype.max}" separator="."/>
	    <span class="help-block"><fmt:message key="help.datatype.max"/></span>
    </div>
  </div>
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.active">
    	<fmt:message key="subtype.active"/>
    </label>
    <div class="col-sm-1">
	    <input type="checkbox" class="form-control" id="machine.active" name="active" value="active"${machine.active?' checked':''}/>
    </div>
  </div>
  <div class="row form-group">
    <div class="col-sm-offset-2 col-sm-1">
		<button type="submit" class="btn btn-success" name="action" value="interface" onclick="clickColor(0,-1,-1)" id="toBeValidatedSubmit"><fmt:message key="action.record"/></button>
	</div>
  </div>
</fieldset>
</form>

<div class="row">
    <div class="col-xs-12">
    <dl class="dl-horizontal">
			<dt id="datatype-${datatype.PK}" style='padding-bottom:10px;<c:if test="${datatype.active==false}">background-color:#CCCCCC;</c:if>'>
			    <a href="DataTypeMan?id=${datatype.PK}&breadcrumb=${breadcrumb}"><strong><big>${datatype.isaletter}</big> ${datatype.unit} ${datatype.symbolBlack}</strong>
			    <fmt:message key="icon.datatype"/></a>
			</dt>
			<dd>
			  <c:if test="${userMayUpdate}"> &nbsp; 
			    <a href="DataTypeMan?id=${datatype.PK}&action=update&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonUpdate.jsp" %></a>
			    <a href="DataTypeMan?id=${datatype.PK}&action=interface&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonInterface.jsp" %></a>
			  </c:if>
			  ${datatype.getBestName(pageContext.request)}<c:if test="not empty datatype.description">: ${datatype.description }</c:if><br/>&nbsp;
 			</dd>
			<dt id="machine-${machine.PK}" style='padding-bottom:10px;<c:if test="${machine.active==false}">background-color:#CCCCCC;</c:if>'>
			    <a href="MachineMan?id=${machine.PK}&breadcrumb=${breadcrumb}">
				<strong>${machine.nodeid} / ${machine.hexMachineMAC}</strong>
				<fmt:message key="icon.machine"/></a>
			</dt>
			<dd>
			  <c:if test="${userMayUpdate}"> &nbsp; 
			    <a href="MachineMan?id=${machine.PK}&action=update&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonUpdate.jsp" %></a>
			    <a href="MachineMan?id=${machine.PK}&action=interface&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonInterface.jsp" %></a>
			  </c:if>
			  ${machine.getBestName(pageContext.request)}<c:if test="not empty machine.description">: ${machine.description }</c:if><br/>&nbsp;
 			</dd>
    <c:forEach var="rawscaling" items="${subtype.subtypeScaling}">
		    <dt>${rawscaling.since}</dt>
		    <dd><a href="ScalingMan?action=display&id=${rawscaling.rawScaling.PK}&breadcrumb=${breadcrumb}"><fmt:message key="icon.scaling"/>${rawscaling.rawScaling.description}</a></dd>
    </c:forEach>
	</dl>
	</div>
</div>
<c:if test="${userMayDelete}">
	<form class="form-horizontal" action="MachineMan" role="form">
	  <fieldset>
		<input type="hidden" name="action" value="interface"/>
		<input type="hidden" name="subaction" value="delete"/>
		<input type="hidden" name="id" value="${machine.PK}"/>
		<input type="hidden" name="rawfieldid" value="${rawtablefield.PK}"/>
		<input type="hidden" name="datatypeid" value="${datatype.PK}"/>
		<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	  <div class="row form-group create-block">
	    <label class="col-xs-2 text-right" for="action.delete">
	      <fmt:message key="action.confirm"/>
	    </label>
	    <div class="col-xs-1">
		  <input type="checkbox" class="form-control" name="confirm" value="confirmed" id="action.delete"/>
		</div>
	    <div class="col-xs-4">
	      <span class="input-group-btn">
	        <button type="submit" class="btn btn-danger btn-xs"><fmt:message key="action.delete"/> <strong><fmt:message key="icon.datatype"/>${datatype.getBestName(pageContext.request)} - <fmt:message key="icon.machine"/><br/>${machine.nodeid}</strong></button>
	      </span>
	    </div>
	   </div>	   
	   </fieldset>
	</form>
</c:if>

<%@ include file="footer.jsp"%>
