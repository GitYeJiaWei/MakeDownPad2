#将Object类型转换成String类型

1. 当类型为Object时，null不能直接使用 obj.toSting(),否则会报空指针异常的，NullPointException,必须加上一个判断
```java
	if（obj!=null）{
		a = obj.toString();	
	}
```

2. (String)Object 该方法要注意类型转换的异常，必须是能转换为string的类型，否则会出现CalssCastException异常错误

3. String.valueOf(Object) 在使用String.valueOf(Object)时，它会判断Object是否为空值，如果是，则返回"null"

- 注：后台传输的数据如果为空，不要给null，最好传“”空字符串