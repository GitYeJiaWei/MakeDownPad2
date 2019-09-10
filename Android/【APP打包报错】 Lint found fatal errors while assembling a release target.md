#【APP打包报错】 Lint found fatal errors while assembling a release target

使用debug的时候编译是没有报错的，可以生成apk，进行正式打包的时候，release版本build的时候报错了，报错信息如下：

```
Lint found fatal errors while assembling a release target. 

To proceed, either fix the issues identified by lint, or modify your build script as follows:  ...

  android {     

	lintOptions {       

  	checkReleaseBuilds false         

// Or, if you prefer, you can continue to check for errors in release builds,       

  // but continue the build even when errors are found:         

		abortOnError false      }

  }
```

给出的解决方案其实是忽略这个问题，但是这样可能会对正式发布的app埋下隐患，所以还是尽量找出问题所在。

打包出错除了上面的日志没有其他详情信息，根据网上的资料和解决方法，找到对应的Android工程下的

app/build/reports/lint-results-release-fatal.html 文件

打开后可以看到问题的详细原因：

![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/%E5%85%B6%E5%AE%83%E5%9B%BE%E7%89%87/4.png)

在对应的问题处运行，查看日志，发现问题出现在如下代码处：
```
2019-03-25 16:01:41.891 12940-12940/? E/AndroidRuntime: FATAL EXCEPTION: main Process: com.meizu.flyme.find, PID: 12940 java.lang.RuntimeException: Unable to start activity ComponentInfo{com.meizu.flyme.find/com.meizu.flyme.find.ui.MapHistoryActivity}: java.lang.NullPointerException: Attempt to invoke virtual method 'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3079) at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3214) at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:78) at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:108) at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:68) at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1936) at android.os.Handler.dispatchMessage(Handler.java:106) at android.os.Looper.loop(Looper.java:193) at android.app.ActivityThread.main(ActivityThread.java:6921) at java.lang.reflect.Method.invoke(Native Method) at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:493) at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:937)Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference at com.meizu.widget.b.<init>(SourceFile:45) at com.meizu.flyme.find.ui.MapHistoryActivity.a(SourceFile:100) at com.meizu.flyme.find.ui.MapHistoryActivity.onCreate(SourceFile:77) 
```

定位到问题出现在 一个TextView的setText()操作中，原因是同步代码不完全，TextView在layout中没有对应的控件。

解决之后release打包正常。问题解决