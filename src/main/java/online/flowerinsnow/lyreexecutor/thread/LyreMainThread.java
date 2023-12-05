package online.flowerinsnow.lyreexecutor.thread;

import java.awt.*;
import java.util.List;

public class LyreMainThread implements Runnable {
    private final List<String> lines;
    private boolean pause;
    private int lineNumber;

    public LyreMainThread(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public void run() {
        int bpm = -1;
        double note = -1.0;
        long waitPerLine = -1L;
        lineNumber = 0;

        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.err.println("*** Fatal Error ***");
            e.printStackTrace();
            System.exit(-1);
            return;
        }

        for (String line : lines) {
            lineNumber++;
            while (pause) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    System.exit(0);
                    return;
                }
            }
            if (line.trim().isEmpty() || line.trim().charAt(0) == '#') { // 空行或注释 跳过
                continue;
            }
            if (line.trim().charAt(0) == '!') { // 命令行
                if (line.trim().startsWith("!BPM ")) {
                    try {
                        bpm = Integer.parseInt(line.trim().substring(5));
                        if (note > 0.0) {
                            waitPerLine = Math.round((60.0 / (double) bpm) * note * 1000.0);
                        }
                    } catch (NumberFormatException e) {
                        fatalError(e.toString(), lineNumber);
                        return;
                    }
                } else if (line.trim().startsWith("!NOTE ")) { // 设置音符
                    if (bpm == -1) { // 必须先设置BPM
                        fatalError("Your must specify BPM first!", lineNumber);
                        return;
                    }
                    try {
                        note = Double.parseDouble(line.trim().substring(6));
                    } catch (NumberFormatException e) {
                        fatalError(e.toString(), lineNumber);
                        return;
                    }
                    // 四分音符休止时间=60/bpm
                    waitPerLine = Math.round((60.0 / (double) bpm) * note * 1000.0);
                }
            } else { // 音符行
                if (waitPerLine == -1L) {
                    fatalError("You must specify BPM and note first!", lineNumber);
                    return;
                }
                for (char c : line.toCharArray()) {
                    if (c == '#') { // 遇到单行注释，直接下一行
                        break;
                    }
                    if (!Character.isLetter(c)) { // 遇到非字母，下一个字符
                        continue;
                    }
                    robot.keyPress(c);
                    robot.keyRelease(c);
                }
                // 单行结束 延迟继续执行
                robot.delay((int) waitPerLine);
            }
        }
        System.exit(0);
    }

    public boolean isPause() {
        return pause;
    }

    public void pause() {
        pause = true;
        System.out.println("Paused at line " + lineNumber);
    }

    public void resume() {
        pause = false;
        System.out.println("Resumed at line " + lineNumber);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean sPause() {
        if (isPause()) {
            resume();
            return false;
        } else {
            pause();
            return true;
        }
    }

    private void fatalError(String reason, int lineNumber) {
        System.err.println("*** Fatal Error ***");
        System.err.println(reason + " (Line number " + lineNumber + ")");
        System.exit(-1);
    }
}
