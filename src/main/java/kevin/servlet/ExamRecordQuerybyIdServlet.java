package kevin.servlet;

import kevin.dao.ExamRecordDAO;
import kevin.model.ExamRecord;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询学生考试成绩记录
 *
 */
@WebServlet("/examRecord/queryById")
public class ExamRecordQuerybyIdServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        ExamRecord record = ExamRecordDAO.queryById(Integer.parseInt(id));
        return record;
    }
}
