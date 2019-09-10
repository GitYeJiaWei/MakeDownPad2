#获取MAC地址

```
private String getLocalMacAddress() {  //获取mac
	    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
	    WifiInfo info = wifi.getConnectionInfo();  
	    return info.getMacAddress();  
	  }
```