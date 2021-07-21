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
                    Character newChar = new Character();
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
