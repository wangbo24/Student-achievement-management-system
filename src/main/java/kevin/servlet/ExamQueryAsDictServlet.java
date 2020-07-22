package kevin.servlet;

import kevin.dao.ExamDAO;
import kevin.model.Exam;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 查询考试信息，通过数据字典进行查询
 * 下拉框的查询
 */
@WebServlet("/exam/queryAsDict")
public class ExamQueryAsDictServlet extends AbstractBaseServlet {
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<Exam> exams = ExamDAO.queryAsDict();
        return exams;
    }
}
