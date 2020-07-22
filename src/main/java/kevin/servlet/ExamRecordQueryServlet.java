package kevin.servlet;

import kevin.dao.ExamRecordDAO;
import kevin.model.ExamRecord;
import kevin.model.Page;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * 查询学生考试成绩信息
 * GET examRecord/query ? searchText=&sortOrder=asc&pageSize=7&pageNumber=1
 * 先进行了
 */
@WebServlet("/examRecord/query")
public class ExamRecordQueryServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Page p = Page.parse(req);
        List<ExamRecord> records = ExamRecordDAO.query(p);
        return records;
    }
}
