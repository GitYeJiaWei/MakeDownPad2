#Android获取设备唯一ID的几种方式

##IMEI
方式：TelephonyManager.getDeviceId():
问题

范围：只能支持拥有通话功能的设备，对于平板不可以。
持久性：返厂，数据擦除的时候不彻底，保留了原来的标识。
权限：需要权限：Android.permission.READ_PHONE_STATE
bug: 有些厂家的实现有bug，返回一些不可用的数据

##Mac地址

ACCESS_WIFI_STATE权限
有些设备没有WiFi，或者蓝牙，就不可以，如果WiFi没有打开，硬件也不会返回Mac地址，不建议使用

##ANDROID_ID
2.2（Froyo，8）版本系统会不可信，来自主要生产厂商的主流手机，至少有一个普遍发现的bug，这些有问题的手机相同的ANDROID_ID: 9774d56d682e549c
但是如果返厂的手机，或者被root的手机，可能会变

##Serial Number
从Android 2.3 (“Gingerbread”)开始可用，可以通过android.os.Build.SERIAL获取，对于没有通话功能的设备，它会返回一个唯一的device ID


```
// IMEI码 
  private static String getIMIEStatus(Context context) { 
    TelephonyManager tm = (TelephonyManager) context 
        .getSystemService(Context.TELEPHONY_SERVICE); 
    String deviceId = tm.getDeviceId(); 
    return deviceId; 
  } 
  
  // Mac地址 
  private static String getLocalMac(Context context) { 
    WifiManager wifi = (WifiManager) context 
        .getSystemService(Context.WIFI_SERVICE); 
    WifiInfo info = wifi.getConnectionInfo(); 
    return info.getMacAddress(); 
  } 
  
  // Android Id 
  private static String getAndroidId(Context context) { 
    String androidId = Settings.Secure.getString( 
        context.getContentResolver(), Settings.Secure.ANDROID_ID); 
    return androidId; 
  } 
```


>对于获取设备唯一ID并没有绝对的方案，这一点在android的官方博客中也提到了，不过以上几种方案，应该可以满足平时的需求，大家可以选择其中自己认为比较好的，用于自己的项目中。