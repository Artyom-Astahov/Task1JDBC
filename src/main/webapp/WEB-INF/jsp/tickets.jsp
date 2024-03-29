<%@ page import="by.artem.je.jdbc.service.TicketService" %>
<%@ page import="by.artem.je.jdbc.dto.TicketDto" %><%--
  Created by IntelliJ IDEA.
  User: astah
  Date: 23.03.2024
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Купленные билеты:</h1>
<ul>
    <%
        TicketService ticketService = TicketService.getInstance();
        Long flightId = Long.valueOf(request.getParameter("flightId"));
        for(TicketDto ticketDto : ticketService.findAllByFlightId(flightId)){
            out.write(String.format("<li> %s</li>", ticketDto.seatNo()));
        }
    %>
</ul>
</body>

</html>
