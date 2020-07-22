package com.nn.empty;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Order
 * @Author nn
 * @Date 2020/7/22 11:16
 */
@Data
public class Order implements Serializable {

    private Integer id ;
    private String orderNu;
    private String money;

    public Order() {
    }

    public Order(Integer id, String orderNu, String money) {
        this.id = id;
        this.orderNu = orderNu;
        this.money = money;
    }


}
