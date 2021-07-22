package CharacterSheetMgmt;

import java.util.ArrayList;

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
    private int ac;
    private int speed;
    private int gold;
    private int passPerception;
    private ArrayList<Equipment> equipment;
    private ArrayList<String> otherProfsLangs;
    private ArrayList<String> skills;
    private int deathSaveSucc;
    private int deathSaveFail;
    private ArrayList<String> inventory;
    private ArrayList<Feature> features;
    // TODO hit die
    // TODO flaws, bonds, ideals, traits
    // TODO character description (age, height, weight, etc)
    // TODO spells

    // constructor
    public Character() {
        level = 1;
        deathSaveFail = 0;
        deathSaveSucc = 0;
        equipment = new ArrayList<>();
        otherProfsLangs = new ArrayList<>();
        skills = new ArrayList<>();
        inventory = new ArrayList<>();
    }

    // methods
    /**
     * Takes the stat score and calculates its modifier
     *
     * @param score stat score (strength, dex, const, ...)
     * @return the modifier applied to the stat score (+1, -1, +2...)
     */
    public int calcModifier(int score) {
        return (score - 10) / 2;
    }

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
        return charData;
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

    public int getAc() {
        return ac;
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

    public int getGold() {
        return gold;
    }

    public int getProfBonus() {
        return profBonus;
    }

    public int getWisdomMod() {
        return wisdomMod;
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

    public void setAc(int ac) {
        this.ac = ac;
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


