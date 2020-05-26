package javastory.club.stage3.step2;

import javastory.club.stage3.step2.ui.menu.MainMenu;

public class StoryAssistant {
	//
	private void startStory() {
		// 
		MainMenu mainMenu = new MainMenu();
		mainMenu.show();
	}
	
	public static void main(String[] args) {
		//
		StoryAssistant assistant = new StoryAssistant(); 
		assistant.startStory(); 
	}
}