package com.ilegra.prova.tabelas;

import java.math.BigDecimal;

public class Vendedor implements Tabela {

  private final String cpf;
  private final String nome;
  private final BigDecimal salario;

  /** Construtor da entidade básica de vendedor. */
  public Vendedor(String cpf, String nome, BigDecimal salario) {
    this.cpf = cpf;
    this.nome = nome;
    this.salario = salario;
  }

  public String getCpf() {
    return cpf;
  }

  public String getNome() {
    return nome;
  }  

  public BigDecimal getSalario() {
    return salario;
  }  
}