package kevin.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 学生考试记录
 */
@Getter
@Setter
@ToString
public class ExamRecord {
    
    private Integer id;

    /**
     * 考试成绩
     */
    private BigDecimal score;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 考试id
     */
    private Integer examId;

    /**
     * 考试记录备注
     */
    private String examRecordDesc;

    /**
     * 创建时间
     */
    private Date createTime;
    private Exam exam;
    private Classes classes;
    private Course course;
    private Student student;
}