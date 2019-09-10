# 一级标题
## 二级标题
### 三级标题
#### 四级标题
##### 五级标题
###### 六级标题
- 注：# 和「一级标题」之间建议保留一个字符的空格，这是最标准的 Markdown 写法

#无序列表
- 文本1
- 文本2
- 文本3


#引用
> 欢迎关注“博客”
> 始于颜值、陷于才华、忠于人品

> 注：和文本之间要保留一个字符的空格。

#有序列表
1. 文本1
2. 文本2
3. 文本3
4. 注：-、1. 和文本之间要保留一个字符的空格。

#斜体的写法
*你要写的博客*

#粗体的写法
**始于颜值、陷于才华、忠于人品**

#表格

| Tables        | Are           | Cool  |
| ------------- |:-------------:| -----:|
| col 3 is      | right-aligned | $1600 |
| col 2 is      | centered      |   $12 |
| zebra stripes | are neat      |    $1 |

#代码引用
    tvType.setText(wb.getWasteType());
	tvType.setText(wb.getWasteType());
	tvType.setText(wb.getWasteType());
	直接在语句前加上一个tab键
	如果不行的话要到工具-选项-markdown-然后选择GitHub离线模式

```java
	AppApplication.getExecutorService().execute(new Runnable() {
                                           @Override
                                           public void run() {
                                               Create2QR2("{iotEPC:" + wb.getId() + "}", imgQr);
                                           }
                                       });
```

#分割线
***

#文字超链接
[百度一下](http://www.baudu.com "百度一下")

#图片（前面多加一个！）
![美女矢量素材](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566381348302&di=a2a1506f24315ba897c195dc57c30ed4&imgtype=jpg&src=http%3A%2F%2Fimg.kuqin.com%2Fupimg%2Fallimg%2F141212%2F2051192014-0.png)

#自动链接
<http://www.baudu.com>

#快捷键
```java
Ctrl + I ： 斜体
Ctrl + B ： 粗体
Ctrl + G ： 图片
Ctrl + Q ： 引用
Ctrl + 1 ： 标题 1
Ctrl + 2 ： 标题 2
Ctrl + 3 ： 标题 3
Ctrl + K ： 代码块
Ctrl + L ： 超链接
Ctrl + T ： 时间戳
Ctrl + U ： 无序列表
Ctrl + Shift + O ： 有序列表
Ctrl + R ： 水平标尺
F4 ： 启用水平布局/竖直预览
F5 ： 启用/关闭实时预览
F6 ： 在浏览器中预览
```
