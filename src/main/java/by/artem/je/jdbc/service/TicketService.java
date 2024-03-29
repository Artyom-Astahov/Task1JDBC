package by.artem.je.jdbc.service;

import by.artem.je.jdbc.dao.TicketDao;
import by.artem.je.jdbc.dto.TicketDto;

import java.util.List;
import java.util.stream.Collectors;

public class TicketService {
    private static final TicketService INSTANCE = new TicketService();
    private final TicketDao ticketDao = TicketDao.getInstance();

    private TicketService() {
    }

    public static TicketService getInstance() {
        return INSTANCE;
    }

    public List<TicketDto> findAllByFlightId(Long id) {
        return ticketDao.findAllByFlightId(id).stream().map(ticket -> new TicketDto(
                ticket.getId(),
                ticket.getFlightId(),
                ticket.getSeatNo())
        ).collect(Collectors.toList());
    }
}
