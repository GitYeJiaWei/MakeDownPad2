#java中常见的几种异常

##java.lang.IndexOutOfBoundsException
该异常通常是指数组下标越界异常，例如：一个ArrayList数组中没有元素，而你想获取第一个元素,运行是就会报此类型的错误

##java.lang.NullPointerException
该异常的解释是"程序遇上了空指针"，简单地说就是调用了未经初始化的对象或者是不存在的对象，这个错误经常出现在创建图片，调用数组这些操作中，比如图片未经初始化，或者图片创建时的路径错误等等。

##java.lang.ClassNotFoundException

该异常的解释是“指定的类不存在”，这里主要考虑一下类的名称和路径是否正确即可，如果是在eclipse等开发工具下做的程序包，一般都是默认加上Package的，所以转到WTK下后要注意把Package的路径加上。

##java.lang.ArithmeticException

该异常的解释是“数学运算异常”，比如程序中出现了除以零这样的运算就会出这样的异常，对这种异常，要检查一下自己程序中涉及到数学运算的地方，公式是不是有不妥。

##java.lang.ArrayIndexOutOfBoundsException

该异常的解释是“数组下标越界”，现在程序中大多都有对数组的操作，因此在调用数组的时候一定要认真检查，看自己调用的下标是不是超出了数组的范围。

##java.lang.IllegalArgumentException

该异常的解释是“方法的参数错误”，很多J2ME的类库中的方法在一些情况下都会引发这样的错误，比如音量调节方法中的音量参数如果写成负数就会出现这个异常。

##java.sql.SQLException 

该异常的解释是“Sql语句执行异常”，由数据库管理系统抛出至服务器，应检查sql语句是否书写正确等。