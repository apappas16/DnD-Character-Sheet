package CharacterSheetMgmt;

import Main.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Weapon extends Equipment {
    //instance variables
    private int rollsForDmg; //1 in 1d8 dmg
    private int dieForDmg; // 8 in 1d8 dmg
    private String damageType;
    private ArrayList<String> properties;
    //private String weaponType; //Simple melee, martial melee, etc

    //constructors
    public Weapon(String name) {
        this.name = name;
        quantity = 1;
        ResultSet resultSet = Database.queryDB("SELECT * FROM weapons WHERE name='" + name + "'");
        try {
            if (resultSet != null) {
                // set dmg variables (e.g. 1d8)
                String dmgRoll = resultSet.getString("dmgRoll");
                String[] splitDmgRoll = dmgRoll.split("d");
                rollsForDmg = Integer.parseInt(splitDmgRoll[0]);
                dieForDmg = Integer.parseInt(splitDmgRoll[1]);
                //set dmg type
                damageType = resultSet.getString("dmgType");
                //set properties list
                String propertiesData = resultSet.getString("properties");
                if (propertiesData.contains(", "))
                    properties.addAll(Arrays.asList(resultSet.getString("properties").split(", ")));
                else
                    properties.add(propertiesData);

                cost = resultSet.getString("cost");
                weight = resultSet.getInt("weight");
            } else {
                System.out.println("No weapon with that name");
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    //methods
    public String toString() {
        String result = "Name: " + name + "\n";
        result += "Damage: " + rollsForDmg + "d" + dieForDmg + "\n";
        result += "Damage Type: " + damageType + "\n";
        result += "Properties: " + properties + "\n";
        return result;
    }


    //getters and setters
}
