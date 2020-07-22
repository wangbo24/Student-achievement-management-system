package kevin.servlet;

import kevin.dao.UserDAO;
import kevin.model.User;
import kevin.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 对登录请求进行处理
 * 调用JSONUnil.read()方法得到具体的对象
 * 将对象交给UserDAO进行具体的数据库处理
 * 将查询到的用户信息设置到Session里面
 */
@WebServlet("/user/login")
public class LoginServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = JSONUtil.read(req.getInputStream(),User.class);
        User qureyUser = UserDAO.query(user);
        if(qureyUser != null){
            HttpSession session = req.getSession();
            session.setAttribute("user",qureyUser);
        }else{
            throw new RuntimeException("用户名密码校验失败");
        }
        return null;
    }
}
