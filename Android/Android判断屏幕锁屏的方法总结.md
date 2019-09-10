#Android判断屏幕锁屏的方法总结
##总共有两类方法：

- **代码直接判定**


1 通过PowerManager的isScreenOn方法，代码如下：


```java
PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。

屏幕“亮”，表示有两种状态：a、未锁屏 b、目前正处于解锁状态 。这两种状态屏幕都是亮的

屏幕“暗”，表示目前屏幕是黑的 。
```

2 通过KeyguardManager的inKeyguardRestrictedInputMode方法，代码如下：

```java
KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();

boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
源码的返回值的解释是：true if in keyguard restricted input mode.
经过试验，总结为：
如果flag为true，表示有两种状态：a、屏幕是黑的 b、目前正处于解锁状态 。
如果flag为false，表示目前未锁屏
```

注明：上面的两种方法，也可以通过反射机制来调用。
下面以第一个方法为例说明一下。

```java
private static Method mReflectScreenState;
try {
    mReflectScreenState = PowerManager.class.getMethod(isScreenOn, new Class[] {});
    PowerManager pm = (PowerManager) context.getSystemService(Activity.POWER_SERVICE);
    boolean isScreenOn= (Boolean) mReflectScreenState.invoke(pm);
} catch (Exception e) {
    e.printStackTrace()
}
```

- **接收广播**

接收系统广播事件，屏幕在三种状态（开屏、锁屏、解锁）之间变换的时候，系统都会发送广播，我们只需要监听这些广播即可。
代码如下：

```java
private ScreenBroadcastReceiver mScreenReceiver;

    /**
     * 定义广播接收者 接受屏幕状态
     */
    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                // 开屏
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                // 锁屏
                ActivityCollecter.finishAll();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                // 解锁
            }
        }
    }

    private void startScreenBroadcastReceiver() {
        mScreenReceiver = new ScreenBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mScreenReceiver, filter);//注册屏幕开关的广播
    }
```