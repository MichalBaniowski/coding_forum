package pl.michal_baniowski.coding_forum.filter;

import javax.servlet.*;
import java.io.IOException;


public class EncodingFilter implements Filter {
    @Override
    public void destroy() {
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        chain.doFilter(req, resp);
    }
    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
