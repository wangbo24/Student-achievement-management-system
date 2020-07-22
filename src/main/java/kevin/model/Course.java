package kevin.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 课程信息
 */
@Getter
@Setter
@ToString
public class Course {
    
    private Integer id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 创建时间
     */
    private Date createTime;
}