package by.artem.je.jdbc.dao.classes;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String flightNo;
    private Date departureDate;
    private String departureAirportCode;
    private Date arrivalDate;
    private String arrivalAirportCode;
    private Integer aircraftId;
    private Enum<FlightStatus> status;

}
