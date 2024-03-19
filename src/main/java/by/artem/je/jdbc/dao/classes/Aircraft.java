package by.artem.je.jdbc.dao.classes;

import lombok.*;

import javax.management.ConstructorParameters;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Aircraft {
    private Integer id;
    private String model;
}
