package by.artem.je.jdbc.service;

import by.artem.je.jdbc.dao.dao_classes.TicketDao;
import by.artem.je.jdbc.dao.hibernate.persister.TicketEntityPersister;
import by.artem.je.jdbc.dto.TicketDto;

import java.util.List;
import java.util.stream.Collectors;

public class TicketService {
    private static final TicketService INSTANCE = new TicketService();
    private final TicketEntityPersister ticketEntityPersister = TicketEntityPersister.getINSTANCE();

    private TicketService() {
    }

    public static TicketService getInstance() {
        return INSTANCE;
    }

    public List<TicketDto> findAllByFlightId(Long id) {
        return ticketEntityPersister.getAllByFlightId(id).stream().map(ticket -> new TicketDto(
                ticket.getId(),
                ticket.getFlightId(),
                ticket.getSeatNo())
        ).collect(Collectors.toList());
    }
}
