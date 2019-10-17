#Gson fromJson() 用法

>Gson提供了fromJson()方法来实现从Json相关对象到Java实体的方法。
在日常应用中，我们一般都会碰到两种情况，转成单一实体对象和转换成对象列表或者其他结构

##第一种
比如json字符串为：[{“name”:”name0”,”age”:0}]

	Person person = gson.fromJson(str, Person.class);

##第二种
提供两个参数，分别是json字符串以及需要转换对象的类型。

	List<Person> ps = gson.fromJson(str, new TypeToken<List<Person>>(){}.getType());

可以看到上面的代码使用了TypeToken，它是gson提供的数据类型转换器，可以支持各种数据集合类型转换。

##调试注意：
经过Gson解析成为map的数据，经常需要通过map.get(key)获取类型为Object的值，我们常需要将Object进行强制转换，转换为我们需要的类型。这里注意，若我们想将整形数字1存入json串，经gson解析后，可能会变成1.0，这样我们只能使用Double类对其进行强转，再使用Double类型的intValue()方法将其转为整形。

