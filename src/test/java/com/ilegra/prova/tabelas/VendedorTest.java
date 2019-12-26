package com.ilegra.prova.tabelas;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class VendedorTest {

  public VendedorTest() {
  }

  @Test
  public void testeVendedorInstanciadoComSucesso() {
    Vendedor vendedorTarget = new Vendedor("3245678865434", "Paulo", BigDecimal.valueOf(40000.99));
    
    Vendedor vendedor = (Vendedor) TabelaFactory.VENDEDOR.gera(String.format("001ç%sç%sç%s", 
        vendedorTarget.getCpf(), vendedorTarget.getNome(), vendedorTarget.getSalario()));
        
    assertEquals(vendedorTarget.getCpf(), vendedor.getCpf());
    assertEquals(vendedorTarget.getNome(), vendedor.getNome());
    assertEquals(vendedorTarget.getSalario(), vendedor.getSalario());
  }
}