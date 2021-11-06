package CharacterSheetMgmt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import Main.Main;

import Main.Database;

public class Character {
    // instance variables
    private String name;
    private String charClass;
    private String race;
    private int level;
    private String background;
    private int maxHP;
    private int currHP;
    private String alignment;
    private int strength;
    private int strengthMod;
    private int dexterity;
    private int dexterityMod;
    private int constitution;
    private int constitutionMod;
    private int intelligence;
    private int intelligenceMod;
    private int wisdom;
    private int wisdomMod;
    private int charisma;
    private int charismaMod;
    private int profBonus;
    private int totalAC;
    private int speed;
    private int gold;
    private int passPerception;
    private int deathSaveSucc;
    private int deathSaveFail;
    private int passivePerception;
    private ArrayList<String> skills;
    private String hitDie;
    private ArrayList<Equipment> equipment;
    private ArrayList<String> otherProfsLangs;
    private ArrayList<String> inventory;
    private ArrayList<Feature> features;
    private ArrayList<String> savingThrows;

    //Use these for querying weapon table for specific weapon type:
    private final String MARTIAL_MELEE = "martial_melee";
    private final String MARTIAL_RANGED = "martial_ranged";
    private final String SIMPLE_MELEE = "simple_melee";
    private final String SIMPLE_RANGED = "simple_ranged";
    private final String SIMPLE = "simple";
    private final String MARTIAL = "martial";

    // TODO flaws, bonds, ideals, traits
    // TODO character description (age, height, weight, etc)
    // TODO spells
    // TODO character level up
    // TODO keep track of variables specific to characters and their features?
    // TODO add different feature paths when leveling up
    // TODO refactor how user sees character creation (print out what they start with)


    // constructors
    public Character() {
        level = 1;
        deathSaveFail = 0;
        deathSaveSucc = 0;
        equipment = new ArrayList<>();
        otherProfsLangs = new ArrayList<>();
        skills = new ArrayList<>();
        inventory = new ArrayList<>();
        profBonus = 2;
    }

    public Character(String race, String charClass, String background) {
        Scanner scan = new Scanner(System.in);
        this.race = race;
        this.charClass = charClass;
        this.background = background;
        profBonus = 2;
        level = 1;
        deathSaveFail = 0;
        deathSaveSucc = 0;
        //equipment = new ArrayList<>();
        //otherProfsLangs = new ArrayList<>();
        //skills = new ArrayList<>();
        //savingThrows = new ArrayList<>();
        //features = new ArrayList<>();
        //inventory = new ArrayList<>();
        System.out.println("Would you like to roll for stats or use predetermined stats?");
        System.out.println("Type 1 or 2 respectively...");
        System.out.println("1. Roll for stats");
        System.out.println("2. Predetermine stats");
        int statInput = scan.nextInt();
        if (statInput == 1) {
            getRolledStats();
        }
        else if (statInput == 2) {
            assignStats();
        }

        //saving throws
        String savingThrowsQuery = Database.retrieveColumnFromTable("class", charClass, "saving_throws");
        savingThrows.addAll(Arrays.asList(savingThrowsQuery.split(", ")));

        //skill scores
        int numSkillsProf = Integer.parseInt(Database.retrieveColumnFromTable("class", charClass, "num_skills"));
        String skillChoices = Database.retrieveColumnFromTable("class", charClass, "skill_choices");
        System.out.println("Choose " + numSkillsProf + " skill(s) below to be proficient in:");
        // TODO ensure user only chooses the number of skills equal to numSkillsProf variable
        System.out.println("Type out the skill(s) you want (separated by a comma and a space: ', ' for choosing multiple skills)");
        ArrayList<String> skillList = (ArrayList<String>) Arrays.asList(skillChoices.split(", "));
        Main.displayList(skillList);
        String input = scan.nextLine();
        skills.addAll(Arrays.asList(input.split(", ")));

        //passive perception
        passivePerception = 10 + wisdomMod;
        if (skills.contains("Perception"))
            passivePerception += profBonus;

        //proficiencies
        String armorProfs = Database.retrieveColumnFromTable("class", charClass, "armor_prof");
        String weaponProfs = Database.retrieveColumnFromTable("class", charClass, "weapon_prof");
        String toolProfs = Database.retrieveColumnFromTable("class", charClass, "tools");
        System.out.println("Your character is proficient in the following items:");
        System.out.println("Armor: " + armorProfs);
        System.out.println("Weapons: " + weaponProfs);
        System.out.println("Tools: " + toolProfs);
        if (!armorProfs.contains("None"))
            otherProfsLangs.addAll(Arrays.asList(armorProfs.split(", ")));
        if (!weaponProfs.contains("None"))
            otherProfsLangs.addAll(Arrays.asList(weaponProfs.split(", ")));
        if (!toolProfs.contains("None")) {
            if (charClass.equals("Bard")) {
                System.out.println("Type out 3 musical instruments to be proficient in (separate each instrument by a comma and a space ', ')");
                Main.printAllInstruments();
                input = scan.nextLine();
                otherProfsLangs.addAll(Arrays.asList(input.split(", ")));
            } else if (charClass.equals("Monk")) {
                System.out.println("Choose any type of Artisan's tools or a musical instrument:");
                Main.printArtisanTools();
                Main.printAllInstruments();
                input = scan.nextLine();
                otherProfsLangs.add(input);
            } else {
                otherProfsLangs.add(toolProfs);
            }
        }

        //languages
        String raceLang = Database.retrieveColumnFromTable("race", race, "languages");
        int backgroundLangs = Integer.parseInt(Database.retrieveColumnFromTable("background", background, "languages"));
        otherProfsLangs.addAll(Arrays.asList(raceLang.split(", ")));
        System.out.println("Choose any " + backgroundLangs + " language(s) from the following languages");
        System.out.println("For multiple languages: separate each language with a comma and a space (', ')");
        input = scan.nextLine();
        otherProfsLangs.addAll(Arrays.asList(input.split(", ")));

        //starting equipment
        //weapons, armor, personal items, gold
        if (charClass.equals("Barbarian")) {
            //any martial melee weapon
            System.out.println("Choose any martial melee weapon below:");
            ArrayList<ArrayList<String>> weaponsList = Database.retrieveWeaponsByType(MARTIAL_MELEE);
            Main.display2DList(weaponsList);
            input = scan.nextLine();
            Weapon newWpn = new Weapon(input);
            equipment.add(newWpn);

            //two handaxes or any simple weapon
            System.out.println("Choose between 2 handaxes or any simple weapon below");
            System.out.println("If choosing 2 handaxes, type out 'Handaxe");
            weaponsList = Database.retrieveWeaponsByType(SIMPLE);
            Main.display2DList(weaponsList);
            input = scan.nextLine();
            newWpn = new Weapon(input);
            if (input.equals("Handaxe")) {
                newWpn.setQuantity(2);
            }
            equipment.add(newWpn);

            //explorer's pack and 4 javelins
            System.out.println("Adding Explorer's pack and 4 javelins...");
            Equipment pack = new Equipment("Explorer's Pack");
            equipment.add(pack);
            newWpn = new Weapon("Javelin");
            newWpn.setQuantity(4);
            equipment.add(newWpn);
        } else if (charClass.equals("Bard")) {
            //a rapier, longsword, or any simple weapon
            System.out.println("Choose between any of the following weapons below:");
            ArrayList<String> rapier = Database.retrieveWeaponByName("Rapier");
            ArrayList<String> longsword = Database.retrieveWeaponByName("Longsword");
            ArrayList<ArrayList<String>> weaponsList = Database.retrieveWeaponsByType(SIMPLE);
            Main.displayList(rapier);
            Main.displayList(longsword);
            Main.display2DList(weaponsList);
            input = scan.nextLine();
            Weapon newWpn = new Weapon(input);
            equipment.add(newWpn);

            //diplomats or entertainers pack
            System.out.println("Choose between a Diplomat's Pack or an Entertainer's Pack");
            input = scan.nextLine();
            Equipment pack = new Equipment(input);
            equipment.add(pack);

            //any musical instrument
            System.out.println("Choose any musical instrument from the list below:");
            Main.printAllInstruments();
            input = scan.nextLine();
            Equipment instrument = new Equipment(input);
            equipment.add(instrument);

            //leather armor and a dagger
            System.out.println("Adding Leather Armor and a Dagger...");
            Armor newArmor = new Armor("Leather Armor");
            equipment.add(newArmor);
            newWpn = new Weapon("Dagger");
            equipment.add(newWpn);
        } else if (charClass.equals("Cleric")) {
            //a mace or warhammer (if proficient)
            Weapon newWpn;
            if (otherProfsLangs.contains("Martial Weapons")) {
                System.out.println("Choose between a mace or a warhammer:");
                ArrayList<String> mace = Database.retrieveWeaponByName("Mace");
                ArrayList<String> warhammer = Database.retrieveWeaponByName("Warhammer");
                Main.displayList(mace);
                Main.displayList(warhammer);
                input = scan.nextLine();
                newWpn = new Weapon(input);
            } else {
                System.out.println("Adding Mace...");
                newWpn = new Weapon("Mace");
            }
            equipment.add(newWpn);

            //scale mail leather armor or chain mail (if proficient)
            if (otherProfsLangs.contains("Heavy Armor"))
                System.out.println("Choose between Scale Mail, Leather Armor, or Chain Mail");
            else
                System.out.println("Choose between Scale Mail or Leather Armor");
            input = scan.nextLine();
            Armor newArmor = new Armor(input);
            equipment.add(newArmor);

            //light crossbow and 20 bolts or any simple weapon
            System.out.println("Choose any simple weapon below:");
            ArrayList<ArrayList<String>> weaponsList = Database.retrieveWeaponsByType(SIMPLE);
            input = scan.nextLine();
            newWpn = new Weapon(input);
            equipment.add(newWpn);
            if (input.equals("Light Crossbow")) {
                Equipment bolts = new Equipment("Crossbow Bolts", "", 0, 20, "Bolts for Crossbow Ammunition");
                equipment.add(bolts);
            }

            //priests or explorers pack
            System.out.println("Choose between a Priest's Pack or an Explorer's Pack");
            input = scan.nextLine();
            Equipment pack = new Equipment(input);
            equipment.add(pack);

            //a shield and a holy symbol
            System.out.println("Adding a Shield...");
            newArmor = new Armor("Shield");
            equipment.add(newArmor);
            assignHolySymbol();
        } else if (charClass.equals("Druid")) {
            //a wooden shield or any simple weapon
            System.out.println("Choose between a Wooden Shield or any Simple Weapon");
            ArrayList<String> woodenShield = Database.retrieveArmorByName("Wooden Shield");
            ArrayList<ArrayList<String>> weaponChoices = Database.retrieveWeaponsByType(SIMPLE);
            weaponChoices.add(woodenShield);
            Main.display2DList(weaponChoices);
            input = scan.nextLine();
            if (input.equals("Wooden Shield")) {
                Armor newArmor = new Armor("Wooden Shield");
                equipment.add(newArmor);
            } else {
                Weapon newWpn = new Weapon(input);
                equipment.add(newWpn);
            }

            //a scimitar or any simple melee weapon
            System.out.println("Choose between a Scimitar or any simple melee weapon");
            ArrayList<String> scimitar = Database.retrieveWeaponByName("Scimitar");
            ArrayList<ArrayList<String>> weaponsList = Database.retrieveWeaponsByType(SIMPLE_MELEE);
            weaponsList.add(scimitar);
            Main.display2DList(weaponsList);
            input = scan.nextLine();
            Weapon newWpn = new Weapon(input);
            equipment.add(newWpn);

            //leather armor, explorers pack, and a druidic focus
            System.out.println("Adding Leather Armor and an Explorer's Pack...");
            Armor newArmor = new Armor("Leather Armor");
            equipment.add(newArmor);
            Equipment pack = new Equipment("Explorer's Pack");
            equipment.add(pack);
            System.out.println("Type out an object to use as your Druidic Focus");
            input = scan.nextLine();
            Equipment druidicFocus = new Equipment(input, "", 0, 1, "");
            System.out.println("Enter a description for your Druidic Focus object");
            input = scan.nextLine();
            druidicFocus.setDescription(input);
            equipment.add(druidicFocus);
        } else if (charClass.equals("Fighter")) {
            //chaim mail or leather armor, longbow and 20 arrows
            System.out.println("Choose between Chain Mail or Leather Armor and a Longbow with 20 arrows:");
            System.out.println("Type out 1 or 2 respectively");
            ArrayList<String> mail = Database.retrieveArmorByName("Chain Mail");
            ArrayList<String> leather = Database.retrieveArmorByName("Leather Armor");
            leather.addAll(Database.retrieveWeaponByName("Longbow"));
            System.out.print("1: ");
            Main.displayList(mail);
            System.out.print("2: ");
            Main.displayList(leather);
            int choice= scan.nextInt();
            if (choice == 1) {
                Armor newArmor = new Armor("Chain Mail");
                equipment.add(newArmor);
            } else if (choice == 2) {
                Armor newArmor = new Armor("Leather Armor");
                Weapon newWpn = new Weapon("Longbow");
                Equipment arrows = new Equipment("Arrows", "", 0, 20, "Ammunition for Bow");
                equipment.add(newArmor);
                equipment.add(newWpn);
                equipment.add(arrows);
            }

            //martial weapon and shield, or two martial weapons
            System.out.println("Choose between any martial weapon and a Shield or two martial weapons");
            System.out.println("Separate each item with a comma and a space (', ')");
            ArrayList<ArrayList<String>> martialWeapons = Database.retrieveWeaponsByType(MARTIAL);
            ArrayList<String> shield = Database.retrieveArmorByName("Shield");
            martialWeapons.add(shield);
            Main.display2DList(martialWeapons);
            int choices = 2;
            while (choices != 0) {
                System.out.println("Enter the name of an item:");
                input = scan.nextLine();
                if (input.contains("Shield")) {
                    Armor newArmor = new Armor("Shield");
                    equipment.add(newArmor);
                } else {
                    Weapon newWpn = new Weapon(input);
                    equipment.add(newWpn);
                }
                choices--;
            }

            //light crossbow and 20 bolts, or 2 handaxes
            System.out.println("Choose between a Light Crossbow and 20 bolts or 2 Handaxes");
            System.out.println("Type out the name of the weapon ('Handaxe')");
            input = scan.nextLine();
            if (input.contains("Handaxe")) {
                Weapon newWpn = new Weapon("Handaxe");
                newWpn.setQuantity(2);
                equipment.add(newWpn);
            } else if (input.contains("Light Crossbow")) {
                Weapon newWpn = new Weapon("Light Crossbow");
                Equipment bolts = new Equipment("Crossbow Bolts", "", 0, 20, "Ammunition for Crossbow");
                equipment.add(newWpn);
                equipment.add(bolts);
            }

            //dungeoneers or explorers pack
            System.out.println("Choose between a Dungeoneer's Pack or an Explorer's Pack:");
            input = scan.nextLine();
            Equipment pack = new Equipment(input);
            equipment.add(pack);

        } else if (charClass.equals("Monk")) {
            //a shortsword or any simple weapon
            System.out.println("Choose between a Shortsword or any simple weapon:");
            ArrayList<String> shortsword = Database.retrieveWeaponByName("Shortsword");
            ArrayList<ArrayList<String>> weaponsList = Database.retrieveWeaponsByType(SIMPLE);
            weaponsList.add(shortsword);
            Main.display2DList(weaponsList);
            input = scan.nextLine();
            Weapon newWpn = new Weapon(input);
            equipment.add(newWpn);

            //a dungeoneers or explorers pack
            System.out.println("Choose between a Dungeoneer's Pack or an Explorer's Pack:");
            input = scan.nextLine();
            Equipment pack = new Equipment(input);
            equipment.add(pack);

            //10 darts
            System.out.println("Adding 10 darts to inventory...");
            Weapon dart = new Weapon("Dart");
            dart.setQuantity(10);

        } else if (charClass.equals("Paladin")) {
            //a martial weapon and shield OR 2 martial weapons
            //5 javelins or any simple melee weapon
            //a priests pack or an explorers pack
            //chain mail and a holy symbol
            assignHolySymbol();
        } else if (charClass.equals("Ranger")) {
            //scale mail or leather armor
            //two shortwords or 2 simple melee weapons
            //dungeoneers or explorers pack
            //longbow and quiver of 20 arrows
        } else if (charClass.equals("Rogue")) {
            //a rapier or a shortsword
            //shortbow and quiver of 20 arrows or a shortsword
            //burglars dungeoneers or explorers pack
            // leather armor two daggers and thieves tools
        } else if (charClass.equals("Sorcerer")) {
            //light crossbow and 20 bolts or any simple weapon
            //a component pouch or an arcane focus
            //a dungeoneers or explorers pack
            //two daggers
        } else if (charClass.equals("Warlock")) {
            //light crossbow and 20 bolts or any simple weapon
            //component pouch or arcane focus
            //scholars or dungeoneers pack
            //leather armor any simple weapon and two daggers
        } else if (charClass.equals("Wizard")) {
            //quarterstaff or a dagger
            //component pouch or arcane focus
            //scholars or explorers pack
            //spellbook
        }




/* Character creation
1. Choose race x
2. Choose class x
3. Choose background x
4. Stat block
    1. ability scores ADD RACE AND BACKGROUND ADDITIONS
    2. ability modifiers
    3. proficiency modifier x
    4. saving throws x
    5. skill scores x
    6. passive perception x
5. Proficiencies and languages x
    1. proficiencies (class) x
    2. languages (race and background) x
6. Equipment (class AND background)
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




        configureCharRace(race);
        //configureCharClass(charClass);
        if (skills.contains("Perception"))
            passivePerception = 10 + profBonus + wisdomMod;
        else
            passivePerception = 10 + wisdomMod;
    }

    // METHODS

    private void configureCharRace(String race) {
        switch (race) {

        }
    }

    /*
    public void configureCharClass(String charClass) {
        Scanner scan = new Scanner(System.in);
        charClass = charClass.toUpperCase();
        switch (charClass) {
            case "BARBARIAN":
                maxHP = 12 + constitutionMod;
                // level up hp 1d12 (or 7) + const mod
                hitDie = level + "d12";
                otherProfsLangs.addAll(Arrays.asList("Light Armor", "Medium Armor", "Shields", "Simple Weapons", "Martial Weapons"));
                savingThrows.add("Strength");
                savingThrows.add("Constitution");
                System.out.println("Choose two of the following skills as proficiencies (type out 'skill1, skill2')");
                System.out.println("Animal Handling, Athletics, Intimidation, Nature, Perception, Survival");
                String input = scan.nextLine();
                skills.addAll(Arrays.asList(input.split(", ")));
                System.out.println("Choose one of the following martial melee weapons:");
                System.out.println("(Type out the name of the weapon you want 'Greataxe')");
                System.out.println("Loading weapons...");
                System.out.println(Database.retrieveWeaponsByType(MARTIAL_MELEE));
                input = scan.nextLine();
                Weapon wpn = new Weapon(input);
                equipment.add(wpn);
                String handaxeData = "";
                StringBuilder simpleWeapons = new StringBuilder();
                try {
                    ResultSet resultSet = Database.queryDB("SELECT * FROM weapon WHERE name='handaxe'");
                    while (resultSet.next()) {
                        handaxeData = resultSet.getString("cost") + ", " + resultSet.getString("dmgRoll") + " " + resultSet.getString("dmgType") + ", " + resultSet.getString("properties");
                    }
                    System.out.println("Choose your second weapon:");
                    System.out.println("Type out the name of the choice as its shown (e.g. '2 Handaxes')");
                    System.out.println("2 Handaxes - " + handaxeData);
                    String query = "SELECT * FROM weapon WHERE type LIKE '%" + SIMPLE + "%' AND NOT name='Handaxe'";
                    resultSet = Database.queryDB(query);
                    while (resultSet.next()) {
                        simpleWeapons.append(resultSet.getString("name")).append(" - ").append(resultSet.getString("cost")).append(", ").append(resultSet.getString("dmgRoll")).append(" ").append(resultSet.getString("dmgType")).append(", ").append(resultSet.getString("type")).append(", ").append(resultSet.getString("properties")).append("\n");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(simpleWeapons);
                input = scan.nextLine();
                if (input.equals("2 Handaxes")) {
                    Weapon handaxes = new Weapon("Handaxe");
                    handaxes.setQuantity(2);
                    equipment.add(handaxes);
                } else {
                    Weapon simpleWpn = new Weapon(input);
                    equipment.add(simpleWpn);
                }
                System.out.println("Adding explorer's pack and 4 javelins...");
                Equipment explorersPack = new Equipment("Explorer's Pack");
                equipment.add(explorersPack);
                Weapon javelin = new Weapon("Javelin");
                javelin.setQuantity(4);
                equipment.add(javelin);
                System.out.println("Adding Rage and Unarmored Defense features...");
                String desc = Database.retrieveFeatureDesc("Rage");
                Feature rage = new Feature("Rage", desc);
                desc = Database.retrieveFeatureDesc("Unarmored Defense");
                Feature unarmoredDefense = new Feature("Unarmored Defense", desc);
                features.add(rage);
                features.add(unarmoredDefense);
                gold = Main.rollDie(2,4) * 10;
                System.out.println("Your starting gold is: " + gold);
                break;
            case "BARD":
                maxHP = 8 + constitutionMod;
                //level up hp 1d8 (or 5) + const mod
                hitDie = level + "d8";
                otherProfsLangs.addAll(Arrays.asList("Light Armor", "Simple Weapons", "Hand Crossbows", "Longswords", "Rapiers", "Shortswords"));
                savingThrows.add("Dexterity");
                savingThrows.add("Charisma");
                System.out.println("Choose any three instruments to be proficient in");
                System.out.println("Type out the instruments separated by commas (\"Viol, Lute, Flute\"");
                Main.printAllInstruments();
                input = scan.nextLine();
                otherProfsLangs.addAll(Arrays.asList(input.split(", ")));
                System.out.println("Choose any three skills to be proficient in");
                System.out.println("Type out the skills separated by commas (\"Athletics, Survival, Intimidation\")");
                Main.printAllSkills();
                input = scan.nextLine();
                skills.addAll(Arrays.asList(input.split(", ")));
                System.out.println("Choose a starting weapon from this list:");
                System.out.println("Type out the weapon name as you see it\n");
                System.out.print(Database.retrieveWeaponByName("Rapier"));
                System.out.print(Database.retrieveWeaponByName("Longsword"));
                System.out.println(Database.retrieveWeaponsByType(SIMPLE));
                input = scan.nextLine();
                wpn = new Weapon(input);
                equipment.add(wpn);
                System.out.println("Choose between the following 2 packs:\nDiplomat's Pack\nEntertainer's Pack");
                input = scan.nextLine();
                Equipment pack = new Equipment(input);
                equipment.add(pack);
                System.out.println("Choose a musical instrument:");
                Main.printAllInstruments();
                input = scan.nextLine();
                inventory.add(input);

                break;
            case "CLERIC":
                break;
            case "DRUID":
                break;
            case "FIGHTER":
                break;
            case "MONK":
                break;
            case "PALADIN":
                break;
            case "RANGER":
                break;
            case "ROGUE":
                break;
            case "SORCERER":
                break;
            case "WARLOCK":
                break;
            case "WIZARD":
                break;
        }
    } */

    public void configureWeaponUserInput(String option1, String option2) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose between \n1. " + option1 + " OR\n2. " + option2 + ":");
        System.out.println("Type out 1 or 2 respectively");
        int input = scan.nextInt();
        if (input == 1) {

        } else if (input == 2) {
            if (option2.contains(SIMPLE) || option2.contains(MARTIAL)) {
                ArrayList<ArrayList<String>> weaponsList = Database.retrieveWeaponsByType(option2);
                userWeaponChoice(weaponsList, scan);
            }
        } else {
            System.out.println("Input error try again...\n\n");
            configureWeaponUserInput(option1, option2);
        }
    }

    private void userWeaponChoice(ArrayList<ArrayList<String>> weaponsList, Scanner scan) {
        if (weaponsList != null) {
            int numOption = 1;
            //int input =
            for (ArrayList<String> weapon : weaponsList) {
                System.out.print(numOption + ". ");
                Main.displayList(weapon);

            }
        }
    }

    public void configurePackUserInput(String option1, String option2) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose between " + option1 + " OR " + option2 + ":");
        System.out.println("Type out 1 or 2 respectively");
    }


    public void assignHolySymbol() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Type any object below to use as your Holy Symbol");
        String input = scan.nextLine();
        Equipment holySymbol = new Equipment(input, "", 0, 1, "");
        System.out.println("Enter a description for your Holy Symbol");
        input = scan.nextLine();
        holySymbol.setDescription(input);
        equipment.add(holySymbol);
    }

    private void assignStats() {
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
            if (scores.contains(input)) {
                switch (i) {
                    case 0:
                        strength = input;
                        strengthMod = calcModifier(input);
                        break;
                    case 1:
                        dexterity = input;
                        dexterityMod = calcModifier(input);
                        break;
                    case 2:
                        constitution = input;
                        constitutionMod = calcModifier(input);
                        break;
                    case 3:
                        intelligence = input;
                        intelligenceMod = calcModifier(input);
                        break;
                    case 4:
                        wisdom = input;
                        wisdomMod = calcModifier(input);
                        break;
                    case 5:
                        charisma = input;
                        charismaMod = calcModifier(input);
                        break;
                }
                scores.remove(scores.indexOf(input));
            }
        }
    }

    private void getRolledStats() {
        System.out.println("Rolling for stats...");
        //Roll for Str score and set modifier
        strength = rollStats();
        strengthMod = calcModifier(strength);

        //Roll for Dex score and set modifier
        dexterity = rollStats();
        dexterityMod = calcModifier(dexterity);

        //Roll for Const score and set modifier
        constitution = rollStats();
        constitutionMod = calcModifier(constitution);

        //Roll for Int score and set modifier
        intelligence = rollStats();
        intelligenceMod = calcModifier(intelligence);

        //Roll for Wis score and set modifier
        wisdom = rollStats();
        wisdomMod = calcModifier(wisdom);

        //Roll for Char score and set modifier
        charisma = rollStats();
        charismaMod = calcModifier(charisma);
    }

    public static int rollStats() {
        ArrayList<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rolls.add(Main.rollDie(6));
        }
        //remove smallest rolls
        Collections.sort(rolls);
        rolls.remove(0);
        //sum highest 3 rolls
        int sum = 0;
        for (int roll : rolls) {
            sum = sum + roll;
            System.out.println("You rolled...");
            System.out.print(roll + " ");
        }
        return sum;
    }

    /**
     * Takes the stat score and calculates its modifier
     *
     * @param score stat score (strength, dex, const, ...)
     * @return the modifier applied to the stat score (+1, -1, +2...)
     */
    public int calcModifier(int score) {
        if (score < 10 && score % 2 != 0)
            return ((score - 10) / 2) - 1;
        else
            return (score - 10) / 2;
    }

    public int calcAC() {
        return 0;
    }

    // TODO refactor to get each item thats in a list
    public String toString() {
        String charData = "\nName: " + name;
        charData += "\nClass: " + charClass;
        charData += "\nRace: " + race;
        charData += "\nBackground: " + background;
        charData += "\n\nAbilities:\nStrength: " + strength + "\t" + strengthMod;
        charData += "\nDexterity: " + dexterity + "\t" + dexterityMod;
        charData += "\nConstitution: " + constitution + "\t" + constitutionMod;
        charData += "\nIntelligence: " + intelligence + "\t" + intelligenceMod;
        charData += "\nWisdom: " + wisdom + "\t" + wisdomMod;
        charData += "\nCharisma: " + charisma + "\t" + charismaMod;
        charData += "\nLanguages & Proficiencies: " + otherProfsLangs;
        charData += "\nSaving Throws: " + savingThrows;
        charData += "\nSkills: " + skills;
        charData += "\nEquipment: " + equipment;
        return charData;
    }

    public void printAllItems() {
        System.out.println("Equipment:");
        for (Equipment eq : equipment) {
            System.out.println(eq + "\n");
        }
        System.out.println("\nOther Items:");
        for (String item : inventory) {
            System.out.println(item);
        }
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public String getCharClass() {
        return charClass;
    }

    public String getRace() {
        return race;
    }

    public int getLevel() {
        return level;
    }

    public String getBackground() {
        return background;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrHP() {
        return currHP;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getTotalAC() {
        return totalAC;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getCharismaMod() {
        return charismaMod;
    }

    public int getConstitutionMod() {
        return constitutionMod;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterityMod() {
        return dexterityMod;
    }

    public int getStrengthMod() {
        return strengthMod;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public String getAlignment() {
        return alignment;
    }

    public int getIntelligenceMod() {
        return intelligenceMod;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWisdom() {
        return wisdom;
    }

    public String getHitDie() {
        return hitDie;
    }

    public int getGold() {
        return gold;
    }

    public int getProfBonus() {
        return profBonus;
    }

    public int getWisdomMod() {
        return wisdomMod;
    }

    public ArrayList<String> getSavingThrows() {
        return savingThrows;
    }

    public int getDeathSaveFail() {
        return deathSaveFail;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public ArrayList<String> getOtherProfsLangs() {
        return otherProfsLangs;
    }

    public int getDeathSaveSucc() {
        return deathSaveSucc;
    }

    public int getPassPerception() {
        return passPerception;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public void setTotalAC(int totalAC) {
        this.totalAC = totalAC;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setCharClass(String charClass) {
        this.charClass = charClass;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public void setCharismaMod(int charismaMod) {
        this.charismaMod = charismaMod;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void setConstitutionMod(int constitutionMod) {
        this.constitutionMod = constitutionMod;
    }

    public void setCurrHP(int currHP) {
        this.currHP = currHP;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setDexterityMod(int dexterityMod) {
        this.dexterityMod = dexterityMod;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setIntelligenceMod(int intelligenceMod) {
        this.intelligenceMod = intelligenceMod;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setEquipment(ArrayList<Equipment> equipment) {
        this.equipment = equipment;
    }

    public void setProfBonus(int profBonus) {
        this.profBonus = profBonus;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setPassPerception(int passPerception) {
        this.passPerception = passPerception;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDeathSaveSucc(int deathSaveSucc) {
        this.deathSaveSucc = deathSaveSucc;
    }

    public void setDeathSaveFail(int deathSaveFail) {
        this.deathSaveFail = deathSaveFail;
    }

    public void setSavingThrows(ArrayList<String> savingThrows) {
        this.savingThrows = savingThrows;
    }

    public void setHitDie(String hitDie) {
        this.hitDie = hitDie;
    }

    public void setStrengthMod(int strengthMod) {
        this.strengthMod = strengthMod;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void setWisdomMod(int wisdomMod) {
        this.wisdomMod = wisdomMod;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    public void setOtherProfsLangs(ArrayList<String> otherProfsLangs) {
        this.otherProfsLangs = otherProfsLangs;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }
}


