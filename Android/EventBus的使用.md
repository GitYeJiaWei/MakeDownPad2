#EventBus的使用
##概述
>关于 EventBus 在开发中经常会选择使用它来进行模块间通信、解耦。平常使用这个库只是很浅显的操作三部曲，register，post，unregister。来达到开发目的。

##关于
>EventBus 是一个开源库，它利用发布/订阅者者模式来对项目进行解耦。它可以利用很少的代码，来实现多组件间通信。android的组件间通信，我们不由得会想到handler消息机制和广播机制，通过它们也可以进行通信，但是使用它们进行通信，代码量多，组件间容易产生耦合引用。

为什么会选择使用EventBus来做通信？

- 简化了组件间交流的方式
- 对事件通信双方进行解耦
- 可以灵活方便的指定工作线程，通过ThreadMode
- 速度快，性能好
- 库比较小，不占内存
- 使用这个库的app多，有权威性
- 功能多，使用方便

##使用
###导入项目
在build.gradle文件中导入EventBus库。
	implementation 'org.greenrobot:eventbus:3.1.1'

在项目的混淆文件中，加入EventBus 的混淆规则，这个千万别忘了，不然会出现，debug版本测试OK，release版本subscriber 收不到消息等诡异问题。
```
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
 
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

```

###使用方式

EventBus库中最重要的三个点，分别是subscriber（订阅者），事件（消息），publisher（发布者）。主要理解这三者的关系即可。

subscriber ——> EventBus 的register方法，传入的object对象

事件（Event）——> EventBus 的post方法，传入的类型。

publisher（发布者）——> EventBus的post方法。


----------

1.创建一个事件类型，消息事件类型可以是string，int等常见类，也可以是自己自定义一个事件类，方便管理。这边演示，创建了一个EventMessage事件类。
```
public class EventMessage {

    private int type;
    private String message;

    public EventMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {

        return "type="+type+"--message= "+message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

```
2.在需要订阅事件的模块中，注册eventbus。

MainActivity.java
```
@Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
		EventBus.getDefault().unregister(this);
    }

```
关于EventBus的注册问题，说几点。

1. 注册完了，在不用的时候千万别忘了unregister。
2. 不能重复注册。注册之后，没有unregister，然后又注册了一次。
3. register与unregister的时间根据实际需求来把控，官方的例子是在onStart（）回调方法进行注册，onStop()回调方法进行unregister（）


在需要接受事件的类中进行好register之后，需要在该类中创建一个方法来接收事件消息。
```
@Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(EventMessage message) {
        Log.e(TAG, "onReceiveMsg: " + message.toString());
    }

```

创建的这个方法是有要求的。要求有如下几点。

1. 该方法有且只有一个参数。
2. 该方法必须是public修饰符修饰，不能用static关键字修饰，不能是抽象的（abstract）
3. 该方法需要用@Subscribe注解进行修饰。


3.在需要发送事件的地方，调用EventBus的post（Object event），postSticky（Object event）来通知订阅者

SecondActivity.java
```
private View.OnClickListener mSendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e(TAG, "onClick: " );
            EventMessage msg = new EventMessage(1,"Hello MainActivity");
            EventBus.getDefault().post(msg);
        }
    };

```

注意，该例子中，我使用了，EventBus.getDefault()方法，该方法会获取一个单例。所以才可以随时使用，如果不是用这种单例模式，需要想办法把订阅者（Subscriber）注册时用的EventBus的引用传给需要发送事件的模块中，简而言之就是Subscriber用的eventbus 和post方法需要的eventbus需要是同一个eventbus。

>那现在关于EventBus的基本使用，就明白了，主要就是三个部分，一个部分是Subscriber，需要在Subscriber类中进行register和unregister操作。一部分是在Subscriber中需要创建一个方法来接收事件信息，最后一部分就是在需要发送事件的环境使用post方法来发送事件信息。这三部分中所用到的eventBus实例得要是同一个实例。
