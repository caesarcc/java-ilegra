package com.ilegra.prova.tabelas;

public class Cliente implements Tabela {

  private String cnpj;
  private String nome;
  private String areaNegocio;

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