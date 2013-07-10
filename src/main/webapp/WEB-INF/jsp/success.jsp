<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false" %>

<fmt:setBundle basename="locale.portlet.Language-ext"/>
<table width="200px">
	<tr>
		<td colspan="2"><fmt:message key="success.message"/></td>
	</tr>
  <tr>
  	<td colspan="2"><b><fmt:message key="label.firstName"/></b><c:out value="${requestScope.user.firstName}"/></td>
  </tr>
  <tr>
  	<td colspan="2"><b><fmt:message key="label.lastName"/></b><c:out value="${requestScope.user.lastName}"/></td>

  </tr>
  <tr>
  	<td colspan="2"><b><fmt:message key="label.email"/></b><c:out value="${requestScope.user.email}"/></td>
  </tr>
</table>
</br>
<div>
    ${requestScope.usersGrid}
</div>



<div id="box_grid" style="height:300px;width: 300px;" style="float: right;"></div>

</br>
<a href="<c:out value='${requestScope.homeUrl}'/>">
    <b>Return to <fmt:message key="label.home"/></b>
</a>