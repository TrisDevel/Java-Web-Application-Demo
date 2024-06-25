<%@page import="workshop2.Model.UserDTO"%>
<%@ page import="workshop2.Model.IngredientsDTO" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("usersession");
    if (user == null) {
        session.setAttribute("msg", "You need to log in to access this page.");
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/ingredientdetail.css">
        <title>Ingredient Detail</title>
    </head>
    <body>
        <div class="container">
            <h1>Ingredient Detail</h1>
            <form method="POST" action="IngredientController">
                <%
                    IngredientsDTO ingredient = (IngredientsDTO) request.getAttribute("ingredient");
                    String mode = (String) request.getAttribute("mode");
                    if (ingredient == null) {
                        ingredient = new IngredientsDTO();
                    }
                %>
                <label for="ingredientId">ID:</label>
                <% if ("edit".equals(mode) || "create".equals(mode)) {%>
                <input type="text" name="ingredientID" value="<%= ingredient.getIngredientID()%>"required><br>
                <% } else {%>
                <p><%= ingredient.getIngredientID()%></p>
                <% }%>
                <input type="hidden" name="action" value="<%= "create".equals(mode) ? "create" : "update"%>">

                <label for="ingredientName">Name:</label>
                <% if ("edit".equals(mode) || "create".equals(mode)) {%>
                <input type="text" id="ingredientName" name="ingredientName" value="<%= ingredient.getIngredientName()%>" required><br>
                <% } else {%>
                <p><%= ingredient.getIngredientName()%></p>
                <% } %>

                <label for="quantity">Quantity:</label>
                <% if ("edit".equals(mode) || "create".equals(mode)) {%>
                <input type="number" id="quantity" name="quantity" value="<%= ingredient.getQuantity()%>" required><br>
                <% } else {%>
                <p><%= ingredient.getQuantity()%></p>
                <% } %>

                <label for="unit">Unit:</label>
                <% if ("edit".equals(mode) || "create".equals(mode)) {%>
                <select id="unit" name="unit" required>
                    <option value="cups" <%= "cups".equals(ingredient.getUnit()) ? "selected" : ""%>>Cups</option>
                    <option value="lbs" <%= "lbs".equals(ingredient.getUnit()) ? "selected" : ""%>>Lbs</option>
                    <option value="units" <%= "units".equals(ingredient.getUnit()) ? "selected" : ""%>>Units</option>
                    <option value="gallons" <%= "gallons".equals(ingredient.getUnit()) ? "selected" : ""%>>Gallons</option>
                </select><br>
                <% } else {%>
                <p><%= ingredient.getUnit()%></p>
                <% } %>

                <div class="btn-container">
                    <% if ("edit".equals(mode)) { %>
                    <button type="submit" class="btn">Save</button>
                    <% } else if ("create".equals(mode)) { %>
                    <button type="submit" class="btn">Create</button>
                    <% } else {%>
                    <a href="IngredientController?action=edit&id=<%= ingredient.getIngredientID()%>" class="btn">Edit</a>
                    <% }%>
                    <a href="IngredientController" class="btn">Cancel</a>
                </div>
            </form>
        </div>
    </body>
</html>
