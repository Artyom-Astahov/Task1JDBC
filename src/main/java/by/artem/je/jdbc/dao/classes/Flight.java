package by.artem.je.jdbc.dao.classes;

import lombok.*;

import javax.persistence.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNo;
    private Date departureDate;
    private String departureAirportCode;
    private Date arrivalDate;
    private String arrivalAirportCode;
    private Integer aircraftId;
    @Enumerated(EnumType.STRING)
    private Enum<FlightStatus> status;

}
