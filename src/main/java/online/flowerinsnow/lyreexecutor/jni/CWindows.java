package online.flowerinsnow.lyreexecutor.jni;

public class CWindows {
    /**
     * 获取按键是否已按下
     *
     * @param vKey 按键
     * @return 按键是否已按下
     */
    public static native boolean getAsyncKeyState(int vKey);

    /**
     * 模拟键盘输入：按下按键
     *
     * @param vKey 按键
     */
    public static native void keyDown(int vKey);

    /**
     * 模拟键盘输入：释放按键
     *
     * @param vKey 按键
     */
    public static native void keyRelease(int vKey);
}
