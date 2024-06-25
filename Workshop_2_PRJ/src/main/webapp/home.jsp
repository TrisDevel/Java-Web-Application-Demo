<%@page import="java.util.List"%>
<%@ page import="workshop2.Model.UserDTO, workshop2.Model.IngredientsDTO, workshop2.DAO.IngredientDAO" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/home.css">
    <title>Ingredients List</title>
    <style>
        .popup {
            position: fixed;
            top: 20px;
            right: 20px;
            background-color: #4caf50;
            color: white;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            display: none;
            z-index: 1000;
        }
    </style>
</head>
<body>
    <%
        UserDTO user = (UserDTO) session.getAttribute("usersession");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        List<IngredientsDTO> ingredientsList = (List<IngredientsDTO>) request.getAttribute("ingredientsList");
    %>

    <p>Welcome, <%= user.getName()%>!</p>
    <div class="container">
        <h1>Ingredients List</h1>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Unit</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (IngredientsDTO ingredient : ingredientsList) {
                %>
                <tr>
                    <td><%= ingredient.getIngredientID()%></td>
                    <td><%= ingredient.getIngredientName()%></td>
                    <td><%= ingredient.getQuantity()%></td>
                    <td><%= ingredient.getUnit()%></td>
                    <td>
                        <a href="IngredientController?action=view&id=<%= ingredient.getIngredientID()%>">
                            <img src="images/edit-icon.png" alt="Edit" width="20" height="20">
                        </a>
                        <a style="margin-left: 20px" href="IngredientController?action=delete&id=<%= ingredient.getIngredientID()%>">
                            <img src="images/remove-icon.jpg" alt="Edit" width="20" height="20" >
                        </a>
                    </td>
                </tr>
                <% }%>
            </tbody>
        </table>
        <a href="IngredientController?action=create" class="create-button">Create New Ingredient</a>
        <a href="login?action=logout" class="logout-button">Logout</a>
    </div>

    <!-- Popup for notifications -->
    <div id="popup" class="popup"></div>

    <script>
        function showPopup(message) {
            var popup = document.getElementById("popup");
            popup.innerHTML = message;
            popup.style.display = "block";
            setTimeout(function() {
                popup.style.display = "none";
            }, 3000);
        }

        // Check if a message is set in the request scope and display the popup
        window.onload = function() {
            <% if (request.getAttribute("message") != null) { %>
                showPopup("<%= request.getAttribute("message") %>");
            <% } %>
        };
    </script>
</body>
</html>
