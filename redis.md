# redis

##### 简介

redis是一个开源的高性能的基于键值对的数据存储系统，通过存储不同类型的数据来实现不同的场景

存储结构类型：

- 字符类型：
- 散列类型：
- 列表类型
- 集合类型：
- 有序集合：

这五种数据类型提供了丰富的应用类型

##### 实现功能

- 可以为每个key设置过期时间
- 可以通过列表类型来实现分布式队列的操作
- 支持发布订阅的消息模式

##### 操作简单

- 提供了很多命令与redis提供交换

##### 应用场景

- 数据缓存（商品数据、新闻、热点新闻）
- 单点登陆
- 秒杀、抢购
- 网站访问排名
- 应用的模块开发



##### redis 安装

1. 下载安装包
2. tar -zxvf tar.gz
3. 在redis的目录上执行make malloc=libc 
4. 可以通过make test 测试编译状态
5. make [prefix=/地址] install 完成安装



##### reids 启动与退出

1. 拷贝文件redis.conf到redis安装目录
2. 执行./redis-server ../redis.conf
3. 配置后台启动daemonize no 改为yes
4. ./redis-cli shutdown 退出
5. 访问redis
   1. 本地redis：./redis-cli
   2. 访问远程服务 ./redis-cli -h ip -p -6379



##### redis 其他命令

redis-server 启动服务

redis-cli 访问redis的控制台

reids-benchmark 性能测试的工具

redis-check-aof aof文件进行检查的工具

redis-check-dump rdb文件检查工具

redis-sentinel sentinel服务器配置



##### redis 多数据库

redis默认是16个数据库，reids 的多数据可以理解为一个命名空间，数据库是从0-15，默认链接的数据库是0 ，可以通过reids.conf 去配置

reids 和关系型数据库的差别

1. redis不支持自定义数据库名称
2. 每个数据库不能单独设置授权
3. 每个数据库之间不是完全隔离的，可以通过flushall命令清空redis实例种的所有数据库中的数据
4. 在使用的时候也可以通过select dbid 的方式去切换不同的数据库



##### 使用入门

1. 获得一个符合匹配规则的键名列表

   kyes  pattern[?/*/[]]

   keys zjx:hobby

2.  判断一个键是否存在 exists key

3. type key 获取一个key的数据结构类型



##### 数据结构的使用

- 字符类型：一个默认的key最大的容量是512M
  - get key /set key value [ex second] : 赋值/取值
  - incr key  ：递增数字，做一个原子递增
    - 短信重发机制：sms:limit:mobile 设置一个过期时间
  -  incryby key increment：递增指定的数
  - decr ：递减
  -  append  key value：向指定的key追加信息
  -  strlen key：获得字符串的对应的value的长度
  - mget key1,key2,key3：获得多个键值
  - mset key/value key/value key/value：设置多个键值
  - setnx: 如果存在那么就不会操作，如果不存在那就设置值，并且返回1（可以用作分布式锁）
- 列表类型：list可以存储一个有序的字符串列表，可以像列表两边添加数据，也可以获得指定范围的数据，列表的实现是一个双向链表
  - 向一个列表中存放字符串
    - lpush key value value ： 向一个列表中存放字符串，存放在左边，可以存放多个value
    - rpush key value value ：向一个列表中存放字符串，存放在右边
  - 从列表中取数据:可以实现分布式消息队列
    - lpop:从左边list弹出一个value，弹出以后list就不会有该数据了
    - rpop:从右边list弹出一个value，弹出以后list就不会有该数据了
  - llen key： 获取列表的长度
  - 获取列表的范围
    - lrange key star end ：获取列表范围，如果是-1那么就是右边最大的值
    - rrange key star end ：获取列表范围，如果是-1那么就是左边最大的值
  - lrem key count value:删除list的key的值，count是删除多少个
  - lset key index value: 设置指定位置的值
- 散列类型：散列类型就是hash，也是存这key，value，不支持数据类型的嵌套，比较适合存储对象
  - hget key field/hset key field：hget key/hset key 是一个固定语法
  - hmset key field value field2 value2 : 可以设置多个值一次性
  - hmget key field field2:一次性获取多个值
  - hgetall key: 可以获取所有的值，field 和value 
  - hexists key field: 判断字段是否存在，存在返回1，不存在返回0
  - hincryby key field : 原子递增
  - hsetnx key field: 如果存在则不做操作
  - hdel key field[field...]：删除一个或多个字段
- 集合类型：集合和列表的差距是集合不能存在重复的值，而且集合是无序的操作
  - sadd key member[member ...]：增加一个或多个数据，返回成功加入的数量，如果增加的值已经存在，那么自动忽略
  - srem key member[member ...] : 删除数据一个或多个
  - smembers key: 获取集合内所有的数据
  - sdiff key1 key2 : 对多个集合进行差集运算操作
  - sunion key1 key2：对多个集合进行并集运算凑走
- 有序集合：对集合中的数据进行排序
  - zadd key score member[score member]: 增加一个数
  - zrange key star end  [withscores]: 范围查找集合中的数据 ，加上withscores可以获取元素的score,如果两个元素的score数一样的话根据[0<9<A<Z<a<z]排序从小到大，可以用来做热搜，排名



##### key 的设计

- 对象类型：对象  id：对象属性：对象子属性 （key 的长度不要太长，key太长对内存消耗较大）
- 对key进行分类，同步在公司的统一管理系统中



##### redis 的事物处理

reids 通过把多组操作放在一个队列中，要不全部成功，要不全部不成功。通过multi 开启，exec执行，redis的事物在有些时候是不会回滚的。如果是在运行过程中是报错的，那么就无法回滚。



##### 过期时间

设置数据缓存的时候，可以使用设置过期时间，发送短信注册的时候可以通过redis进行设置

- expire key second: 可以对key设置一个过期的时间，second是秒的时间。
- ttl key： 可以获得key的过期的时间



##### 发布订阅

redis 主动提供了一个pub/sub 的模型，用的比较少，功能不太稳定

- publish channel message: 发布消息
- subscribe channel : 订阅消息



##### redis 实现分布式锁

分布式锁可以通过数据库做，zookeeper做，缓存

redis 可以通过setnx来做分布式锁













