package by.artem.je.jdbc.dao.classes;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Airport {
    private byte[] code;
    private String country;
    private String city;
}
