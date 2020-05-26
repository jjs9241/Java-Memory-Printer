package javastory.club.stage3.step4.logic;

import javastory.club.stage3.step4.da.map.ClubStoreMapLycler;
import javastory.club.stage3.step4.service.IOService;
import javastory.club.stage3.step4.store.IOStore;

public class IOServiceLogic implements IOService {
    //
    private IOStore ioStore;

    public IOServiceLogic(){
        //
        this.ioStore = ClubStoreMapLycler.getInstance().requestIOStore();
    }

    public void saveToText() {
        //
        //ioStore.saveToSimpleText();
        ioStore.saveToText();
    }

    public void loadSaveTextfile() {
        //
        ioStore.loadTextfile();
    }
}
