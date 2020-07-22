package com.nn.dao.impl;

import com.nn.dao.OrderDao;
import com.nn.empty.Order;
import org.springframework.stereotype.Repository;

/**
 * @ClassName OrderDaoImpl
 * @Author nn
 * @Date 2020/7/22 11:23
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    private static int tag = 0 ;


    /**
     * 根据 id 查询 order
     *
     * @param id
     * @return
     */
    @Override
    public Order queryById(Integer id) {
//        System.out.println("volatile调用第 "+tag++ + "次");
//        synchronized (this){
//            System.out.println("调用第 "+tag++ + "次");
//        }
        try {
            Thread.sleep(10L);
            switch (id) {
                case 1:
                    return new Order(1, "B1234562", "998");
                case 2:
                    return new Order(2, "B1234564", "995");
                case 3:
                    return new Order(3, "B1234563", "994");
                case 4:
                    return new Order(4, "B1234566", "993");
                case 5:
                    return new Order(5, "B1234568", "991");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
