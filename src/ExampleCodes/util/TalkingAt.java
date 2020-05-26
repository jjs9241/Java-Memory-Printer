package ExampleCodes.util;

public enum TalkingAt {
	//
	Left(0), 
	Middle(3), 
	Right(6); 
	
	private int tabCount; 
	
	private TalkingAt(int tabCount) {
		this.tabCount = tabCount; 
	}
	
	public int tabCount() {
		return tabCount; 
	}
}