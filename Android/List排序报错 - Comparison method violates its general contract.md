#List排序报错 - Comparison method violates its general contract!

>在JDK1.7之后，ArrayList的默认排序方式做了修改，使用TimeSort排序算法来排序
```
 public static <T> void sort(T[] a, Comparator<? super T> c) {
        if (c == null) {
            sort(a);
        } else {
            // Android-changed: LegacyMergeSort is no longer supported
            // if (LegacyMergeSort.userRequested)
            //     legacyMergeSort(a, c);
            // else
                TimSort.sort(a, 0, a.length, c, null, 0, 0);
        }
    }
```

但是，此排序算法比老版本的算法多了如下几个限制条件，如果不注意，排序可能会抛异常

 1. 自反性，compare(x, y) = - compare(y, x)
 2. 传递性，如果compare(x, y) > 0, compare(y, z) > 0, 则必须保证compare(x, z) > 0
 3. 对称性, 如果compare(x, y) == 0, 则必须保证compare(x, z) == compare(y, z)


 如果不满足以上3个条件，TimeSort并不是一定会报错，在某些序列条件下才会报错，这与TimeSort的排序逻辑有关。同时只有在排序序列长度在32以上时才有可能抛异常，因为在排序序列长度小于32时，TimeSort会直接退化为二分排序
```
 static <T> void sort(T[] a, int lo, int hi, Comparator<? super T> c,
                         T[] work, int workBase, int workLen) {
        assert c != null && a != null && lo >= 0 && lo <= hi && hi <= a.length;

        int nRemaining  = hi - lo;
        if (nRemaining < 2)
            return;  // Arrays of size 0 and 1 are always sorted

        // If array is small, do a "mini-TimSort" with no merges
        if (nRemaining < 32) {
            int initRunLen = countRunAndMakeAscending(a, lo, hi, c);
            binarySort(a, lo, hi, lo + initRunLen, c);
            return;
        }
```

 JDK1.7之后的标准排序方式如下，以长整形为例，其它对象类似：（此处没考虑为Null的情况，如果有可能为null，代码逻辑要保证满足上述的三个条件）
```
@Override
public int compare(Long a, Long b) {
    return a == b ? 0 : (a > b ? 1 : -1);

```

常见的几种错误排序方式（JDK1.7之前可以使用，1.7之后可能会异常）

1. 未判断相等的情况，及不满足自反性

```
@Override
public int compare(Long a, Long b) {
    return a > b ? 1 : -1;

```

2. 不满足传递性

```

@Override
public int compare(Long a, Long b) {
    return (int)(a-b);

```


