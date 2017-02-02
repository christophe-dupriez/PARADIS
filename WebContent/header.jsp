<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="${currentLocale}"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang="${currentLocale}"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang="${currentLocale}"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="${currentLocale}"> <!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>  
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title><c:if test="${not empty newcrumb}"><fmt:message key="obj${newcrumb.object==null?'s':''}.${newcrumb.type}"/> ${newcrumb.name}</c:if></title>
    <meta name="description" content="<fmt:message key="application.description"/>"/>
    <meta name="author" content="akuino.net"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="shortcut icon" href="favicon.ico"/>

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic' rel='stylesheet' type='text/css' -->
    <style>
    	html {
    		position:relative;
			min-height: 100%;    		
    	}
        body {
            padding-top: 50px;
            padding-bottom: 40px;
            <%--padding-right:5px;
            padding-left:5px;--%>
        }
        <%--Never make navbar taller when in Horizontal mode (not in Stacked mode)--%>
        @media (min-width:768px) {
	        #navbar {
	        	max-height:50px;
	        	overflow:initial;
	        }
        }
        .footer {
		  position: absolute;
		  bottom: 0;
		  width: 100%;
		  /* Set the fixed height of the footer here */
		  height: 40px;
		  background-color: #f5f5f5;
		}		        
    </style>

    <link rel="stylesheet" href="css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="css/bootstrapValidator.min.css">
    <link rel="stylesheet" href="css/glyphicons.css">
    <link rel="stylesheet" href="css/main.css">

    <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    <script type="text/javascript" src="js/vendor/jquery-1.11.0.min.js"></script>
    
</head>
<body>
        <!--[if lt IE 7]>
            <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
	  <div class="navbar navbar-inverse navbar-fixed-top" role="navigation" id="navbar">
       <div class="container">
        <div class="navbar-header"><%@ include file="inc/buttonToggle.jsp" %>
          <a class="navbar-brand" href="index" style="margin-top:-10px;"><%@ include file="inc/linkHome.jsp" %></a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="dropdown<c:if test="${section=='data'}"> active</c:if>" data-show-icon="true">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="section.data"/><b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="DataTypeMan" tabindex="-1"><fmt:message key="icon.datatype"/> <fmt:message key="objs.datatype"/></a></li>
                <li><a href="GraphConfigMan" tabindex="-1"><fmt:message key="icon.graphconfig"/> <fmt:message key="objs.graphconfig"/></a></li>
                <li><a href="EventMan" tabindex="-1"><fmt:message key="icon.event"/> <fmt:message key="objs.event"/></a></li>
                <li><a href="ImagemapMan" tabindex="-1"><fmt:message key="icon.imagemap"/> <fmt:message key="objs.imagemap"/></a></li>
                <li><a href="PlaceMan" tabindex="-1"><fmt:message key="icon.place"/> <fmt:message key="objs.place"/></a></li>
                <li><a href="CategoryMan" tabindex="-1"><fmt:message key="icon.category"/> <fmt:message key="objs.category"/></a></li>
              </ul>
            </li>
            <li class="dropdown<c:if test="${section=='hardware'}"> active</c:if>" data-show-icon="true">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="section.hardware"/><b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="NetworkMan" tabindex="-1"><fmt:message key="icon.network"/> <fmt:message key="objs.network"/></a></li>
                <li><a href="MachineMan" tabindex="-1"><fmt:message key="icon.machine"/> <fmt:message key="objs.machine"/></a></li>
                <li><a href="ScalingMan" tabindex="-1"><fmt:message key="icon.scaling"/> <fmt:message key="objs.scaling"/></a></li>
                <li><a href="SubTypeMan" tabindex="-1"><fmt:message key="icon.subtype"/> <fmt:message key="objs.subtype"/></a></li>
              </ul>
            </li>
            <li class="dropdown<c:if test="${section=='people'}"> active</c:if>" data-show-icon="true">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="section.people"/><b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="UserMan" tabindex="-1"><fmt:message key="icon.user"/> <fmt:message key="objs.user"/></a></li>
                <li><a href="GroupOfUsersMan" tabindex="-1"><fmt:message key="icon.groupofusers"/> <fmt:message key="objs.groupofusers"/></a></li>
                <li><a href="AlertMan" tabindex="-1"><fmt:message key="icon.alert"/> <fmt:message key="objs.alert"/></a></li>
              </ul>
            </li>
          </ul>
          	<c:if test="${not empty currentUser}">
          		<span class="pull-right">
          	    <span class="totip" data-toggle="tooltip" data-placement="bottom" title="${currentUser.name}"><a href="UserMan?id=self"><button type="submit" class="btn btn-info"><fmt:message key="icon.user"/></button></a></span>
          	    <span class="totip" data-toggle="tooltip" data-placement="bottom" title="<fmt:message key="login.logout"/>"><a href="index?_logout_=1"><span class="sr-only"><fmt:message key="login.logout"/></span><button class="btn btn-default"><fmt:message key="icon.logoff"/></button></a></span>
          	    </span>
            </c:if>
        </div><!--/.navbar-collapse -->
      </div>
    </div>  
    <div class="container">
      <div class="row">
        <div class="col-xs-12">
          <c:if test="${not empty currentUser}">
	        <ol class="btn-group btn-breadcrumb">
	        	<c:forEach var="crumb" items="${crumbs}">
	        	 <c:if test="${newcrumb.type!=crumb.type || newcrumb.object.PK!=crumb.object.PK}">
				  <li class="btn"><a href="${crumb.href}&breadcrumb=${breadcrumb}"><fmt:message key="icon.${crumb.type}"/>
				  			<c:if test="${empty crumb.object}"><fmt:message key="objs.${crumb.type}"/></c:if>
				  			${crumb.name}</a></li>
				 </c:if>
	        	</c:forEach>
			</ol>
          </c:if>
        </div>
      </div>
<c:if test="${not empty errors}">
      <div class="row">
        <div class="col-xs-12">
        	<span class="label label-error">${errors}</span>
        </div>
      </div>
</c:if>
<c:if test="${not empty warnings}">
      <div class="row">
        <div class="col-xs-12">
        	<span class="label label-warning">${warnings}</span>
        </div>
      </div>
</c:if>
<c:if test="${not empty infos}">
      <div class="row">
        <div class="col-xs-12">
        	<span class="label label-success">${infos}</span>
        </div>
      </div>
</c:if>
      <div class="row">
        <div class="col-xs-12">
		  <c:if test="${not empty newcrumb}">
	        <h2><fmt:message key="icon.${newcrumb.type}"/>  <fmt:message key="obj${newcrumb.object==null?'s':''}.${newcrumb.type}"/> ${newcrumb.name}: ${newcrumb.object==null?'':newcrumb.multiples}</h2>
	      </c:if>
        </div>
      </div>
