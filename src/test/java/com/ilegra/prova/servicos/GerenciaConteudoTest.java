package com.ilegra.prova.servicos;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GerenciaConteudoTest {

  private static List<String> linhas = new ArrayList<>();
  private static GerenciaConteudo gerenciaConteudo = new GerenciaConteudo();
  
  /** Executa antes da classe iniciar, montando o dataset de exemplo.
   */
  @BeforeClass
  public static void montaLista() {
    linhas.add("001ç1234567891234çPedroç50000");
    linhas.add("001ç3245678865434çPauloç40000.99");
    linhas.add("002ç2345675434544345çJose da SilvaçRural");
    linhas.add("002ç2345675433444345çEduardo PereiraçRural");
    linhas.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro");
    linhas.add("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");  
  }

  @Before
  public void registraLinhasAoIniciarTestes() {
    gerenciaConteudo.carregaNovosRegistros(linhas);
  }
  
  @After
  public void limpaLinhasAoFinalizarTestes() {
    gerenciaConteudo.limpaHistorico();
  }
    
  @Test
  public void testeConfereQuantidadeClientes() {
    assertEquals(gerenciaConteudo.getQtdeClientes(), Long.valueOf(2));
    gerenciaConteudo.carregaRegistro("002ç12345678998765çCaesar TesteçGovernamental");
    assertEquals(gerenciaConteudo.getQtdeClientes(), Long.valueOf(3));
  }

  @Test
  public void testeConfereQuantidadeVendedores() {
    assertEquals(gerenciaConteudo.getQtdeVendedores(), Long.valueOf(2));
    gerenciaConteudo.carregaRegistro("001ç96261846049çJoaoç30000");
    gerenciaConteudo.carregaRegistro("001ç96261846049çMariaç80000");
    assertEquals(gerenciaConteudo.getQtdeVendedores(), Long.valueOf(4));
  }

  @Test
  public void testeConfereMaiorVenda() {
    assertEquals(gerenciaConteudo.getMaiorVenda(), Long.valueOf(10));
    gerenciaConteudo.carregaRegistro("003ç13ç[1-15-85.9,2-35-2.90,3-41-3.55]çCaesar");
    assertEquals(gerenciaConteudo.getMaiorVenda(), Long.valueOf(13));
  }

  @Test
  public void testeConferePiorVendedor() {
    assertEquals(gerenciaConteudo.getPiorVendedor(), "Paulo");
    gerenciaConteudo.carregaRegistro("003ç13ç[1-1-5.5,2-5-1.1,3-3-2.1]çCaesar");
    gerenciaConteudo.carregaRegistro("003ç14ç[1-1-1.1,2-1-2.1]çCaesar");
    assertEquals(gerenciaConteudo.getPiorVendedor(), "Caesar");
  }

}