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
public class ProcessaArquivo implements Runnable {

  private String caminhoBase;
  private Path arquivo;

  public ProcessaArquivo(String caminhoBase, Path arquivo) {
    this.caminhoBase = caminhoBase;
    this.arquivo = arquivo;
  }

  /**Método de início da thread de processamento do arquivo.
  */
  public void run() {

    this.caminhoBase = this.caminhoBase.concat(File.separator).concat("out");

    final Path novoArquivo = Paths.get(
        caminhoBase.concat(File.separator).concat(
        arquivo.getFileName().toString()));

    List<String> conteudo = null;
    BufferedWriter writer = null;
    try {
      conteudo = Files.readAllLines(arquivo);

      //Iniciar o serviço de importação

      writer = Files.newBufferedWriter(novoArquivo);
      for (String linha : conteudo) {
        writer.write(linha);
      }
      System.out.println(String.format(
          "Arquivo %s foi processado com sucesso.", arquivo.toString()));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}