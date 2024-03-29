package by.artem.je.jdbc.mapper;

import by.artem.je.jdbc.dto.UserDto;
import by.artem.je.jdbc.entity.Role;
import by.artem.je.jdbc.entity.User;
import by.artem.je.jdbc.util.LocalDateFormater;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper  implements Mapper<UserDto,User > {
    @Getter
    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .name(user.getName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
