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
@Table(name = "airport")
public class Airport {
    @Id
    private byte[] code;
    private String country;
    private String city;
}
