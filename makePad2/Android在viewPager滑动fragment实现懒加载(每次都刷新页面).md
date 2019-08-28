#Android在viewPager滑动fragment实现懒加载(每次都刷新页面)

- 有需求是，每次滑动页面就要刷新加载数据，在viewPager里有一个预加载的方法，不管你设置是0也好，默认都是预加载1页的，所以，干脆就不动预加载，搞一下fragment里的代码实现每次滑动就刷新咯

1. **首先**
	private boolean isPrepared = false;

2. **然后在onCreateView写：**
	isPrepared =true;

3. **再后来就写一个setUserVisibleHint的方法：**

```java
	@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isPrepared && isVisibleToUser) {
         		//加载数据
//isVisibleTouser为true表示当前界面正在展示（fragment滑动的时候调用）
        }
    }

```