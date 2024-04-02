package by.artem.je.jdbc.filter;

import by.artem.je.jdbc.dto.UserDto;
import by.artem.je.jdbc.entity.RoleEnum;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/content")
public class ContentFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        if (user == null || user.getRole() != RoleEnum.ADMIN) {
            ((HttpServletResponse) servletResponse).sendRedirect("/registration");

        } else {
            filterChain.doFilter(servletRequest, servletResponse);

        }
    }
}
