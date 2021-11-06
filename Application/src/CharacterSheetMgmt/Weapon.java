package CharacterSheetMgmt;

import Main.Database;

import java.util.ArrayList;

public class Weapon extends Equipment {
    //instance variables
    private String dmgRoll;
    private String damageType;
    private String properties;
    private String weaponType;

    // TODO refactor class constructor
    // TODO fix checking if query worked;

    //constructors
    public Weapon(String name) {
        this.name = name;
        quantity = 1;
        ArrayList<String> wpnAttributes = Database.retrieveWeaponByName(name);
        cost = wpnAttributes.get(1);
        dmgRoll = wpnAttributes.get(2);
        damageType = wpnAttributes.get(3);
        weight = Integer.parseInt(wpnAttributes.get(4));
        weaponType = wpnAttributes.get(5);
        properties = wpnAttributes.get(6);
    }

    //methods
    public String toString() {
        String result = name.toUpperCase();
        result += "\n\tAttack Roll: " + dmgRoll;
        result += "\n\tDamage Type: " + damageType;
        result += "\n\tProperties: " + properties;
        result += "\n\tCost: " + cost;
        result += "\n\tWeight: " + weight;
        result += "\n\tQuantity: " + quantity;
        return result;
    }

    //getters and setters
    public String getDamageType() {
        return damageType;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public String getProperties() {
        return properties;
    }

    public String getDmgRoll() {
        return dmgRoll;
    }

    public void setDmgRoll(String dmgRoll) {
        this.dmgRoll = dmgRoll;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }
}
