package by.artem.je.jdbc.dao.classes;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    private Integer aircraftId;
    private byte[] seatNo;
}
