package com.ilegra.prova.tabelas;

public class Cliente implements Tabela {

  private final String cnpj;
  private final String nome;
  private final String areaNegocio;

  /** Construtor da entidade básica de cliente. */
  public Cliente(String cnpj, String nome, String areaNegocio) {
    this.cnpj = cnpj;
    this.nome = nome;
    this.areaNegocio = areaNegocio;
  }

  public String getCnpj() {
    return cnpj;
  }

  public String getNome() {
    return nome;
  }  

  public String getAreaNegocio() {
    return areaNegocio;
  }
  
}