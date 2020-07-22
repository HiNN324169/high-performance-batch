package com.nn.service.impl;

import com.nn.dao.OrderDao;
import com.nn.empty.Order;
import com.nn.empty.Request;
import com.nn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;

/**
 * @ClassName OrderServiceImpl
 * @Author nn
 * @Date 2020/7/22 11:21
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    private LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    /**
     * 根据 id 查询订单
     *
     * @param id
     * @return order
     */
    @Override
    public Order queryById(Integer id) throws ExecutionException, InterruptedException {

        // 标识
        String serialNo = UUID.randomUUID().toString();

        // 结果 监听器
        CompletableFuture<Order> future = new CompletableFuture<>();

        // 封装请求参数
        Request request = new Request();
        request.setFuture(future);
        request.setId(id);
        request.setSerialNo(serialNo);

        queue.add(request);

        // 不断监听 自己的线程有没有返回结果，阻塞式
        return future.get();
//        return orderDao.queryById(id);
    }

    /**
     * 在 系统启动的时候 会执行
     */
    @PostConstruct
    public void init(){

        // 定时任务
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        /**
         *  参数一：需要执行的任务
         *  参数二：执行run 方法 时 延时 0 毫秒
         *  参数三：间隔 10 毫秒 执行 任务
         *  参数四：时间单位
         */
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int size = queue.size();
                if (size == 0){
                    System.out.println("没事干哟。。。。。。");
                    return;
                }

                List<Map<String, Object>> list = new ArrayList<>();
                List<Request> requests = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    // 弹出 队列中的数据
                    Request request = queue.poll();
                    Map<String, Object> map = new HashMap<>();
                    map.put("serialNo",request.getSerialNo());
                    map.put("id",request.getId());
                    list.add(map);
                    requests.add(request);
                }

                System.out.println("批量处理的数据量为："+ size);
                // 调用 批量请求接口
                List<Map<String, Object>> result = queryByIdBatch(list);
                for (Request req:requests
                     ) {
                    String serialNo = req.getSerialNo();
                    for (Map<String, Object> re: result
                    ) {
                        if(serialNo.equals(re.get("serialNo").toString())){
                            req.getFuture().complete((Order)re.get("order"));
                            break;
                        }

                    }
                }

            }
        },0,10,TimeUnit.MILLISECONDS);

    }

    /**
     * 批量 查询
     *
     * @param paramList
     * @return
     */
    @Override
    public List<Map<String, Object>> queryByIdBatch(List<Map<String, Object>> paramList) {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Map<String, Object> map :
                paramList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("serialNo",map.get("serialNo"));
            hashMap.put("order",orderDao.queryById(Integer.valueOf(map.get("id").toString().trim())));
            result.add(hashMap);
        }
        return result;
    }

}
