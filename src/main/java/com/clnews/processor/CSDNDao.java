package com.clnews.processor;

import com.clnews.processor.CsdnBlog;

import java.sql.*;

/**
 * @program: cl-news
 * @description:
 * @analysis:
 * @author: 李学亮    email: 18222027300@163.com
 * @create: 2019-03-17 19:35
 **/
public class CSDNDao {
    private Connection conn = null;
    private Statement stmt = null;

    public CSDNDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/clnews?user=root&password=19960130&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int add(CsdnBlog csdnBlog) {
        try {
            String sql =
                    "INSERT INTO `webmagic`.`csdnblog` (`id`, `title`, `date`, `tags`, `category`, `view`, `comments`, `copyright`)"
                            + "VALUES(?,?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, csdnBlog.getId());
            ps.setString(2, csdnBlog.getTitle());
            ps.setString(3, csdnBlog.getDate());
            ps.setString(4, csdnBlog.getTags());
            ps.setString(5, csdnBlog.getCategory());
            ps.setInt(6, csdnBlog.getView());
            ps.setInt(7, csdnBlog.getComments());
            ps.setInt(8, csdnBlog.getCopyright());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
