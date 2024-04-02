package by.artem.je.jdbc.conventer;

import by.artem.je.jdbc.entity.RoleEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class RoleEnumConventerAttribute implements AttributeConverter<RoleEnum, String> {
    @Override
    public String convertToDatabaseColumn(RoleEnum role) {
        if(role == null)
            return null;
        return role.toString();
    }

    @Override
    public RoleEnum convertToEntityAttribute(String string) {
        return RoleEnum.valueOf(string);
    }
}
