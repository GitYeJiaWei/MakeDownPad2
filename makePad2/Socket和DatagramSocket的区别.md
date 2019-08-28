#Socket和DatagramSocket的区别

- Socket使用的tcp连接，客户端需要先连接之后才能发送数据
- DatagramSocket使用的UDP连接，客户端不需要先连接数据，可以直接发送给指定服务端。

##DatagramSocket：
- **客户端发送（直接发送数据，没有连接的过程）：**

```java

protected void connectServerWithUDPSocket(Context context, String id) {
        DatagramSocket socket;
        try {
            //创建DatagramSocket对象并指定一个端口号，注意，如果客户端需要接收服务器的返回数据,
            //还需要使用这个端口号来receive，所以一定要记住
            socket = new DatagramSocket(null);
            //使用InetAddress(Inet4Address).getByName把IP地址转换为网络地址
            InetAddress serverAddress = null;
            mHost = Utils.getLocalIpStr(context);
            Log.d(TAG, "connectServerWithUDPSocket mHost =" + mHost);
            if (null == mHost) return;
            try {
                serverAddress = InetAddress.getByName(mHost);
            } catch (UnknownHostException e) {
                Log.d(TAG, "未找到服务器");
                e.printStackTrace();
            }
            //Inet4Address serverAddress = (Inet4Address) Inet4Address.getByName("192.168.1.32");
            String str = id;//设置要发送的报文
            byte data[] = str.getBytes();//把字符串str字符串转换为字节数组
            //创建一个DatagramPacket对象，用于发送数据。
            //参数一：要发送的数据  参数二：数据的长度  参数三：服务端的网络地址  参数四：服务器端端口号
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, PORT);
            try {
                socket.send(packet);//把数据发送到服务端。
            } catch (IOException e) {
                Log.d(TAG, "发送失败");
                e.printStackTrace();
            }
            Log.d(TAG, "socket.send------------------------");
        } catch (SocketException e) {
            Log.i(TAG, "建立接收数据报失败");
            e.printStackTrace();
        }
    }
```

- **服务端接收：**

```java
public void serverReceviedByUdp() {
        //创建一个DatagramSocket对象，并指定监听端口。（UDP使用DatagramSocket）
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(PORT);
            //创建一个byte类型的数组，用于存放接收到得数据
            byte data[] = new byte[4 * 1024];
            //创建一个DatagramPacket对象，并指定DatagramPacket对象的大小
            DatagramPacket packet = new DatagramPacket(data, data.length);
            while (true) {
                //读取接收到得数据
                socket.receive(packet);
                //把客户端发送的数据转换为字符串。
                //使用三个参数的String方法。参数一：数据包 参数二：起始位置 参数三：数据包长
                String result = new String(packet.getData(), packet.getOffset(), packet.getLength());
                Log.d(TAG, "service result = " + result);
                
            }
 
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

##Socket：
- **服务端：**

```java
public class TcpServer {
    private static ServerSocket serverSocket;
    private static Socket socket;
 
    public static void startServer(){
        if (serverSocket == null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        serverSocket = new ServerSocket(8080);
                        Log.i("tcp" , "服务器等待连接中");
                        socket = serverSocket.accept();
                        Log.i("tcp" , "客户端连接上来了");
                        InputStream inputStream = socket.getInputStream();
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = inputStream.read(buffer)) != -1) {
                            String data = new String(buffer, 0, len);
                            Log.i("tcp" , "收到客户端的数据-----------------------------:" + data);
                            EventBus.getDefault().post(new MessageServer(data));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
 
                    }finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            serverSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        socket = null;
                        serverSocket = null;
                    }
                }
            }).start();
        }
    }
 
    public static void sendTcpMessage(final String msg){
        if (socket != null && socket.isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket.getOutputStream().write(msg.getBytes());
                        socket.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
```

- **客户端（这边需要先连接服务端之后，才能发送数据）：**

```java

public class TcpClient {
 
    public static Socket socket;
 
    public static void startClient(final String address ,final int port){
        if (address == null){
            return;
        }
        if (socket == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i("tcp", "启动客户端");
                        socket = new Socket(address, port);
                        Log.i("tcp", "客户端连接成功");
                        PrintWriter pw = new PrintWriter(socket.getOutputStream());
 
                        InputStream inputStream = socket.getInputStream();
 
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = inputStream.read(buffer)) != -1) {
                            String data = new String(buffer, 0, len);
                            Log.i("tcp", "收到服务器的数据---------------------------------------------:" + data);
                            EventBus.getDefault().post(new MessageClient(data));
                        }
                        Log.i("tcp", "客户端断开连接");
                        pw.close();
 
                    } catch (Exception EE) {
                        EE.printStackTrace();
                        Log.i("tcp", "客户端无法连接服务器");
 
                    }finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        socket = null;
                    }
                }
            }).start();
        }
    }
 
    public static void sendTcpMessage(final String msg){
        if (socket != null && socket.isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket.getOutputStream().write(msg.getBytes());
                        socket.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
```