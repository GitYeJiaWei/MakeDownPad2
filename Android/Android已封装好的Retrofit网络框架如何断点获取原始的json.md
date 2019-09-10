#Android已封装好的Retrofit网络框架如何断点获取原始的json

我们知道Retrofit的Call对象返回的数据是基于动态编译的，也就是在运行阶段就编译好的，所以我们不能在收到实体结果后再来转成我们想要的json结果，所以我们在Call传的泛型必须是Okhttp的ResponseBody

```java

 @FormUrlEncoded
    @POST("/api/User/ChangePassword")
    Observable<Object> setting(@FieldMap Map<String,String> params);
```

此时我们观察者回调到P层得到的数据还是为ResponseBody类型的（表现为Object），此时我们强转成ResponseBody，此时调用ResponseBody的string方法返回json字符串

```java

public void setting(String passord,String newpassord,String twoPassword){
        if (!NetUtils.isConnected(mContext)){
            ToastUtil.toast(R.string.error_network_unreachable);
            return;
        }
        mModel.setting(passord,newpassord,twoPassword)
                .subscribeOn(Schedulers.io())//访问数据在子线程
                .observeOn(AndroidSchedulers.mainThread())//拿到数据在主线程
                .subscribe(new ProgressSubcriber<Object o>(mContext,mView) {
                    @Override
                    public void onNext(Object o) {
                        //当Observable发生事件的时候触发
						ResponseBody body = (ResponseBody) o;
						try{
							body.string();
						}catch(IOException e){
							e.printStackTrace();
						}
                        mView.settingResult(body.string());
                    }
                });
    }
```

- body.string();就是想要的数据信息