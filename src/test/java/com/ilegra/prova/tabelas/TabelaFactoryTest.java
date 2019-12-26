package com.ilegra.prova.tabelas;

import static org.junit.Assert.assertEquals;

import java.security.InvalidParameterException;

import org.junit.Test;

public class TabelaFactoryTest {
    
  @Test
  public void testeRegistroVendedorIdentificado() {
    TabelaFactory tabelaFactory = TabelaFactory.getTabela("001");
    assertEquals(TabelaFactory.VENDEDOR, tabelaFactory);
  }

  @Test
  public void testeRegistroClienteIdentificado() {
    TabelaFactory tabelaFactory = TabelaFactory.getTabela("002");
    assertEquals(TabelaFactory.CLIENTE, tabelaFactory);
  }

  @Test
  public void testeRegistroVendaIdentificado() {
    TabelaFactory tabelaFactory = TabelaFactory.getTabela("003");
    assertEquals(TabelaFactory.VENDA, tabelaFactory);
  }

  @Test(expected = InvalidParameterException.class)
  public void testeNaoDeveIdentificarRegistro() {
    TabelaFactory.getTabela("004");
  }
  
}