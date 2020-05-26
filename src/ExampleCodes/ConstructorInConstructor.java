package ExampleCodes;

public class ConstructorInConstructor {
	
	private Object element;
	
	public ConstructorInConstructor() {
		this.element = "element";
	}
	
	public ConstructorInConstructor(String addedString) {
		this();
		this.element += addedString;
	}

}
