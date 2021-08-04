package Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {
    // TODO add where clause option for retrieval queries?
    // TODO potentially have list of all columns for each table to replace long results.add() method calls

    public static ResultSet queryDB(String query) {
        try {
            Connection conn = DriverManager.getConnection(Main.dbURL, Main.dbUser, "");
            Statement statement = conn.createStatement();
            return statement.executeQuery(query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> retrieveWeaponsByType(String weaponType) {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        ResultSet resultSet = queryDB("SELECT * FROM weapons WHERE type LIKE'%" + weaponType + "%';");
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    ArrayList<String> resultRow = new ArrayList<>();
                    resultRow.add(resultSet.getString("name"));
                    resultRow.add(resultSet.getString("cost"));
                    resultRow.add(resultSet.getString("dmgRoll"));
                    resultRow.add(resultSet.getString("dmgType"));
                    resultRow.add(resultSet.getString("weight"));
                    resultRow.add(resultSet.getString("type"));
                    resultRow.add(resultSet.getString("properties"));
                    results.add(resultRow);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static ArrayList<String> retrieveWeaponsByName(String name) {
        ResultSet resultSet = queryDB("SELECT * FROM weapons WHERE name='" + name + "';");
        ArrayList<String> results = new ArrayList<>();
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    results.add(resultSet.getString("name"));
                    results.add(resultSet.getString("cost"));
                    results.add(resultSet.getString("dmgRoll"));
                    results.add(resultSet.getString("dmgType"));
                    results.add(resultSet.getString("weight"));
                    results.add(resultSet.getString("type"));
                    results.add(resultSet.getString("properties"));
                }
            } else {
                results.add("Query returned no results");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static String retrieveFeatureDesc(String featureName) {
        String query = "SELECT description FROM feature_desc WHERE name='" + featureName + "'";
        String result = "";
        try {
            ResultSet resultSet = queryDB(query);
            while (resultSet.next()) {
                result = resultSet.getString("description");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
