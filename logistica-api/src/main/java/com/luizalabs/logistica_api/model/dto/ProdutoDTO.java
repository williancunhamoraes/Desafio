package com.luizalabs.logistica_api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("value")
    private String value;

}
