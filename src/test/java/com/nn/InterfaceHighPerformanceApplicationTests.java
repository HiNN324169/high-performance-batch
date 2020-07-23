package com.nn;

import com.nn.dao.OrderDao;
import com.nn.empty.Order;
import com.nn.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.*;

@SpringBootTest
class InterfaceHighPerformanceApplicationTests {

    @Test
    void contextLoads() {
    }



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
                    countDownLatch.countDown();
                    countDownLatch.await();
                    Order order = orderService.queryById(5);
//                    System.out.println(order.toString());

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        // 让程序 不停止
        System.in.read();
    }

}
