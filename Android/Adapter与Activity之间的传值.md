#Adapter与Activity之间的传值

- 在Adapter中定义一个监听器接口 addClickListener

```
 private static interface addClickListerner{
                  public void addClick(int position);  //自行配置参数  需要传递到activity的值
       }
```

- 两种方式

```
private addClidkListener listener；

      (1)在adapter的构造函数中传入该监听器的对象

      private CutomAdapter(Context context,ArrayList<UserInfo> userList,addClidkListener listener) {

          mInflater = LayoutInflater.from(context);

          mContext = context;

          this.userList = userList;

          this.listener = listener;

      }

      (2)或者在Adapter中设置方法

  public void setCusClickListener(addClidkListener cusClickListener) {

    this.listener = cusClickListener;
  } 
```

- adapter中有监听的地方调用接口中的方法 例如：

```
holder.textview.setOnClickListener(new OnClickListener() {


                @Override

                public void onClick(View v) {

                                 if(listener!= null) {
                                        listener.addClick(position);
                                 }

                }

       });
```

- 在Activity中调用操作 与步骤2两种方式对应

```
    (1) 在Adapter构造函数中调用
        adapter = new  CutomAdapter(this,userList,new addClickListener(){

                @Override

                public void addClick(int position){

                   //更新Activity

                }

      }

    );

       在Adapter设置方法调用
       (2)adapter.setCusClickListener(new addClickListener(){

                @Override

                public void addClick(int position){

                   //更新Activity

                }

      });
```