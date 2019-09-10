#GreenDao:3.2.0用法（其它版本可以参考）

- **第一步：在app的Build.gradle中添加如下配置：**


	apply plugin: 'org.greenrobot.greendao'

```java
buildTypes {
    release {
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }
}

greendao{
    schemaVersion 1
    daoPackage 'com.admom.mygreendaotest'
    targetGenDir 'src/main/java'
}
//schemaVersion： 数据库schema版本，也可以理解为数据库版本号
//daoPackage：设置DaoMaster、DaoSession、Dao包名
//targetGenDir：设置DaoMaster、DaoSession、Dao目录
//targetGenDirTest：设置生成单元测试目录
//generateTests：设置自动生成单元测试用例

```


```java

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:appcompat-v7:24.2.1'
    testCompile 'junit:junit:4.12'

    compile 'org.greenrobot:greendao:3.2.0'
}
```

- **在工程的Build.gradle中添加如下配置：**

```java
buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.0'
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.0'
    }
}
```

- **写实体类**


```
@Entity
public class User {    @Id(autoincrement = true)    private Long id;    private String name;    private int age;    private boolean isBoy;

```


- **点击Build后make project后GreenDao会自动帮你生成get/set方法如下:**

```java

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int age;
    private boolean isBoy;
    @Generated(hash = 1724489812)
    public User(Long id, String name, int age, boolean isBoy) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isBoy = isBoy;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public boolean getIsBoy() {
        return this.isBoy;
    }
    public void setIsBoy(boolean isBoy) {
        this.isBoy = isBoy;
    }

}
```

- **接下来就是封装 GreenDaoManager这个自己的工具类调用方法：设置为单例模式，方便后续操作**

```java
public class GreenDaoManager {

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreenDaoManager mInstance; //单例

    private GreenDaoManager(){
        if (mInstance == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new
                    DaoMaster.DevOpenHelper(MyApplication.getContext(), "user1-db", null);//此处为自己需要处理的表
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {//保证异步处理安全操作

                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }
    public DaoSession getSession() {
        return mDaoSession;
    }
    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
```

在Application中配置好：

```java
public class MyApplication extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //greenDao全局配置,只希望有一个数据库操作对象
        GreenDaoManager.getInstance();
    }
    public static Context getContext() {
        return mContext;
    }
}
```

- **这时就可以在自己的代码中进行使用了，我将GreenDao一些常用方法置下：**

```java
public class MainActivity extends AppCompatActivity {
    //@Transient，该注解表示这个属性将不会作为数据表中的一个字段
    //@NotNull表示该字段不可以为空，@Unique表示该字段唯一

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        updatadata();
//        insertdata();
//        querydata();
          //删除数据
//        getUserDao().deleteByKey(2l);//long类型
//        querydata();
//        querydataBy();

        getuserById();


    }

    private void getuserById() {
        User user =getUserDao().load(1l);
        Log.i("tag", "结果：" + user.getId() + "," + user.getName() + "," + user.getAge() + "," + user.getIsBoy() + ";");


    }

    private void insertdata() {
        //插入数据
        User insertData = new User(null, "插入数据", 24, false);
        getUserDao().insert(insertData);
    }

    private void updatadata() {
        //更改数据
        List<User> userss = getUserDao().loadAll();
        User user = new User(userss.get(0).getId(), "更改后的数据用户", 22, true);
        getUserDao().update(user);

    }

    private void querydata() {
        //查询数据详细
        List<User> users = getUserDao().loadAll();
        Log.i("tag", "当前数量：" + users.size());
        for (int i = 0; i < users.size(); i++) {
            Log.i("tag", "结果：" + users.get(i).getId() + "," + users.get(i).getName() + "," + users.get(i).getAge() + "," + users.get(i).getIsBoy() + ";");
        }

    }

    private void querydataBy() {////查询条件
        Query<User> nQuery = getUserDao().queryBuilder()
//                .where(UserDao.Properties.Name.eq("user1"))//.where(UserDao.Properties.Id.notEq(999))
                .orderAsc(UserDao.Properties.Age)//.limit(5)//orderDesc
                .build();
        List<User> users = nQuery.list();
        Log.i("tag", "当前数量：" + users.size());
        for (int i = 0; i < users.size(); i++) {
            Log.i("tag", "结果：" + users.get(i).getId() + "," + users.get(i).getName() + "," + users.get(i).getAge() + "," + users.get(i).getIsBoy() + ";");
        }

//        QueryBuilder qb = userDao.queryBuilder();
//        qb.where(Properties.FirstName.eq("Joe"),
//                qb.or(Properties.YearOfBirth.gt(1970),
//                        qb.and(Properties.YearOfBirth.eq(1970), Properties.MonthOfBirth.ge(10))));
//        List youngJoes = qb.list();
    }


    /**
     * 根据查询条件,返回数据列表
     * @param where        条件
     * @param params       参数
     * @return             数据列表
     */
    public List<User> queryN(String where, String... params){
        return getUserDao().queryRaw(where, params);
    }

    /**
     * 根据用户信息,插件或修改信息
     * @param user              用户信息
     * @return 插件或修改的用户id
     */
    public long saveN(User user){
        return getUserDao().insertOrReplace(user);
    }

    /**
     * 批量插入或修改用户信息
     * @param list      用户信息列表
     */
    public void saveNLists(final List<User> list){
        if(list == null || list.isEmpty()){
            return;
        }
        getUserDao().getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    User user = list.get(i);
                    getUserDao().insertOrReplace(user);
                }
            }
        });

    }

    /**
     * 删除所有数据
     */
    public void deleteAllNote(){
        getUserDao().deleteAll();
    }

    /**
     * 根据用户类,删除信息
     * @param user    用户信息类
     */
    public void deleteNote(User user){
        getUserDao().delete(user);
    }
    private UserDao getUserDao() {
        return GreenDaoManager.getInstance().getSession().getUserDao();
    }

}
```


- **一般的简单数据库升级，如下操作即可：**

```java
1 首先在module的gradle文件中修改版本号：
 greendao{
    schemaVersion 5
    daoPackage 'xxxxxxxxxx'
    targetGenDir 'src/main/java'
}


2 修改实体类为自己需要的类型，重现编译项目运行即可。
这样修改，之前的数据库会被删除，重新创建。
```

- **下面为一些常用的注解使用方式：**

```java
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
  
    @Property(nameInDb = "USERNAME")
    private String name;
  
    @NotNull
    private int repos;
  
    @Transient
    private int tempUsageCount;
  
    ...
}
@Entity 用于标识这是一个需要Greendao帮我们生成代码的bean

@Id 标明主键，括号里可以指定是否自增

@Property 用于设置属性在数据库中的列名（默认不写就是保持一致）

@NotNull 非空

@Transient 标识这个字段是自定义的不会创建到数据库表里

@Unique 添加唯一约束

关系注解：
@Entity
public class Order {
    @Id private Long id;
  
    private long customerId;
  
    @ToOne(joinProperty = "customerId")
    private Customer customer;
}
  
@Entity
public class Customer {
    @Id private Long id;
}
@ToOne 是将自己的一个属性与另一个表建立关联

@Entity
public class User {
    @Id private Long id;
  
    @ToMany(referencedJoinProperty = "ownerId")
    private List<Site> ownedSites;
}
  
@Entity
public class Site {
    @Id private Long id;
    private long ownerId;
}

// ----------------------------
 
@Entity
public class User {
    @Id private Long id;
    @Unique private String authorTag;
  
    @ToMany(joinProperties = {
            @JoinProperty(name = "authorTag", referencedName = "ownerTag")
    })
    private List<Site> ownedSites;
}
  
@Entity
public class Site {
    @Id private Long id;
    @NotNull private String ownerTag;
}
 
// ----------------------------
@ToMany的属性referencedJoinProperty，类似于外键约束。

@JoinProperty 对于更复杂的关系，可以使用这个注解标明目标属性的源属性。
```