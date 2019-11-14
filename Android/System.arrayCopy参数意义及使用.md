#java System.arrayCopy 参数意义，使用
##下面是 System.arrayCopy的源代码声明 :

```
public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
代码解释:
　　Object src : 原数组
   int srcPos : 从元数据的起始位置开始
　　Object dest : 目标数组
　　int destPos : 目标数组的开始起始位置
　　int length  : 要copy的数组的长度
```
比如 ：我们有一个数组数据 
	byte[]  srcBytes = new byte[]{2,4,0,0,0,0,0,10,15,50};  // 源数组
	byte[] destBytes = new byte[5]; // 目标数组

>我们使用System.arraycopy进行转换(copy)
System.arrayCopy(srcBytes,0,destBytes ,0,5)
上面这段代码就是 : 创建一个一维空数组,数组的总长度为 12位,然后将srcBytes源数组中 从0位 到 第5位之间的数值 copy 到 destBytes目标数组中,在目标数组的第0位开始放置.
那么这行代码的运行效果应该是 2,4,0,0,0,