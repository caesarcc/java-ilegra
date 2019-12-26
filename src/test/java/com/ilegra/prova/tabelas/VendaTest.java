package com.ilegra.prova.tabelas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

public class VendaTest {

  @Test
  public void testeVendaInstanciadaComSucesso() {
    Venda vendaTarget = new Venda(8L, Collections.emptyList(), "Paulo");

    Venda venda = (Venda) TabelaFactory.VENDA.gera(
        String.format("003ç%sç[1-1-1]ç%s", vendaTarget.getId(), vendaTarget.getNomeVendedor()));

    assertEquals(vendaTarget.getId(), venda.getId());
    assertEquals(vendaTarget.getNomeVendedor(), venda.getNomeVendedor());
    assertTrue(vendaTarget.getItens().isEmpty());
    assertTrue(venda.getItens().size() == 1);
  }

  @Test
  public void testeVendaComItensInstanciadaComSucesso() {
    List<VendaItem> listItens = new ArrayList<>();
    listItens.add(new VendaItem(1L, 10L, new BigDecimal("100")));
    listItens.add(new VendaItem(2L, 30L, new BigDecimal("2.50")));
    listItens.add(new VendaItem(3L, 40L, new BigDecimal("3.10")));

    Venda vendaTarget = new Venda(10L, listItens, "Pedro");

    Venda venda = (Venda) TabelaFactory.VENDA
        .gera(String.format("003ç%sç[1-10-100,2-30-2.50,3-40-3.10]ç%s", 
        vendaTarget.getId(), vendaTarget.getNomeVendedor()));

    assertTrue(venda.getItens().size() == 3);

    venda.getItens().forEach(item -> {
      Optional<VendaItem> itemTarget = vendaTarget.getItens().stream()
          .filter(itemT -> itemT.getId().equals(item.getId())).findFirst();
      if (itemTarget.isPresent()) {
        assertEquals(itemTarget.get().getId(), item.getId());
        assertEquals(itemTarget.get().getQuantidade(), item.getQuantidade());
        assertEquals(itemTarget.get().getPrecoUnitario(), item.getPrecoUnitario());
      } else {
        fail("Item não encontrado.");
      }
    });
  }
  
  @Test
  public void testeVendaValorCalculadoDoItemCorreto() {
    List<VendaItem> listItens = new ArrayList<>();
    listItens.add(new VendaItem(1L, 13L, BigDecimal.valueOf(3.75)));
    BigDecimal valorTarget = listItens.get(0).getPrecoCalculado();
    Venda vendaTarget = new Venda(1L, listItens, "Pedro");
    
    Venda venda = (Venda) TabelaFactory.VENDA.gera(
        String.format("003ç%sç[1-13-3.75]ç%s", 
        vendaTarget.getId(), vendaTarget.getNomeVendedor()));
        
    assertEquals(valorTarget, venda.getItens().get(0).getPrecoCalculado());
  }  
}