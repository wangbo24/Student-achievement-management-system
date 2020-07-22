package kevin.dao;

import kevin.model.Classes;
import kevin.model.Course;
import kevin.model.Exam;
import kevin.model.User;
import kevin.util.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static User query(User user) {
        User queryUser = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = DBUtil.getConnection();
            String sql = "select id,nickname from user where username=? and password=?";

            ps = c.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            rs = ps.executeQuery();
            while(rs.next()){
                queryUser = user;
                queryUser.setId(rs.getInt("id"));
                queryUser.setNickname(rs.getString("nickname"));
            }
        } catch (Exception e) {
            throw new RuntimeException("用户登陆操作错误",e);
        }
        return queryUser;
    }
}
