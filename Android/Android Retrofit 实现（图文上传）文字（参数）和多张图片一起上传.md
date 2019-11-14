#Android Retrofit 实现（图文上传）文字（参数）和多张图片一起上传

##HTTP协议
其中HTTP协议版本有两种：HTTP1.0/HTTP1.1 可以这样区别：
> HTTP1.0对于每个连接都的建立一次连接一次只能传送一个请求和响应，请求就会关闭，HTTP1.0没有Host字段；
HTTP1.1在同一个连接中可以传送多个请求和响应，多个请求可以重叠和同时进行，HTTP1.1必须有Host字段。


##HTTP请求类型

根据HTTP标准，HTTP请求可以使用多种请求方法。例如：HTTP1.1支持7种请求方法：GET、POST、HEAD、OPTIONS、PUT、DELETE和TARCE。在Internet应用中，最常用的方法是GET和POST。

GET： 请求指定的页面信息，并返回实体主体。
POST： 请求服务器接受所指定的文档作为对所标识的URI的新的从属实体。

##HTTP请求格式
当浏览器向Web服务器发出请求时，它向服务器传递了一个数据块，也就是请求信息，HTTP请求信息由3部分组成：

① 请求方法 URI 协议/版本
② 请求头(Request Header)
③ 请求正文

下面是一个HTTP请求的例子：

```
POST /DeviceApi/Pick/Upload?dustbinCode=1&amp; weight=11.2 HTTP/1.1
Host: yf.jz315.net
User-Agent: PostmanRuntime/7.19.0
Accept: */*
Cache-Control: no-cache
Postman-Token: f6fa9a0e-94a5-4ea2-8261-0c1df26208f9,ae17de2a-152c-412c-a7be-4e11d1d48c4e
Host: yf.jz315.net
Content-Type: multipart/form-data; boundary=--------------------------844939156340971952328654
Accept-Encoding: gzip, deflate
Content-Length: 3095124
Connection: keep-alive
cache-control: no-cache


Content-Disposition: form-data; name="file"; filename="/C:/Users/Administrator/Desktop/新建文件夹/新建文件夹/p5_2.png


------WebKitFormBoundary7MA4YWxkTrZu0gW--
```

##Retrofit实现文件和图片一起上传

>每一次投递上传图片(body中提交图片，参数名为“File”)
请求方式 POST
请求路径 DeviceApi/Pick/Upload?dustbinCode={dustbinCode}&weight={weight}

```
 
    @Multipart
    @POST("DeviceApi/Pick/Upload")
    Observable<BaseBean>  upload(@Query("dustbinCode") String dustbinCode, @Query("weight") BigDecimal weight,
                          @PartMap Map<String, RequestBody> params);
```
这里用到了@Partmap注解，将图片文件信息放入map中。

##准备图片
>/storage/emulated/0/DCIM/USBCameraTest/123.png
在sdcard根目录的DCIM/USBCameraTest中存放图片，分别为123.png和123.jpg(不要是gif图片啊，服务器不支持)

##代码实现
```
String path1 = Environment.getExternalStorageDirectory() + "/"+Environment.DIRECTORY_DCIM + "/USBCameraTest/123.png";
        ArrayList<String> pathList = new ArrayList<>();
        pathList.add(path1);

        Map<String, RequestBody> bodyMap = new HashMap<>();
        if (pathList.size()>0){
            for (int i = 0; i < pathList.size(); i++) {
                File file = new File(pathList.get(i));
                bodyMap.put("file"+"\";filename=\""+file.getName(), RequestBody.create(MediaType.parse("image/png"),file));
            }
        }

        BigDecimal bigDecimal = new BigDecimal(12.5);
        ApiService apIservice = NetUtils.toretrofit().create(ApiService.class);
        Observable<BaseBean> qqDataCall = apIservice.upload("1",bigDecimal,bodyMap);
                qqDataCall.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更新UI
                .subscribe(new Observer<BaseBean>() {

                               @Override
                               public void onSubscribe(Disposable d) {
                               }

                               @Override
                               public void onNext(BaseBean baseBean) {
                                   if (baseBean == null) {
                                       ToastUtil.toast("上传图片失败");
                                       return;
                                   }
                                   if (baseBean.getCode() == 0) {
                                       ToastUtil.toast("上传图片成功!");
                                   } else {
                                       ToastUtil.toast(baseBean.getMessage());
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {
                                   ToastUtil.toast(e.getMessage());
                               }

                               @Override
                               public void onComplete() {
                               }
                           }
                );
```

##关键代码
>根据postman抓包，发现请求头是
>Content-Disposition: form-data; name="file"; filename="/C:/Users/Administrator/Desktop/新建文件夹/新建文件夹/p5_2.png
>按照请求头拼接"file"+"\";filename=\""+file.getName(), RequestBody.create(MediaType.parse("image/png"),file)
>"file"为图片参数名
```
File file = new File(pathList.get(i));
                bodyMap.put("file"+"\";filename=\""+file.getName(), RequestBody.create(MediaType.parse("image/png"),file));
```

##关于文字类参数上传
写到最后忘了说文字参数了，文字参数相对文件来说容易些。

在接口中，我们有文字参数 @Query("dustbinCode") String dustbinCode是在url中添加参数，@Part("data") String des是在body中田间参数，如果你需要多个，增加就行了。需要注意的是这个参数的名字比如”data”，不是前端自定义，而是后台定义的。