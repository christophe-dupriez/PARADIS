<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %><%@ include file="header.jsp"%>
      <div class="row">
        <div class="col-lg-4" id="measure">
          <h2>pour <a href="MachineUpdate?action=list&id=${machine.PK }#machine-${machine.PK }">${machine.nodeid }</a>
                - <a href="TypeUpdate?action=list&id=${type.PK }#type-${type.PK}">${type.name }</a></h2>
		<div class="demo-container">
			<div id="placeholder" class="demo-placeholder"></div>
		</div>
 		<div class="demo-container" style="height:150px;">
			<div id="overview" class="demo-placeholder"></div>
		</div>

		<%--div class="table-responsive">
		  <table class="table">
          <c:forEach var="measure" items="${measures}">
          <tr><td>${measure.whenmeasured }</td>
              <th>${measure.value } ${measure.measureType.unit }</th></tr>
          </c:forEach>
          </table>
        </div--%>
      </div>
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
	var d = [<c:forEach var="measure" items="${measures}">[${measure.whenmeasured.time},${measure.value}],
</c:forEach>];
	var colours = {1:"#33EE33",200:"#CCCC00",201:"#EE3333"};

	// first correct the timestamps - they are recorded as the daily
	// midnights in UTC+0100, but Flot always displays dates in UTC
	// so we have to add one hour to hit the midnights in the plot

	for (var i = 0; i < d.length; ++i) {
		d[i][0] += 60 * 60 * 1000;
	}

	// helper for returning the weekends in a period

	function weekendAreas(axes) {

		var markings = [],
			d = new Date(axes.xaxis.min);

		// go to the first Saturday

		d.setUTCDate(d.getUTCDate() - ((d.getUTCDay() + 1) % 7))
		d.setUTCSeconds(0);
		d.setUTCMinutes(0);
		d.setUTCHours(0);

		var i = d.getTime();

		// when we don't set yaxis, the rectangle automatically
		// extends to infinity upwards and downwards

		do {
			markings.push({ xaxis: { from: i, to: i + 2 * 24 * 60 * 60 * 1000 } });
			i += 7 * 24 * 60 * 60 * 1000;
		} while (i < axes.xaxis.max);

		return markings;
	}

	var options = {
		points: {
			show:true
		},
		lines: {
			show:true
		},
		xaxis: {
			mode: "time",
			tickLength: 5
		},
		selection: {
			mode: "x"
		},
		grid: {
			hoverable: true,
			clickable: true,
			markings: weekendAreas
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

	var plot = $.plot("#placeholder", [{data:d, label:"${machine.nodeid}: ${machine.description}", color:colours[${machine.PK}]}],  options);

	$("#placeholder").bind("plothover", function (event, pos, item) {
		if (item) {
			var x = new Date(item.datapoint[0]),
				y = item.datapoint[1].toFixed(2);

			$("#tooltip").html(item.series.label + ", " + x.getUTCFullYear()+"/"+("0"+(1+x.getUTCMonth())).slice(-2)+"/"+("0"+x.getUTCDate()).slice(-2)+" "+("0"+x.getUTCHours()).slice(-2)+":"+("0"+x.getUTCMinutes()).slice(-2)+":"+("0"+x.getUTCSeconds()).slice(-2) + " = " + y)
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
	
    var overview = $.plot("#overview", [{data:d, label:"${machine.nodeid}: ${machine.description}", color:colours[${machine.PK}]}], {
		series: {
			lines: {
				show: true,
				lineWidth: 1
			},
			shadowSize: 0
		},
		xaxis: {
			ticks: [],
			mode: "time"
		},
		yaxis: {
			ticks: [],
			min: 0,
			autoscaleMargin: 0.1
		},
		selection: {
			mode: "x"
		}
	});

	// now connect the two

	$("#placeholder").bind("plotselected", function (event, ranges) {

		// do the zooming

		plot = $.plot("#placeholder",  [{data:d, label:"${machine.nodeid}: ${machine.description}", color:colours[${machine.PK}]}],
				       $.extend(true, {}, options, {
			xaxis: {
				min: ranges.xaxis.from,
				max: ranges.xaxis.to
			}
		}));

		// don't fire event on the overview to prevent eternal loop

		overview.setSelection(ranges, true);
	});

	$("#overview").bind("plotselected", function (event, ranges) {
		plot.setSelection(ranges);
	});
});//--></script>
<%@ include file="footer.jsp"%>

