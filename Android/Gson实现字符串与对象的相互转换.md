#Gson实现字符串与对象的相互转换
1. **从实体类到JSON字符串**

	Gson gson = new Gson();
	String jsonBDID = gson.toJson(bdPushID);

2. **从JSON字符串到实体类**
	Gson gson = new Gson();
	BDPushID bdPushID2 = gson.fromJson(bdPushId, BDPushID.class);

 

3. **从JSON数组到ArrayList**

	Gson gson = new Gson();

	Car cars = gson.fromJson(result,new TypeToken<ArrayList<Car>>() {}.getType());

- **把对象转为JSON格式的字符串**

```java
 	Gson gs = new Gson();
 
        Person person = new Person();
        person.setId(1);
        person.setName("我是酱油");
        person.setAge(24);
 
        String objectStr = gs.toJson(person);//把对象转为JSON格式的字符串
        System.out.println("把对象转为JSON格式的字符串///  "+objectStr);


	结果为{“id”:1,”name”:”我是酱油”,”age”:24}

```


- **把List转为JSON格式的字符串**

```java
 Gson gs = new Gson();
 
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 10; i++) {//初始化测试数据
            Person ps = new Person();
            ps.setId(i);
            ps.setName("我是第"+i+"个");
            ps.setAge(i+10);
            persons.add(ps);
        }
 
        String listStr = gs.toJson(persons);//把List转为JSON格式的字符串
        System.out.println("把list转为JSON格式的字符串///  "+listStr);


		结果为[{“id”:0,”name”:”我是第0个”,”age”:10},
		{“id”:1,”name”:”我是第1个”,”age”:11},
		{“id”:2,”name”:”我是第2个”,”age”:12},
		{“id”:3,”name”:”我是第3个”,”age”:13},
		{“id”:4,”name”:”我是第4个”,”age”:14},
		{“id”:5,”name”:”我是第5个”,”age”:15},
		{“id”:6,”name”:”我是第6个”,”age”:16},
		{“id”:7,”name”:”我是第7个”,”age”:17},
		{“id”:8,”name”:”我是第8个”,”age”:18},
		{“id”:9,”name”:”我是第9个”,”age”:19}]

```