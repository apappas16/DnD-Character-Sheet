import java.util.Scanner;

public class Main {

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
        // TODO force user input to be correct
        Character newChar = new Character();
        Scanner scan = new Scanner(System.in);
        System.out.println("What is yours character's name?");
        newChar.setName(scan.nextLine());

        // TODO classify the races?
        System.out.println("What is your character's race?");
        System.out.println("Type out one of the following races...");
        printRaceOptions();
        newChar.setRace(scan.nextLine());

        // TODO classify the classes?
        System.out.println("What is your character's class?");
        System.out.println("Type out one of the following classes...");
        printClassOptions();
        newChar.setCharClass(scan.nextLine());

        // TODO classify the backgrounds?
        System.out.println("What is your character's background?");
        System.out.println("Type out one of the following backgrounds...");
        printBackgroundOptions();
        newChar.setBackground(scan.nextLine());
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
}
