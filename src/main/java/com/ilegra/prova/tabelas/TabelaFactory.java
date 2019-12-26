package com.ilegra.prova.tabelas;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TabelaFactory {

  VENDEDOR("001") {
    @Override
    public Tabela gera(final String linha) {
      final String[] registro = linha.split("ç");
      return new Vendedor(registro[1], registro[2], new BigDecimal(registro[3]));
    }
  },
  CLIENTE("002") {
    @Override
    public Tabela gera(final String linha) {
      final String[] registro = linha.split("ç");
      return new Cliente(registro[1], registro[2], registro[3]);
    }
  },
  VENDA("003") {
    @Override
    public Tabela gera(final String linha) {
      final String[] registro = linha.split("ç");
      return new Venda(new Long(registro[1]), geraItens(registro[2]), registro[3]);
    }
  };

  private final String codigo;

  public abstract Tabela gera(final String linha);

  TabelaFactory(final String codigo) {
    this.codigo = codigo;
  }

  public String getCodigo() {
    return this.codigo;
  }

  protected List<VendaItem> geraItens(String grupoItens) {
    String[] listaItens = grupoItens.replaceAll("[\\[\\]]", "").split(",");
    
    List<String[]> itens = Arrays.stream(listaItens)
        .map(item -> item.split("-")).collect(Collectors.toList());
  
    return itens.stream()
        .map(item -> new VendaItem(Long.valueOf(item[0]), 
            Long.valueOf(item[1]), new BigDecimal(item[2])))
        .collect(Collectors.toList());
  }

  /** Obtem o enum pelo código do cabeçalho do arquivo. */
  public static TabelaFactory getTabela(final String codigo) {
    return Arrays.stream(TabelaFactory.values())
      .filter(tabela -> tabela.getCodigo().equals(codigo))
      .findFirst()
      .orElseThrow(() -> new InvalidParameterException("Tipo de Registro Inválido"));
  }
}