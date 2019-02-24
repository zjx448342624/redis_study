# lua

##### lua 脚本

1. lua语言：是一个轻量级的脚本语言，可以嵌套在应用系统中为系统提供服务。

- 好处：lua脚本一次性可以执行多条语言
  - 减少网络开销
  - 原子操作
  - 复用性

1. lua语言在redis中的应用

2. 动态类型的语言：

   - 全局变量：(a = 1)，局部变量 ：(locla b = 2)
   - 逻辑表达式： + 、-、& 、/
   - 逻辑表达式：==（等于），~=（不等于），>=,<=
   - 逻辑运算符： and （且）/or（或）/ not (反)

   ```
   if expression then 
   elseif expression then 
   else 
   end
   
   while expression do
   end
   
   for i=1, 100 do 
   end
   
   local xx={'a','b','c','d'}
   for i,v in inpairs(xx) do end
   ```

   - 注释： --（单行注释），--[[

     ssssdddd]](多行注释)

   - 函数： function xxx(a,d) end,print(xxx(a,d)) (调用)

   - 连接两个字符串： ..

     > a='hello'
     > b='world'
     > print(a..b)
     > helloworld

   - 计算长度：#

   - 调用redis的命令： redis.call("set",key,value)

##### lua脚本安装

1. 下载安装包
2. tar -zxvf .tar.gz
3. make linux （报错）然后执行```yum install libtermcap-devel ncurses-devel libevent-devel readline-devel ```
4. 安装成功，执行```lua ``` 进入lua操作页面

