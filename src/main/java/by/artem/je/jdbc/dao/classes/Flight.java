package by.artem.je.jdbc.dao.classes;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Flight {
    private Long id;
    private String flightNo;
    private Date departureDate;
    private byte[] departureAirportCode;
    private Date arrivalDate;
    private byte[] arrivalAirportCode;
    private Integer aircraftId;
    private Enum<FlightStatus> status;

}
