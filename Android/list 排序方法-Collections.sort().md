#list 排序方法-Collections.sort()
```
			/**
             *负整数	当前对象的值 < 比较对象的值 ， 位置排在前
             *零	当前对象的值 = 比较对象的值 ， 位置不变
             *正整数	当前对象的值 > 比较对象的值 ， 位置排在后
             */
```

##Android对数据按照时间排序
>经常遇见一个列表，两个接口的情况，两个接口属于两个不同的  
>表数据，那么数据拼接回来之后，并不是按照时间排序的，看起
>来就相当混乱，所以记录一下如何对数据按照时间排序。

步骤一：

格式化日期

```
   public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }
```

步骤二：

对拼接的列表进行排序

```
  private void sortData(ArrayList<CourseModel> mList) {
        Collections.sort(mList, new Comparator<CourseModel>() {
            /**
             *
             * @param lhs
             * @param rhs
             * @return an integer < 0 if lhs is less than rhs, 0 if they are
             *         equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
             */
            @Override
            public int compare(CourseModel lhs, CourseModel rhs) {
                Date date1 = DateUtil.stringToDate(lhs.getCREATE_TIME());
                Date date2 = DateUtil.stringToDate(rhs.getCREATE_TIME());
                // 对日期字段进行升序，如果欲降序可采用after方法
                return date1.equals(date2) ? 0 : (date1.before(date2) ? 1 : -1);
            }
        });
        adapter.replaceAll(mList);
    }
```