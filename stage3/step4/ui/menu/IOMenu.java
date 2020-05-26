package javastory.club.stage3.step4.ui.menu;

import java.util.Scanner;

import javastory.club.stage3.step4.ui.console.ClubConsole;
import javastory.club.stage3.step4.ui.console.IOConsole;
import javastory.club.stage3.util.Narrator;
import javastory.club.stage3.util.TalkingAt;

public class IOMenu {
    //
    private IOConsole ioConsole;

    private Scanner scanner;
    private Narrator narrator;

    public IOMenu() {
        //
        this.ioConsole = new IOConsole();

        this.scanner = new Scanner(System.in);
        this.narrator = new Narrator(this, TalkingAt.Left);
    }

    public void show() {
        //
        int inputNumber = 0;

        while (true) {
            displayMenu();
            inputNumber = selectMenu();

            switch (inputNumber) {
                //
                case 1:
                    ioConsole.loadSavefile();
                    break;
                case 2:
                    ioConsole.saveDataToFile();
                    break;
                case 0:
                    return;

                default:
                    narrator.sayln("Choose again!");
            }
        }
    }

    private void displayMenu() {
        //
        narrator.sayln("");
        narrator.sayln("..............................");
        narrator.sayln(" IO menu ");
        narrator.sayln("..............................");
        narrator.sayln(" 1. load save file");
        narrator.sayln(" 2. Save data to file");
        narrator.sayln("..............................");
        narrator.sayln(" 0. Previous");
        narrator.sayln("..............................");
    }

    private int selectMenu() {
        //
        narrator.say("Select: ");
        int menuNumber = scanner.nextInt();

        if (menuNumber >= 0 && menuNumber <= 2) {
            scanner.nextLine();
            return menuNumber;
        } else {
            narrator.sayln("It's a invalid number --> " + menuNumber);
            return -1;
        }
    }
}
