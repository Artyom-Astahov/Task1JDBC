package by.artem.je.jdbc.dao.classes;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Seat {
    private Integer aircraftId;
    private byte[] seatNo;
}
