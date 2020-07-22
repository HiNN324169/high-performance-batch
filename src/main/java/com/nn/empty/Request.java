package com.nn.empty;

import lombok.Data;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName Request
 * @Author nn
 * @Date 2020/7/22 12:32
 */
@Data
public class Request {

    private String serialNo;
    private CompletableFuture<Order> future;
    private Integer id;
}
