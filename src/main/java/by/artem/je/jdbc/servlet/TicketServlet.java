package by.artem.je.jdbc.servlet;

import by.artem.je.jdbc.dao.TicketDao;
import by.artem.je.jdbc.service.FlightService;
import by.artem.je.jdbc.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {

    private final TicketService ticketService = TicketService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;UTF_8");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Long flightid = Long.valueOf(req.getParameter("flightId"));
        try (var writer = resp.getWriter()) {
            writer.write("<h1>Купленные билеты:</h1>");
            writer.write("<ul>");
            ticketService.findAllByFlightId(flightid).forEach(ticketDto ->
                    writer.write("""
                            <li>%s</li>
                            """.formatted(ticketDto.seatNo())));
            writer.write("</ul>");
        }
    }
}
