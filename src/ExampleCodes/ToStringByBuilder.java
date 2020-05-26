package ExampleCodes;

public class ToStringByBuilder {
	
	private Object element;
	
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("element name: " + this.element);
		return builder.toString();
	}

}
