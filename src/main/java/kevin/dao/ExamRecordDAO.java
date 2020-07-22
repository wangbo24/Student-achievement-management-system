package kevin.dao;

import kevin.model.*;
import kevin.util.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生考试记录的数据靠操作类
 */
public class ExamRecordDAO {
    public static List<ExamRecord> query(Page p) {
        List<ExamRecord> records = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("select er.id, " +
                    "       er.score, " +
                    "       er.student_id, " +
                    "       er.exam_id, " +
                    "       er.exam_record_desc, " +
                    "       er.create_time, " +
                    "       e.id           prefix_e_id, " +
                    "       e.exam_name    prefix_e_exam_name, " +
                    "       e.exam_desc    prefix_e_exam_desc, " +
                    "       c.id           prefix_c_id, " +
                    "       c.classes_name prefix_c_classes_name, " +
                    "       c2.id          prefix_c2_id, " +
                    "       c2.course_name prefix_c2_course_name," +
                    "       s.id           prefix_s_id, " +
                    "       student_name   prefix_s_student_name, " +
                    "       student_no     prefix_s_student_no, " +
                    "       id_card        prefix_s_id_card, " +
                    "       student_email  prefix_s_student_email " +
                    " from exam_record er " +
                    "         join exam e ON er.exam_id = e.id " +
                    "         join classes c on e.classes_id = c.id " +
                    "         join course c2 on e.course_id = c2.id " +
                    "         join student s on c.id = s.classes_id");
            if(p.getSearchText() != null){
                sql.append(" WHERE s.student_name like ?");
            }
            //查询总条数
            StringBuilder countSQl = new StringBuilder("select count(0) count from (");
            countSQl.append(sql);
            countSQl.append(") tmp");
            ps = c.prepareStatement(countSQl.toString());
            if(p.getSearchText() != null){
                ps.setString(1,"%"+p.getSearchText()+"%");
            }
            rs = ps.executeQuery();
            while(rs.next()){
                int total = rs.getInt("count");
                Response.setPageTotal(total);
            }

            sql.append(" ORDER BY er.create_time " + p.getSortOrder());
            sql.append(" LIMIT ?,?");
            ps = c.prepareStatement(sql.toString());
            int i = 1;
            if(p.getSearchText() != null){
                ps.setString(i,"%"+p.getSearchText()+"%");
                i++;
            }
            ps.setInt(i,(p.getPageNumber()-1)*p.getPageSize()+1);
            i++;
            ps.setInt(i,p.getPageSize());

            rs = ps.executeQuery();
            while(rs.next()){
                //设置考试成绩对象
                ExamRecord er = new ExamRecord();
                er.setId(rs.getInt("id"));
                er.setScore(rs.getBigDecimal("score"));
                er.setExamRecordDesc(rs.getString("exam_record_desc"));
                er.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                //设置考试对象
                Exam e = new Exam();
                e.setId(rs.getInt("prefix_e_id"));
                e.setExamName(rs.getString("prefix_e_exam_name"));
                e.setExamDesc(rs.getString("prefix_e_exam_desc"));
                er.setExam(e);

                Classes classes = new Classes();
                classes.setId(rs.getInt("prefix_c_id"));
                classes.setClassesName(rs.getString("prefix_c_classes_name"));
                er.setClasses(classes);

                Course course = new Course();
                course.setId(rs.getInt("prefix_c2_id"));
                course.setCourseName(rs.getString("prefix_c2_course_name"));
                er.setCourse(course);

                Student student = new Student();
                student.setId(rs.getInt("prefix_s_id"));
                student.setStudentName(rs.getString("prefix_s_student_name"));
                student.setStudentNo(rs.getString("prefix_s_student_no"));
                student.setIdCard(rs.getString("prefix_s_id_card"));
                student.setStudentEmail(rs.getString("prefix_s_student_email"));
                er.setStudent(student);
                records.add(er);
            }
        } catch (Exception e) {
            throw new RuntimeException("查询考试成绩列表错误",e);
        }


        return records;
    }

    public static ExamRecord queryById(int id) {
        ExamRecord er = new ExamRecord();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = DBUtil.getConnection();
            String sql = "select er.id, " +
                    "       er.score, " +
                    "       er.student_id, " +
                    "       er.exam_id, " +
                    "       er.exam_record_desc, " +
                    "       er.create_time, " +
                    "       e.id           prefix_e_id, " +
                    "       e.exam_name    prefix_e_exam_name, " +
                    "       e.exam_desc    prefix_e_exam_desc, " +
                    "       c.id           prefix_c_id, " +
                    "       c.classes_name prefix_c_classes_name, " +
                    "       c2.id          prefix_c2_id, " +
                    "       c2.course_name prefix_c2_course_name," +
                    "       s.id           prefix_s_id, " +
                    "       student_name   prefix_s_student_name, " +
                    "       student_no     prefix_s_student_no, " +
                    "       id_card        prefix_s_id_card, " +
                    "       student_email  prefix_s_student_email " +
                    " from exam_record er " +
                    "         join exam e ON er.exam_id = e.id " +
                    "         join classes c on e.classes_id = c.id " +
                    "         join course c2 on e.course_id = c2.id " +
                    "         join student s on c.id = s.classes_id" +
                    "   where er.id=?";
            ps = c.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                //设置考试成绩对象
                er.setId(rs.getInt("id"));
                er.setScore(rs.getBigDecimal("score"));
                er.setExamRecordDesc(rs.getString("exam_record_desc"));
                er.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                //设置考试对象
                Exam e = new Exam();
                e.setId(rs.getInt("prefix_e_id"));
                e.setExamName(rs.getString("prefix_e_exam_name"));
                e.setExamDesc(rs.getString("prefix_e_exam_desc"));
                er.setExam(e);

                Classes classes = new Classes();
                classes.setId(rs.getInt("prefix_c_id"));
                classes.setClassesName(rs.getString("prefix_c_classes_name"));
                er.setClasses(classes);

                Course course = new Course();
                course.setId(rs.getInt("prefix_c2_id"));
                course.setCourseName(rs.getString("prefix_c2_course_name"));
                er.setCourse(course);

                Student student = new Student();
                student.setId(rs.getInt("prefix_s_id"));
                student.setStudentName(rs.getString("prefix_s_student_name"));
                student.setStudentNo(rs.getString("prefix_s_student_no"));
                student.setIdCard(rs.getString("prefix_s_id_card"));
                student.setStudentEmail(rs.getString("prefix_s_student_email"));
                er.setStudent(student);
            }
        } catch (Exception e) {
            throw new RuntimeException("查询考试成绩详情错误",e);
        }


        return er;
    }

    public static int insert(ExamRecord record) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = DBUtil.getConnection();
            String sql = "insert exam_record(exam_id,student_id,score) values (?,?,?)";
            ps = c.prepareStatement(sql);
            ps.setInt(1,record.getExamId());
            ps.setInt(2,record.getStudentId());
            ps.setBigDecimal(3,record.getScore());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("插入考试成绩错误",e);
        }
    }

    public static int update(ExamRecord record) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = DBUtil.getConnection();
            String sql = "update exam_record set exam_id=?,student_id=?,score=? where id=?";
            ps = c.prepareStatement(sql);
            ps.setInt(1,record.getExamId());
            ps.setInt(2,record.getStudentId());
            ps.setBigDecimal(3,record.getScore());
            ps.setInt(4,record.getId());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("修改考试成绩错误",e);
        }
    }

    public static int delete(String[] ids) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("delete from exam_record where id in (");
            for(int i = 0; i < ids.length;i++){
                if(i != 0){
                    sql.append(",");
                }
                sql.append("?");
            }
            sql.append(")");
            ps = c.prepareStatement(sql.toString());
            for(int i = 0;i < ids.length;i++){
                ps.setInt(i+1,Integer.parseInt(ids[i]));
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("删除考试成绩错误",e);
        }
    }
}
