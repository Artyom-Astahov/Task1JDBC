<%--
  Created by IntelliJ IDEA.
  User: astah
  Date: 23.03.2024
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <span>CONTENT РУССКИЙ</span>
    <p>Size: ${requestScope.flights.size()}</p>
    <p>Size: ${requestScope.flights.get(0).description()}</p>
</div>
</body>
</html>
