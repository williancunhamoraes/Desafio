package com.luizalabs.logistica_api.controller;

import com.luizalabs.logistica_api.model.dto.PedidoDTO;
import lombok.RequiredArgsConstructor;
import com.luizalabs.logistica_api.model.dto.UsuarioPedidoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.luizalabs.logistica_api.service.PedidoProcessorService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoProcessorService service;

    @PostMapping("/processar")
    public ResponseEntity<List<UsuarioPedidoDTO>> processar(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(service.processarArquivo(file));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<UsuarioPedidoDTO>> filtrarPorData(@RequestParam String dataInicio, @RequestParam String dataFim) {
        return ResponseEntity.ok(service.filtrarPorData(dataInicio, dataFim));
    }

    @GetMapping("/pedido/{orderId}")
    public ResponseEntity<PedidoDTO> buscarPorOrderId(@PathVariable Long orderId) {
        return service.buscarPorOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
