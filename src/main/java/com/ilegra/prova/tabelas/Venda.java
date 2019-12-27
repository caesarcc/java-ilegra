package com.ilegra.prova.tabelas;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class Venda implements Tabela {

  private final Long id;
  private final List<VendaItem> itens;
  private final String nomeVendedor;

  /** Construtor da entidade básica da venda. */
  public Venda(Long id, List<VendaItem> itens, String nomeVendedor) {
    this.id = id;
    this.nomeVendedor = nomeVendedor;
    this.itens = itens;
  }

  public Long getId() {
    return id;
  }

  public String getNomeVendedor() {
    return nomeVendedor;
  }  

  public List<VendaItem> getItens() {
    return Collections.unmodifiableList(itens);
  }

  /** Valor total da venda. */
  public BigDecimal getValorTotalCalculado() {
    return itens.stream()
        .map((item) -> item.getPrecoCalculado())
        .reduce((itemA, itemB) -> itemA.add(itemB)).get();
  }
  
}