package TestCases;
import CharacterSheetMgmt.Character;

public class CharacterTestCase {
    public static void main(String[] args) {
        Character testChar = new Character();
        int mod;
        for (int i = 3; i < 21; i++) {
            mod = testChar.calcModifier(i);
            System.out.println(i + ": " + mod);
        }
    }
}
