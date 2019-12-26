package com.ilegra.prova.servicos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
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
public class ProcessaArquivo {

  private static ProcessaArquivo arquivoSingleton = null;
  
  private final GerenciaConteudo conteudoGerenciador;
  
  private ProcessaArquivo() {
    conteudoGerenciador = new GerenciaConteudo();
  }

  /**Obter a instância da classe singleton.
  */
  public static ProcessaArquivo getInstance() { 
    if (arquivoSingleton == null) {
      arquivoSingleton = new ProcessaArquivo(); 
    }
    return arquivoSingleton; 
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

      try (BufferedWriter writer = Files.newBufferedWriter(novoArquivo)) {
        for (String linha : relatorio) {
          writer.write(linha);
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
    final String caminhoBase = arquivo.getParent().getParent().toString();
    
    final File diretorioSaida = new File(caminhoBase.concat(File.separator).concat("out"));
    if (!diretorioSaida.exists()) {
      diretorioSaida.mkdir();
    }    
    return Paths.get(
        caminhoBase.concat(File.separator).concat("out")
        .concat(File.separator)
        .concat(arquivo.getFileName().toString()));
  }
}