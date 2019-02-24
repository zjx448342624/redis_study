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
6. 外网访问 ： 设置reids.conf 的配置
   1. ```protected-mode ``` 设置为no
   2. 注释掉```bind ```

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



##### redis 实现lua脚本

eval 脚本内容 keynumber key1,key2...  arg...

```eval "return redis.call('set',KEYS[1],ARGV[1])" 1 hello world```

KEYS[],ARGV[]：这两个是全局变量，表示入参，数组从1开始，必须大写

redis执行lua脚本 :```./redis-cli --eval "phone_limit.lua" phone:limit:18822112211 , 50 10```lua传值的时候必须前后带空格，在lua执行操作的时候是原子性的操作，在redis调用lua脚本的时候别的操作无法执行

在代码调用的时候可以让redis生成lua的摘要，缓存上lua的脚本，在以后的调用的时候就可以不用传输lua代码，但是reids在重启的时候lua会丢失。

##### redis 实现分布式锁

分布式锁可以通过数据库做，zookeeper做，缓存

redis 可以通过setnx来做分布式锁



##### redis 持久化机制

reids提供了两种持久化策略，一种是RDB，一种是AOF，这两种可以同时使用也可是使用其中一种，**如果同时使用，redis重启的时候会优先使用AOF文件来还原数据**

- RDB: 是按照规则定时把内存的数据同步到磁盘上，rdb是通过快照来完成的（snapshot）。redis会在指定的情况会处罚规则：

  1. 自己配置的快照规则：

     配置快照的规则多个快照规则是或的关系：save <seconds> <changes> :seconds：时间 changes: 数量

     - 这是指在一段seconds时间内更改的数量，如果满足那么就更新快照，默认的快照更新为：
       1. save 900 1:在900秒内更新的key的数量大于1 的时候，更新快照
       2. save 300 10：在300秒内更新的key的数量大于10的时候，更新快照
       3. save 60 10000：在60 秒内如果更新的key大于10000的时候，更新快照

  2. 执行seve或者bgsave的命令会触发一个快照

     1. save:执行内存的数据同步到磁盘的操作，这个操作会阻塞客户端的请求，如果内存的数据比较多的时候会很慢
     2. bgsave:后台执行内存的数据同步到磁盘的操作，这个操作不会阻塞客户端的请求

  3. 执行flushall的时候:清除内存的所有数据，只要快照的规则存在，那么redis会执行快照

  4. 执行复制的时候

  5. 快照的实现原理：redis 会使用fork函数赋值一份当前进程的副本（子进程），fork进程负责把内存的数据同步到磁盘的临时文件，父进程继续处理客户端请求

  6. 优缺点

     - 使用快照的可能会出现数据丢失，在第一次与快照与第二次快照中间（缺点）
     - 可以最大化redis的性能（优点），但是当 内存的数据比较多的时候fork进程会比较慢

- AOF:每次执行命令的后，会把命令本身存储到磁盘上，相当于是一个实时备份的方式，使用追加的操作，对性能会有影响

  aof开启：在redis.conf 配置文件修改	为```appendonly yes``` 	

  aof的日志文件中默认是记录所有的操作，如果对一个key进行多次操作的话，只需要保留最后一个就可以了，那么就可以对redis的aof的压缩策略进行修改，修改reids.conf 配置文件修改

  ```auto-aof-rewrite-percentage 100 表示当前aof文件大小超过上一次aof文件的百分之多少以后进行重写（优化），如果之前没有重写过，那么以启动时的aof文件大小为准```

  ``` auto-aof-rewrite-min-size 64mb 表示文件大小小于64m的时候不需要进行重写（优化）```

  aof重写的过程：reids 会自动在后台对aof文件进行重写，重写以后对最小命令的压缩，aof重写的整个过程是安全的，在重写的时候客户端仍然会发送请求，在用户发送请求后，会对现有的aof文件中追加命令，也会对新的aof临时文件去做重写，新的aof重写完以后会覆盖现有的aof文件，新发送的请求也会存在

  同步磁盘数据：redis每次更改数据的时候，aof机制都会讲命令记录到aof文件中，但是实际上由于操作系统的缓存机制，数据并没有实时写入到硬盘，而是进入到硬盘缓存中，再通过硬盘缓存机制去刷新到要保存的文件，可能会在硬盘缓存过程中发生数据丢失。

  appendfsync always 每次写入都会同步，最安全，性能最低
  appendfsync everysec 每一秒执行，默认的，既保证性能有保证安全性
  appendfsync no 由操作系统来执行，最快但是最不安全的

  在aof中被写入的时候宕机，那么aof文件就有可能发生错误，可以使用```redis-check-aof -fix```进行修复,修复的时候对源文件进行备份，如果错误的语法会被丢弃

- AOF和RDB的选择：如果数据的安全性比较高，那么可以使用两种，如果数据的某一段时间的丢失可以使用rdb



##### redis集群

- 复制（master,slave)

  master，slave模式可以再slave下再跟slave 用以保证数据的同步

  配置：在slave机器的redis.conf 中设置```slaveof ip port```  进入redis客户端通过  ``` info replication```可以查看reids主从的信息,从服务器的信息是只读的

  实现原理：

  - slave 第一次或者重连到master以后，会想master发送到一个sync的命令
  - master收到sync的时候，会做两件事
    1. 执行bgsvae（rdb的快照文件）
    2. master会把新收到的修改命令存入到缓存区
  - master 在执行完bgsave以后会把rdb的文件传送给slave，master新收到的数据会通过命令传送给slave

  复制数据的方式：

  1. 基于rdb方式的复制（第一次链接或者重连的时候），如果磁盘比较慢会有影响
  2. 无硬盘复制： ```repl-diskless-sync no``` 对该属性修改
  3. 增量复制：PSYNC master run.id， master判断run.id 是不是master的run.id，判断slave最后成功的偏移量和master是否相等，如果不想等就进行增量复制。

  master挂掉以后，没有对master进行动态选举，只可以进行读已有数据，不能进行写操作（缺点）

- 哨兵机制



- redis3.0以后的集群





##### 补充信息：分布式锁

多进程的架构：

1. 资源共享竞争问题
2. 数据的安全性

分布式锁的解决方案

1 怎么取获取数据

- 数据库：建立一个表，表中有methodName，mome（唯一约束），modifyTime。
  - 在插入一条数据的时候只要不报重复数据的错误，那么就可以获取到锁
  - 如果释放锁可以个根据删除的方式
  - 乐观锁和悲观锁
- zookeeper：向一个节点写入有序的操作，最小的节点获得锁，或则多个节点向写入固定的一个节点，谁写入谁获取锁
- redis：通过setnx来实现，如果写入成功，那么就可以获取锁。如果不成功等待













