#重写系统返回键方法onBackPressed()无效（super的位置）

```java
@Override
    public void onBackPressed() {
 
        Intent intent = new Intent();
        intent.putExtra(ISANSWER, 1);
        setResult(RESULT_OK, intent);
 
        super.onBackPressed();  // 源码中有finish方法，使用时注意该语句的位置
    }
 
 
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                String isAnswer = data.getStringExtra(WtjcxDetialActivity.ISANSWER);
                if ("1".equals(isAnswer)) {
                    Toast.makeText(TestActivity.this, "点击了安卓手机系统的返回键", Toast.LENGTH_SHORT).show();
                }
            }
 
        }
    }

```


- 重写onBackPressed()方法要注意，super.onBackPressed()中含有finish()，先调用super.onBackPressed()的话，resultCode = 0，而不是接收RESULT_OK的值-1（RESULT_OK值为 -1）。所以，重写onBackPressed()方法时，如果在该方法中需要调用setResult方法获取信息，一定要把setResult方法写在super.onBackPressed()前面。

##OnkeyDown事件和OnBackPressed方法

直接获取按钮按下事件，此方法兼容Android 1.0到Android 2.1 也是常规方法，直接重写Activity的onKeyDown方法即可，代码如下:

```
@Override
public boolean onKeyDown(int keyCode, KeyEvent event)  {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
       Toast.makeText(ml78.this,"返回键Back键测试",1).show();
        return true;
    }

    return super.onKeyDown(keyCode, event);
}
```

而对于Android 2.0开始又多出了一种新的方法，对于Activity 可以单独获取Back键的按下事件，直接重写onBackPressed方法即可，代码如下

```
@Override
public void onBackPressed() {
// 这里处理逻辑代码，大家注意：该方法仅适用于2.0或更新版的sdk
return;
}
```