package TestCases;
import CharacterSheetMgmt.Character;
import CharacterSheetMgmt.Equipment;
import Main.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class CharacterTestCase {
    public static void main(String[] args) throws SQLException {
        Character testChar = new Character("Alex", "Human", "Fighter", "Acolyte");
        ArrayList<String> inventory = new ArrayList<>(Arrays.asList("50 gold", "silver sword", "ballsack"));
        Equipment pack = new Equipment("Burglar's Pack");
        Equipment eq = new Equipment("Spyglass", "5gp", 3, 1, "Used to see better a long distance");
        ArrayList<Equipment> equipment = new ArrayList<>(Arrays.asList(pack, eq));
        testChar.setInventory(inventory);
        testChar.setEquipment(equipment);
        testChar.printAllItems();
    }
}
