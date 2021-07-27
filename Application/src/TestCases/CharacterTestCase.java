package TestCases;
import CharacterSheetMgmt.Character;
import Main.Database;

public class CharacterTestCase {
    public static void main(String[] args) {
        Character testChar = new Character();
        int mod;
        for (int i = 3; i < 21; i++) {
            mod = testChar.calcModifier(i);
            System.out.println(i + ": " + mod);
        }
        // System.out.println(Database.retrieveFromWeaponsTable("martial_melee_weapons"));

        String testStr = "1d87";
        String[] newStr = testStr.split("d");
        System.out.println(newStr[0]);
        System.out.println(newStr[1]);

        testChar.configureCharClass("Barbarian");
        System.out.println(testChar);
    }
}
