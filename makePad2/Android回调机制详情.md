#Android回调机制详情

作为一个程序员，在我们的日常开发中总是免不了模块与模块之间的调用，一般来说调用方式一般可以分为三种：

同步调用、异步调用、回调。

  1、同步调用：这种调用方式就是简单的类A调用类B里面的方法，并且整个过程是在同一个线程里面完成的。

  2、异步调用：有时候我们调用的方法太耗时了，这样就会形成线程阻塞，造成用户体验不好。这个时候呢我们就会开启一个新的线程来执行耗时的方法，这种方式称为异步调用。

  3、回调：我们在A类里面的a()方法调用B类的b()方法，待到b()方法执行完毕后再回过头来调用A类里面的c()方法，这样的方式称为回调。在我们的实际开发中一般不会直接在A类中直接写回调方法，这样做耦合度太高，不利于后期维护。我们一般会定义一个回调接口，然后进行相关的操作。具体应该怎么做呢？请往下看：

首先先定义一个回调接口：

```
public interface Callback {
	void callbackA(String result);
}
```
然后实现该接口：
```
public class Instance implements Callback{
	@Override
	public void callbackA(String result) {
		System.out.println("请求结果：" + result);
	}
}
```
然后定义一个被调用的类B:
```
public class B {
 
	private Callback mCallback;
 
	public B(Callback callback) {
		mCallback = callback;
	}
 
	public void b() {
		try {
			// 模拟耗时操作
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mCallback.callbackA("请求成功");
	}
}
```
这里我们首先在构造器里面传入Callback接口的实例，然后在这里定义一个方法b(),里面新开了一个线程模拟耗时操作，然后在方法b()中调用回调方法callbackA()。

  最后我们定义调用类A:
```
public class A {
	public static void main(String[] args) {
		Callback callback = new Instance();
		B b = new B(callback);
		new Thread(new Runnable() {
 
			@Override
			public void run() {
				b.b();
			}
		}).start();
		System.out.println("这是A类的方法");
	}
}
```

在这个方法中我们用了Java多态的特性，父类的引用指向子类的实现。然后把对象传入类B，最后开启新线程调用b对象的方法b()，因为b()方法属于耗时操作，如果不开启新线程会导致线程阻塞。

通过例子我们可以看到，既没有方法阻塞，又得到了我们想要的结果。perfect!

  在我们的实际开发中，一般是采用匿名类的方式来完成的，我们可以把上面的代码做一下小修改。

  首先改一下A类，改动后如下：
```
public class A {
	public static void main(String[] args) {
		B b = new B(new Callback() {
			@Override
			public void callbackA(String result) {
				System.out.println("请求结果：" + result);
			}
		});
		new Thread(new Runnable() {
 
			@Override
			public void run() {
				b.b();
			}
		}).start();
		System.out.println("这是A类的方法");
	}
}
```

然后删掉Instance这个类就可以啦。

看了上面的代码是不是有似曾相识的感觉？没错，在我们的Android中有很多地方都是用到了这种回调机制。举个简单的例子吧，比如我们平时的按钮的点击事件就是采用的这种方式：

```
button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击事件回调", Toast.LENGTH_SHORT).show();
            }
        });
```

我们可以看下setOnclickListener()方法具体实现：
```
public void setOnClickListener(@Nullable OnClickListener l) {
        if (!isClickable()) {
            setClickable(true);
        }
        getListenerInfo().mOnClickListener = l;
    }
```

我们可以看到传入的OnclickListener对象赋给了getListenerInfo()方法里的mOnClickListener属性，接着看下getListenerInfo()方法：

```
ListenerInfo getListenerInfo() {
        if (mListenerInfo != null) {
            return mListenerInfo;
        }
        mListenerInfo = new ListenerInfo();
        return mListenerInfo;
    }
```

我们可以看到，这个方法返回的是Listenerinfo对象，然后在这个类里面找到mOnClickListener属性，打开后发现这是一个接口：

```
public interface OnClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        void onClick(View v);
    }
```
看到这里是不是感觉跟上面的例子有点像？没错，Button点击事件整体的思想其实就是回调机制，但是需要注意的是：我的例子采用的是异步回调，而Button是同步回调，具体的代码实现感兴趣的可以自己去看下源码。
