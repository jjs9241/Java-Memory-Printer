package fileIoByReflect.ui;

import ExampleCodes.util.ConsoleUtil;
import ExampleCodes.util.Narrator;
import ExampleCodes.util.TalkingAt;
import fileIoByReflect.ioLogic.IoStore;
import fileIoByReflect.ioLogic.LyclerImpl;

public class IoConsole {

	private IoStore ioStore;

    private ConsoleUtil consoleUtil;
    private Narrator narrator;

    public IoConsole() {
        //
        this.ioStore = LyclerImpl.getInstance().requestIoStore();
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }

    public void saveDataToFile() {
        //
        ioStore.saveToText();
    }

    public void loadSavefile() {
        //ioStore.loadTextfile();
    }
}
