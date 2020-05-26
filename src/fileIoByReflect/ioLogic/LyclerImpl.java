package fileIoByReflect.ioLogic;

public class LyclerImpl implements Lycler{

	private static Lycler lycler; 
	
	private IoStore ioStore;
	
	private LyclerImpl() {
	}; 
	
	public static Lycler getInstance() {
		// 
		if (lycler == null) {
			lycler = new LyclerImpl(); 
		}
		
		return lycler;
	}
	
	@Override
	public IoStore requestIoStore() {
		// 
		if (ioStore == null) {
			ioStore = new IoMapStore();
		}
		return ioStore;
	}

}
