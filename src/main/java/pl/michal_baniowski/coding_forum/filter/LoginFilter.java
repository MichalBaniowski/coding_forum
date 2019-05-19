package pl.michal_baniowski.coding_forum.filter;

import pl.michal_baniowski.coding_forum.model.User;
import pl.michal_baniowski.coding_forum.services.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getUserPrincipal() != null && request.getSession().getAttribute("user") == null) {
            setUserInSession(request);
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    private void setUserInSession(HttpServletRequest request) {
        String userName = request.getUserPrincipal().getName();
        User user = UserService.getInstance().getUserByUserName(userName);
        request.getSession().setAttribute("user", user);
    }

}
