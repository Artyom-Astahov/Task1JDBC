<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="email">Email
        <input type="text" name="email" id="email" value="${param.email}" required>
    </label><br>
    <label for="password">Password:
        <input type="password" name="password" id="password" required>
    </label><br>
    <input type="submit" value="Login">


    <a href="${pageContext.request.contextPath}/registration">
        <input type="submit" value="Зарегистрироваться" >
    </a>
    <c:if test="${param.error != null}">
        <div style="color: red">
            <span>Email or password is not correct</span>
        </div>
    </c:if>
</form>

</body>
</html>
