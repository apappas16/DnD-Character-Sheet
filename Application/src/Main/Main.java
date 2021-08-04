package Main;

import CharacterSheetMgmt.Character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

// TODO refactor
// TODO force user input type everywhere
// TODO add inspiration?
// TODO configure db to be accessed remotely?
// TODO configure db tables into CSV files??
// TODO add class or methods to display everything nicely to user

/*Character creation
1. Choose race
2. Choose class
3. Choose background
4. Stat block
    1. ability scores
    2. ability modifiers
    3. proficiency modifier
    4. saving throws
    5. skill scores
    6. passive perception
5. Proficiencies and languages
    1. proficiencies (class)
    2. languages (race and background)
6. Equipment
    1. list all equipment, weapons, armor, personal items
    2. money
7. Attacks and Spellcasting
    1. physical weapon names
    2. attack modifiers
    3. damage
    4. special actions
8. HP and combat stats
    1. AC
    2. Initiative
    3. Speed
    4. Hit Dice
    5. Max HP
    6. Current HP
    7. Temporary HP - leave blank
    8. Death Saves
9. Features
    1. List all remaining features of class, race, background
    2. Add any additional skills, passive benefits, or relevant bonuses
10. Traits - Roll or decide yourself
    1. Personality Traits
    2. Ideals
    3. Bonds
    4. Flaws
11. Name and remaining info
    1. Name
    2. Level
    3. Player Name
    4. Alignment
*/

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
        System.out.println("What is yours character's name?");
        String name = scan.nextLine();

        // TODO classify the races?
        System.out.println("What is your character's race?");
        System.out.println("Type out one of the following races...");
        printRaceOptions();
        String race = scan.nextLine();

        // TODO classify the classes?
        System.out.println("What is your character's class?");
        System.out.println("Type out one of the following classes...");
        printClassOptions();
        String charClass = scan.nextLine();

        System.out.println("What is your character's background?");
        System.out.println("Type out one of the following backgrounds...");
        printBackgroundOptions();
        String background = scan.nextLine();

        Character newChar = new Character(name, race, charClass, background);

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
            rolls.add(rollDie(6));
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
        System.out.println("\n\nInstruments: ");
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
}
