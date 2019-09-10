#idea配置github创建本地仓库并上传项目

- 在IDEA中设置Git，在File-->Setting->Version Control-->Git-->Path to Git executable选择你的git安装后的git.exe文件，然后点击Test，测试是否设置成功

![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/1.png)

- 在IDEA中设置GitHub，File-->Setting->Version Control-->GibHub

　　Host：github.com

　　Token：点击Create API Token，输入在github中注册的用户名和密码生成token

　　点击Test，测试是否连接成功
![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/2.png)

- 创建本地仓库，VCS-->Import into Version Control-->Create Git Repository...
![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/3.png)

- 在弹框中选中项目所在的位置，点击OK，此时项目文件全部变成红色（若选中其他位置，则git-->add不可点选，不知为何）
![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/4.png)

- 上传项目到本地仓库，项目右键选择Git-->add，此时项目文件变成绿色，此时文件只是处于暂存区，并没有真正进入到版本库中
![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/5.png)

 ![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/6.png)

- 项目右键Git--> Commit Directory，在弹窗中输入Commit Message，点击commit，此时项目文件从暂存区真正进入版本库中，项目文件变成白色
![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/7.png)

 ![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/8.png)

 ![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/9.png)

- 上传项目到GitHub中，VCS-->Import into Version Control-->Share Project on GitHub，在弹框中输入仓库名和描述，点击Share，即可是上传，中间会弹窗输入GitHub的用户名和密码（已输入过用户名和密码并记住的不会再次弹框输入），上传成功后IDEA右下角会给出提示

![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/10.png)

![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/11.png)

![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/12.png)

- 提交修改文件到GitHub

　　新增文件（红色），右键-->Git-->add，将新增的文件加入本地仓库，此时文件变绿色

　　修改文件（蓝色）

　　在项目右键-->Git-->Commit Directory，查看有变动的文件并输入Commit Message，点击Commit and Push...

　　提交后会进行语法检查，若存在错误或警告会给出确认提示，点击Commit，弹出Push框，点击Push，上传GitHub成功
![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/13.png)

![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/14.png)

![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/15.png)

![](https://raw.githubusercontent.com/GitYeJiaWei/MakeDownPad2/master/Idea%E4%B8%8A%E4%BC%A0%E9%A1%B9%E7%9B%AE/16.png)