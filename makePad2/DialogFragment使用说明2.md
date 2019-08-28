#DialogFragment使用说明2

DialogFragment是Android3.0之后出来的组件，DialogFragment其实就是一个dialog对话框。跟AlertDialog相比呢DialogFragment具有完整的生命周期，更好控制一点。单独的AlertDialog在旋转设备的时候会消失，如果使用DialogFragment就不会有这个情况出现（旋转后对话框会重新创建）。下面来简单介绍一下DialogFragment的使用：

首先自己新建一个MyDialogFragment继承DialogFragment,这里要选择v4包的，可以更好的向后兼容（在api28上app包的DialogFragment已经被废弃了），重写onCreateDialog()方法：

```java
import android.support.v4.app.DialogFragment
 
public class MyDialogFragment extends DialogFragment {
 
    public static MyDialogFragment newInstance() {
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        return myDialogFragment;
    }
 
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        
        return super.onCreateDialog(savedInstanceState);
    }
}
```

接下来我们就在onCreateDialog()方法里面创建自己的布局，先实例化一个AlertDialog对话框，进行相关配置。最后返回dialog即可：

```
 public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("我是标题");
        dialog.setMessage("我是内容");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "确定", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
            }
        });
        return dialog.create();
    }
```
到这里一个简单的DialogFragment就完成了，接下来调用一下：

```
public class MainActivity extends AppCompatActivity {
 
    private static final String DIALOG_DATA = "DialogData";
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                MyDialogFragment myDialogFragment = MyDialogFragment.newInstance();
                myDialogFragment.show(fragmentManager, DIALOG_DATA);
            }
        });
    }
}
```

![图片](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/pic1/20181212172615790.png)

##数据交互
**DialogFragment向Activity传递信息**

接下来我们用一个简单的登录操作来介绍一下DialogFragment与Activity的数据交互。

 首先编写页面布局，代码如下：
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
 
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="8dp"
        android:text="用户登录"
        android:textSize="18sp" />
 
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        android:orientation="horizontal">
 
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="账号：" />
 
        <EditText
            android:id="@+id/account"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1" />
    </LinearLayout>
 
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        android:orientation="horizontal">
 
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="密码：" />
 
        <EditText
            android:id="@+id/password"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1" />
    </LinearLayout>
 
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="8dp">
 
        <Button
            android:id="@+id/cancel"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="取消" />
 
        <Button
            android:id="@+id/submit"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="确定" />
    </LinearLayout>
</LinearLayout>
```
然后在MyDialogFragment的onCreateDialog方法中加入自定义的布局，代码如下：

```
@NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_login, null);
        dialog.setView(view);
        final EditText account = view.findViewById(R.id.account);
        final EditText password = view.findViewById(R.id.password);
        Button cancel = view.findViewById(R.id.cancel);
        Button submit = view.findViewById(R.id.submit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                dismiss();
            }
        });
        return dialog.create();
    }
```
完成这一步之后开始编写数据传递，从DialogFragment中传数据给Activity我们采用的是回调。如果对回调不熟悉的可以去看下这篇文章https://blog.csdn.net/qq1161857279/article/details/88105287

第一步：先在DialogFragment中定义一个接口：

```
interface MyDialogListener {
        void getLoginInfo(String account, String password);
    }
```
第二步：在DialogFragment中编写实例化接口的方法 ：
```
 private MyDialogListener mMyDialogListener;
 
    public void setMyDialogListener(MyDialogListener myDialogListener) {
        mMyDialogListener = myDialogListener;
    }
```
第三步：在想要回调的方法中调用该接口的方法，这里我们想要得到用户登录名、密码，我们可以这样做：
```
submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在确定点击事件里调用接口方法
                mMyDialogListener.getLoginInfo(account.getText().toString(), password.getText().toString());
                dismiss();
            }
        });
```

到这里DialogFragment中的工作基本完成，完整的DialogFragment代码如下：
```
public class MyDialogFragment extends DialogFragment {
 
    private MyDialogListener mMyDialogListener;
 
    public void setMyDialogListener(MyDialogListener myDialogListener) {
        mMyDialogListener = myDialogListener;
    }
 
    public static MyDialogFragment newInstance() {
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        return myDialogFragment;
    }
 
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_login, null);
        dialog.setView(view);
        final EditText account = view.findViewById(R.id.account);
        final EditText password = view.findViewById(R.id.password);
        Button cancel = view.findViewById(R.id.cancel);
        Button submit = view.findViewById(R.id.submit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在确定点击事件里调用接口方法
                mMyDialogListener.getLoginInfo(account.getText().toString(), password.getText().toString());
                dismiss();
            }
        });
        return dialog.create();
    }
 
    interface MyDialogListener {
        void getLoginInfo(String account, String password);
    }
}
```

接下来我们就在Activity中调用DialogFragment的回调方法即可获取相关信息：

```
public class MainActivity extends AppCompatActivity {
 
    private static final String DIALOG_DATA = "DialogData";
 
    private Button mSubmit;
    private TextView mAccount;
    private TextView mPassword;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSubmit = findViewById(R.id.submit);
        mAccount = findViewById(R.id.account);
        mPassword = findViewById(R.id.password);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                MyDialogFragment myDialogFragment = MyDialogFragment.newInstance();
                myDialogFragment.show(fragmentManager, DIALOG_DATA);
                //获取登录信息
                myDialogFragment.setMyDialogListener(new MyDialogFragment.MyDialogListener() {
                    @Override
                    public void getLoginInfo(String account, String password) {
                        mAccount.setText(account);
                        mPassword.setText(password);
                    }
                });
            }
        });
    }
}
```
**Activity向DialogFragment传递信息**

第一步：使用DialogFragment中的setArguments()方法，这个方法可携带一个Bundle数据。在DialogFragment中的newInstance()方法中添加此方法，代码如下

```
public static MyDialogFragment newInstance(String hintAccount, String hintPassword) {
        Bundle bundle = new Bundle();
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        bundle.putString("hint_account", hintAccount);
        bundle.putString("hint_password", hintPassword);
        myDialogFragment.setArguments(bundle);
        return myDialogFragment;
    }
```

第二步：修改Activity中的newInstance()方法，代码如下：

```
MyDialogFragment myDialogFragment = MyDialogFragment.newInstance(
                        mAccount.getText().toString(), mPassword.getText().toString());
```

第三步：在MyDialogFragment中获取数据，代码如下：
```
@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mHintAccount = bundle.getString("hint_account");
            mHintPassword = bundle.getString("hint_password");
        }
    }
```

完整代码如下：

Activity:
```
public class MainActivity extends AppCompatActivity {
 
    private static final String DIALOG_DATA = "DialogData";
 
    private Button mSubmit;
    private TextView mAccount;
    private TextView mPassword;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSubmit = findViewById(R.id.submit);
        mAccount = findViewById(R.id.account);
        mPassword = findViewById(R.id.password);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                MyDialogFragment myDialogFragment = MyDialogFragment.newInstance(
                        mAccount.getText().toString(), mPassword.getText().toString());
                myDialogFragment.show(fragmentManager, DIALOG_DATA);
                //获取登录信息
                myDialogFragment.setMyDialogListener(new MyDialogFragment.MyDialogListener() {
                    @Override
                    public void getLoginInfo(String account, String password) {
                        mAccount.setText(account);
                        mPassword.setText(password);
                    }
                });
            }
        });
    }
}
```

DialogFragment:
```
public class MyDialogFragment extends DialogFragment {
 
    private MyDialogListener mMyDialogListener;
    private String mHintAccount = "";
    private String mHintPassword = "";
 
    public void setMyDialogListener(MyDialogListener myDialogListener) {
        mMyDialogListener = myDialogListener;
    }
 
    public static MyDialogFragment newInstance(String hintAccount, String hintPassword) {
        Bundle bundle = new Bundle();
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        bundle.putString("hint_account", hintAccount);
        bundle.putString("hint_password", hintPassword);
        myDialogFragment.setArguments(bundle);
        return myDialogFragment;
    }
 
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mHintAccount = bundle.getString("hint_account");
            mHintPassword = bundle.getString("hint_password");
        }
    }
 
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_login, null);
        dialog.setView(view);
        final EditText account = view.findViewById(R.id.account);
        final EditText password = view.findViewById(R.id.password);
        account.setText(mHintAccount);
        password.setText(mHintPassword);
        Button cancel = view.findViewById(R.id.cancel);
        Button submit = view.findViewById(R.id.submit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在确定点击事件里调用接口方法
                mMyDialogListener.getLoginInfo(account.getText().toString(), password.getText().toString());
                dismiss();
            }
        });
        return dialog.create();
    }
 
    interface MyDialogListener {
        void getLoginInfo(String account, String password);
    }
}
```
