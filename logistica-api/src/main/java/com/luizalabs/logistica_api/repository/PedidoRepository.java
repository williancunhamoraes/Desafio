package com.luizalabs.logistica_api.repository;

import com.luizalabs.logistica_api.model.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
    List<PedidoEntity> findByOrderDateBetween(String dataInicio, String dataFim);
    List<PedidoEntity> findByOrderId(Long orderId);
}
