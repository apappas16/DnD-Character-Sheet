package Main;

import CharacterSheetMgmt.Character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

// TODO refactor
// TODO user input type validation
// TODO add inspiration?
// TODO configure db to be accessed remotely?
// TODO configure db tables into CSV files??
// TODO add class or methods to display everything nicely to user

public class Main {

    public final static String dbURL = "jdbc:mysql://localhost:3306/dnd_db";
    public final static String dbUser = "reg_user";

    public static void main(String[] args) {
        boolean nextMenu = false;
        do {
            printStartMenu();
            Scanner scan = new Scanner(System.in);
            try {
                int input = Integer.parseInt(scan.next());
                if (input == 1) {
                    createCharacter();
                }
                else if (input == 2) {
                    loadCharacter();
                }
                else {
                    printInputError();
                }
            } catch (NumberFormatException e) {
                printInputError();
            }
        } while (!nextMenu);
    }

    private static void createCharacter() {
        Scanner scan = new Scanner(System.in);
        //System.out.println("What is yours character's name?");
        //String name = scan.nextLine();

        System.out.println("Choose a race?");
        System.out.println("Type out one of the following races below");
        printRaceOptions();
        String race = scan.nextLine();

        System.out.println("Choose a class?");
        System.out.println("Type out one of the following classes below");
        printClassOptions();
        String charClass = scan.nextLine();

        System.out.println("Choose a background for your character");
        System.out.println("Type out one of the following backgrounds below");
        printBackgroundOptions();
        String background = scan.nextLine();

        Character newChar = new Character(race, charClass, background);



        System.out.println("Here's your character so far:");
        System.out.println(newChar);
    }

    public static int rollDie(int diceType) {
        return (int)(Math.random()*diceType + 1);
    }

    public static int rollDie(int numRolls, int diceType) {
        int rollSum = 0;
        while (numRolls != 0) {
            rollSum += (int)(Math.random() * diceType + 1);
            numRolls--;
        }
        return rollSum;
    }

    private static void printBackgroundOptions() {
        System.out.println("\n*******Background Options*******");
        System.out.println("- Acolyte");
        System.out.println("- Charlatan");
        System.out.println("- Criminal");
        System.out.println("- Entertainer");
        System.out.println("- Folk Hero");
        System.out.println("- Guild Artisan");
        System.out.println("- Hermit");
        System.out.println("- Noble");
        System.out.println("- Outlander");
        System.out.println("- Sage");
        System.out.println("- Sailor");
        System.out.println("- Soldier");
        System.out.println("- Urchin\n");
    }

    private static void printClassOptions() {
        System.out.println("\n*******Class Options*******");
        System.out.println("- Barbarian");
        System.out.println("- Bard");
        System.out.println("- Cleric");
        System.out.println("- Druid");
        System.out.println("- Fighter");
        System.out.println("- Monk");
        System.out.println("- Paladin");
        System.out.println("- Ranger");
        System.out.println("- Rogue");
        System.out.println("- Sorcerer");
        System.out.println("- Warlock");
        System.out.println("- Wizard\n");
    }

    private static void printRaceOptions() {
        System.out.println("\n*******Race Options*******");
        System.out.println("- Dwarf");
        System.out.println("- Elf");
        System.out.println("- Halfling");
        System.out.println("- Human");
        System.out.println("- Dragonborn");
        System.out.println("- Gnome");
        System.out.println("- Half-Elf");
        System.out.println("- Half-Orc");
        System.out.println("- Tiefling\n");
    }

    private static void printInputError() {
        System.out.println("\nInput error. Please try again.\n");
    }

    private static void loadCharacter() {
    }

    private static void printStartMenu() {
        System.out.println("1. Create new character");
        System.out.println("2. Load existing character");
    }

    public static void printAllInstruments() {
        System.out.println("\n\nInstruments:");
        System.out.println("- Bagpipes \t 30gp \t 6lb");
        System.out.println("- Drum \t 6gp \t 3lb");
        System.out.println("- Dulcimer\t 25gp \t 10lb");
        System.out.println("- Flute\t 2gp \t 1lb");
        System.out.println("- Lute\t 35gp \t 2lb");
        System.out.println("- Lyre\t 30gp \t 2lb");
        System.out.println("- Horn\t 3gp \t 2lb");
        System.out.println("- Pan Flute\t 12gp \t 2lb");
        System.out.println("- Shawm\t 2gp \t 1lb");
        System.out.println("- Viol\t 30gp \t 1lb");
    }

    public static void printAllSkills() {
        System.out.println("\n\nSkills:");
        System.out.println("Acrobatics (Dex)");
        System.out.println("Animal Handling (Wis)");
        System.out.println("Arcana (Int)");
        System.out.println("Athletics (Str)");
        System.out.println("Deception (Cha)");
        System.out.println("History (Int)");
        System.out.println("Insight (Wis)");
        System.out.println("Intimidation (Cha)");
        System.out.println("Investigation (Int)");
        System.out.println("Medicine (Wis)");
        System.out.println("Nature (Int)");
        System.out.println("Perception (Wis)");
        System.out.println("Performance (Cha)");
        System.out.println("Persuasion (Cha)");
        System.out.println("Religion (Int)");
        System.out.println("Sleight of Hand (Dex)");
        System.out.println("Stealth (Dex)");
        System.out.println("Survival (Wis)");
    }

    public static void displayList(ArrayList<String> list) {
        System.out.print("- ");
        for (String item : list) {
            System.out.print(item + "\t");
        }
        System.out.println("");
    }

    public static void display2DList(ArrayList<ArrayList<String>> list) {
        for (ArrayList<String> row : list) {
            System.out.print("- ");
            for (String col : row) {
                System.out.print(col + "\t");
            }
            System.out.println("");
        }
    }

    public static void printArtisanTools() {
        System.out.println("\n\nArtisan Tools:");
        System.out.println("- Alchemist's supplies \t 50gp \t 8lb");
        System.out.println("- Brewer's supplies \t 20gp \t 9lb");
        System.out.println("- Calligrapher's supplies \t 10gp \t 5lb");
        System.out.println("- Carpenter's supplies \t 8gp \t 6lb");
        System.out.println("- Cartographer's supplies \t 15gp \t 6lb");
        System.out.println("- Cobbler's tools \t 5gp \t 5lb");
        System.out.println("- Cook's utensils \t 1gp \t 8lb");
        System.out.println("- Glassblower's tools \t 30gp \t 5lb");
        System.out.println("- Jewler's tools \t 25gp \t 2lb");
        System.out.println("- Leatherworker's tools \t 5gp \t 5lb");
        System.out.println("- Mason's tools \t 10gp \t 8lb");
        System.out.println("- Navigator's tools \t 25gp \t 2lb");
        System.out.println("- Painter's supplies \t 10gp \t 5lb");
        System.out.println("- Potter's tools \t 10gp \t 3lb");
        System.out.println("- Smith's tools \t 20gp \t 8lb");
        System.out.println("- Thieves' tools \t 25gp \t 1lb");
        System.out.println("- Tinker's tools \t 50gp \t 10lb");
        System.out.println("- Weaver's tools \t 1gp \t 5lb");
        System.out.println("- Woodcarver's tools \t 1gp \t 5lb");
    }
}
