package workshop2.DAO;

import workshop2.Model.IngredientsDTO;
import workshop2.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {

    // Create an ingredient
    public boolean createIngredient(IngredientsDTO ingredient) {
        String sql = "INSERT INTO Ingredients (IngredientID, IngredientName, Quantity, Unit) VALUES (?, ?, ?, ?)";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ingredient.getIngredientID());
            ps.setString(2, ingredient.getIngredientName());
            ps.setDouble(3, ingredient.getQuantity());
            ps.setString(4, ingredient.getUnit());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Read all ingredients
    public List<IngredientsDTO> getAllIngredients() {
        List<IngredientsDTO> ingredientsList = new ArrayList<>();
        String sql = "SELECT IngredientID, IngredientName, Quantity, Unit FROM Ingredients";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int ingredientID = rs.getInt("IngredientID");
                String ingredientName = rs.getString("IngredientName");
                double quantity = rs.getDouble("Quantity");
                String unit = rs.getString("Unit");
                IngredientsDTO ingredient = new IngredientsDTO(ingredientID, ingredientName, quantity, unit);
                ingredientsList.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientsList;
    }

    // Read an ingredient by ID
    public IngredientsDTO getIngredientById(int ingredientID) {
        String sql = "SELECT IngredientID, IngredientName, Quantity, Unit FROM Ingredients WHERE IngredientID = ?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ingredientID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String ingredientName = rs.getString("IngredientName");
                    double quantity = rs.getDouble("Quantity");
                    String unit = rs.getString("Unit");
                    return new IngredientsDTO(ingredientID, ingredientName, quantity, unit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an ingredient
    public boolean updateIngredient(IngredientsDTO ingredient) {
        String sql = "UPDATE Ingredients SET IngredientName = ?, Quantity = ?, Unit = ? WHERE IngredientID = ?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ingredient.getIngredientName());
            ps.setDouble(2, ingredient.getQuantity());
            ps.setString(3, ingredient.getUnit());
            ps.setInt(4, ingredient.getIngredientID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete an ingredient
    public boolean deleteIngredient(int ingredientID) {
        String sql = "DELETE FROM Ingredients WHERE IngredientID = ?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ingredientID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
