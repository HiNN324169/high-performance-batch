package com.nn.dao;

import com.nn.empty.Order;
import org.springframework.stereotype.Repository;

/**
 * @ClassName OrderDao
 * @Author nn
 * @Date 2020/7/22 11:22
 */
@Repository
public interface OrderDao {

    /**
     *  根据 id 查询 order
     * @param id
     * @return
     */
    Order queryById(Integer id);
}
