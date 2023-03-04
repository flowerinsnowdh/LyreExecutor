package online.flowerinsnow.lyreexecutor;

import com.melloware.jintellitype.JIntellitype;
import online.flowerinsnow.lyreexecutor.thread.LyreMainThread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LyreExecutor {
    private static LyreMainThread mainThread;

    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : "chart.txt";
        System.out.println("Loading " + fileName);
        List<String> fileContent;
        try {
            fileContent = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("*** Fatal Error ***");
            e.printStackTrace();
            return;
        }
        final List<String> var0 = fileContent;

        JIntellitype.getInstance().registerHotKey(1, 0, CKeyboard.VK_HOME);
        JIntellitype.getInstance().registerHotKey(2, 0, CKeyboard.VK_PAUSE);
        JIntellitype.getInstance().registerHotKey(3, 0, CKeyboard.VK_END);

        JIntellitype.getInstance().addHotKeyListener(vKey -> {
            switch (vKey) {
                case 1:
                    if (mainThread == null) {
                        mainThread = new LyreMainThread(var0);
                        new Thread(mainThread).start();
                    }
                    return;
                case 2:
                    if (mainThread != null) {
                        mainThread.sPause();
                    }
                    return;
                case 3:
                    System.exit(0);
            }
        });
        System.out.println("Press Home to start.");
    }
}
