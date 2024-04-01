package by.artem.je.jdbc.service;


import by.artem.je.jdbc.dao.dao_classes.UserDao;
import by.artem.je.jdbc.dto.CreateUserDto;
import by.artem.je.jdbc.dto.UserDto;
import by.artem.je.jdbc.exception.ValidationException;
import by.artem.je.jdbc.mapper.CreateUserMapper;
import by.artem.je.jdbc.mapper.UserMapper;
import by.artem.je.jdbc.validator.CreateUserValidator;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getINSTANCE();
    private final UserDao userDao = UserDao.getINSTANCE();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getINSTANCE();

    private final UserMapper userMapper = UserMapper.getINSTANCE();
    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<UserDto> login(String email, String password) {
        return userDao.findEmailAndPassword(email, password).map(userMapper::mapFrom);

    }

    public Integer createUser(CreateUserDto createUserDto) throws ValidationException {

        var validationResult = createUserValidator.isValid(createUserDto);
        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }
        var user = createUserMapper.mapFrom(createUserDto);
        userDao.create(user);
        return user.getId();
    }
}
