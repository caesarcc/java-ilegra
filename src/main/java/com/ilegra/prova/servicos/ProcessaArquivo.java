package com.ilegra.prova.servicos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ProcessaArquivo implements Runnable {

    private Path arquivo;

    public ProcessaArquivo(Path arquivoParam) {
        this.arquivo = arquivoParam;
    }

    public void run() {

        final String caminhoBase = System.getProperty("user.home")
            .concat(File.separator).concat("data")
            .concat(File.separator).concat("out");

        final Path novoArquivo = Paths.get(caminhoBase.concat(File.separator)
            .concat(arquivo.getFileName().toString()));

        List<String> conteudo = null;
        BufferedWriter writer = null;
        try {
            conteudo = Files.readAllLines(arquivo);
 
            //Iniciar o serviço de importação

            writer = Files.newBufferedWriter(novoArquivo);
            for (String linha : conteudo) {
                writer.write(linha);
            }
            System.out.println(String.format("Arquivo %s foi processado com sucesso.", arquivo.toString()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}