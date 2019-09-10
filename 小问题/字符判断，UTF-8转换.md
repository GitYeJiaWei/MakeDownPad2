#字符判断，UTF-8转换

```
case R.id.sure://南京测试机,省份，城市，应用
			s1=spinner1.getSelectedItem().toString();
			s2=spinner2.getSelectedItem().toString();
			s3=spinner3.getSelectedItem().toString();
			s4=spinner4.getSelectedItem().toString();
			String url3="http://153.3.49.41:8078/CQTTest/num/findDisplayNum.do";
			Thread thread1=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String url3="http://153.3.49.41:8078/CQTTest/num/findDisplayNum.do";
					
					if (s1.indexOf("省")>-1) {
						s1=s1.replaceAll("省", "");
					}else if (s1.indexOf("特别行政区")>-1) {
						s1=s1.replaceAll("特别行政区", "");
					}else if (s1.indexOf("市")>-1) {
						s1=s1.replaceAll("市", "");
					}else if (s1.indexOf("壮族自治区")>-1) {
						s1=s1.replaceAll("壮族自治区", "");
					}else if (s1.indexOf("回族自治区")>-1) {
						s1=s1.replaceAll("回族自治区", "");
					}else if (s1.indexOf("维吾尔自治区")>-1) {
						s1=s1.replaceAll("维吾尔自治区", "");
					}
					if(s2.indexOf("市辖区")>-1 || s2.indexOf("市辖县")>-1){
						s2=s2.replaceAll(s2, s1);
					}else if  (s2.indexOf("市")>-1)  {
						s2=s2.replaceAll("市", "");
					}
					if (s4.equals("苏州")) {
						s4="1";
					}else if (s4.equals("钉钉")) {
						s4="2";
					}else if (s4.equals("CTD自留")) {
						s4="3";
					}else if (s4.equals("阿里通信")) {
						s4="4";
					}else if (s4.equals("测试号码")) {
						s4="5";
					}else if (s4.equals("默认")) {
						s4="0";
					}
					
					try {
						Log.e("报文", s1+" "+s2+" "+s4+" "+getLocalMacAddress());
						s1 = new String(s1.getBytes("UTF-8"),"iso-8859-1");
			        	//把UTF-8转换为iso-8859-1
						s2 = new String(s2.getBytes("UTF-8"),"iso-8859-1");
			        	String url=url3+"?province="+s1+"&city="+s2+"&type="+s4+"&macAddress="+getLocalMacAddress();
			        	URL httpUrl = new URL(url);
			            Log.e("curl", url);
			            HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
			            conn.setConnectTimeout(5000);
			            conn.setDoOutput(true);// 允许输出
			            conn.setDoInput(true);
			            conn.setUseCaches(false);// 不使用缓存
			            conn.setRequestMethod("POST");
			            conn.setRequestProperty("Charset", "UTF-8");
			            
			            if (conn.getResponseCode() != 200)
			                throw new RuntimeException("请求url失败");
			            InputStream is = conn.getInputStream();// 获取返回数据

			            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
			            byte[] buf = new byte[1024];
			            int len;
			            while ((len = is.read(buf)) != -1) {
			                out1.write(buf, 0, len);
			            }
			            String string = out1.toString("UTF-8");
			            Log.e("返回报文", "123");
			            System.out.println(string);
			            Message msg1=Message.obtain();
				        msg1.obj=string;
				        handler.sendMessage(msg1);
			            Log.e("返回报文", "123");
			            out1.close();
			            is.close();
			        } catch (Exception e) {
			            System.out.println(e);
			        }
				}
			});
			thread1.start();
			handler=new Handler(){
				public void handleMessage(Message msg1) {
					super.handleMessage(msg1);
					getResult1=(String) msg1.obj;
					String per[]=getResult1.split(",");
					if (per.length<5) {
						Toast.makeText(MainActivity.this, "该省市没有对应号码！", Toast.LENGTH_LONG).show();
					}else{
					objects1.clear();//清除数据  
					objects2.clear();
					for(int i=0;i<per.length;i++){
						if (per[i].indexOf("[")>-1) {
							per[i]=per[i].replace("[", "");
						}else if (per[i].indexOf("]")>-1) {
							per[i]=per[i].replace("]", "");
						}
						if (per[i].indexOf("\"")>-1) {
							per[i]=per[i].replace("\"", "");
						}
						objects1.add(per[i]);
						objects2.add(per[i]);
					}
					Number.setText(per[0]);
					Numbert.setText(per[0]);
					
			        adapter1.notifyDataSetChanged();//数据更新
			        adapter2.notifyDataSetChanged();
			        }
				};
			};
			
			break;
```