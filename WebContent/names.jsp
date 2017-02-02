<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><c:forEach var="lang" items="${supportedLocales}"><li>${lang}:<input name="name_${lang}" value="${namesMap[lang]}"></li></c:forEach>
