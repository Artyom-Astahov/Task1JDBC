package by.artem.je.jdbc.validator;

import by.artem.je.jdbc.dto.CreateUserDto;
import by.artem.je.jdbc.entity.RoleEnum;
import by.artem.je.jdbc.util.LocalDateFormater;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    @Getter
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();


    @Override
    public ValidationResult isValid(CreateUserDto createUserDto) {
        var validationResult = new ValidationResult();
        if(!LocalDateFormater.isValid(createUserDto.getBirthday())){
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if(RoleEnum.find(createUserDto.getRole()).isEmpty()){
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        if(createUserDto.getEmail().isEmpty()){
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }
        if(createUserDto.getPassword().isEmpty()){
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
            if(createUserDto.getName().isEmpty()){
                validationResult.add(Error.of("invalid.name", "Name is invalid"));
            }
        }
        return validationResult;
    }
}
