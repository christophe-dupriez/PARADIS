<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<c:if test="${not empty readonly}"><div class="info-block"></c:if>
<c:if test="${empty readonly}">
<form role="form" method="POST" action="MachineMan" class="form-horizontal" id="toBeValidated"
	  data-bv-message="This value is not valid"
	  data-bv-live="enabled"
	  data-bv-container="tooltip"
	  data-bv-submitButtons="#toBeValidatedSubmit">
  <fieldset>
	<input type="hidden" name="action" value="update"/>
	<input type="hidden" name="subaction" value="save"/>
	<input type="hidden" name="id" value="${machine.PK}"/>
	<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	<input type="hidden" name="currenttab" id="currentTab" value=""/>
</c:if>	
	<!-- Nav tabs -->
<ul class="nav nav-tabs">
  <li${empty currentTab or currentTab=='#profile'?' class="active"':''}><a href="#profile" data-toggle="tab"><fmt:message key="tab.profile"/></a></li>
  <li${currentTab=='#network'?' class="active"':''}><a href="#network" data-toggle="tab"><fmt:message key="tab.network"/></a></li>
</ul>

<!-- Tab panes -->
<div class="tab-content">
  <div class="tab-pane${empty currentTab or currentTab=='#profile'?' active':''}" id="profile">
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.nodeid">
    	<fmt:message key="machine.nodeid"/>
    </label>
    <div class="col-sm-10">
    	<input${readonly} type="text" pattern="^[0-9a-zA-Z-]{1,20}$" maxlength="20" class="form-control" id="machine.nodeid" name="nodeid" value="${fn:escapeXml(machine.nodeid)}" required/>
		<c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.machine.nodeid"/></span></c:if>
    </div>
  </div>
<c:forEach var="lang" items="${supportedLocales}">
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.nodeid">
		  	<fmt:message key="name.name"/> ${lang}
    </label>
    <div class="col-sm-10">
		  	<input${readonly} type="text" class="form-control" name="name_${lang}" value="${fn:escapeXml(machine.namesMap[lang])}" maxlength="63" width="63"/>
  	</div>
  </div>
</c:forEach>
<div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.description">
	    <fmt:message key="machine.description"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="text" maxlength="127" id="machine.description" class="form-control" name="description" value="${fn:escapeXml(machine.description)}" width="63"/>
		<c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.machine.description"/></span></c:if>
    </div>
  </div>
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.gsm">
	    <fmt:message key="machine.gsm"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="tel" class="form-control" name="gsm" id="machine.gsm" value="${fn:escapeXml(machine.gsm)}" maxlength="31" width="31"/>
		<c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.machine.gsm"/></span></c:if>
    </div>
  </div>
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.active">
    	<fmt:message key="machine.active"/>
    </label>
    <div class="col-sm-2">
	    <input${readonly} type="checkbox" class="form-control" id="machine.active" name="active" value="active"${machine.active?' checked':''}/>
    </div>
  </div>
  </div>
  <div class="tab-pane${currentTab=='#network'?' active':''}" id="network">
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.network">
    	<fmt:message key="icon.network"/><fmt:message key="obj.network"/>:
    </label>
    <div class="col-sm-10">
    	<select${readonly} class="form-control" name="networkid" id="machine.network" required>
    	<c:forEach var="network" items="${auxil.allNetworks}">
    		<option value="${network.PK}" <c:if test="${network==machine.machineNetwork }">selected</c:if>>${network.getBestName(pageContext.request)}</option>
   		</c:forEach>
    	</select>
    </div>
  </div>
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.place">
    	<fmt:message key="icon.place"/><fmt:message key="obj.place"/>:
    </label>
    <div class="col-sm-10">
    	<select${readonly} class="form-control" name="placeid" id="machine.place" required>
    	<c:forEach var="place" items="${auxil.allPlaces}">
    		<option value="${place.PK}" <c:if test="${place==machine.machinePlace }">selected</c:if>>${place.getBestName(pageContext.request)}</option>
   		</c:forEach>
    	</select>
    </div>
  </div>
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.timeslot">
    	<fmt:message key="machine.timeslot"/>:
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="number" min="0" max="999" step="1" size="9" maxlength="4" id="machine.timeslot" class="form-control" name="timeslot" value="${machine.timeslot}"/>
	    <c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.machine.timeslot"/></span></c:if>
    </div>
  </div>
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.mac">
    	<fmt:message key="machine.machinemac"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="text" pattern="[0-9a-fA-F]{6}" size="9" maxlength="6" id="machine.mac" class="form-control" name="machinemac" value="${machine.hexMachineMAC}" required data-bv-regexp-message="Must start with 'a' and end with 'z'"/>
	    <c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.machine.machinemac"/></span></c:if>
    </div>
  </div>
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="machine.devicetype">
    	<fmt:message key="machine.devicetype"/>
    </label>
    <div class="col-sm-10">
    	<select${readonly} class="form-control" name="devicetype" id="machine.machineDeviceType" required>
    	<c:forEach var="deviceType" items="${auxil.allDeviceTypes}">
    		<option value="${deviceType.PK}" <c:if test="${deviceType==machine.machineDeviceType }">selected</c:if>>${deviceType.description}</option>
   		</c:forEach>
    	</select>
    </div>
  </div>
  <div class="row form-group">
    <label class="col-xs-2 text-right" for="machine.rawtable">
    	<fmt:message key="obj.rawtable"/>:
    </label>
    <div class="col-xs-10">
    	<select${readonly} class="form-control" name="rawtableid" id="machine.rawtable" required>
    	<c:forEach var="rawtable" items="${auxil.allRawTables}">
    		<option value="${rawtable.rawtype}" <c:if test="${rawtable==machine.machineRawTable}">selected</c:if>>${rawtable.rawtype} - ${rawtable.description}</option>
   		</c:forEach>
    	</select>
    </div>
  </div>
  </div>
</div>
<c:if test="${empty readonly}">
  <div class="row form-group">
    <div class="col-sm-offset-2 col-sm-10">
		<button type="submit" class="btn btn-success" name="action" value="update" id="toBeValidatedSubmit"><fmt:message key="action.record"/></button>
	</div>
  </div>
</fieldset>
</form>
</c:if>
<c:if test="${not empty readonly}"></div></c:if>
<div class="row buttons-list">
  <div class="col-sm-12 text-right">
  <%@include file="machinerel.jsp" %>
  <span class="button-spacer">&nbsp;</span>
  <span class="button-holder">
	  <c:if test="${userMayUpdate}">
	    <a href="MachineMan?id=${machine.PK}&action=interface&breadcrumb=${breadcrumb}"><%@include file="inc/buttonInterface.jsp" %></a>
	  </c:if>
  </span><span class="button-spacer">&nbsp;</span>
  <span class="button-holder">
	  <c:if test="${userMayUpdate and not empty readonly}">
	    <a href="MachineMan?id=${machine.PK}&action=update&breadcrumb=${breadcrumb}"><%@include file="inc/buttonUpdate.jsp" %></a>
	  </c:if>
  </span>
  </div>
</div>

<c:if test="${userMayDelete}">
	<form class="form-horizontal" action="MachineMan" role="form">
	  <fieldset>
	    <input type="hidden" name="action" value="delete"/>
	    <input type="hidden" name="id" value="${machine.PK}"/>
	    <input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	  <div class="row form-group create-block">
	    <label class="col-xs-2 text-right" for="action.delete"><fmt:message key="action.confirm"/></label>
	    <div class="col-xs-1">
		  <input type="checkbox" class="form-control" name="confirm" value="confirmed" id="action.delete"/>
		</div>
	    <div class="col-xs-9">
	      <span class="input-group-btn"><button type="submit" class="btn btn-danger btn-xs"><fmt:message key="action.delete"/><strong><fmt:message key="icon.machine"/><br/>${machine.hexMachineMAC} - ${machine.nodeid}</strong></button></span>
	    </div>
	   </div>	   
	   </fieldset>
	</form>
</div>
</c:if>
<%@ include file="footer.jsp"%>
