package kevin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse)response;
        //判断是否是敏感资源，如果是的话，获取session，获取不到则重定向到登录页面
        String uri = req.getServletPath();
        if(!uri.startsWith("/public/") && !uri.startsWith("/static/") && !"/user/login".equals(uri)){
            HttpSession session = req.getSession(false);
            if(session == null){//没有登录，不允许访问
                //resp.sendRedirect("/ses/public/index.html");
                String schema = req.getScheme();
                String host = req.getServerName();
                int port = req.getServerPort();
                String contextPath = req.getContextPath();
                String basePath = schema+"://"+host+":"+port+contextPath;
                resp.sendRedirect(basePath+"/public/index.html");
                return;
            }

        }
        chain.doFilter(request,response);//二次过滤
    }

    @Override
    public void destroy() {

    }
}
