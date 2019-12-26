package com.ilegra.prova.servicos;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProcessaArquivoItTest {

  private static final String NOME_ARQUIVO_TESTE = "teste.txt";
  private static Path arquivo;
  private static Path arquivoOut;
  
  /**
   * Executa antes de cada teste para configurar a entrada e saida.
   */
  @Before
  public void configurarArquivoEntrada() throws IOException {
    File arquivoIn = new File(System.getProperty("user.home")
        .concat(File.separator).concat("data"));
    if (!arquivoIn.exists()) {
      arquivoIn.mkdir();
    }
    arquivoIn = new File(arquivoIn.getPath().concat(File.separator).concat("in"));
    if (!arquivoIn.exists()) {
      arquivoIn.mkdir();
    }
    arquivo = Paths.get(arquivoIn.getPath()
        .concat(File.separator).concat(NOME_ARQUIVO_TESTE)); 
    try (BufferedWriter writer = Files.newBufferedWriter(arquivo)) {
      writer.write("001ç1234567891234çPedroç50000");
    }
  }

  @Test
  public void testeArquivoProcessadoComSucesso() {
    arquivoOut = ProcessaArquivoSingleton.getInstance().processa(arquivo);
    assertTrue(arquivoOut.toFile().exists());
  }

  @Test
  public void testeArquivoProcessadoComSucessoSemPastaDeSaida() {
    File diretorioOut = new File(System.getProperty("user.home")
        .concat(File.separator).concat("data")
        .concat(File.separator).concat("out"));
    if (diretorioOut.exists()) {
      diretorioOut.delete();
    }    
    arquivoOut = ProcessaArquivoSingleton.getInstance().processa(arquivo);
    assertTrue(arquivoOut.toFile().exists());
  }

  @Test(expected = RuntimeException.class)
  public void testeNaoPodeProcessarArquivoRemovido() {
    new File(arquivo.toString()).delete();
    ProcessaArquivoSingleton.getInstance().processa(arquivo);
  }

  /** Executa ao final de cada teste para limpar arquivo gerado. 
  */   
  @After
  public void destruirArquivos() {
    if (arquivoOut != null && arquivoOut.toFile().exists()) {
      arquivoOut.toFile().delete();
    }
  }

}