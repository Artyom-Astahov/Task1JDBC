package by.artem.je.jdbc.mapper;

import by.artem.je.jdbc.entity.RoleEnum;
import by.artem.je.jdbc.entity.User;
import by.artem.je.jdbc.util.LocalDateFormater;
import by.artem.je.jdbc.dto.CreateUserDto;
import lombok.Getter;

public class CreateUserMapper implements Mapper<User, CreateUserDto> {

    @Getter
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    private CreateUserMapper() {
    }



    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .name(createUserDto.getName())
                .birthday(LocalDateFormater.format(createUserDto.getBirthday()))
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .role(RoleEnum.valueOf(createUserDto.getRole()))
                .build();
    }
}
