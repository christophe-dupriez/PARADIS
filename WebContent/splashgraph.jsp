<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><?xml version="1.0" encoding="UTF-8" standalone="no"?>
<?xml-stylesheet href="/scheme/css/graf.css" type="text/css"?>
<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN"
 "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
<svg preserveAspectRatio="xMinYMin meet"
 viewBox="0.00 0.00 ${imagemap.xmax} ${imagemap.ymax}" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
<style type="text/css" ><![CDATA[
<c:forEach var="label" items="${auxil.allCategories}">
.labelColor${label.PK} {
	fill: ${label.colorHex};
}
</c:forEach>
.node {
}
]]></style>
<image x="0" y="0" width="${imagemap.xmax}" height="${imagemap.ymax}" xlink:href="imagemap/${imagemap.PK}.png" />
<c:forEach var="mapplace" items="${imagemap.mapPlace}">
<c:set var="place" value="${mapplace.mapplace}"/>
<svg id="${place.PK}" class="node" x="${mapplace.xpos-25}" y="${mapplace.ypos-25}" width="50" height="50">
<title>${place.getBestName(pageContext.request)}</title>
<a xlink:href="PlaceMan?id=${place.PK}" xlink:title="${place.getBestName(pageContext.request)}">
<svg preserveAspectRatio="none" width="50" height="50" viewBox="0.00 0.00 50.00 4.0">
<c:set var="x" value="1"/>
<c:forEach var="i" begin="100" end="199" step="006">
   <c:set var="x" value="${x+1}"/>
   <rect x="${x}" y="${4.0-(i/100.0)}" width="1" height="${i/100.0}" class="labelColor6"/> 
</c:forEach>
<c:forEach var="i" begin="200" end="299" step="006">
   <c:set var="x" value="${x+1}"/>
   <rect x="${x}" y="${4.0-(i/100.0)}" width="1" height="${i/100.0}" class="labelColor8"/> 
</c:forEach>
<c:forEach var="i" begin="300" end="399" step="006">
   <c:set var="x" value="${x+1}"/>
   <rect x="${x}" y="${4.0-(i/100.0)}" width="1" height="${i/100.0}" class="labelColor7"/> 
</c:forEach>
</svg>
</a>
</svg>
<a xlink:href="PlaceMan?id=${place.PK}" xlink:title="${place.getBestName(pageContext.request)}">
<text text-anchor="middle" x="${mapplace.xpos}" y="${mapplace.ypos}" font-family="Arial" font-size="14">${place.getBestName(pageContext.request)}</text>
</a>
</c:forEach>
<text text-anchor="middle" x="${imagemap.xmax / 2}" y="${imagemap.ymax-30}" font-family="Arial" font-size="14.00">${imagemap.getBestName(pageContext.request)}</text>
<text text-anchor="middle" x="${imagemap.xmax / 2}" y="${imagemap.ymax-15}" font-family="Arial" font-size="14.00">${datatype.getBestName(pageContext.request)}</text>
</svg>
