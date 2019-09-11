#Toolbar的正确使用姿势

##前言
Toolbar是谷歌在2014年Google IO 大会上推出的一套全新的设计规范Material Design。它的出现规范了Android开发者APP标题栏的设计风格，极大地提高了开发效率，而且Material Design设计规范也越来越多出现在我们常用的APP中。所以，学习和使用Toolbar是必要的。

##初识Toolbar

![]()

根据图中的资料，可以知道，Toolbar首先是一个ViewGroup，它是用来做APP的标题栏，其中包括5个部分，分别是一个导航按钮（a navigation button）、一个logo图片(a branded logo image)、一个标题和副标题(a title and subtitle)、一个或多个自定义View(one or more custom views)以及一个action menu( an action menu)。看一张效果图

![]()

从效果图中，我们可以很明显地看出来5个部分都是哪里，因为Toolbar是一个ViewGroup，你只有都设置出来了才会显示，如果不设置的话，那么都是空的，啥也没有。那么究竟应该怎么设置呢？下面我们就开始使用Toolbar。

##Toolbar的正确使用姿势

###第一步 导入v7包
	implementation 'com.android.support:appcompat-v7:27.0.2

###第二步 继承AppCompatActivity
	public class MainActivity extends AppCompatActivity

###第三步 设置主题theme
```
 <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat.NoActionBar">
```
这里如果不想让所有的app共用一个主题，可以不在application中设置，可以在单独的activity里面设置。

###第四步 各种设置
前三步都很简单，而且基本都是新建项目都能创建好的，这里就不多讲了，重点就在这第四步怎么设置这里了。
首先，在布局文件中的基本属性设置：
```
 <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="@color/colorPrimary"
        app:logo="@mipmap/ic_launcher"
        app:title="标题"
        app:titleTextColor="#fff"
        app:subtitle="副标题"
        app:subtitleTextColor="#fff"
        app:navigationIcon="@drawable/ic_menu"
        android:theme="@style/Base.Theme.AppCompat.Light"
        app:popupTheme="@style/toolBar_pop_item"
        >
```

这里的属性设置了导航按钮、logo和主标题副标题，属性名称很清楚不多讲，action menu的设置需要通过代码，自定义View放到后面来讲。
导航Button设置点击事件
```
mToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);//利用Toolbar代替ActionBar
        //设置导航Button点击事件
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"点击导航栏",Toast.LENGTH_SHORT).show();
            }
        });
```

设置action menu
```
 //设置移除图片  如果不设置会默认使用系统灰色的图标
        mToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.icon_action));
//填充menu
        mToolbar.inflateMenu(R.menu.toolbar_menu);
//设置点击事件
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_settings:
                        Toast.makeText(MainActivity.this,"action_settings",Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.action_share:
                        Toast.makeText(MainActivity.this,"action_share",Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.action_search:
                        Toast.makeText(MainActivity.this,"action_search",Toast.LENGTH_SHORT).show();

                        break;
                        default:
                            break;
                }
                return false;
            }
        });
```

你发现设置了这一对之后，action menu 依然没有显示出来，因为你还没有重写onCreateOptionsMenu，让action menu显示出来。
```
@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }
```

加上这个重写方法以后，action menu就会显示，如同上面的介绍图一样，这个时候有朋友就可能问了，为啥action menu在标题栏上显示这么多图标

![]()

下面我们来看一下R.menu.toolbar_menu这个配置文件
```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".MainActivity">
    <item
        android:id="@+id/action_settings"
        android:orderInCategory="100"
        android:title="测试1"
        android:icon="@drawable/icon_search"
        app:showAsAction="ifRoom"/>
    <item
        android:id="@+id/action_share"
        android:orderInCategory="100"
        android:title="测试2"
        android:icon="@drawable/icon_notify"
        app:showAsAction="ifRoom"/>
    <item
        android:id="@+id/action_search"
        android:orderInCategory="100"
        android:title="设置"
        app:showAsAction="never"/>
    <item
        android:id="@+id/action_search2"
        android:orderInCategory="100"
        android:title="关于"
        app:showAsAction="never"/>
</menu>
```

这其中app:showAsAction属性的作用是来控制item在标题栏上展示的形式，一般多取三个值：always、ifRoom以及never。always:总是展示在标题栏上；ifRoom如果标题栏上有位置就展示出来；never：永不展示标题栏。我这里设置的是前两个item的showAsAction属性是ifRoom，后两个是never，所以在状态栏上前两个图标被展示出来了，因为还有两个item未被在标题栏上显示出来，系统会默认一个图标让用户来点击。这里如果我们把mToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.icon_action)); 这句代码注释掉，结果就会是这样

![]()

，如果没有未被展示的item，这里就不会出现这个图标。
点击溢出图标，系统默认的弹出样式是这样的

![]()

没错，就是这么丑，那么怎么设置一下这个弹出框，能让它变得好看一些，并且符合我们设计师的要求呢？在xml文件里

![]()

就是通过app:popupTheme属性来控制的，在style文件里可以设置风格、字体颜色大小等等属性。简单看一下toolBar_pop_item
```
<style name="toolBar_pop_item" parent="Base.Theme.AppCompat.Light">
        <item name="android:textColor">@color/colorAccent</item>
</style>
```
看一下现在的效果

![]()

具体的效果根据UI设计师的设计手稿来定

##小结

关于ToolBar的使用，在xml布局文件里面，可以通过属性设置好导航Button、logo图标以及正副标题，在代码可以设置导航Button的点击事件，action menu通过代码来设置，如果要显示出来记得重写onCreateOptionsMenu(Menu menu)方法，关于让menu里面的item在标题栏中的显示通过showAsAction属性来控制，常用的三个属性分别是always、ifRoom以及never，item弹出的样式可以通过style文件来设置。知道这些，基本就可以完成标题栏导航栏、logo图标、正副标题已经action menu的设置了，关于自定义View，下面会单独拿出来讲。

##标题居中问题和自定义Toolbar
关于标题居中问题，我看很多小伙伴们都提出过，其实解决起来非常的简单，就是利用自定义View。之前文中提到过Toolbar是一个ViewGroup，如果需要添加自定义View，只需要在Toolbar里面增加其子ViewGroup或者子View。

```
<android.support.v7.widget.Toolbar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/colorPrimary"
        >

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_marginLeft="10dp"
            android:layout_marginRight="10dp"
            android:gravity="center_vertical"

            >
            <ImageView
                android:id="@+id/iv_back"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:src="@drawable/ic_back"
                android:layout_centerVertical="true"
                />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="#fff"
                android:text="标题"
                android:textSize="18sp"
                android:layout_centerHorizontal="true"
                />

               <EditText
                   android:visibility="gone"
                   android:layout_width="match_parent"
                   android:layout_height="wrap_content"
                   android:layout_marginLeft="50dp"
                   android:layout_marginRight="50dp"
                   android:layout_centerHorizontal="true"
                   android:background="@drawable/search_bg"
                   android:drawableLeft="@drawable/icon_search"
                   android:padding="5dp"
                   android:textColorHint="#fff"
                   android:hint="请输入搜索内容"
                   android:gravity="center"
                   android:cursorVisible="false"
                   />
        </RelativeLayout>
    </android.support.v7.widget.Toolbar>
```

通过这种自定义View方式就可以解决标题居中的问题，看一下效果

![]()

注意这里返回键不要通过Toolbar的导航Button设置，这样会影响标题居中的效果，直接在自定义View里面设置就行了。
有些App用搜索框，其实也是利用自定义View来实现，实现起来也很简单，搜索框在中间跟标题重叠，通过设置可见性来调控，简单看一下效果

![]()

写到这里，肯定会有小伙伴问了，这里使用Toolbar有什么用，我自己写一个RelativeLayout或者其他什么布局都能实现，为啥非要用Toolbar呢？这里说一下，使用Toolbar比起传统的自定义布局的好处。第一、不需要考虑标题栏和系统状态栏匹配的问题，你自己写还得匹配系统状态栏；第二、就是Toolbar可以和其他的MD设计风格的空间连用，做出比较炫的效果，比如Toolbar+NestScrollView,Toolbar+DrawerLayout + NavigationView等等；第三、谷歌推荐的控件当然要用（嘿嘿，强行凑三条）。

##总结

Toolbar是一个ViewGroup，用来做App的标题栏，主要有5部分，导航Button、logo、正副标题、自定义View以及action menu。通过xml文件属性可以设置导航Button、logo和正副标题，通过代码设置action menu，利用自定义View可以解决标题不居中的问题。