#Android 错误锦集

##问题查找方法
>在Build中，找到Toggle view,查看出错的地方

##报错1：D:\Android\Android Projects\xxx\app\build\intermediates\instant_run_split_apk_resources

大概意思就是在 instant run 的过程中出现了问题，可以通过取消 instant run 的功能解决：

File->Settings->Build,Execution ->Instant Run 去掉前面的“√”即可


##报错2：Fragment 中报错 commit already called

每一个实例化后的 FragmentTransaction 的事物只能被提交一次，我开始时是定义了一个全局的  FragmentTransaction，然后就只在 onCreate() 方法中实例化了一次，但是我设置点击事件切换 fragment 时，会导致二次 commit，所以会报错。
```
@Override
    public void onClick(View view) {
        switch (view.getId()) {
 
            case R.id.btn_first:
                mTransaction.hide(secondFragment).hide(thirdFragment).show(firstFragment).commit();
                break;
            case R.id.btn_second:
                mTransaction.hide(firstFragment).hide(thirdFragment).show(secondFragment).commit();
                break;
            case R.id.btn_third:
                mTransaction.hide(firstFragment).hide(secondFragment).show(thirdFragment).commit();
                break;
            default:
                break;
        }
    }
```

修改后
```
@Override
    public void onClick(View view) {
        // 实例化 mTransaction
        mTransaction = mManger.beginTransaction();
 
        switch (view.getId()) {
 
            case R.id.btn_first:
                mTransaction.hide(secondFragment).hide(thirdFragment).show(firstFragment).commit();
                break;
            case R.id.btn_second:
                mTransaction.hide(firstFragment).hide(thirdFragment).show(secondFragment).commit();
                break;
            case R.id.btn_third:
                mTransaction.hide(firstFragment).hide(secondFragment).show(thirdFragment).commit();
                break;
            default:
                break;
        }
    }
```

我们添加了一行代码，每一次我们发生点击事件时，都会重新实例化一下 mTransaction。

    总结：当我们发现 commit already called 错误时，我们可以看看我们是不是在一个实例化里面多次重复 commit 了，如果是我们可以选择合并要 commit 的事件，或者重新实例化 FragmentTransaction。

##错误3：Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference


>解决方法：这个null异常通常是找不到该Layout中的TextView组件，检查layout和 
 初始化组件是否有误,也可能是@BindView(R.id.tv_top) LinearLayout tvTop;黄油刀找不到Layout组件


##错误4：error: duplicate value for resource 'attr/hintColor' with config ''.

>error: duplicate value for resource ‘attr/hintColor’ with config ”. 大概意思就是这个资源“attr/hintColor”和配置文件的值重复了,于是找到了attr文件找到了 hintColor ，把重复的 hintColor 改为 hint_Color，再次运行，ok，解决了


##错误5：java.lang.VirtualMachineError: Invoking sendto with bad arg 0, type 'Ljava/util/HashMap;' not instance of 'Ljava/io/FileDescriptor;'

>虚拟机报错异常，具体原因不知道,只有解决方法,就是通过'Ljava/util/HashMap;'这句话把 

	@FieldMap Map<String,Object> params
>改成

	@Field("Bat") int bat,@Field("Network") int Network,@Field("DustbinStatus") String DustbinStatus


