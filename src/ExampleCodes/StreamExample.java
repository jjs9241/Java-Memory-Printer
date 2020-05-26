package ExampleCodes;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class StreamExample {
	
	public void streamFilterForEach() {
		
		Collection<Object> collection = new ArrayList();
		
		collection.stream()
		.filter(element->element!=null)
		.forEach(targetElement->System.out.println(targetElement));
	}
	
	public void streamFilterMapCollect() {
		
		Collection<Object> collection = new ArrayList();
		

		List<String> elementList = collection.stream()
				.filter(element->element!=null)
				.map(targetElement->targetElement.toString())
				.collect(Collectors.toList());
	}

}
