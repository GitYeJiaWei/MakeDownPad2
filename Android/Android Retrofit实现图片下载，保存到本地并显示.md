#Android Retrofit实现多张图片下载，保存到本地并显示

##首先在ApiService中定义接口
```
	@Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
```

##下载多张图片并保存到本地
```
private void download() {
        List<Pay> users = getEpcModelDao().loadAll();
        for (int i = 0; i < users.size(); i++) {
            String url = users.get(i).getImageRec();
            if (!TextUtils.isEmpty(url)) {
                AppApplication.getExecutorService().execute(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(Environment.getExternalStorageDirectory() + "/imgpic/" + url);
                        if (!file.exists()) {
                            ApiService apIservice = getRetofit().create(ApiService.class);
                            Observable<ResponseBody> qqDataCall = apIservice.download("http://192.168.66.3:8121/imgs/" + url);
                            qqDataCall.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
							//.observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更新UI，这句话要注释掉，图片的下载也要在子线程中完成（耗时操作）
                                    .subscribe(new Observer<ResponseBody>() {
                                                   @Override
                                                   public void onSubscribe(Disposable d) {
                                                   }

                                                   @Override
                                                   public void onNext(ResponseBody baseBean) {
                                                       if (baseBean == null) {
                                                           ToastUtil.toast("获取图片失败");
                                                       } else {
                                                           byte[] bytes = new byte[0];
                                                           try {
                                                               bytes = baseBean.bytes();//注意：把byte[]转换为bitmap时，也是耗时操作，也必须在子线程
                                                               Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                               saveFile(bitmap, Environment.getExternalStorageDirectory() + "/imgpic/", url);
                                                           } catch (IOException e) {
                                                               e.printStackTrace();
                                                           }
                                                       }
                                                   }

                                                   @Override
                                                   public void onError(Throwable e) {
                                                       ToastUtil.toast(e.getMessage());
                                                   }

                                                   @Override
                                                   public void onComplete() {
                                                   }//订阅
                                               }
                                    );
                        }
                    }

                });
            }
        }
    }

    //保存图片到SD卡
    public void saveFile(Bitmap bm, String fileName, String _imgName) throws IOException {
        String imgName = _imgName; //图片的名字
        File jia = new File(fileName);              //新创的文件夹的名字
        if (!jia.exists()) {   //判断文件夹是否存在，不存在则创建
            jia.mkdirs();
        }
        File file = new File(jia + "/" + imgName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bm.compress(Bitmap.CompressFormat.PNG, 10, bos);

        bos.flush();
        bos.close();
    }
```

##在Adapter中展示
```
String path = Environment.getExternalStorageDirectory().toString() + "/imgpic";
        ListItemView finalListItemView = listItemView;

        AppApplication.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //图片资源较大，加载到ui界面是耗时操作，所以开线程加载
                    Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(new File(path, m1.getImageRec())));

                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = finalListItemView;
                    Bundle bundle =new Bundle();
                    bundle.putParcelable("bitmap", bmp);
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

.
.
.

 public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                ListItemView listItemView = (ListItemView) msg.obj;
                Bundle bundle =msg.getData();
                Bitmap bitmap = bundle.getParcelable("bitmap");
                listItemView.image.setImageBitmap(bitmap);
            }
        }
    };
```