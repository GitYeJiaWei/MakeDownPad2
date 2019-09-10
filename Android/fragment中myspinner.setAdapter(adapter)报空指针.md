#fragment中myspinner.setAdapter(adapter)报空指针

在fragment里设置下拉框时，myspinner.setAdapter(adapter)总是报空指针，找了下原因：

##adapter为空（其实它不为空）
因为是在fragment中使用createFromResource，我本来以为是这个它的第一个参数context写错导致adapter为空，实际上不是的。找了很多资料，顺便共享一下几个知识点

```
 /*
          /*
         * 第二个参数是显示的布局
         * 第三个参数是在布局显示的位置id
         * 第四个参数是将要显示的数据
         */
        ArrayAdapter adapter2 = new ArrayAdapter(AppApplication.getApplication(), R.layout.item, R.id.text_item, list);
        spKuqu.setAdapter(adapter2);
```

- fragment里的this通常用getActivity().this，fragment里的context通常用getActivity().getApplication()

- getApplication(); getApplicationContext(); getBaseContext(); MainActivity.this; this; getContext()的区别
    在Android框架中，有两种类别的Context，分别是Application Context和Activity Context：

Application Context：存在于“应用”的整个生命周期，只要应用存在，Application Context就存在，并且是唯一的。当应用被销毁了，Application Context才被销毁。

Activity Context：存在于“Activity”的整个生命周期，当onDestroy()执行后，Activity Context也被销毁。

- getApplication()：虽然它返回的是Application对象，但Application类继承自Context，所以它可以用来提供Application Context；

- getApplicationContext()：返回Application Context；

- getBaseContext()：返回Activity Context；

- MainActivity.this：表示MainActivity对象，一般用在内部类中指示外面的this，如果在内部类直接用this，指示的是内部类本身。因为MainActivity继承Activity，而Activity继承Context，所以它也可以用来提供Activity Contex；

- this：表示当前对象；当它表示MainActivity时，也可以用来提供Activity Context，原因同上。

- getContext()：这个是View类中提供的方法，在继承了View的类中才可以调用，返回的是当前View运行在哪个Activity Context中。前面的3个方法可以在Activity中调用。

##myspinner为空

我的spinner用的是Bind找到的，错误就出在这里。

fragment中还是不要用Bind，找到数据多是在onViewCreated中用myspinner = (Spinner)view.fingViewById(R.id.spinner)
