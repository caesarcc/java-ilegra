package com.ilegra.prova.servicos;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**Classe onde a leitura e escrita dos arquivos da pasta monitorada é realizadado.
 * Este processamento pode rodar paralelo em múltiplas threads para acelerar o 
 * processamento de lotes grandes de arquivos.
* @author Caesar C. Cesar
* @version 1.0
*/
public final class ProcessaArquivoSingleton {

  private static volatile ProcessaArquivoSingleton arquivoInstance;
  
  private final GerenciaConteudo conteudoGerenciador;
  
  private ProcessaArquivoSingleton() {
    conteudoGerenciador = new GerenciaConteudo();
  }

  /**Obter a instância da classe singleton.
  * Uso de double-check locking para garantir thread safety no singleton
  */
  public static ProcessaArquivoSingleton getInstance() { 
    if (arquivoInstance == null) {
      synchronized (ProcessaArquivoSingleton.class) {
        if (arquivoInstance == null) {
          arquivoInstance = new ProcessaArquivoSingleton();
        }
      }
    }
    return arquivoInstance; 
  }

  /**
   * Método de processamento.
   * @param arquivo caminho do arquivo de entrada.
   * @return caminho do arquivo gerado no processamento.
   */
  public Path processa(final Path arquivo) {
    
    final Path novoArquivo = preparaArquivoSaida(arquivo);
    
    try { 
      conteudoGerenciador.carregaNovosRegistros(Files.readAllLines(arquivo));

      List<String> relatorio = conteudoGerenciador.geraRelatorio();

      try (final PrintWriter writer = new PrintWriter(Files.newBufferedWriter(novoArquivo))) {
        for (String linha : relatorio) {
          writer.println(linha);
        }
      }

      System.out.println(String.format(
          "Arquivo %s foi processado com sucesso.", arquivo.toString()));

    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    return novoArquivo;
  }

  private Path preparaArquivoSaida(Path arquivo) {
    final String caminhoBase = System.getProperty("user.home")
        .concat(File.separator).concat("data");
    
    final File diretorioSaida = new File(caminhoBase.concat(File.separator).concat("out"));
    if (!diretorioSaida.exists()) {
      diretorioSaida.mkdir();
    }    
    return Paths.get(
        diretorioSaida.toString().concat(File.separator).concat("relatorio.txt"));
  }
}