#EditText 监听字符变化

>每隔一秒扫描一次数据进去，清除上次的数据

```
private long mExitTime1;

edit_user.addTextChangedListener(new TextWatcher() {
                private int selectionStart;
                private int selectionEnd;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
 				// 输入前的监听
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
				 // 输入的内容变化的监听
                }

                @Override
                public void afterTextChanged(Editable s) {
				// 输入后的监听
                    if ((System.currentTimeMillis() - mExitTime1) > 1000) {
                        mExitTime1 = System.currentTimeMillis();
                        if (!TextUtils.isEmpty(edit_user.getText().toString())) {
                            //获取光标开始的位置
                            selectionStart = edit_user.getSelectionStart();
                            //获取光标结束的位置
                            selectionEnd = edit_user.getSelectionEnd();
                            //这里其实selectionStart  == selectionEnd
                            //删除部分字符串 为[x,y) 包含x位置不包含y
                            //也就是说删除 位置x到y-1
                            s.delete(0,selectionStart-1);
                        }
                    }
                }
            });
```