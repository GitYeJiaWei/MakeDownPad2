#IntentService使用和注意点

> IntentService，可以看做是Service和HandlerThread的结合体，在完成了使命之后会自动停止，适合需要在工作线程处理UI无关任务的场景。


- IntentService 是继承自 Service 并处理异步请求的一个类，在 IntentService 内有一个工作线程来处理耗时操作。
- 当任务执行完后，IntentService 会自动停止，不需要我们去手动结束。
- 如果启动 IntentService 多次，那么每一个耗时操作会以工作队列的方式在 IntentService 的 onHandleIntent 回调方法中执行，依次去执行，使用串行的方式，执行完自动结束。

##使用IntentService的方法：

- 继承IntentService。

- 实现不带参数的构造方法，并且调用父类IntentService的构造方法。

- 实现onHandleIntent方法。

在onHandleIntent方法中可以根据intent来区分任务，这里有两个任务，一个是下载图片、一个是下载视频（模拟耗时操作）。
```
public class MyIntentService extends IntentService {
    private final static String TAG = "MyIntentService";
     
    public static final String ACTION_DOWN_IMG = "down.image";
    public static final String ACTION_DOWN_VID = "down.vid";
 
    public static final String ACTION_DOWN_PROGRESS = "com.zpengyong.down.progress";
    public static final String ACTION_SERVICE_STATE = "com.zpengyong.service.state";
     
    public static final String PROGRESS = "progress";
    public static final String SERVICE_STATE = "service_state";
 
    //构造方法
    public MyIntentService() {
        super("MyIntentService");
    }
     
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        sendServiceState("onCreate");
    }
     
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "");
    }
     
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent thread:"+Thread.currentThread());
        String action = intent.getAction();
        if(action.equals(ACTION_DOWN_IMG)){
            for(int i = 0; i < 100; i++){
                try{ //模拟耗时操作
                    Thread.sleep(50);
                }catch (Exception e) {
                }
                sendProgress(i);
            }
        }else if(action.equals(ACTION_DOWN_VID)){
            for(int i = 0; i < 100; i++){
                try{ //模拟耗时操作
                    Thread.sleep(70);
                }catch (Exception e) {
                }
                sendProgress(i);
            }
        }
        Log.i(TAG, "onHandleIntent end");
    }
 
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        sendServiceState("onDestroy");
    }
    //发送Service的状态
    private void sendServiceState(String state){
        Intent intent = new Intent();
        intent.setAction(ACTION_SERVICE_STATE);
        intent.putExtra(SERVICE_STATE, state);
        sendBroadcast(intent);
    }
     
    //发送进度
    private void sendProgress(int progress){
        Intent intent = new Intent();
        intent.setAction(ACTION_DOWN_PROGRESS);
        intent.putExtra(PROGRESS, progress);
        sendBroadcast(intent);
    }
}
```

##记得在AndroidManifest.xml中配置：

 	<service android:name=".MyIntentService"></service>


##再Activity中调用

	 Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);