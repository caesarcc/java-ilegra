package com.ilegra.prova.tabelas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClienteTest {

  @Test
  public void testeClienteInstanciadoComSucesso() {
    Cliente clienteTarget = new Cliente("2345675434544345","Eduardo Pereira","Rural");
    
    Cliente cliente = (Cliente) TabelaFactory.CLIENTE.gera(String.format("002ç%sç%sç%s", 
        clienteTarget.getCnpj(), clienteTarget.getNome(), clienteTarget.getAreaNegocio()));
        
    assertEquals(clienteTarget.getCnpj(), cliente.getCnpj());
    assertEquals(clienteTarget.getNome(), cliente.getNome());
    assertEquals(clienteTarget.getAreaNegocio(), cliente.getAreaNegocio());
  }
}