#使用ACache缓存List类型的数据

##如何使用 ASimpleCache？

```
ACache mCache = ACache.get(this);
mCache.put("test_key1", "test value");
mCache.put("test_key2", "test value", 10);//保存10秒，如果超过10秒去获取这个key，将为null
mCache.put("test_key3", "test value", 2 * ACache.TIME_DAY);//保存两天，如果超过两天去获取这个key，将为null

获取数据

ACache mCache = ACache.get(this);
String value = mCache.getAsString("test_key1");
```

##存储list

- 存入本地
```
ACache aCache = ACache.get(mContext);  
                    //只能使用List的子类  
                    ArrayList<CarLocation> arrayList = new ArrayList();  
                    //注意：一定要序列化  
                    CarLocation carLocation = new CarLocation();  
                    carLocation.setTitle("测试");  
                    arrayList.add(carLocation);  
                    aCache.put("car", arrayList);  
```

- 取出
```
ACache aCache = ACache.get(activity);  
           //使用getAsObject()，直接进行强转  
           ArrayList<CarLocation> carLocations = (ArrayList<CarLocation>) aCache.getAsObject("car");  
```