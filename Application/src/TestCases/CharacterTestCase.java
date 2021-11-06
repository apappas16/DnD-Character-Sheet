package TestCases;
import CharacterSheetMgmt.Character;
import CharacterSheetMgmt.Equipment;
import CharacterSheetMgmt.Weapon;
import Main.Database;
import Main.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class CharacterTestCase {
    public static void main(String[] args) throws SQLException {
        //Character testChar = new Character("Human", "Fighter", "Acolyte");
        ArrayList<String> inventory = new ArrayList<>(Arrays.asList("50 gold", "silver sword", "ballsack"));
        Equipment pack = new Equipment("Burglar's Pack");
        Equipment eq = new Equipment("Spyglass", "5gp", 3, 1, "Used to see better a long distance");
        ArrayList<Equipment> equipment = new ArrayList<>(Arrays.asList(pack, eq));
        //testChar.setInventory(inventory);
        //testChar.setEquipment(equipment);
        //testChar.printAllItems();

        //Main.printInstrumentsMenu();

        System.out.println("Choose a starting weapon from this list:");
        System.out.println("Type out the weapon name as you see it\n");
        System.out.println(Database.retrieveWeaponByName("Rapier"));
        System.out.println(Database.retrieveWeaponByName("Longsword"));
        System.out.println(Database.retrieveWeaponsByType("simple"));

        ArrayList<String> testList = Database.retrieveWeaponByName("Greatsword");
        System.out.println(testList);

        Weapon testWpn = new Weapon("Greatsword");
        equipment.add(testWpn);
        testWpn = new Weapon("Handaxe");
        equipment.add(testWpn);
        System.out.println(equipment);
    }
}
