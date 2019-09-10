#DialogFragment的使用说明1

弹框有三种类型的可供使用，PopupWindow、dialog，DialogFragment。
比如说需求：

- 只拦截自身所占空间部分的事件，其余空间的点击事件不处理

- 可以根据改变View的布局排列方式，View是否设置底部背景及居中方式

虽然在功能上 PopupWindow 更符合需要，dialog也能做到，但是使用 DialogFragment 代码更简洁、更方便封装功能模块。

##DialogFragment

基于Fragment的DialogFragment。

- 从代码的编写角度看，Dialog使用起来要更为简单
 
- Android 官方推荐使用 DialogFragment 来代替 Dialog ，可以让它具有更高的可复用性（降低耦合）和更好的便利性（很好的处理屏幕翻转的情况）。
 
- DialogFragment果然有一个非常好的特性（在手机配置变化，导致Activity需要重新创建时

- 例如旋屏，基于DialogFragment的对话框将会由FragmentManager自动重建，然而基于Dialog实现的对话框则没有这样的能力）。


**下面是两段实例代码：**
他们使用的界面都一样：（dialog.xml）
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" >

    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/ic_launcher" />

</LinearLayout>
```

1. **基于Dialog实现的对话框**
```java
public class MainActivity extends Activity {
	private Button clk;
	private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		clk = (Button) findViewById(R.id.clk);
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog);
		clk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.show();
			}
		});
	}
}
```
当我们点击按钮时，会弹出对话框（内容为android logo），当我们旋转屏幕后，Activity重新创建，整个Activity的界面没有问题，而对话框消失了。

除此之外，其实还有一个问题，就是在logcat中会看到异常信息：Android…leaked … window，这是因为在Activity结束之前，Android要求所有的Dialog必须要关闭。

我们旋屏后，Activity会被重建，而上面的代码逻辑并没有考虑到对话框的状态以及是否已关闭。

优化dialog代码修改为：

```java
public class MainActivity extends Activity {
	private Button clk;
	private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		clk = (Button) findViewById(R.id.clk);
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog);
		clk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.show();
			}
		});

		//用户恢复对话框的状态
		if(savedInstanceState != null && savedInstanceState.getBoolean("dialog_show"))
			clk.performClick();
	}

	/**
	 * 用于保存对话框的状态以便恢复
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(dialog != null && dialog.isShowing())
			outState.putBoolean("dialog_show", true);
		else
			outState.putBoolean("dialog_show", false);
	}

	/**
	 * 在Activity销毁之前，确保对话框以关闭
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(dialog != null && dialog.isShowing())
			dialog.dismiss();
	}
}
```

2.. **基于DialogFragment的对话框**
与上面的对话框使用同样的界面布局，此处仅仅展现一个简单对话框，因此只重写了onCreateView方法

```java
public class MyDialogFragment extends DialogFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog, container, false);
		return v;
	}
}


public class MainActivity extends FragmentActivity {
	private Button clk;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		clk = (Button) findViewById(R.id.clk);
		clk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyDialogFragment mdf = new MyDialogFragment();
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				mdf.show(ft, "df");
			}
		});
	}
}
```
这两段代码可以实现第一种方式的同样功能，此处我们并没有去关心对话框的重建，以及Activity销毁前对话框是否已关闭，这一切都是由FragmentManager来管理。

其实DialogFragment还拥有fragment的优点，即可以在一个Activity内部实现回退（因为FragmentManager会管理一个回退栈）

##**创建：**
创建 DialogFragment 有两种方式：

覆写其 onCreateDialog 方法

应用场景：一般用于创建替代传统的 Dialog 对话框的场景，UI 简单，功能单一。
方法
覆写其 onCreateView 方法

应用场景：
一般用于创建复杂内容弹窗或全屏展示效果的场景，UI 复杂，功能复杂，一般有网络请求等异步操作。

##**覆写其 onCreateDialog**

```
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
  	//为了样式统一和兼容性，可以使用 V7 包下的 AlertDialog.Builder
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
  	// 设置主题的构造方法
	// AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
    builder.setTitle("注意：")
           .setMessage("是否退出应用？")
           .setPositiveButton("确定", null)
           .setNegativeButton("取消", null)
           .setCancelable(false);
           //builder.show(); // 不能在这里使用 show() 方法
    return builder.create();
}
```

##**自定义 View 来创建**
```
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
  	// 设置主题的构造方法
	// AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
	LayoutInflater inflater = getActivity().getLayoutInflater();  
    View view = inflater.inflate(R.layout.fragment_dialog, null);  
    builder.setView(view) 
 	// Do Someting,eg: TextView tv = view.findViewById(R.id.tv);
    return builder.create();
}
```
##处理屏幕翻转
如果使用传统的 Dialog ，需要我们手动处理屏幕翻转的情况

但使用 DialogFragment 的话，则不需要我们进行任何处理， FragmentManager 会自动管理 DialogFragment 的生命周期。

##无标题栏
```
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
	LayoutInflater inflater = getActivity().getLayoutInflater();
	View view = inflater.inflate(R.layout.fragment_dialog, null);
	Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
  	// 关闭标题栏，setContentView() 之前调用
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	dialog.setContentView(view);
	dialog.setCanceledOnTouchOutside(true);
	return dialog;
}
```
对于在onCreateView里
```
@Override
public void onCreate(@Nullable Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	/**
	 * setStyle() 的第一个参数有四个可选值：
	 * STYLE_NORMAL|STYLE_NO_TITLE|STYLE_NO_FRAME|STYLE_NO_INPUT
	 * 其中 STYLE_NO_TITLE 和 STYLE_NO_FRAME 可以关闭标题栏
	 * 每一个参数的详细用途可以直接看 Android 源码的说明
	 */
	setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
}
```

##实现全屏（宽/高度全屏）
```
// 设置宽度为屏宽、位置靠近屏幕底部
Window window = dialog.getWindow();
window.setBackgroundDrawableResource(R.color.transparent);
WindowManager.LayoutParams wlp = window.getAttributes();
wlp.gravity = Gravity.BOTTOM;
wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
window.setAttributes(wlp); 
```

##DialogFragment
```
public class MyShouDialogFramgment2 extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //为了样式统一和兼容性，可以使用 V7 包下的 AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog, null);
        Button    btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
        Button     btn_ok = (Button) view.findViewById(R.id.btn_ok);
        final Dialog dialog = new Dialog(getActivity(), R.style.activity_DialogTransparent);
        // 关闭标题栏，setContentView() 之前调用
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetToast.useString(getActivity(),"今晚开始搞事情了...");
            }
        });
        return dialog;
    }
}
```

总结
可以让DialogFragment的使用像Dialog一样的简单、灵活，同时也保持了DialogFragment的优点，可以在任何的类中使用

很简单的新增新类型的Dialog

利用Fragment的特性，为不同屏幕做弹窗