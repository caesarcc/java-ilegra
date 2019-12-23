package com.ilegra.prova;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.ilegra.prova.servicos.ProcessaArquivo;

public class Monitorador {

    public static void main(final String[] args) throws IOException, InterruptedException {

        final WatchService monitor = FileSystems.getDefault().newWatchService();

        final String caminhoBase = System.getProperty("user.home")
            .concat(File.separator).concat("data");

        final Path caminhoMonitorado = Paths.get(
            caminhoBase.concat(File.separator).concat("in"));

        caminhoMonitorado.register(monitor, ENTRY_CREATE, ENTRY_MODIFY);
        monitorar(caminhoMonitorado, monitor);
    }

    protected static void monitorar(Path caminhoMonitorado, WatchService monitor) throws InterruptedException {

        WatchKey itemMonitorado;
        while ((itemMonitorado = monitor.take()) != null) {
            for (WatchEvent<?> evento : itemMonitorado.pollEvents()) {

                WatchEvent.Kind<?> tipoEvento = evento.kind();
                final Path arquivo = caminhoMonitorado.resolve(
                    evento.context().toString());

                if (ENTRY_CREATE.equals(tipoEvento) ||
                    (ENTRY_MODIFY.equals(tipoEvento) && evento.count() == 1)) {
                    new Thread(new ProcessaArquivo(arquivo)).start();
                }
            }
            if (!itemMonitorado.reset()) {
                break;
            }
        }
    }
}
