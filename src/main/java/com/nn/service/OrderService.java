package com.nn.service;

import com.nn.empty.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName OrderService
 * @Author nn
 * @Date 2020/7/22 11:18
 */
@Service
public interface OrderService {


    /**
     * 根据 id 查询订单
     *
     * @param id
     * @return order
     */
    Order queryById(Integer id) throws ExecutionException, InterruptedException;


    /**
     * 批量 查询
     *
     * @param paramList
     * @return
     */
    List<Map<String, Object>> queryByIdBatch(List<Map<String, Object>> paramList);
}
