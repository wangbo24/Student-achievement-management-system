package kevin.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import kevin.model.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**
 * 抽象类，process方法进行具体的数据处理
 * 基类，做统一处理
 */
public abstract class AbstractBaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter pw = resp.getWriter();

        Response response = null;
        Object data = null;
        try {
            data = process(req,resp);
            response = Response.ok(data);
            response.setTotal(Response.getPageTotal());
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.error(e);
        }

        ObjectMapper  mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        String json = mapper.writeValueAsString(response);
        pw.println(json);
        pw.flush();
    }

    public abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception;


}