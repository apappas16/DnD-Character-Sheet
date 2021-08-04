package CharacterSheetMgmt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

    //Use these for querying weapons table for specific weapon type:
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

    public Character(String name, String race, String charClass, String background) {
        this.name = name;
        this.race = race;
        this.charClass = charClass;
        this.background = background;
        level = 1;
        deathSaveFail = 0;
        deathSaveSucc = 0;
        equipment = new ArrayList<>();
        otherProfsLangs = new ArrayList<>();
        skills = new ArrayList<>();
        inventory = new ArrayList<>();
        savingThrows = new ArrayList<>();
        features = new ArrayList<>();
        inventory = new ArrayList<>();
        profBonus = 2;
        configureCharRace(race);
        configureCharClass(charClass);
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
                    ResultSet resultSet = Database.queryDB("SELECT * FROM weapons WHERE name='handaxe'");
                    while (resultSet.next()) {
                        handaxeData = resultSet.getString("cost") + ", " + resultSet.getString("dmgRoll") + " " + resultSet.getString("dmgType") + ", " + resultSet.getString("properties");
                    }
                    System.out.println("Choose your second weapon:");
                    System.out.println("Type out the name of the choice as its shown (e.g. '2 Handaxes')");
                    System.out.println("2 Handaxes - " + handaxeData);
                    String query = "SELECT * FROM weapons WHERE type LIKE '%" + SIMPLE + "%' AND NOT name='Handaxe'";
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
                System.out.print(Database.retrieveWeaponsByName("Rapier"));
                System.out.print(Database.retrieveWeaponsByName("Longsword"));
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


