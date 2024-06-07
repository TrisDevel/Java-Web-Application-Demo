package ClothesOnlineShop.User;

import ClothesOnlineShop.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public UserDTO login(String email, String password) {
        String sql = "SELECT email, firstName FROM Customer WHERE email = ? AND customerPassword = ?";
        UserDTO user = null;

        try {
            Connection conn = DBUtils.getConnection();
            if (conn != null) {
                System.out.println("Database connection successful");  // Log kết nối cơ sở dữ liệu thành công
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs != null && rs.next()) {
                    System.out.println("User found in database");  // Log tìm thấy người dùng
                    user = new UserDTO();
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("firstName"));
                } else {
                    System.out.println("No user found with given credentials");  // Log không tìm thấy người dùng
                }
                rs.close();
                ps.close();
                conn.close();
            } else {
                System.out.println("Failed to make connection!");  // Log thất bại kết nối
            }
        } catch (SQLException e) {
            System.out.println("Error in DAO: " + e.getMessage());
        }
        return user;
    }
}
