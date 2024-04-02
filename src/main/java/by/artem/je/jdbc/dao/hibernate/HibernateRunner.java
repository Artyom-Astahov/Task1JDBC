package by.artem.je.jdbc.dao.hibernate;

import by.artem.je.jdbc.dao.hibernate.persister.UserEntityPersister;
import by.artem.je.jdbc.entity.RoleEnum;
import by.artem.je.jdbc.entity.User;

import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args)
    {

        UserEntityPersister userEntityPersister = UserEntityPersister.getINSTANCE();
        User user = User.builder()
                .name("assssd")
                .role(RoleEnum.ADMIN)
                .email("asssd12@gmail.ru")
                .password("123")
                .birthday(LocalDate.now())
                .build();
//        userEntityPersister.save(user);

//        System.out.println(userEntityPersister.getAll());
        System.out.println(userEntityPersister.getById(0));

    }
}
