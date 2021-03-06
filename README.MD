# 批量 请求

```text
问题引出：
用户  --->  controller  ----> service ---(rpc,http)--> 远程接口
用户同类型请求，请求 一次，服务后端 调用一次远程接口，用户调用1万次，而后端就要调用一万次，频繁的创建http链接非常耗时，
若用户请求数据大，垃圾回收不及时，导致服务器压力剧增OOM，占用磁盘空间，系统崩溃。


解决思路：
将多个用户发送的请求 任务 保存存到 队列中，然后内部使用定时任务，间隔时间到队列中获取任务进行进行组装拼接，
调用远程批处理接口（远程服务端 需要 提供批量请求接口）处理，从而达到减少请求次数
```
## 知识点1
### java 模拟 多用户并发请求

- 模拟 1000 用户 同时请求

`知识点：CountDownLatch`
```
// 用户线程数（模拟用户量）
    private static final int MAX_THREADS = 1000;
    private CountDownLatch countDownLatch = new CountDownLatch(MAX_THREADS);
    @Autowired
    private OrderService orderService;

    @Test
    void orderTest() throws IOException {
        for (int i = 0; i < MAX_THREADS; i++) {
            Thread t =  new Thread(()->{

                try {
                    // 当 MAX_THREADS 减为 0 时，内部 会 唤醒 所有线程 去执行任务
                    // 从而 实现 并发执行
                    countDownLatch.countDown();
                    // 阻塞线程
                    countDownLatch.await();
                    Order order = orderService.queryById(5);
                    System.out.println(order.toString());

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        // 让程序 不停止
        System.in.read();
    }
```


## 知识点2
### 注解的使用：@PostConstruct
特点：

1、只有一个非静态方法能使用此注解

2、被注解的方法不得有任何参数

3、被注解的方法返回值必须为void

4、被注解方法不得抛出已检查异常

5、此方法只会被执行一次

被 @PostConstruct 注解修饰过的方法 会在 系统系统的时候自动执行。

## 知识点3 定时任务
```
 // 创建定时任务线程池
ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

/**
 *  参数一：需要执行的任务
 *  参数二：执行run 方法 时 延时 0 毫秒
 *  参数三：间隔 10 毫秒 执行 任务
 *  参数四：时间单位
 */
executorService.scheduleAtFixedRate()
```


## 知识点4 队列

```text
// 有界队列，默认：Integer.Max_size
LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

// 指定 队列大小
LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>(100);

加入队列
add(); // 当 队列 容量不够时，会抛出 java.lang.IllegalStateException: Queue full 异常

queue.put() // 当 队列 容量不够时，调用该方法 会阻塞，直到 队列容量可用

```
