package kevin.servlet;

import kevin.dao.ExamRecordDAO;
import kevin.model.ExamRecord;
import kevin.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增学生考试成绩
 */
@WebServlet("/examRecord/add")
public class ExamRecordAddServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ExamRecord record = JSONUtil.read(req.getInputStream(),ExamRecord.class);
        int num = ExamRecordDAO.insert(record);
        return null;
    }
}
