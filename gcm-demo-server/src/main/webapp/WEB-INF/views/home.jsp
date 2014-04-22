<%--
  Created by IntelliJ IDEA.
  User: vladyslavprytula
  Date: 4/18/14
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div>
    <h2>Demo for Adaptive analytcs and push notificaitons</h2>
    <h3>Look at what payments we have now...</h3>

    <ol class="payment-list">
        <c:forEach var="user" items="${users}">

            <s:url value="/payments/{userId}"
                   var="payement_url" >
                <s:param name="userId"
                         value="${user.userId}" />
            </s:url>
                  <span class="paymentList">
                    <a href="${payement_url}">
                        <c:out value="${user.userId}" /></a>
                        <c:out value="${payment.amount}" />
                  </span>
            </li>

        </c:forEach>
    </ol>
</div>