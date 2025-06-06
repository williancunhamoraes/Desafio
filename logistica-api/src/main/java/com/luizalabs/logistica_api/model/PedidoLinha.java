package com.luizalabs.logistica_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoLinha {
    private String userId;
    private String userName;
    private String orderId;
    private String productId;
    private String productValue;
    private String orderDate;
}
