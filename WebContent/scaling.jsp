<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<div class="row">
  <div class="col-lg-12">
	  <fmt:message key="objs.machine"/>: <a href="MachineMan?action=list&scalingid=${scaling.PK}&breadcrumb=${breadcrumb}">${fn:length(scaling.machines)}<fmt:message key="icon.machine"/></a>
	  <fmt:message key="objs.datatype"/>: <a href="DataTypeMan?action=list&scalingid=${scaling.PK}&breadcrumb=${breadcrumb}">${fn:length(scaling.datatypes)}<fmt:message key="icon.datatype"/></a>
		<c:if test="${userMayUpdate}"> &nbsp;
	    <a href="ScalingMan?id=${scaling.PK}&action=update&breadcrumb=${breadcrumb}"><%@include file="inc/buttonUpdate.jsp" %></a>
		</c:if>
	  <table class="table table-condensed table-striped">
		   <tr><td><fmt:message key="scaling.unit"/></td><th>${scaling.unit }</th></tr>
		   <tr><td><fmt:message key="scaling.description"/></td><th>${scaling.description }</th></tr>
		   <tr><td><fmt:message key="scaling.interpolation"/></td><th><fmt:message key="interpolation.${scaling.interpolation}"/></th></tr>
	  </table>

		<div class="demo-container">
			<div id="placeholder" class="demo-placeholder" style="height:150px;"></div>
		</div>
	<style type="text/css">

	#placeholder .button {
		position: absolute;
		cursor: pointer;
	}

	#placeholder div.button {
		font-size: smaller;
		color: #999;
		background-color: #eee;
		padding: 2px;
	}
	.message {
		padding-left: 50px;
		font-size: smaller;
	}

	</style>
<script type="text/javascript"><!--
$(function() {
	var d = [<c:forEach var="line" items="${scaling.line}">[${line.rawmin},${line.min}],
</c:forEach>];

	var options = {
		points: {
			show:true
		},
		lines: {
			show:true
		},
		selection: {
			mode: "x"
		},
		grid: {
			hoverable: true,
			clickable: true,
		}
	};

	$("<div id='tooltip'></div>").css({
		position: "absolute",
		display: "none",
		border: "1px solid #fdd",
		padding: "2px",
		"background-color": "#fee",
		opacity: 0.80
	}).appendTo("body");

	var plot = $.plot("#placeholder", [{data:d, label:"${scaling.unit}: ${scaling.description}"}],  options);

	$("#placeholder").bind("plothover", function (event, pos, item) {
		if (item) {
			var x = item.datapoint[0].toFixed(2),
				y = item.datapoint[1].toFixed(2);

			$("#tooltip").html(item.series.label + ", " + x + " = " + y)
				.css({top: item.pageY+5, left: item.pageX+5})
				.fadeIn(200);
		} else {
			$("#tooltip").hide();
		}
	});
	
	$("#placeholder").bind("plotclick", function (event, pos, item) {
		if (item) {
			//$("#clickdata").text(" - click point " + item.dataIndex + " in " + item.series.label);
			plot.highlight(item.series, item.datapoint);
		}
	});
	
	// now connect the two

	$("#placeholder").bind("plotselected", function (event, ranges) {

		// do the zooming

		plot = $.plot("#placeholder",  [{data:d, label:"${scaling.unit}: ${scaling.description}"}],
				       $.extend(true, {}, options, {
			xaxis: {
				min: ranges.xaxis.from,
				max: ranges.xaxis.to
			}
		}));
	});
	$('#placeholder').on('plotclick', function ( event, pos, item ) {
	    //...
	});
});//--></script>

<c:if test="${userMayCreate}">
	<a href="ScalingMan?action=create&breadcrumb=${breadcrumb}"><button type="button" class="btn btn-default btn-xs"><fmt:message key="action.create"/> <fmt:message key="icon.scaling"/> <fmt:message key="obj.scaling"/></button></a>
</c:if>
    
  </div>
</div>
<%@ include file="footer.jsp"%>
