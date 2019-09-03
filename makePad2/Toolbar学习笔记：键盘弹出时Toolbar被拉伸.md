#Toolbar学习笔记：键盘弹出时Toolbar被拉伸

在Android开发中我们经常会用到Toolbar，为了App更加美观，也会支持沉浸式状态栏，这时，我们一般会这么做：
在style中添加

	<item name="android:windowTranslucentStatus">true</item>

在Toolbar中添加

	android:fitsSystemWindows="true"

这时，如果我们的layout中包含可滚动的控件如ListView、ScrollView（即在键盘弹出时会调用onSizeChanged方法），而且屏幕中包含可编辑的控件如EditText，就会出现意想不到的现象，即Toolbar自动拉伸，直到接近键盘顶部,Toolbar被顶出

目前有两种解决办法：
- 将Toolbar的android:fitsSystemWindows="true"属性移至root view**
这样可能无法支持沉浸式，但相信聪明的你肯定能找到兼顾的解决办法

- 在AndroidManifest中添加android:windowSoftInputMode="adjustPan"属性**
这个方法有一个前提就是你不需要在键盘弹出时让你的布局自适应屏幕（即在键盘弹出时view不会调用onSizeChanged方法），并且可能会导致键盘遮挡屏幕上的内容。

- 还有一种方法（没试过）
1.在所需要的布局上添加入Scrollview
```
<ScrollView ... >
        <Edittext ... />
        ... 
</ScrollView>
```
2.如果有设置windowSoftInputMode="adjustPan" 取消该设置
如果此时发现不生效 
3.在你的根布局的layout 下设置
android:fitsSystemWindows="true" 
4.如果有部分机型发现状态栏布局变透明
自定义了一个layout继承你的根layout。
重写fitSystemWindows方法，并且在根layout中声明 fitSystemWindows="true"
```
package com.meizu.mzbbs.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.RelativeLayout;

/**
 * Created by yangjingxuan on 16/6/15.
 */
public class SoftInputRelativeLayout extends RelativeLayout {

    private int[] mInsets = new int[4];

    public SoftInputRelativeLayout(Context context) {
        super(context);
    }

    public SoftInputRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public WindowInsets computeSystemWindowInsets(WindowInsets in, Rect outLocalInsets) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Intentionally do not modify the bottom inset. For some reason,
            // if the bottom inset is modified, window resizing stops working.
            // TODO: Figure out why.

            mInsets[0] = outLocalInsets.left;
            mInsets[1] = outLocalInsets.top;
            mInsets[2] = outLocalInsets.right;

            outLocalInsets.left = 0;
            outLocalInsets.top = 0;
            outLocalInsets.right = 0;
        }

        return super.computeSystemWindowInsets(in, outLocalInsets);

    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            mInsets[0] = insets.getSystemWindowInsetLeft();
            mInsets[1] = insets.getSystemWindowInsetTop();
            mInsets[2] = insets.getSystemWindowInsetRight();
            return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(0, 0, 0,
                    insets.getSystemWindowInsetBottom()));
        } else {
            return insets;
        }
    }

}

```


注：

1，为了支持透明状态栏，可以这样调整：

当fitsSystemWindows=true移到根root view时，状态栏文字图标看不见，此时可以在root view添加和ToolBar一样的背景色，再在ToolBar一下区域添加默认的窗口背景色，即可实现

2，添加adjustPan后，进入该界面软件盘会自动弹出，此时可以在root view添加属性:

	android:focusable="true"
	android:focusableInTouchMode="true"
即可默认不弹出软件盘。