#Android 捕捉app系统中未处理的异常

一：为什么要处理？



其实我们都知道，在开发过程中，自己的app系统或许有许多隐藏的异常，自己没有捕捉到，那么关于异常的捕捉，这是相当重要的，如果系统发生崩溃，那么至少也可以让系统挂在系统之内，不会发现什么系统直接退了，或者是卡死，这样做，能够使得用户体验效果更加，自己也可以发现用户到底出现什么异常，便于自己以后好处理这个问题，优化处理自己的系统。



二：如何解决


在Android 开发中，自身其实带有一个系统默认的异常处理接口，UncaughtExceptionHandler，该接口呢，能够很好的处理程序中发生的异常，所以，往往开发者都喜欢使用它，而且它也是一个非常简单好用的东西。



三：具体实现
（1）实现UncaughtExceptionHandler接口的类

```
package com.x1.tools;
 
import java.lang.Thread.UncaughtExceptionHandler;
 
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
 
import com.x1.dao.SubmitConfigBugs;
import com.x1.ui.R;
 
/**
 * 未捕获异常捕捉类
 * 
 * @author zengtao 2015年5月6日
 *
 *
 */
public class CrashHandlers implements UncaughtExceptionHandler {
 
	public static final String TGA = "CrashHandlers";
 
	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
 
	// CrashHandler实例
	private static CrashHandlers instance;
	// 程序的Context对象
	private Context mContext;
 
	private GetPhoneInfo phone;
 
	/** 保证只有一个CrashHandler实例 */
	private CrashHandlers() {
	}
 
	/** 获取CrashHandler实例 ,单例模式 */
	public synchronized static CrashHandlers getInstance() {
		if (instance == null) {
			instance = new CrashHandlers();
		}
		return instance;
	}
 
	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
		phone = new GetPhoneInfo(context);
	}
 
	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(thread, ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TGA, e.toString());
			}
			// 退出程序
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}
 
	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Thread thread, Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				ShowToast.show(mContext, "喵,很抱歉,程序出现异常,即将退出!",
						R.drawable.error_icon);
				Looper.loop();
			}
		}.start();
		// 把异常信息和设备信息上传到服务器
		subMitThreadAndDeviceInfo(mContext, thread, ex);
		return true;
	}
 
	// 提交信息到服务器
	public void subMitThreadAndDeviceInfo(Context ctx, Thread thread,
			Throwable ex) {
		// 当前用户的账号
		String Account = null;
		if (Config.getCachedAccount(ctx) != null) {
			Account = Config.getCachedAccount(ctx);
		} else {
			Account = "当前无用户登录";
		}
		// 手机设备的信息
		String PhoneModel = phone.PhoneModel;
		String PhoneVersion = phone.PhoneVersion;
		String PhoneResolution = phone.PhoneResolution;
		String ZcmVersion = phone.ZcmVersion;
		String AvailableRom = phone.AvailableRom;
		// 获取当前显示在界面上的Activity的路径加类名
		ActivityManager am = (ActivityManager) ctx
				.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		// 异常信息加异常所在类的绝对路径
		final String content = "thread: " + thread + " , name: "
				+ thread.getName() + ", id: " + thread.getId()
				+ ", exception: " + ex + ", " + cn.getClassName();
 
		// 执行接口,把异常信息提交到服务器
		new SubmitConfigBugs(0, ctx, Account, PhoneModel, PhoneVersion,
				PhoneResolution, ZcmVersion, AvailableRom, content,
				new SubmitConfigBugs.SuccessCallback() {
					@Override
					public void onSuccess() {
						Log.e(TGA, content + "\n错误信息提交成功");
					}
				}, new SubmitConfigBugs.FailCallback() {
 
					@Override
					public void onFail() {
						Log.e(TGA, content + "\n错误信息提交失败");
					}
				});
	}
 
}
```

（2）实体类：手机信息

```
package com.x1.tools;
 
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
 
/**
 * 获取当前手机的设备信息和当前软件的版本
 * 
 * @author zengtao 2015年5月6日
 *
 *
 */
public class GetPhoneInfo {
	private Context context;
	public String PhoneModel;
	public String PhoneVersion;
	public String PhoneResolution;
	public String ZcmVersion;
	public String AvailableRom;
 
	public GetPhoneInfo(Context context) {
		this.context = context;
		PhoneModel = android.os.Build.MODEL;
		PhoneVersion = android.os.Build.VERSION.RELEASE;
		PhoneResolution = getDisplayWAndH();
		ZcmVersion = getAppVersionName(this.context);
		AvailableRom = "ROM剩余存储空间: " + getAvailableInternalMemorySize() + "MB"
				+ ",内置SDCard剩余存储空间: " + getAvailableExternalMemorySize() + "MB";
	}
 
	// 获取当前版本号
	private String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					"com.x1.ui", 0);
			versionName = packageInfo.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}
 
	// 获取屏幕分辨率
	@SuppressWarnings("deprecation")
	private String getDisplayWAndH() {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		String string = "屏幕分辨率: " + display.getWidth() + "x"
				+ display.getHeight();
		return string;
	}
 
	/**
	 * 
	 * @return ROM存储路径
	 */
	private String getInternalMemoryPath() {
		return Environment.getDataDirectory().getPath();
	}
 
	/**
	 * 
	 * @return 内置sd卡路径
	 */
	private String getExternalMemoryPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}
 
	/**
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件路径的StatFs对象
	 * @throws Exception
	 *             路径为空或非法异常抛出
	 */
	private StatFs getStatFs(String path) {
		try {
			return new StatFs(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 
	 * @param stat
	 *            文件StatFs对象
	 * @return 剩余存储空间的MB数
	 * 
	 */
	@SuppressWarnings("deprecation")
	private float calculateSizeInMB(StatFs stat) {
		if (stat != null)
			return stat.getAvailableBlocks()
					* (stat.getBlockSize() / (1024f * 1024f));
		return 0.0f;
	}
 
	/**
	 * 
	 * @return ROM剩余存储空间的MB数
	 */
	private float getAvailableInternalMemorySize() {
 
		String path = getInternalMemoryPath();// 获取数据目录
		StatFs stat = getStatFs(path);
		return calculateSizeInMB(stat);
	}
 
	/**
	 * 
	 * @return 内置SDCard剩余存储空间MB数
	 */
	private float getAvailableExternalMemorySize() {
 
		String path = getExternalMemoryPath();// 获取数据目录
		StatFs stat = getStatFs(path);
		return calculateSizeInMB(stat);
 
	}
}
```

四：总结

以上呢，就可以直接抓捕程序中未处理的异常，而且使得用户在系统自身开发崩溃的时候，不至于造成，直接挂了，所以，在程序中，做这样的处理是非常有必要的，在上面CrashHandlers有一个执行接口的处理，提交数据到服务器，这个是自身开发中所定义的接口，这个可以根据自己的想法实现，我在这个当中，是将手机提交的一些信息，和错误信息发送到服务器，而完成的，这样也可以让自己知道，是什么手机出错，有目的去处理，更好的维护自己的系统。