package CharacterSheetMgmt;


import java.util.ArrayList;
import java.util.Arrays;

public class Equipment {
    // TODO modify pack items
    //instance variables
    protected String name;
    protected String cost;
    protected int weight;
    protected String description;
    protected int quantity;
    protected ArrayList<String> packItems;

    //constructors
    public Equipment() {
        name = "--";
        cost = "--";
        weight = 0;
        description = "--";
        quantity = 1;
    }

    public Equipment(String name, String cost, int weight, int quantity, String description) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.quantity = quantity;
        this.description = description;
        packItems = null;
    }

    public Equipment(String name) {
        this.name = name;
        quantity = 1;
        weight = 0;
        description = "";
        switch (name) {
            case "Burglar's Pack":
                cost = "16gp";
                packItems = new ArrayList<>(Arrays.asList("backpack", "1000 ball bearings", "10 feet of string", "bell", "5 candles", "crowbar", "hammer", "10 pitons", "hooded lantern", "2 flasks of oil", "5 days of rations", "tinderbox", "waterskin", "50 feet of rope"));
                break;
            case "Diplomat's Pack":
                cost = "39gp";
                packItems = new ArrayList<>(Arrays.asList("chest", "2 cases for maps and scrolls", "fine clothes", "bottle of ink", "ink pen", "lamp", "2 flasks of oil", "5 sheets of paper", "vial of perfume", "sealing wax", "soap"));
                break;
            case "Dungeoneer's Pack":
                cost = "12gp";
                packItems = new ArrayList<>(Arrays.asList("backpack", "crowbar", "hammer", "10 pitons", "10 torches", "tinderbox", "10 days of rations", "waterskin", "50 feet of rope"));
                break;
            case "Entertainer's Pack":
                cost = "40gp";
                packItems = new ArrayList<>(Arrays.asList("backpack", "bedroll", "2 costumes", "5 candles", "5 days of rations", "waterskin", "disguise kit"));
                break;
            case "Explorer's Pack":
                cost = "10gp";
                packItems = new ArrayList<>(Arrays.asList("backpack", "bedroll", "mess kit", "tinderbox", "10 torches", "10 days of rations", "waterskin", "50 feet of rope"));
                break;
            case "Priest's Pack":
                cost = "19gp";
                packItems = new ArrayList<>(Arrays.asList("backpack", "blanket", "10 candles", "tinderbox", "alms box", "2 blocks of incense", "censer", "vestments", "2 days of rations", "waterskin"));
                break;
            case "Scholar's Pack":
                cost = "40gp";
                packItems = new ArrayList<>(Arrays.asList("backpack", "book of lore", "bottle of ink", "ink pen", "10 sheets of parchment", "small bag of sand", "small knife"));
                break;
        }
    }


    //methods
    public String toString() {
        String result = name.toUpperCase();
        result += "\n\tCost: " + cost;
        result += "\n\tQuantity: " + quantity;
        if (packItems != null) {
            result += "\n\tIncludes: ";
            for (String item : packItems) {
                result += "\n\t\t" + item;
            }
        }
        result += "\n\tWeight: " + weight;
        result += "\n\tDescription: " + description;
        return  result;
    }


    //getters and setters
    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }

    public int getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public ArrayList<String> getPackItems() {
        return packItems;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPackItems(ArrayList<String> packItems) {
        this.packItems = packItems;
    }
}
