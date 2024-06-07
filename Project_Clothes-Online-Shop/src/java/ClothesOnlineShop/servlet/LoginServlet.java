/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClothesOnlineShop.servlet;

import ClothesOnlineShop.User.UserDAO;
import ClothesOnlineShop.User.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author buitr
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin từ form đăng nhập
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO DAO = new UserDAO();
        UserDTO user = DAO.login(email, password);
        if (user != null) {
            request.setAttribute("usersession", user);
            RequestDispatcher rd = request.getRequestDispatcher("/home.html");
            rd.forward(request, response);
        } else {
            response.sendRedirect("login.html?error=1");
        }
    }
}
