package fileIoByReflect.ui;

import java.util.Scanner;

//import javastory.club.stage3.step4.ui.console.ClubConsole;
//import javastory.club.stage3.step4.ui.console.IOConsole;
import ExampleCodes.util.Narrator;
import ExampleCodes.util.TalkingAt;

public class IoMenu {
    //
    private IoConsole ioConsole;

    private Scanner scanner;
    private Narrator narrator;

    public IoMenu() {
        //
        this.ioConsole = new IoConsole();

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
                	
                	this.exitProgram();
                    //return;

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
        narrator.sayln(" 0. Exit (Previous)");
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
    
    private void exitProgram() {
		//
		narrator.sayln("Program exit. Bye...");
		scanner.close();
		System.exit(0);
	}
}
