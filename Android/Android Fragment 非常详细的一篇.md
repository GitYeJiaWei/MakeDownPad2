#Android Fragment 非常详细的一篇

> 导语: Fragment作为Android最基本，最重要的基础概念之一，在开发中经常会和他打交道。本文从为什么出现Fragment开始，介绍了Fragment相关的方方面面，包括Fragment的基本定义及使用、回退栈的内部实现、Fragment通信、DialogFragment、ViewPager+Fragment的使用、嵌套Fragment、懒加载等。

##**基本概念**

Fragment，简称碎片，是Android 3.0（API 11）提出的，为了兼容低版本，support-v4库中也开发了一套Fragment API，最低兼容Android 1.6。 

过去support-v4库是一个jar包，24.2.0版本开始，将support-v4库模块化为多个jar包，包含：support-fragment, support-ui, support-media-compat等，这么做是为了减少APK包大小，你需要用哪个模块就引入哪个模块。

如果想引入整个support-v4库，则compile 'com.android.support:support-v4:24.2.1'，
如果只想引入support-fragment库，则com.android.support:support-fragment:24.2.1。


> 因为support库是不断更新的，因此建议使用support库中的android.support.v4.app.Fragment，而不要用系统自带的android.app.Fragment。而如果要使用support库的Fragment，Activity必须要继承FragmentActivity（AppCompatActivity是FragmentActivity的子类）。


- Fragment是依赖于Activity的，不能独立存在的。
- 一个Activity里可以有多个Fragment。
- 一个Fragment可以被多个Activity重用。
- Fragment有自己的生命周期，并能接收输入事件。
- 我们能在Activity运行时动态地添加或删除Fragment。

Fragment的优势有以下几点：

模块化（Modularity）：我们不必把所有代码全部写在Activity中，而是把代码写在各自的Fragment中。
可重用（Reusability）：多个Activity可以重用一个Fragment。
可适配（Adaptability）：根据硬件的屏幕尺寸、屏幕方向，能够方便地实现不同的布局，这样用户体验更好。

## **基本使用**

这里给出Fragment最基本的使用方式。首先，创建继承Fragment的类，名为Fragment1：

```java
public class Fragment1 extends Fragment{  
  private static String ARG_PARAM = "param_key"; 
     private String mParam; 
     private Activity mActivity; 
     public void onAttach(Context context) {
        mActivity = (Activity) context;
        mParam = getArguments().getString(ARG_PARAM);  //获取参数
    }
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_1, container, false);
        TextView view = root.findViewById(R.id.text);
        view.setText(mParam);
             return root;
    }    
     public static Fragment1 newInstance(String str) {
        Fragment1 frag = new Fragment1();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM, str);
        fragment.setArguments(bundle);   //设置参数
        return fragment;
    }
}
```


Fragment有很多可以复写的方法，其中最常用的就是onCreateView()，该方法返回Fragment的UI布局，需要注意的是inflate()的第三个参数是false，因为在Fragment内部实现中，会把该布局添加到container中，如果设为true，那么就会重复做两次添加，则会抛如下异常：

```java
Caused by: java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
```

如果在创建Fragment时要传入参数，必须要通过setArguments(Bundle bundle)方式添加，而不建议通过为Fragment添加带参数的构造函数，因为通过setArguments()方式添加，在由于内存紧张导致Fragment被系统杀掉并恢复（re-instantiate）时能保留这些数据。官方建议如下：

```java
It is strongly recommended that subclasses do not have other constructors with parameters, since these constructors will not be called when the fragment is re-instantiated.
```

我们可以在Fragment的onAttach()中通过getArguments()获得传进来的参数，并在之后使用这些参数。如果要获取Activity对象，不建议调用getActivity()，而是在onAttach()中将Context对象强转为Activity对象。
创建完Fragment后，接下来就是把Fragment添加到Activity中。在Activity中添加Fragment的方式有两种：

- 静态添加：通过xml的方式添加，缺点是一旦添加就不能在运行时删除。
- 动态添加：运行时添加，这种方式比较灵活，因此建议使用这种方式。虽然Fragment能在XML中添加，但是这只是一个语法糖而已，Fragment并不是一个View，而是和Activity同一层次的。

这里只给出动态添加的方式。首先Activity需要有一个容器存放Fragment，一般是FrameLayout，因此在Activity的布局文件中加入FrameLayout：

```java
<FrameLayout
    android:id="@+id/container"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

然后在onCreate()中，通过以下代码将Fragment添加进Activity中。

```java
if (bundle == null) {
    getSupportFragmentManager().beginTransaction()
        .add(R.id.container, Fragment1.newInstance("hello world"), "f1")        //.addToBackStack("fname")
        .commit();
}
```

这里需要注意几点：

- 因为我们使用了support库的Fragment，因此需要使用getSupportFragmentManager()获取FragmentManager。

- add()是对Fragment众多操作中的一种，还有remove(), replace()等，第一个参数是根容器的id（FrameLayout的id，即”@id/container”），第二个参数是Fragment对象，第三个参数是fragment的tag名，指定tag的好处是后续我们可以通过Fragment1 frag = getSupportFragmentManager().findFragmentByTag("f1")从FragmentManager中查找Fragment对象。
- 在一次事务中，可以做多个操作，比如同时做add().remove().replace()。

- commit()操作是异步的，内部通过mManager.enqueueAction()加入处理队列。对应的同步方法为commitNow()，commit()内部会有checkStateLoss()操作，如果开发人员使用不当（比如commit()操作在onSaveInstanceState()之后），可能会抛出异常，而commitAllowingStateLoss()方法则是不会抛出异常版本的commit()方法，但是尽量使用commit()，而不要使用commitAllowingStateLoss()。

- addToBackStack("fname")是可选的。FragmentManager拥有回退栈（BackStack），类似于Activity的任务栈，如果添加了该语句，就把该事务加入回退栈，当用户点击返回按钮，会回退该事务（回退指的是如果事务是add(frag1)，那么回退操作就是remove(frag1)）；如果没添加该语句，用户点击返回按钮会直接销毁Activity。
- Fragment有一个常见的问题，即Fragment重叠问题，这是由于Fragment被系统杀掉，并重新初始化时再次将fragment加入activity，因此通过在外围加if语句能判断此时是否是被系统杀掉并重新初始化的情况。

**Fragment有个常见的异常：**

```java
java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
    at android.support.v4.app.FragmentManagerImpl.checkStateLoss(FragmentManager.java:1341)
    at android.support.v4.app.FragmentManagerImpl.enqueueAction(FragmentManager.java:1352)
    at android.support.v4.app.BackStackRecord.commitInternal(BackStackRecord.java:595)
    at android.support.v4.app.BackStackRecord.commit(BackStackRecord.java:574)
```

该异常出现的原因是：commit()在onSaveInstanceState()后调用。首先，onSaveInstanceState()在onPause()之后，onStop()之前调用。onRestoreInstanceState()在onStart()之后，onResume()之前。

**因此避免出现该异常的方案有：**

不要把Fragment事务放在异步线程的回调中，比如不要把Fragment事务放在AsyncTask的onPostExecute()，因此onPostExecute()可能会在onSaveInstanceState()之后执行。
逼不得已时使用commitAllowingStateLoss()

## **生命周期**

![生命周期图](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/pic1/2952813-fa6d293c2b59c13f.png)

Fragment的生命周期解释如下：

onAttach()：Fragment和Activity相关联时调用。可以通过该方法获取Activity引用，还可以通过getArguments()获取参数。
onCreate()：Fragment被创建时调用。
onCreateView()：创建Fragment的布局。
onActivityCreated()：当Activity完成onCreate()时调用。
onStart()：当Fragment可见时调用。
onResume()：当Fragment可见且可交互时调用。
onPause()：当Fragment不可交互但可见时调用。
onStop()：当Fragment不可见时调用。
onDestroyView()：当Fragment的UI从视图结构中移除时调用。
onDestroy()：销毁Fragment时调用。
onDetach()：当Fragment和Activity解除关联时调用。

上面的方法中，只有onCreateView()在重写时不用写super方法，其他都需要。

FragmentTransaction有一些基本方法，下面给出调用这些方法时，Fragment生命周期的变化：

- add(): onAttach()->…->onResume()。
- remove(): onPause()->…->onDetach()。
- replace(): 相当于旧Fragment调用remove()，新Fragment调用add()。
- show(): 不调用任何生命周期方法，调用该方法的前提是要显示的 Fragment已经被添加到容器，只是纯粹把Fragment UI的setVisibility为true。
- hide(): 不调用任何生命周期方法，调用该方法的前提是要显示的Fragment已经被添加到容器，只是纯粹把Fragment UI的setVisibility为false。
- detach(): onPause()->onStop()->onDestroyView()。UI从布局中移除，但是仍然被FragmentManager管理。
- attach(): onCreateView()->onStart()->onResume()。



##**Fragment实现原理和Back Stack**

我们知道Activity有任务栈，用户通过startActivity将Activity加入栈，点击返回按钮将Activity出栈。Fragment也有类似的栈，称为回退栈（Back Stack），回退栈是由FragmentManager管理的。默认情况下，Fragment事务是不会加入回退栈的，如果想将Fragment事务加入回退栈，则可以加入addToBackStack("")。如果没有加入回退栈，则用户点击返回按钮会直接将Activity出栈；如果加入了回退栈，则用户点击返回按钮会回滚Fragment事务。

我们将通过最常见的Fragment用法，讲解Back Stack的实现原理：
```java
	getSupportFragmentManager().beginTransaction()
    .add(R.id.container, f1, "f1")
    .addToBackStack("")
    .commit();
```

上面这个代码的功能就是将Fragment加入Activity中，内部实现为：创建一个BackStackRecord对象，该对象记录了这个事务的全部操作轨迹（这里只做了一次add操作，并且加入回退栈），随后将该对象提交到FragmentManager的执行队列中，等待执行。

BackStackRecord类的定义如下

	class BackStackRecord extends FragmentTransaction implements FragmentManager.BackStackEntry, Runnable {}

从定义可以看出，BackStackRecord有三重含义：

- 继承了FragmentTransaction，即是事务，保存了整个事务的全部操作轨迹。
- 实现了BackStackEntry，作为回退栈的元素，正是因为该类拥有事务全部的操作轨迹，因此在popBackStack()时能回退整个事务。
- 继承了Runnable，即被放入FragmentManager执行队列，等待被执行。

先看第一层含义，getSupportFragmentManager.beginTransaction()返回的就是BackStackRecord对象，代码如下：

```
public FragmentTransaction beginTransaction() {
    return new BackStackRecord(this);
}
```
BackStackRecord类包含了一次事务的整个操作轨迹，是以链表形式存在的，链表的元素是Op类，表示其中某个操作，定义如下：
```
static final class Op {
    Op next; //链表后一个节点
    Op prev; //链表前一个节点
    int cmd;  //操作是add或remove或replace或hide或show等
    Fragment fragment; //对哪个Fragment对象做操作
}
```
我们来看下具体场景下这些类是怎么被使用的，比如我们的事务做add操作。add函数的定义：
```
public FragmentTransaction add(int containerViewId, Fragment fragment, String tag) {
   doAddOp(containerViewId, fragment, tag, OP_ADD);
   return this;
}
```

doAddOp()方法就是创建Op对象，并加入链表，定义如下：
```
private void doAddOp(int containerViewId, Fragment fragment, String tag, int opcmd) {
    fragment.mTag = tag;  //设置fragment的tag
    fragment.mContainerId = fragment.mFragmentId = containerViewId;  //设置fragment的容器id
    Op op = new Op();
    op.cmd = opcmd;
    op.fragment = fragment;
    addOp(op);
}
```
addOp()是将创建好的Op对象加入链表，定义如下：
```
void addOp(Op op) {
    if (mHead == null) {
        mHead = mTail = op;
    } else {
        op.prev = mTail;
        mTail.next = op;
        mTail = op;
    }
    mNumOp++;
}
```
addToBackStack(“”)是将mAddToBackStack变量记为true，在commit()中会用到该变量。commit()是异步的，即不是立即生效的，但是后面会看到整个过程还是在主线程完成，只是把事务的执行扔给主线程的Handler，commit()内部是commitInternal()，实现如下：
```
int commitInternal(boolean allowStateLoss) {
    mCommitted = true;
    if (mAddToBackStack) {
        mIndex = mManager.allocBackStackIndex(this);
    } else {
        mIndex = -1;
    }
    mManager.enqueueAction(this, allowStateLoss); //将事务添加进待执行队列中
    return mIndex;
}
```
如果mAddToBackStack为true，则调用allocBackStackIndex(this)将事务添加进回退栈，FragmentManager类的变量ArrayListmBackStackIndices;就是回退栈。实现如下：
```
public int allocBackStackIndex(BackStackRecord bse) {
    if (mBackStackIndices == null) {
        mBackStackIndices = new ArrayList<BackStackRecord>();
    }
    int index = mBackStackIndices.size();
    mBackStackIndices.add(bse);
        return index;
}
```

在commitInternal()中，mManager.enqueueAction(this, allowStateLoss);是将BackStackRecord加入待执行队列中，定义如下：
```
public void enqueueAction(Runnable action, boolean allowStateLoss) {
    if (mPendingActions == null) {
        mPendingActions = new ArrayList<Runnable>();
    }
    mPendingActions.add(action);
    if (mPendingActions.size() == 1) {
        mHost.getHandler().removeCallbacks(mExecCommit);
        mHost.getHandler().post(mExecCommit); //调用execPendingActions()执行待执行队列的事务
    }
}
```

mPendingActions就是前面说的待执行队列，mHost.getHandler()就是主线程的Handler，因此Runnable是在主线程执行的，mExecCommit的内部就是调用了execPendingActions()，即把mPendingActions中所有积压的没被执行的事务全部执行。执行队列中的事务会怎样被执行呢？就是调用BackStackRecord的run()方法，run()方法就是执行Fragment的生命周期函数，还有将视图添加进container中。
与addToBackStack()对应的是popBackStack()，有以下几种变种：

- popBackStack()：将回退栈的栈顶弹出，并回退该事务。
- popBackStack(String name, int flag)：name为addToBackStack(String name)的参数，通过name能找到回退栈的特定元素，flag可以为0或者FragmentManager.POP_BACK_STACK_INCLUSIVE，0表示只弹出该元素以上的所有元素，POP_BACK_STACK_INCLUSIVE表示弹出包含该元素及以上的所有元素。这里说的弹出所有元素包含回退这些事务。
- popBackStack()是异步执行的，是丢到主线程的MessageQueue执行，popBackStackImmediate()是同步版本。

我们通过讲解Demo来更清晰地了解回退栈的使用。功能如下：共有三个Fragment：F1, F2, F3，F1在初始化时就加入Activity，点击F1中的按钮跳转到F2，点击F2的按钮跳转到F3，点击F3的按钮回退到F1。
在Activity的onCreate()中，将F1加入Activity中：
```
getSupportFragmentManager().beginTransaction()
    .add(R.id.container, f1, "f1")
    .addToBackStack(Fragment1.class.getSimpleName())
    .commit();
```

F1按钮的onClick()内容如下：
```
getFragmentManager().beginTransaction()
    .replace(R.id.container, f2, "f2")
    .addToBackStack(Fragment2.class.getSimpleName())
    .commit();
```

F2按钮的onClick()如下：
```
getFragmentManager().beginTransaction()
    .replace(R.id.container, f3, "f3")
    .addToBackStack(Fragment3.class.getSimpleName())
    .commit();
```

F3按钮的onClick()如下：
```
getFragmentManager().popBackStack(Fragment2.class.getSimpleName(),
             FragmentManager.POP_BACK_STACK_INCLUSIVE);
```

这样就完成了整个界面的跳转逻辑。

##这里补充一个点
getSupportFragmentManager().findFragmentByTag()是经常用到的方法，他是FragmentManager的方法，FragmentManager是抽象类，FragmentManagerImpl是继承FragmentManager的实现类，他的内部实现是：
```
class FragmentManagerImpl extends FragmentManager {
    ArrayList<Fragment> mActive;
    ArrayList<Fragment> mAdded;
    public Fragment findFragmentByTag(String tag) { 
           if (mAdded != null && tag != null) { 
               for (int i=mAdded.size()-1; i>=0; i--) {
                Fragment f = mAdded.get(i);
                if (f != null && tag.equals(f.mTag)) {
                        return f;
                }
            }
        }       
          if (mActive != null && tag != null) {
               for (int i=mActive.size()-1; i>=0; i--) {
                    Fragment f = mActive.get(i);
                    if (f != null && tag.equals(f.mTag)) {
                          return f;
                }
            }
        } 
          return null;
    }
}
```

从上面看到，先从mAdded中查找是否有该Fragment，如果没找到，再从mActive中查找是否有该Fragment。mAdded是已经添加到Activity的Fragment的集合，mActive不仅包含mAdded，还包含虽然不在Activity中，但还在回退栈中的Fragment。

##Fragment通信

###Fragment向Activity传递数据
首先，在Fragment中定义接口，并让Activity实现该接口（具体实现省略）：

	public interface OnFragmentInteractionListener {    void onItemClick(String str);  //将str从Fragment传递给Activity}

在Fragment的onAttach()中，将参数Context强转为OnFragmentInteractionListener对象：

```
public void onAttach(Context context) {
    super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
        mListener = (OnFragmentInteractionListener) context;
    } else {
                throw new RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener");
    }
}
```

并在Fragment合适的地方调用mListener.onItemClick("hello")将”hello”从Fragment传递给Activity。

###Activity向Fragment传递数据
Activity向Fragment传递数据比较简单，获取Fragment对象，并调用Fragment的方法即可，比如要将一个字符串传递给Fragment，则在Fragment中定义方法：

	public void setString(String str) { 
    this.str = str;
	}

并在Activity中调用fragment.setString("hello")即可。

###Fragment之间通信
由于Fragment之间是没有任何依赖关系的，因此如果要进行Fragment之间的通信，建议通过Activity作为中介，不要Fragment之间直接通信。

##DialogFragment

DialogFragment是Android 3.0提出的，代替了Dialog，用于实现对话框。他的优点是：即使旋转屏幕，也能保留对话框状态。

如果要自定义对话框样式，只需要继承DialogFragment，并重写onCreateView()，该方法返回对话框UI。这里我们举个例子，实现进度条样式的圆角对话框。

```
public class ProgressDialogFragment extends DialogFragment {    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //消除Title区域
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //将背景变为透明
        setCancelable(false);  //点击外部不可取消
        View root = inflater.inflate(R.layout.fragment_progress_dialog, container);
        return root;
    }
     public static ProgressDialogFragment newInstance() {
        return new ProgressDialogFragment();
    }
}
```

进度条动画我们使用Lottie([https://github.com/airbnb/lottie-android](https://github.com/airbnb/lottie-android))实现，Lottie动画从这里([https://www.lottiefiles.com/](https://www.lottiefiles.com/))找到。使用非常方便，只需要下载JSON动画文件，然后在XML中写入：
```
<com.airbnb.lottie.LottieAnimationView
    android:layout_width="wrap_content"  //大小根据JSON文件确定
    android:layout_height="wrap_content"
    app:lottie_fileName="loader_ring.json"   //JSON文件
    app:lottie_loop="true"    //循环播放
    app:lottie_autoPlay="true" />  //自动播放
```

然后通过下面代码显示对话框：

```
ProgressDialogFragment fragment = ProgressDialogFragment.newInstance();
fragment.show(getSupportFragmentManager(), "tag");//fragment.dismiss();
```

为了实现圆角，除了在onCreateView()中把背景设为透明，还需要对UI加入背景：

```
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#ffffff"/>
    <corners
        android:radius="20dp"/>
</shape>
```