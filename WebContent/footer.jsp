<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %><hr/>
         <c:if test="${empty currentUser}">
   	      <form class="form-inline" method="post" action="${empty erroruri?(empty newcrumb?'index':newcrumb.href):erroruri}"
   	      	  id="loginForm"
   	      	  data-bv-message="This value is not valid"
			  data-bv-live="enabled"
			  data-bv-container="tooltip"
			  data-bv-submitButtons="#loginSubmit">
			<input type="hidden" name="erroruri" value="${erroruri}"/>
			<c:forEach var="paramName" items="${errorparameternames}">
				<input type="hidden" name="${paramName}" value="${auxil.getAttribute(paramName)}"/>
			</c:forEach>
            <div class="form-group">
              <input type="email" name="_username_" placeholder="<fmt:message key="login.email"/>" class="form-control" size="20"/>
            </div>
            <div class="form-group">
              <input type="password" name="_password_" placeholder="<fmt:message key="login.password"/>" class="form-control" size="8"/>
            </div>
            <span class="sr-only"><fmt:message key="login.login"/></span>
            <%@ include file="inc/buttonLogin.jsp" %>
          </form>
         </c:if>
  	<div class="row">
        <div class="col-xs-10 text-center"><a href="http://www.akuino.net">AKUINO</a>, &copy;<small><a href="http://www.destin-informatique.com">DESTIN-Informatique</a>, <a href="http://www.ethaconcept.be">EthaConcept</a>, <a href="http://www.glyphicons.com">GlyphIcons</a>, 2014</small></div>
    </div>
</div><%--CONTAINER-FLUID--%>
        <script type="text/javascript" src="js/vendor/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/vendor/bootstrapValidator.min.js"></script>
        <script type="text/javascript" src="js/vendor/jquery.flot.js"></script>
		<script type="text/javascript" src="js/vendor/jquery.flot.time.js"></script>
		<script type="text/javascript" src="js/vendor/jquery.flot.selection.js"></script>
		<%--script type="text/javascript" src="js/jqBootstrap3Validation.js"></script --%>
		<script type="text/javascript" src="js/main.js"></script>
    </body>
</html>
