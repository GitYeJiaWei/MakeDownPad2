#FragmentStatePagerAdapter与FragmentPagerAdapter的区别

>在ViewPager中使用Fragment的情况下，可以给ViewPager设置两种Adapter，一种是FragmentStatePagerAdapter，一种是FragmentPagerAdapter。
那这两种Adapter有什么区别呢？

##FragmentStatePagerAdapter

FragmentStatePagerAdapter会销毁不需要的Fragment，一般来说，ViewHolder会保存正在显示的Fragment和它左右两边第一个Fragment，分别为A、B、C，那么当显示的Fragment变成C时，保存的Fragment就会变成B、C、D了，而A此时就会被销毁，但是需要注意的是，此时A在销毁的时候，会通过onSaveInstanceState方法来保存Fragment中的Bundle信息，当再次切换回来的时候，就可以利用保存的信息来恢复到原来的状态。

![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/%E5%85%B6%E5%AE%83%E5%9B%BE%E7%89%87/fragment1.png)

##FragmentPagerAdapter

FragmentPageAdapter会调用事务的detach方法来处理，而不是使用remove方法。因此，FragmentPageAdapter只是销毁了Fragment的视图，其实例还是保存在FragmentManager中。

![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/%E5%85%B6%E5%AE%83%E5%9B%BE%E7%89%87/fragment2.png)

##如何选择Adapter呢

从上文得知，FragmentStatePageAdapter适用于Fragment较多的情况，例如月圆之夜这个卡牌游戏，需要展示数十个不同的场景，需要数十个不同的Fragment，如果这些Fragment都保存在FragmentManager中的话，对应用的性能会造成很大的影响。
而FragmentPageAdapter则适用于固定的，少量的Fragment情况，例如和TabLayout共同使用时，典型的一个例子就是知乎上方的TabLayout