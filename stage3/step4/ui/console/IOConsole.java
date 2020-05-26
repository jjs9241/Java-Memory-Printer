package javastory.club.stage3.step4.ui.console;

import javastory.club.stage3.step4.logic.ServiceLogicLycler;
import javastory.club.stage3.step4.service.IOService;
import javastory.club.stage3.util.ConsoleUtil;
import javastory.club.stage3.util.Narrator;
import javastory.club.stage3.util.TalkingAt;

public class IOConsole {
    //
    private IOService ioService;

    private ConsoleUtil consoleUtil;
    private Narrator narrator;

    public IOConsole() {
        //
        this.ioService = ServiceLogicLycler.shareInstance().createIOService();
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }

    public void saveDataToFile() {
        //
        ioService.saveToText();
    }

    public void loadSavefile() {
        ioService.loadSaveTextfile();
    }
}
