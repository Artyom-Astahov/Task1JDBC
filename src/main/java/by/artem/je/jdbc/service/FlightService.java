package by.artem.je.jdbc.service;

import by.artem.je.jdbc.dao.dao_classes.FlightDao;
import by.artem.je.jdbc.dao.hibernate.persister.FlightEntityPersister;
import by.artem.je.jdbc.dto.FlightDto;

import java.util.List;
import java.util.stream.Collectors;

public class FlightService {

    private static final FlightService INSTANCE = new FlightService();
    private final FlightEntityPersister flightEntityPersister = FlightEntityPersister.getINSTANCE();


    private FlightService() {
    }

    public static FlightService getInstance() {
        return INSTANCE;
    }


    public List<FlightDto> findAll() {
        return flightEntityPersister.getAll().stream().map(flight -> new FlightDto(flight.getId(),
                "%s - %s - %s".formatted(
                        flight.getArrivalAirportCode(),
                        flight.getDepartureAirportCode(),
                        flight.getStatus()
                ))).collect(Collectors.toList());
    }
}
