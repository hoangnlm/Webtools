package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 * DAO Model
 * @created on Dec 23, 2016, 9:58:17 PM
 * @author HoangNLM
 */
public class UserDao {

    private DbConnect db = new DbConnect();

    public List<User> get() {
        List<User> list = new ArrayList<>();
        String sql = "select * from user";
        try (
                Connection con = db.getConnection();
                ResultSet rs = con.createStatement().executeQuery(sql);) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullname(rs.getString("fullname"));
                user.setLevel(rs.getInt("level"));
                list.add(user);
            }
        } catch (Exception ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public long insert(User model) {
        String sql = "insert into user(username,password,fullname,level) values(?,?,?,?)";
        try (
                Connection con = db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, model.getUsername());
            ps.setString(2, model.getPassword());
            ps.setString(3, model.getFullname());
            ps.setInt(4, model.getLevel());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getLong(1) : -1;
        } catch (Exception ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public boolean update(User model) {
        String sql = "update user set username=?,password=?,fullname=?,level=? where id=?";
        try (
                Connection con = db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, model.getUsername());
            ps.setString(2, model.getPassword());
            ps.setString(3, model.getFullname());
            ps.setInt(4, model.getLevel());
            ps.setInt(5, model.getId());
            int result = ps.executeUpdate();
            return result != 0;
        } catch (Exception ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(User model) {
        String sql = "delete from user where id=?";
        try (
                Connection con = db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, model.getId());
            int result = ps.executeUpdate();
            return result != 0;
        } catch (Exception ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
