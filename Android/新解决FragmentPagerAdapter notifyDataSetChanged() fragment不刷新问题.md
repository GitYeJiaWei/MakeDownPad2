#解决FragmentPagerAdapter notifyDataSetChanged() fragment不刷新问题

思路：在设置新数据时候移除老的fragment从manager中：
```
   public void setData(List<Fragment> fragment){
        if (fragment.size()>0) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            for (Fragment f : fragments) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            getSupportFragmentManager().executePendingTransactions();
        }
        fragments=fragment;
        mAdapter.notifyDataSetChanged();
    }

```

fragments 是老的fragment的集合，根据自己业务逻辑记性补充，这里是全部移除，你可以根据具体业务移除需要重建的老fragment。