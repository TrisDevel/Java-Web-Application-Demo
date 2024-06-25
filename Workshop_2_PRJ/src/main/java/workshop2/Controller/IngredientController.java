package workshop2.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import workshop2.DAO.IngredientDAO;
import workshop2.Model.IngredientsDTO;
import workshop2.Model.UserDTO;

@WebServlet(name = "IngredientController", urlPatterns = {"/IngredientController"})
public class IngredientController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usersession") == null) {
                session = request.getSession();
                session.setAttribute("msg", "You need to log in to access this page.");
                response.sendRedirect("login.jsp");
                return;
            } else {
                IngredientDAO ingredientDAO = new IngredientDAO();
                List<IngredientsDTO> ingredientsList = ingredientDAO.getAllIngredients();

                request.setAttribute("ingredientsList", ingredientsList);
                String message = (String) session.getAttribute("message");
                if (message != null) {
                    request.setAttribute("message", message);
                    session.removeAttribute("message");
                }
                RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
                rd.forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        if ("edit".equals(action)) {
            int ingredientID = Integer.parseInt(request.getParameter("id"));
            IngredientDAO ingredientDAO = new IngredientDAO();
            IngredientsDTO ingredient = ingredientDAO.getIngredientById(ingredientID);
            request.setAttribute("ingredient", ingredient);
            request.setAttribute("mode", "edit");
            RequestDispatcher rd = request.getRequestDispatcher("ingredientdetail.jsp");
            rd.forward(request, response);
        } else if ("view".equals(action)) {
            int ingredientID = Integer.parseInt(request.getParameter("id"));
            IngredientDAO ingredientDAO = new IngredientDAO();
            IngredientsDTO ingredient = ingredientDAO.getIngredientById(ingredientID);
            request.setAttribute("ingredient", ingredient);
            request.setAttribute("mode", "view");
            RequestDispatcher rd = request.getRequestDispatcher("ingredientdetail.jsp");
            rd.forward(request, response);
        } else if ("delete".equals(action)) {
            int ingredientID = Integer.parseInt(request.getParameter("id"));
            IngredientDAO ingredientDAO = new IngredientDAO();
            boolean deleted = ingredientDAO.deleteIngredient(ingredientID);
            if (deleted) {
                HttpSession session = request.getSession();
                session.setAttribute("message", "Ingredient removed successfully.");
                response.sendRedirect("IngredientController");
            } else {
                // Handle delete failure (if needed)
            }
        } else if ("create".equals(action)) {
            request.setAttribute("mode", "create");
            RequestDispatcher rd = request.getRequestDispatcher("ingredientdetail.jsp");
            rd.forward(request, response);
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            int ingredientID = Integer.parseInt(request.getParameter("ingredientID"));
            String ingredientName = request.getParameter("ingredientName");
            double quantity = Double.parseDouble(request.getParameter("quantity"));
            String unit = request.getParameter("unit");

            IngredientsDTO ingredient = new IngredientsDTO(ingredientID, ingredientName, quantity, unit);
            IngredientDAO ingredientDAO = new IngredientDAO();
            boolean updated = ingredientDAO.updateIngredient(ingredient);

            if (updated) {
                HttpSession session = request.getSession();
                session.setAttribute("message", "Ingredient updated successfully.");
                response.sendRedirect("IngredientController");
            } else {
                // Handle update failure (if needed)
            }
        } else if ("create".equals(action)) {
            int ingredientID = Integer.parseInt(request.getParameter("ingredientID"));
            String ingredientName = request.getParameter("ingredientName");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String unit = request.getParameter("unit");

            IngredientsDTO newIngredient = new IngredientsDTO(ingredientID, ingredientName, quantity, unit);
            IngredientDAO ingredientDAO = new IngredientDAO();
            boolean created = ingredientDAO.createIngredient(newIngredient);

            if (created) {
                HttpSession session = request.getSession();
                session.setAttribute("message", "Ingredient created successfully.");
                response.sendRedirect("IngredientController");
            } else {
                // Handle creation failure (if needed)
            }
        } else {
            processRequest(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
