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
