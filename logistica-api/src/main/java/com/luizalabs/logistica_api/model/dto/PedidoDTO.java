package com.luizalabs.logistica_api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("total")
    private String total;

    @JsonProperty("date")
    private String date;

    @JsonProperty("products")
    private List<ProdutoDTO> products;

}
