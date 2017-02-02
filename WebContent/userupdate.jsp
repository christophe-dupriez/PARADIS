<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %><%@ include file="header.jsp"%>
      <div class="row">
        <div class="col-lg-4">
<form role="form" method="POST" action="UserUpdate">
<input type="hidden" name="id" value="${user.PK }">
<input type="hidden" name="breadcrumb" value="&breadcrumb=${breadcrumb}">
  <div class="form-group">
    <label for="exampleInputName1">Nom</label>
    <input type="text" class="form-control" id="exampleInputName1" placeholder="Nom, Prénom" name="name" value="${user.name}"/>
  </div>
  <div class="form-group">
    <label for="exampleInputEmail1">Courriel</label>
    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="id@domaine.tld" name="useremail" value="${user.email }">
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Mot de passe</label>
    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="userpassword" value="${user.password }">
  </div>
  <div class="form-group">
    <label for="exampleInputGsm1">GSM pour les SMS d'alerte</label>
    <input type="tel" class="form-control" id="exampleInputGsm1" placeholder="0499123456" name="gsm" value="${user.gsm}"/>
  </div><c:if test="${isadmin}">
  <div class="checkbox">
    <label>
      <input type="checkbox" ${user.admin!=0?'checked':''} name="admin" value="1"/> Droits d'Administration?
    </label>
  </div></c:if>
  <div class="form-group">
    <label for="exampleInputlanguage1">Langue pour les SMS d'alerte</label>
    <input type="text" class="form-control" id="exampleInputlanguage1" placeholder="XX" name="language" value="${user.language}"/>
  </div>
 <button type="submit" class="btn btn-default" name="action" value="update">Enregistrer</button>
</form>
      </div>
      </div>
<%@ include file="footer.jsp"%>
