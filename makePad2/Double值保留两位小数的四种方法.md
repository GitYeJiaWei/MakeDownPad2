#Double值保留两位小数的四种方法

```java
public class DoubleTest {
    //保留两位小数第三位如果大于4会进一位（四舍五入）
    double f = 6.23556;
    /**
      *使用精确小数BigDecimal
      */
    public void fun1() {
        BigDecimal bg = new BigDecimal(f);
        /**
         * 参数：
                newScale - 要返回的 BigDecimal 值的标度。
                roundingMode - 要应用的舍入模式。
          返回：
                一个 BigDecimal，其标度为指定值，其非标度值可以通过此 BigDecimal 的非标度值乘以或除以十的适当次幂来确定。
         */
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }
 
    /**
     * DecimalFormat转换最简便
     */
    public void fun2() {
        DecimalFormat df = new DecimalFormat("######.00");
        System.out.println(df.format(f));
    }
 
    /**
     * String.format打印最简便
     */
    public void fun3() {
        System.out.println(String.format("%.2f", f));
    }
   /**
     *使用NumberFormat
     */    public void fun4() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        /**
         * setMaximumFractionDigits(int digits)
           设置数的小数部分所允许的最大位数。
			digits 显示的数字位数 
			为格式化对象设定小数点后的显示的最多位,显示的最后位是舍入的
         */
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(f));
    }
 
    public static void main(String[] args) {
        DoubleTest dt = new DoubleTest();
        dt.fun1();
        dt.fun2();
        dt.fun3();
        dt.fun4();
 
    }
}
```

```java
假如需求改为向下取两位小数，而不是四舍五入，那么方式一的代码就有问题，
BigDecimal bg = new BigDecimal(Doubel.toString(f));
应该最优选用这个构造方法


1、ROUND_UP

舍入远离零的舍入模式。

在丢弃非零部分之前始终增加数字(始终对非零舍弃部分前面的数字加1)。

注意，此舍入模式始终不会减少计算值的大小。

2、ROUND_DOWN

接近零的舍入模式。

在丢弃某部分之前始终不增加数字(从不对舍弃部分前面的数字加1，即截短)。

注意，此舍入模式始终不会增加计算值的大小。

7、ROUND_HALF_EVEN    银行家舍入法

向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。

如果舍弃部分左边的数字为奇数，则舍入行为与 ROUND_HALF_UP 相同;

如果为偶数，则舍入行为与 ROUND_HALF_DOWN 相同。

注意，在重复进行一系列计算时，此舍入模式可以将累加错误减到最小。

此舍入模式也称为“银行家舍入法”，主要在美国使用。四舍六入，五分两种情况。

如果前一位为奇数，则入位，否则舍去。

以下例子为保留小数点1位，那么这种舍入方式下的结果。

1.15>1.2 1.25>1.2
```