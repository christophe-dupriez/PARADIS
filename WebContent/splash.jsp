<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ include file="header.jsp"%>
<div class="row">
<div class="col-xs-12">
<%--<div id="paradis" style="position:relative;">
<img src="img/paradis.png" width="80%" border="0" usemap="#paradisMap" alt="Bassin du Paradis"/>
<map name="paradisMap">
<area id="areaC" alt="" title="C" href="Details?place=C" shape="rect" coords="264,106,314,156" style="outline:none;" target="_self"     />
<area id="areaB" alt="" title="B" href="Details?place=B" shape="rect" coords="242,231,292,281" style="outline:none;" target="_self"     />
<area id="areaD0" alt="" title="D0" href="Details?place=D0" shape="rect" coords="575,430,625,480" style="outline:none;" target="_self"     />
<area id="areaD1" alt="" title="D1" href="Details?place=D1" shape="rect" coords="809,355,859,405" style="outline:none;" target="_self"     />
<area id="areaD2" alt="" title="D2" href="Details?place=2" shape="rect" coords="931,383,981,433" style="outline:none;" target="_self"     />
</map>
<div id="graphC" style="position:absolute;left:264px;top:106px;width:50px;height:50px;">X</div>
<div id="graphB" style="position:absolute;left:242px;top:231px;width:50px;height:50px;">X</div>
<div id="graphD0" style="position:absolute;left:575px;top:430px;width:50px;height:50px;">X</div>
<div id="graphD1" style="position:absolute;left:809px;top:355px;width:50px;height:50px;">X</div>
<div id="graphD2" style="position:absolute;left:931px;top:383px;width:50px;height:50px;">X</div>
</div>
</div>
</div> --%>
<div class="row">
<div class="col-xs-12">
<%@ include file="splashgraph.jsp"%>
</div>
</div>
<form class="form-horizontal">
    <fieldset>
        <div class="row form-group">
            <label class="col-xs-2 text-right"><fmt:message key="objs.datatype"/></label>
            <div class="col-xs-10">
            	<select name="datatypeid">
            	<c:forEach var="anImageType" items="${imagemap.imageType }">
            		<c:set var="aType" value="${anImageType.imageType }"/>
            		<option value="${aType.PK}">${aType.getBestName(pageContext.request)}</option>
            	</c:forEach>
            	</select>
            </div>
        </div>
        <%--
        <div class="row form-group">
            <label class="col-xs-2 text-right">Intervalle</label>
            <div class="col-xs-10">
                <input id="min" type="range" value="1" min="0" max="10" step="any" name="from" style="background-color:yellow;"/><br/>
                <input id="max" type="range" value="9" min="0" max="10" step="any" name="upto" style="background-color:green;"/><br/>
            	<span id="ha" style="background-color:grey;display:inline-block"> &nbsp; </span><span id="hb" style="background-color:yellow;display:inline-block"> &nbsp; </span><span id="hc" style="background-color:green;display:inline-block"> &nbsp; </span><br/>
                <input id="smin" name="smin" readonly />-
                <input id="smax" name="smax" readonly />
            </div>
        </div>
         --%>
    </fieldset>
</form>
<%--
<script>
$(document).ready(function(){
$("#max").on("change input",
		function () {
			$el = $('#max');
			$em = $('#min');
		    if (parseFloat($el.val()) < parseFloat($em.val())) {
		    	$el.val($em.val());
		    }
		    $('#smax').val($el.val());
		    $('#ha').width ( (parseFloat($em.val())*10.0)+'%'); 
		    $('#hb').width(((parseFloat($el.val())-parseFloat($em.val()))*10.0)+'%'); 
		    $('#hc').width(((10.0-parseFloat($el.val()))*10.0)+'%'); 
		});

$("#min").on("change input",
		function () {
			$el = $('#min');
			$em = $('#max');
		    if (parseFloat($el.val()) > parseFloat($em.val())) {
		    	$el.val($em.val());
		    }
		    $('#smin').val($el.val());
		    $('#ha').width ( (parseFloat($el.val())*10.0)+'%'); 
		    $('#hb').width(((parseFloat($em.val())-parseFloat($el.val()))*10.0)+'%'); 
		    $('#hc').width(((10.0-parseFloat($em.val()))*10.0)+'%'); 
		});
$('#ha').width('10%'); 
$('#hb').width('80%'); 
$('#hc').width('10%'); 
});
</script>
--%>
<%@ include file="footer.jsp"%>