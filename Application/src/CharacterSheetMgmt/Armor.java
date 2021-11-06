package CharacterSheetMgmt;

import Main.Database;

import java.util.ArrayList;

public class Armor extends Equipment {
    // instance variables
    private int baseAC;
    private boolean addDexMod;
    private boolean disadvOnStealth;
    private String armorType;


    //constructors
    public Armor() {
    }

    public Armor(String name) {
        ArrayList<String> armorAttributes = Database.retrieveArmorByName(name);
        cost = armorAttributes.get(0);
        baseAC = Integer.parseInt(armorAttributes.get(1));
        addDexMod = Boolean.parseBoolean(armorAttributes.get(2));
        disadvOnStealth = Boolean.parseBoolean(armorAttributes.get(3));
        weight = Integer.parseInt(armorAttributes.get(4));
        armorType = armorAttributes.get(5);
    }

    //methods



    //getters and setters
    public String getArmorType() {
        return armorType;
    }

    public void setArmorType(String armorType) {
        this.armorType = armorType;
    }

    public boolean isDisadvOnStealth() {
        return disadvOnStealth;
    }

    public void setDisadvOnStealth(boolean disadvOnStealth) {
        this.disadvOnStealth = disadvOnStealth;
    }

    public boolean isAddDexMod() {
        return addDexMod;
    }

    public void setAddDexMod(boolean addDexMod) {
        this.addDexMod = addDexMod;
    }

    public int getBaseAC() {
        return baseAC;
    }

    public void setBaseAC(int baseAC) {
        this.baseAC = baseAC;
    }
}
