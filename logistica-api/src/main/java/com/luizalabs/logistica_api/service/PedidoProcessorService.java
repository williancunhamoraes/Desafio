package com.luizalabs.logistica_api.service;

import com.luizalabs.logistica_api.model.PedidoEntity;
import com.luizalabs.logistica_api.model.PedidoLinha;
import com.luizalabs.logistica_api.model.dto.PedidoDTO;
import com.luizalabs.logistica_api.model.dto.ProdutoDTO;
import com.luizalabs.logistica_api.model.dto.UsuarioPedidoDTO;
import com.luizalabs.logistica_api.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoProcessorService {

    private final PedidoRepository repository;

    public List<UsuarioPedidoDTO> processarArquivo(MultipartFile file) throws IOException {
        List<String> linhas = new BufferedReader(new InputStreamReader(file.getInputStream())).lines().toList();

        List<PedidoLinha> dados = linhas.stream()
                .map(this::parseLinha)
                .toList();

        List<PedidoEntity> entidades = dados.stream().map(linha -> new PedidoEntity(
                null,
                Long.valueOf(linha.getUserId()),
                linha.getUserName(),
                Long.valueOf(linha.getOrderId()),
                linha.getOrderDate(),
                Long.valueOf(linha.getProductId()),
                linha.getProductValue()
        )).toList();
        repository.saveAll(entidades);

        return agruparPorUsuario(entidades);
    }

    public List<UsuarioPedidoDTO> filtrarPorData(String dataInicio, String dataFim) {
        List<PedidoEntity> filtrados = repository.findByOrderDateBetween(dataInicio.replace("-", ""),
                dataFim.replace("-", ""));
        return agruparPorUsuario(filtrados);
    }

    public Optional<PedidoDTO> buscarPorOrderId(Long orderId) {
        List<PedidoEntity> pedidos = repository.findByOrderId(orderId);
        if (pedidos.isEmpty()) return Optional.empty();

        List<ProdutoDTO> produtos = pedidos.stream().map(p -> new ProdutoDTO(
                p.getProductId(),
                p.getProductValue()
        )).toList();

        double total = produtos.stream().mapToDouble(pr -> Double.parseDouble(pr.getValue())).sum();

        PedidoDTO dto = new PedidoDTO(
                orderId,
                String.format("%.2f", total),
                formatarData(pedidos.get(0).getOrderDate()),
                produtos
        );

        return Optional.of(dto);
    }

    private List<UsuarioPedidoDTO> agruparPorUsuario(List<PedidoEntity> entidades) {
        return entidades.stream()
                .collect(Collectors.groupingBy(PedidoEntity::getUserId))
                .entrySet().stream()
                .map(entry -> {
                    List<PedidoEntity> lista = entry.getValue();
                    Map<Long, List<PedidoEntity>> agrupadoPorPedido = lista.stream()
                            .collect(Collectors.groupingBy(PedidoEntity::getOrderId));

                    List<PedidoDTO> pedidos = agrupadoPorPedido.entrySet().stream().map(e -> {
                        List<ProdutoDTO> produtos = e.getValue().stream()
                                .map(p -> new ProdutoDTO(p.getProductId(), p.getProductValue()))
                                .toList();
                        double total = produtos.stream().mapToDouble(p -> Double.parseDouble(p.getValue())).sum();
                        String data = formatarData(e.getValue().get(0).getOrderDate());
                        return new PedidoDTO(e.getKey(), String.format("%.2f", total), data, produtos);
                    }).toList();

                    return new UsuarioPedidoDTO(entry.getKey(), lista.get(0).getUserName(), pedidos);
                })
                .toList();
    }

    private PedidoLinha parseLinha(String linha) {
        return new PedidoLinha(
                limparZeros(linha.substring(0, 10)),
                linha.substring(10, 55).trim(),
                limparZeros(linha.substring(55, 65)),
                limparZeros(linha.substring(65, 75)),
                linha.substring(75, 87).trim(),
                linha.substring(87, 95)
        );
    }

    private String limparZeros(String valor) {
        String limpo = valor.replaceFirst("^0+", "");
        return limpo.isEmpty() ? "0" : limpo;
    }

    private String formatarData(String yyyymmdd) {
        return yyyymmdd.substring(0, 4) + "-" + yyyymmdd.substring(4, 6) + "-" + yyyymmdd.substring(6);
    }
}