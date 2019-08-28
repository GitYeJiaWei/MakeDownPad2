#网络请求:Retrofit2 + Rx2, 通过拦截器自动添加请求参数


1. **用到的依赖库**

```java
 implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.9.1'
    implementation 'io.reactivex.rxjava2:rxjava:2.1.7'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
```

2.. **网络请求的**

```java
 	//Post请求
    @FormUrlEncoded
    @POST("api/StockIn/Save")
    Observable<BaseBean<Object>> stocksave(@FieldMap Map<String,Object> params);

    //Get请求
    @GET("api/Waste/Detail")
    Observable<BaseBean<WasteViewsBean>> wastedetail(@QueryMap Map<String,String> params);
```

3.. **一个完整的网络请求**

```java
public interface ApiURL {
    @GET("geocoding")
    Observable<ApiBean> getApiBean(@Query("a") String city); //@Query是向后追加的效果 等同于 geocoding?a=city
}
```

```java
private void getDataByRx() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //添加拦截器，自动追加参数
        builder.addInterceptor(new AppendUrlParamIntercepter());
        builder.addInterceptor(new AppendHeaderParamIntercepter());
        //添加拦截器，打印网络请求
        if (NetworkConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gc.ditu.aliyun.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(builder.build())
                .build();
        ApiURL apiURL = retrofit.create(ApiURL.class);
        Observable<ApiBean> api = apiURL.getApiBean("北京市");

        api.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiBean>() {
                    @Override
                    public void accept(ApiBean apiBean) throws Exception {

                    }
                });
    }
```

4.. **统一追加Url**

```java
/**
 * Get请求自动追加参数
 */

public class AppendUrlParamIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //偷天换日
        Request oldRequest = chain.request();

        //拿到拥有以前的request里的url的那些信息的builder
        HttpUrl.Builder builder = oldRequest
                .url()
                .newBuilder();

        //得到新的url（已经追加好了参数）
        HttpUrl newUrl = builder.addQueryParameter("deviceId", "12345")
                .addQueryParameter("token", "i_am_token")
                .addQueryParameter("appVersion", "1.0.0-beta")
                .build();

        //利用新的Url，构建新的request，并发送给服务器
        Request newRequest = oldRequest
                .newBuilder()
                .url(newUrl)
                .build();

        return chain.proceed(newRequest);
    }
}
```

5.. **统一追加Header**

```java
/**
 * 统一追加Header
 */

public class AppendHeaderParamIntercepter implements Interceptor {

    // 1.获取以前的Builder
    // 2.为以前的Builder添加参数
    // 3.生成新的Builder

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Headers.Builder builder = request
                .headers()
                .newBuilder();

        //统一追加Header参数
        Headers newBuilder = builder.add("header1", "i am header 1")
                .add("token", "i am token")
                .build();

        Request newRequest = request.newBuilder()
                .headers(newBuilder)
                .build();

        return chain.proceed(newRequest);
    }
}
```

6.. **统一追加Body**

```java
/**
 * 将所有的Get -> POST, 将Get后面的Query Params -> Body (基本用不到)
 */

public class AppendBodyParamIntercepter implements Interceptor {

    // 1.获取以前的Builder
    // 2.为以前的Builder添加参数
    // 3.生成新的Builder

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //拿到所有Query的Key
        Set<String> queryKeyName = request
                .url()
                .queryParameterNames();
        //将query -> body
        StringBuilder bodyString = new StringBuilder();
        for (String s : queryKeyName) {
            bodyString.append(s)
                    .append("=")
                    //查询url后面key的value
                    .append(request.url().queryParameterValues(s))
                    .append(",");
        }
        //构建新body。 MediaType根据实际情况更换
        RequestBody newBody = RequestBody.create(MediaType.parse("application/json"),
                bodyString.toString().substring(0, bodyString.toString().length() - 1));

        Request newRequest = request.newBuilder()
                .post(newBody)
                .build();

        return chain.proceed(newRequest);
    }
}
```

7.. **POST请求的两种编码格式：application/x-www-urlencoded是浏览器默认的编码格式，用于键值对参数，参数之间用&间隔；multipart/form-data常用于文件等二进制，也可用于键值对参数，最后连接成一串字符传输(参考Java OK HTTP)。除了这两个编码格式，还有application/json也经常使用**