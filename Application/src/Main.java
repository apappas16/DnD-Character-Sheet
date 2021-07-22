import CharacterSheetMgmt.Character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

// TODO refactor
// TODO force user input type

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

        System.out.println("Would you like to roll for stats or use predetermined stats?");
        System.out.println("Type 1 or 2 respectively...");
        System.out.println("1. Roll for stats");
        System.out.println("2. Predetermine stats");
        int statInput = scan.nextInt();
        if (statInput == 1) {
            getRolledStats(newChar);
        }
        else if (statInput == 2) {
            assignStats(newChar);
        }

        System.out.println("Here's your character so far:");
        System.out.println(newChar);
    }

    private static void assignStats(Character character) {
        System.out.println("Choose each of these following numbers to one of your abilities...");
        ArrayList<Integer> scores = new ArrayList<>(Arrays.asList(15, 14, 13, 12, 10, 8));
        ArrayList<String> abilities = new ArrayList<>(Arrays.asList("Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"));
        Scanner scan = new Scanner(System.in);

        for (int i = 0; i < abilities.size(); i++) {
            System.out.println("Type out which score to apply to " + abilities.get(i));
            for (int score : scores) {
                System.out.print(score + "  ");
            }
            int input = scan.nextInt();
            int modifier;
            if (scores.contains(input)) {
                switch (i) {
                    case 0:
                        character.setStrength(input);
                        modifier = character.calcModifier(input);
                        character.setStrengthMod(modifier);
                        break;
                    case 1:
                        character.setDexterity(input);
                        modifier = character.calcModifier(input);
                        character.setDexterityMod(modifier);
                        break;
                    case 2:
                        character.setConstitution(input);
                        modifier = character.calcModifier(input);
                        character.setCharismaMod(modifier);
                        break;
                    case 3:
                        character.setIntelligence(input);
                        modifier = character.calcModifier(input);
                        character.setIntelligenceMod(modifier);
                        break;
                    case 4:
                        character.setWisdom(input);
                        modifier = character.calcModifier(input);
                        character.setWisdomMod(modifier);
                        break;
                    case 5:
                        character.setCharisma(input);
                        modifier = character.calcModifier(input);
                        character.setCharismaMod(modifier);
                        break;
                }
                scores.remove(scores.indexOf(input));
            }
        }
    }

    private static void getRolledStats(Character character) {
        System.out.println("Rolling for stats...");
        //Roll for Str score and set modifier
        int score = rollStats();
        character.setStrength(score);
        int modifier = character.calcModifier(score);
        character.setStrengthMod(modifier);

        //Roll for Dex score and set modifier
        score = rollStats();
        character.setDexterity(score);
        modifier = character.calcModifier(score);
        character.setDexterityMod(modifier);

        //Roll for Const score and set modifier
        score = rollStats();
        character.setConstitution(score);
        modifier = character.calcModifier(score);
        character.setConstitutionMod(modifier);

        //Roll for Int score and set modifier
        score = rollStats();
        character.setIntelligence(score);
        modifier = character.calcModifier(score);
        character.setIntelligenceMod(modifier);

        //Roll for Wis score and set modifier
        score = rollStats();
        character.setWisdom(score);
        modifier = character.calcModifier(score);
        character.setWisdomMod(modifier);

        //Roll for Char score and set modifier
        score = rollStats();
        character.setCharisma(score);
        modifier = character.calcModifier(score);
        character.setCharismaMod(modifier);
    }

    public static int rollStats() {
        ArrayList<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rolls.add(rollD6());
        }
        //remove smallest rolls
        Collections.sort(rolls);
        rolls.remove(0);
        //sum highest 3 rolls
        int sum = 0;
        for (int roll : rolls) {
            sum = sum + roll;
        }
        return sum;
    }

    private static int rollD6() {
        return (int)(Math.random()*6 + 1);
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
