package ExampleCodes;

public class SingletonLycler implements Lycler {
	//
	private static Lycler lycler;
	
	private Object element;
	
	private SingletonLycler() {
		//
	}
	public synchronized static Lycler shareInstance() {
		//
		if (lycler == null) {
			lycler = new SingletonLycler();
		}
		
		return lycler;
	}

	@Override
	public Object createElement() {
		//
		if (this.element == null) {
			this.element = new Object();
		}
		return this.element;
	}
}
