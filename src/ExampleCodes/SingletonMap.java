package ExampleCodes;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SingletonMap {
	//
	private static SingletonMap singletonMap; 
	
	private Map<String,String>  stringMap; 
	
	private SingletonMap() {
		// 
		this.stringMap = new LinkedHashMap<>(); 
	}
	
	public static SingletonMap getInstance() {
		// 
		if (singletonMap == null) {
			singletonMap = new SingletonMap(); 
		}
		
		return singletonMap; 
	}
	
	public Map<String,String> getStringMap() {
		// 
		return this.stringMap; 
	}
	
}