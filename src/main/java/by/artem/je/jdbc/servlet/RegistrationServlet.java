package by.artem.je.jdbc.servlet;

import by.artem.je.jdbc.entity.RoleEnum;
import by.artem.je.jdbc.exception.ValidationException;
import by.artem.je.jdbc.service.UserService;
import by.artem.je.jdbc.util.JspHelper;
import by.artem.je.jdbc.dto.CreateUserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", RoleEnum.values());
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = CreateUserDto.builder()
                .name(req.getParameter("name"))
                .birthday(req.getParameter("birthday"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .role(req.getParameter("role"))
                .build();

        try {
            userService.createUser(userDto);
            resp.sendRedirect("/login");
        } catch (ValidationException e) {
            req.setAttribute("error", e.getErrors());
            doGet(req, resp);
        }


    }
}
