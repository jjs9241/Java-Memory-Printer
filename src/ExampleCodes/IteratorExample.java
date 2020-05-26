package ExampleCodes;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;

public class IteratorExample {
	
	public void f(String name) {
		//
		Collection collection = new ArrayList<Object>();
		Iterator<Object> objectIter = collection.iterator(); 
		while(objectIter.hasNext()) {
			Object object = objectIter.next(); 
			System.out.println(object);
		}
	}

}
