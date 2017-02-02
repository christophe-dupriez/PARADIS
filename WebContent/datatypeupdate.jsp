<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>

<c:if test="${not empty readonly}"><div class="info-block without-tabs"></c:if>

<c:if test="${empty readonly}">
<form role="form" method="POST" action="DataTypeMan" class="form-horizontal without-tabs">
  <fieldset>
	<input type="hidden" name="action" value="update"/>
	<input type="hidden" name="currentTab" value="update"/>
	<input type="hidden" name="subaction" value="save"/>
	<input type="hidden" name="id" value="${datatype.PK}"/>
	<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	<input type="hidden" name="currenttab" id="currentTab" value="#categories"/>
</c:if>
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.presentation">
    	<fmt:message key="datatype.presentation"/>
    </label>
    <div class="col-sm-10">
    	<select${readonly} class="form-control" name="presentation" id="datatype.presentation">
	    	<c:forEach var="symb" items="${presentations.entrySet()}">
	    		<option value="${symb.key}" <c:if test="${datatype.presentation==symb.key}">selected</c:if>>${symb.value}</option>
	   		</c:forEach>
    	</select>
	</div>
   </div>
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.nbcategories">
    	<fmt:message key="datatype.nbcategories"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="number" maxlength="1" width="1" id="datatype.nbcategories" class="form-control" name="nbcategories" value="${datatype.nbcategories}"/>
	</div>
   </div>
<c:if test="${empty readonly}">
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.header">
    	<fmt:message key="datatype.header"/>
    </label>
    <div class="col-sm-2">
	    <button type="submit" class="btn btn-success form-control" id="datatype.header" name="action" value="update"><fmt:message key="action.update"/></button>
	</div>
   </div>
</fieldset>
</form>
</c:if>

<c:if test="${not empty readonly}"></div><br/><div class="info-block"></c:if>

<c:if test="${empty readonly}">
<br/>
<form role="form" method="POST" action="DataTypeMan" class="form-horizontal" id="toBeValidated"
	  data-bv-message="This value is not valid"
	  data-bv-live="enabled"
	  data-bv-container="tooltip"
	  data-bv-submitButtons="#toBeValidatedSubmit">
  <fieldset>
	<input type="hidden" name="action" value="update"/>
	<input type="hidden" name="subaction" value="save"/>
	<input type="hidden" name="id" value="${datatype.PK}"/>
	<input type="hidden" name="breadcrumb" value="${breadcrumb}"/>
	<input type="hidden" name="currenttab" id="currentTab" value=""/>
</c:if>
	<!-- Nav tabs -->
<ul class="nav nav-tabs">
  <li${empty currentTab or currentTab=='#profile'?' class="active"':''}><a href="#profile" data-toggle="tab"><fmt:message key="tab.profile"/></a></li>
  <li${currentTab=='#categories'?' class="active"':''}><a href="#categories" data-toggle="tab"><fmt:message key="tab.categories"/></a></li>
  <li${currentTab=='#graphs'?' class="active"':''}><a href="#graphs" data-toggle="tab"><fmt:message key="tab.graphs"/></a></li>
</ul>
<!-- Tab panes -->
<div class="tab-content">
  <div class="tab-pane${empty currentTab or currentTab=='#profile'?' active':''}" id="profile">
  	<c:forEach var="lang" items="${supportedLocales}">
	  <div class="row form-group">
		  	<label class="col-sm-2 text-right" for="${lang}"><fmt:message key="name.name"/> ${lang}</label>
		    <div class="col-sm-10">
		  	    <input${readonly} type="text" class="form-control" name="name_${lang}" value="${fn:escapeXml(datatype.namesMap[lang])}" maxlength="63" width="63"/>
		  	</div>
	  </div>
  	</c:forEach>
<div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.isaletter">
    	<fmt:message key="datatype.isaletter"/>
    </label>
    <div class="col-sm-10">
    	<select${readonly} class="form-control" name="isaletter" id="datatype.isaletter">
    		<option value=" "> - - - </option>
	    	<c:forEach var="symb" items="${isaletters.entrySet()}">
	    		<option value="${symb.key}" <c:if test="${datatype.isaletter==symb.key}">selected</c:if>>${symb.key}-${symb.value}</option>
	   		</c:forEach>
    	</select>
    </div>
  </div>
  <c:if test="${empty datatype.presentation or datatype.presentation == 0}">
    <div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.unit">
	    <fmt:message key="datatype.unit"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="text" maxlength="20" id="datatype.unit" class="form-control" name="unit" value="${fn:escapeXml(datatype.unit)}" width="20"/>
		<c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.datatype.unit"/></span></c:if>
    </div>
  </div>
  </c:if>
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.description">
	    <fmt:message key="datatype.description"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="text" maxlength="127" id="datatype.description" class="form-control" name="description" value="${fn:escapeXml(datatype.description)}" width="63"/>
		<c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.datatype.description"/></span></c:if>
    </div>
  </div>
  </div>

  <div class="tab-pane${currentTab=='#categories'?' active':''}" id="categories">
  <c:set var="noCat" value="1"/>
  <c:forEach var="categ" items="${datatype.intervals}">
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.min${noCat>1?noCat-1:''}">
    	#${noCat}:
    </label>
    <div class="col-sm-10" style="background-color:${categ.label.colorHex};padding-top:4px;padding-bottom:4px;">
	    <input${readonly} type="number" step="any" maxlength="31" width="31" id="datatype.min${noCat>1?noCat-1:''}" class="form-control" name="min${noCat>1?noCat-1:''}" value="${categ.min}" separator="."/>
	    <select${readonly} name="categ${noCat-1}" style="font-family:monospace;">
	    <c:forEach var="aLabel" items="${auxil.allCategories}">
	    	<option value="${aLabel.PK}"<c:if test="${categ.label.PK==aLabel.PK}"> selected</c:if> style="background-color:${aLabel.colorHex}">
	    		${aLabel.colorHex} :  ${aLabel.getBestName(pageContext.request)}
	    	</option>
	    </c:forEach>
	    </select> 
	</div>
    <%--<div class="col-sm-2 " id="colorPick${noCat-1}">
		<input${readonly} type="text" pattern="\#[0-9a-fA-F]{6}" class="form-control" name="color${noCat-1}" id="colorhex${noCat-1}" value='#${categ.webColor}' maxlength="7" width="7" onfocus="showPicker(${noCat-1})" onblurre="hidePicker(${noCat-1})"/>
    </div> --%>
  </div>
    <c:set var="noCat" value="${noCat+1}"/>
  </c:forEach>
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.max">
    	<fmt:message key="datatype.max"/>:
    </label>
    <div class="col-sm-2">
	    <input${readonly} type="number" step="any" maxlength="31" width="31" id="datatype.max" class="form-control" name="max" value="${datatype.max}" separator="."/>
	    <c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.datatype.max"/></span></c:if>
    </div>
  </div>  
  </div>
  
  <div class="tab-pane${currentTab=='#graphs'?' active':''}" id="graphs">
  <div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.symbol">
    	<fmt:message key="datatype.symbol"/>
    </label>
    <div class="col-sm-10">
    	<select${readonly} class="form-control" name="symbol" id="datatype.symbol">
    	<c:forEach var="symb" items="${symbols}">
    		<option value="${symb.ordinal}" <c:if test="${datatype.symbol==symb.ordinal}">selected</c:if>>${symb} ${symb.whiteEntity} ${symb.blackEntity}</option>
   		</c:forEach>
    	</select>
    </div>
  </div>
  <c:if test="${empty datatype.presentation or datatype.presentation == 0}">
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.decimals">
    	<fmt:message key="datatype.decimals"/>
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="number" maxlength="1" width="1" id="datatype.decimals" class="form-control" name="decimals" value="${datatype.decimals}"/>
	</div>
  </div>
  </c:if>
   <div class="row form-group">
    <label class="col-sm-2 text-right" for="datatype.defaulttimespan">
    	<fmt:message key="datatype.defaulttimespan"/>:
    </label>
    <div class="col-sm-10">
	    <input${readonly} type="text" pattern="[0-9]*\.?[0-9:]*" maxlength="15" width="15" id="datatype.defaulttimespan" class="form-control" name="defaulttimespan" value="${datatype.defaultSeconds}"/>
	    <c:if test="${empty readonly}"><span class="help-block"><fmt:message key="help.datatype.timespan"/></span></c:if>
    </div>
  </div>
  </div>

  </div>

  <div class="row form-group">
    <label class="col-xs-2 text-right" for="datatype.active">
    	<fmt:message key="datatype.active"/>
    </label>
    <div class="col-xs-2">
	    <input${readonly} type="checkbox" class="form-control" id="datatype.active" name="active" value="active"${datatype.active?' checked':''}/>
    </div>
  </div>
<c:if test="${empty readonly}">
  <div class="row form-group">
    <div class="col-xs-offset-2 col-xs-10">
		<button type="submit" class="btn btn-success" name="action" value="update" id="toBeValidatedSubmit"><fmt:message key="action.record"/></button>
	</div>
  </div>
</fieldset>
</form>
</c:if>

<c:if test="${not empty readonly}"></div></c:if>

<div class="row buttons-list">
  <div class="col-xs-12 text-right">
	  <%@include file="datatyperel.jsp" %>
	  <c:if test="${userMayUpdate}">
	    <span class="button-spacer"></span>
		<span class="button-holder">
	    <a href="DataTypeMan?id=${datatype.PK}&action=interface&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonInterface.jsp" %></a>
	    </span><span class="button-spacer"></span>
		<span class="button-holder">
		<c:if test="${not empty readonly}">
	    <a href="DataTypeMan?id=${datatype.PK}&action=update&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonUpdate.jsp" %></a>
	    </c:if>
	    </span>
	  </c:if>
  </div>
</div>
<div class="row buttons-list">
   <div class="col-xs-12">
   	  <c:set var="nbdtf" value="${fn:length(datatype.dataTypeField)}"/>
   	  <c:if test="${nbdtf gt 0}">${nbdtf } <fmt:message key="objs.rawdatafield"/>:
      <dl class="dl-horizontal">
		<c:forEach var="datafield" items="${datatype.dataTypeField}">
		   	<dt>${datafield.description}</dt>
		   	<dd>(<fmt:message key="rawtab.${datafield.inheader}"/><c:if test="${datafield.fixedDataType}">: <strong><fmt:message key="rawdatafield.fixed"/></strong></c:if>) <strong>${datafield.precision}</strong> bits
		   	<c:forEach var="rawtable" items="${datafield.tables }">, ${rawtable.rawtype}-<i>${rawtable.description}</i></c:forEach>
		   	</dd>
		</c:forEach>
	  </dl>
	  </c:if>
	</div>
</div>
<div class="row">
   <div class="col-xs-12">
      <dl class="dl-horizontal">
		<c:forEach var="subtype" items="${datatype.subtype}">
			<c:set var="machine" value="${subtype.subtypeMachine}"/>
			<dt id="machine-${machine.PK}" class="<c:if test="${machine.active==false}">in</c:if>active">
			    <a href="MachineMan?id=${machine.PK}&breadcrumb=${breadcrumb}">
				<strong>${machine.nodeid} / ${machine.hexMachineMAC}</strong>
				<fmt:message key="icon.machine"/></a>
			</dt>
			<dd>
			  <c:if test="${userMayUpdate}"> &nbsp; 
			    <a href="MachineMan?id=${machine.PK}&rawfieldid=${subtype.subtypeField.PK}&datatypeid=${datatype.PK}&action=interface&breadcrumb=${breadcrumb}" style="float:left"><%@include file="inc/buttonInterface.jsp" %></a>
			  </c:if>
			  ${machine.getBestName(pageContext.request)}<c:if test="not empty machine.description">: ${machine.description }</c:if><br/>&nbsp;
 			</dd>
		</c:forEach>
	  </dl>
	</div>
</div>

<c:if test="${userMayDelete}">
	<form class="form-horizontal" action="DataTypeMan" role="form">
		<fieldset>
			<input type="hidden" name="action" value="delete" /> <input
				type="hidden" name="id" value="${datatype.PK}" /> <input
				type="hidden" name="breadcrumb" value="${breadcrumb}" />
			<div class="row form-group create-block">
					<div class="col-xs-5 text-right">
						<label for="action.delete"> <fmt:message
								key="action.confirm" />
						</label>
					</div>
					<div class="col-xs-1">
					   <input type="checkbox" class="form-control" name="confirm"
							value="confirmed" id="action.delete" />
					</div>
					<div class="col-xs-4">
					    <span class="input-group-btn">
					    	<button type="submit" class="btn btn-danger btn-xs">
								<fmt:message key="action.delete" />
								<strong><fmt:message key="icon.datatype" /><br/>${datatype.getBestName(pageContext.request)}</strong>
							</button>
						</span>
					</div>
			</div>
		</fieldset>
	</form>
</c:if>
<%@ include file="footer.jsp"%>
