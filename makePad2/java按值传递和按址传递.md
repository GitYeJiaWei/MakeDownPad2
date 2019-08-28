#java按值传递和按址传递
- **按值传递：只有当参数为基本类型变量的时候，java按这种策略的方式传递。**
- **基本类型变量-按值传递，按值传递通过复制获取参数的副本**



- **按址传递：只有当参数为引用类型变量，java按这种策略方式进行传递**
- **引用类型变量-按址传递，按址传递通过传递对象的引用地址**

```java
	/**
     * 基本类型，按值传递
     * 举例：给朋友分享你的照片，对方接收的是你的照片的一个实际的副本，
     * 你和朋友分别对各自的照片进行操作，不会影响彼此的照片！
     */
    public static void testVal(int photo){
        photo++;//朋友对照片进行修改
        System.out.println("My friend see photo = " + photo);
    }
    
    /**
     * 引用类型：按址传递
     * 举例：给朋友分享你的照片，分享的是你上传网上的一个照片的Url（地址），
     * 你和朋友都可以通过这个地址访问照片，并对照片进行一个操作！
     */
    public static void testRef(Photo photo){
        photo.setPhoto("java Photo,Great!");//朋友对你的照片进行修改
        System.out.println("My friend see photo = " + photo.getPhoto());
    }
    
    /**
     * 引用类型：按址传递
     * 因为数组是一个引用类型。所以传递进去的是它们的引用,故在方法中互换了它们的值,也必然影响到它们原来的值.
     */
    public static void testArrayRef(int[] array){
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
        System.out.println("testArrayRef array is = "+Arrays.toString(array));
    }

	//数组也是对象，数组在堆内存。引用是在栈。
        int array[] = {1,2,3,4,5};
        testArrayRef(array);
        System.out.println("array is = "+Arrays.toString(array));
```