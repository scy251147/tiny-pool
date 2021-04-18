# tiny-pool

### Yet another connection pool manager.

It's tiny, no need to import other dependencies, jar size totally less than 30kb.

### Usage of the pool

Am writing the rpc components and found that for the client connection, I need a connection pool to manage the connections. 
Then I wrote this tiny-pool structure in my spare time. It's simple and tiny, like usage below:
```java
ConnectionPool connectionPool = ConnectionPool.Builder(() -> new NettyConnection())
.setMaxTotal(2)
.setMaxIdle(2)
.setMinIdle(0)
.build();
```
Need to mention that, the NettyConnection component is your own defined connection class that implement IConnection class from tiny-pool.
You can define your own connection biz like what NettyConnection class does.

After we created the connection instance, then we can set the config parameters as what you want, the detailed parameters list below:

```java

/**
     * 连接池中最大的连接数，默认为8
     */
    private int maxTotal = 8;

    /**
     * 连接池中允许最大空闲的连接数，默认为8
     */
    private int maxIdle = 8;

    /**
     * 连接池中确保最少空闲的连接数，默认为0
     * 当启用空闲连接的有效性检测后，如果空闲连接无效，则销毁，如果连接数小于minidle数量，则新建连接，直至连接数达到minidle
     * minidle确保连接池中空闲连接的数量
     */
    private int minIdle = 0;

    /**
     * 连接池耗尽，调用者是否需要等待，true表示等待，false表示不等待
     * 为true的时候，maxWaitMillis才有效
     */
    private boolean blockWhenExhausted = true;

    /**
     * 连接池耗尽，调用者的最大等待时间，默认值为-1，表示永不超时
     * 注意，值为-1且blockWhenExhausted=true，连接将一直阻塞
     */
    private long maxWaitMillis = -1;

    /**
     * 从连接池借用连接的时候，是否做有效性检测（ping命令），如果连接无效，则移除。false代表不做检测，true代表作检测。
     * 在业务量小的应用场景，建议为true，在业务量大的应用场景，建议为false，少一次ping命令的开销，有助于提升性能。
     */
    private boolean testOnBorrow = false;

    /**
     * 向连接池归还连接的时候，是否做有效性检测（ping命令），如果连接无效，则移除。false表示不做检测，true代表作检测。
     * 业务量小的应用场景，建议为true，在业务量大的应用场景，建议为false，少一次ping命令的开销，有助于提升性能。
     */
    private boolean testOnReturn = false;

    /**
     * 用一个专门的线程对空闲的连接进行有效性检测，如果连接无效，则移除。
     * 注意，需要配置timeBetweenEvictionRunsMillis>0，否则将不生效
     */
    private boolean testWhileIdle = true;

    /**
     * 表示两次空闲连接扫描的活动之间，要睡眠的毫秒数，默认为30秒钟
     */
    private long timeBetweenEvictionRunsMillis = 30000;

    /**
     * 表示一个连接至少停留在空闲状态的最短时间，然后才能被空闲连接扫描线程进行有效性检测。
     * 需要timeBetweenEvictionRunsMillis>0才会生效
     */
    private long minEvictableIdleTimeMillis = 60000;
    
```
Lots of parameters here!! Remeber that we can set only part of the parameters, not all required.

After the  initialization of the connection pool, then we can borrow connection if need and return connection if complete the request.

```java
//从连接池中拿实例
IConnection connection = connectionPool.borrowConnection();
//将实例归还连接池
connectionPool.returnConnection(connection);
```

### Monitor of the pool 
I provide the ConnectionPoolMonitor class to monitor the pool, you can get the ```GenericObjectPool<IConnection> ``` instance to check what you want.

Any advice is appreciated. 