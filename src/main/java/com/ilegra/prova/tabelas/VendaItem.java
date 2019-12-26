package com.ilegra.prova.tabelas;

import java.math.BigDecimal;

public class VendaItem {

  private Long id;
  private Long quantidade;
  private BigDecimal precoUnitario;

  /** Construtor da entidade básica de item de venda. */
  public VendaItem(Long id, Long quantidade, BigDecimal precoUnitario) {
    this.id = id;
    this.quantidade = quantidade;
    this.precoUnitario = precoUnitario;
  }
  
  public Long getId() {
    return id;
  }

  public Long getQuantidade() {
    return quantidade;
  }

  public BigDecimal getPrecoUnitario() {
    return precoUnitario;
  }

  public BigDecimal getPrecoCalculado() {
    return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
  }  
}