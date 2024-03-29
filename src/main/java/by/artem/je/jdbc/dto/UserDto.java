package by.artem.je.jdbc.dto;

import by.artem.je.jdbc.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Integer id;
    String name;
    LocalDate birthday;
    String password;
    String email;
    Role role;

}
