package com.ilegra.prova.servicos;

import static org.junit.Assert.assertTrue;

import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProcessaArquivoItTest {

  private static final String NOME_ARQUIVO_TESTE = "itTest.txt";
  private static File arquivo;
  private static File diretorioOut;

  public ProcessaArquivoItTest() {
  }

  /** Executa antes de cada teste para configurar a entrada e saida.
  */   
  @Before
  public void configuraEntradaSaida() {
    arquivo = new File(ProcessaArquivoItTest.class.getClassLoader()
        .getResource(NOME_ARQUIVO_TESTE).getFile());
    diretorioOut = new File(arquivo.getParent().toString()
        .concat(File.separator).concat("out"));
    if (!diretorioOut.exists()) {
      diretorioOut.mkdir();
    }
  }

  @Test
  public void arquivoProcessadoComSucesso() {
    ProcessaArquivo processo = new ProcessaArquivo(
        arquivo.getParent().toString(), arquivo.toPath());
    processo.run();
    assertTrue(diretorioOut.toPath().resolve(NOME_ARQUIVO_TESTE).toFile().exists());
  }

  @Test(expected = RuntimeException.class)
  public void falhaAoProcessarArquivo() {
    if (diretorioOut.exists()) {
      diretorioOut.delete();
    }
    ProcessaArquivo processo = new ProcessaArquivo(
        arquivo.getParent().toString(), arquivo.toPath());
    processo.run();
  }

  /** Executa ao final de cada teste para limpar arquivo gerado. 
  */   
  @After
  public void removeArquivos() {
    File arquivoOut = diretorioOut.toPath().resolve(NOME_ARQUIVO_TESTE).toFile();
    if (arquivoOut.exists()) {
      arquivoOut.delete();
    }
    if (diretorioOut.exists()) {
      diretorioOut.delete();
    }    
  }

}