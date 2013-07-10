<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false" %>

<fmt:setBundle basename="locale.portlet.Language-ext"/>
<form action="<c:out value='${requestScope.registerUserActionUrl}'/>" method="POST">
<table width="200px" id="userForm">
  <tr>
  	<td colspan="2">
  		<font color="#FF0000"><c:out 
  		   value="${requestScope.errorMsg}"/></font>
  	</td>
  </tr>
  <tr>
  	<td class="tdform"><fmt:message key="label.firstName"/></td>
  	<td class="tdform"><input type="text" name="firstName" value="${requestScope.user.firstName}"></input></td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
  </tr>
  <tr>
  	<td class="tdform"><fmt:message key="label.lastName"/></td>
  	<td class="tdform"><input type="text" name="lastName" value="${requestScope.user.lastName}"></input></td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
  </tr>
  <tr>
  	<!--<td><font color="#FF0000"><b>*</b></font>&nbsp;<fmt:message key="label.email"/></td>-->
    <td class="tdform"><fmt:message key="label.email"/></td>
  	<td class="tdform"><input type="text" name="email" value="${requestScope.email}"></input></td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
  </tr>
  <tr align="center">
    <td colspan="2">
    	<input type="submit"/>
    	&nbsp;
      <a href="<c:out value='${requestScope.resetRenderUrl}'/>">
        <b><fmt:message key="label.reset"/></b>
      </a>
    </td>
  </tr>
</table>
</form>

<!--
<div class="toggler">
    <div id="effect" class="ui-widget-content ui-corner-all">
        <img src="<%=request.getContextPath()%>/images/gatein_logo.png"/>
    </div>
</div>

<a href="#" id="button">Show me the magic</a>
-->
<!--<p>I would like to say: </p>-->



<div id="box_grid" style="height:300px;width: 300px;" style="float: right;"></div>


<div>
    ${requestScope.usersGrid}
</div>