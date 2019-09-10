#关于ComponentName的使用

ComponentName，顾名思义，就是组件名称，通过调用Intent中的setComponent方法，我们可以打开另外一个应用中的Activity或者服务。

实例化一个ComponentName需要两个参数，第一个参数是要启动应用的包名称，这个包名称是指清单文件中列出的应用的包名称：

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.otherapp">

	...

```

第二个参数是你要启动的Activity或者Service的全称（包名+类名），代码如下：

启动一个Activity：

```
Intent intent = new Intent();
			intent.setComponent(new ComponentName("com.example.otherapp",
					"com.example.otherapp.MainActivity2"));
			startActivity(intent);
```

启动一个Service：
```
			Intent it = new Intent();
			it.setComponent(new ComponentName("com.example.otherapp",
					"com.example.otherapp.MyService"));
			startService(it);
```

##注意
如果你要的启动的其他应用的Activity不是该应用的入口Activity，那么在清单文件中，该Activity节点一定要加上android:exported="true"，表示允许其他应用打开，对于所有的Service，如果想从其他应用打开，也都要加上这个属性：

```
        <service
            android:name="com.example.otherapp.MyService"
            android:exported="true" >
        </service>
 
        <activity
            android:name="com.example.otherapp.MainActivity2"
            android:exported="true" >
        </activity>
```

对于除了入口Activity之外的其他组件，如果不加这个属性，都会抛出“java.lang.SecurityException: Permission Denial.....”异常(没有权限异常)


那么为什么入口Activity不用添加这个属性就可以被其他应用启动呢？我们来看一段入口Activity的注册代码：
```
        <activity
            android:name=".MainActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
 
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
```

入口Activity和普通Activity唯一不同的地方就是入口Activity多了一个过滤器，对于包含了过滤器的组件，意味着该组件可以提供给外部的其他应用来使用，它的exported属性默认为true，相反，如果一个组件不包含任何过滤器，那么意味着该组件只能通过指定明确的类名来调用，也就是说该组件只能在应用程序的内部使用，在这种情况下，exported属性的默认值是false
