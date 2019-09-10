#LayoutInflater.from(this).inflate()方法参数解析

	三个参数的:
	public View inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)

```java
第一个参数：要获取的布局，传入R.layout.xxx
第二个参数：这个参数也是一个布局，是为第一个参数指定的父布局。
第三个参数（如果第二个参数为null）这个参数将失去作用
true：将第一个参数表示的布局添加到第二参数的布局中。
false：不将第一个参数表示的布局添加到第二参数的布局中。
既然不添加，那么为什么第二个参数不设置为null呢。
不添加的话，这个函数就只剩下一个作用了，那就是获取布局，为了使第一个参数的宽高属性不失效，所以要为他指定一个父布局
```

	两个参数的
	public View inflate(@LayoutRes int resource, @Nullable ViewGroup root)

```java
第一个参数：要获取的布局，传入R.layout.xxx
第二个参数：这个参数也是一个布局，是为第一个参数指定的父布局。
如果这个参数是null就不把第一个参数的布局添加进来
如果这个参数不是null就把第一个参数的布局添加进来
```