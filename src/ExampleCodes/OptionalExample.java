package ExampleCodes;

import java.util.Optional;

public class OptionalExample {
	
	private Object element;
	
	public Object getElement() {
		return this.element;
	}
	
	public void throwNotNullException() {
	
		Optional.ofNullable(this.element)
		.ifPresent((target)->{throw new NotNullException("Not null element --> " + this.element);});
	}
	public void throwNullException() {
		
		Object element = Optional.ofNullable(this.element)
				.orElseThrow(()->new NoSuchElementException("Null element exception."));
	}
	
	
	public void useNotNullThrowNull() {
		
		String use =  Optional.ofNullable(this.element)
				.map(elementForUse->elementForUse.toString())
				.orElseThrow(()->new NoSuchElementException("Null element exception."));
	}
		
	
	//*/

}
