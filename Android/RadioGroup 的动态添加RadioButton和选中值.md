#RadioGroup 的动态添加RadioButton和选中值

- 添加RadioButton到RadioGroup中
```
RadioGroup group;
for(int i=0; i<10; i++)
{
    RadioButton tempButton = new RadioButton(this);
    tempButton.setBackgroundResource(R.drawable.xxx);	// 设置RadioButton的背景图片
	//tempButton.setButtonDrawable(null);
    tempButton.setButtonDrawable(R.drawable.xxx);			// 设置按钮的样式
    tempButton.setPadding(80, 0, 0, 0);           		// 设置文字距离按钮四周的距离 
    tempButton.setText("按钮 " + i);
	tempButton.setTextSize(20);                         //设置文字的大小
    group.addView(tempButton, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

	if（i==0）{
		radioGroup.check(tempButton.getId());			//设置默认值
	}
}
```

- 为RadioGroup添加事件处理，可以得到当前选择的RadioButton
```
        group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                RadioButton tempButton = (RadioButton)findViewById(checkedId); // 通过RadioGroup的findViewById方法，找到ID为checkedID的RadioButton
                // 以下就可以对这个RadioButton进行处理了
            }
        });
```
