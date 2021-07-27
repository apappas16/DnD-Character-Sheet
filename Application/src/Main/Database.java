package Main;

import java.sql.*;

public class Database {
    // TODO add where clause option for retrieval queries?

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

    public static String retrieveFromWeaponsTable(String weaponType) {
        StringBuilder result = new StringBuilder();
        ResultSet resultSet = queryDB("SELECT * FROM " + weaponType);
        try {
            if (resultSet != null) {
                int numResults = 1;
                while (resultSet.next()) {
                    result.append(numResults).append(". ").append(resultSet.getString("name")).append(" - ").append(resultSet.getString("cost")).append(", ").append(resultSet.getString("dmgRoll")).append(" ").append(resultSet.getString("dmgType")).append(", ").append(resultSet.getInt("weight")).append("lbs.\n").append("\tproperties: ").append(resultSet.getString("properties")).append("\n");
                    numResults++;
                }
            } else {
                result.append("Query returned no results");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
