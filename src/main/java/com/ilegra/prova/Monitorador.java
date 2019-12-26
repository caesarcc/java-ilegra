package com.ilegra.prova;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import com.ilegra.prova.servicos.ProcessaArquivo;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**Aplicação de monitoria de lotes de venda desenvolvida para nivelamento técnico para Ilegra.
* @author Caesar C. Cesar
* @version 1.0
*/
public final class Monitorador {

  /**Método de entrada da aplicação o qual configura o monitoramento da pasta de lotes de venda.
  * Cada ação de NOVO arquivo será iniciada uma nova thread de processamento.
  * @param args argumentos não utilizados
  */
  public static void main(final String[] args) throws IOException, InterruptedException {

    //A propriedade do sistema "user.home" contem o caminho da pasta padrão do usuário
    final String caminhoBase = System.getProperty("user.home")
        .concat(File.separator).concat("data");

    final Path caminhoMonitorado = Paths.get(
        caminhoBase.concat(File.separator).concat("in"));

    final WatchService monitor = FileSystems.getDefault().newWatchService();
    caminhoMonitorado.register(monitor, ENTRY_CREATE);
    
    WatchKey itemMonitorado;
    while ((itemMonitorado = monitor.take()) != null) {
      for (WatchEvent<?> evento : itemMonitorado.pollEvents()) {

        WatchEvent.Kind<?> tipoEvento = evento.kind();
        final Path arquivo = caminhoMonitorado.resolve(
            evento.context().toString());

        if (ENTRY_CREATE.equals(tipoEvento)) {
          ProcessaArquivo.getInstance().processa(arquivo);
        }
      }
      if (!itemMonitorado.reset()) {
        break;
      }
    }    
  }

  private Monitorador() {
    // Evitar inicialização incorreta
    throw new AssertionError("Classe não deve ser instanciada...");
  } 
}
