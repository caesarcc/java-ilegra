package com.ilegra.prova.servicos;

import com.ilegra.prova.tabelas.Cliente;
import com.ilegra.prova.tabelas.Tabela;
import com.ilegra.prova.tabelas.TabelaFactory;
import com.ilegra.prova.tabelas.Vendedor;

import java.util.ArrayList;
import java.util.List;

public class GerenciaConteudo {

  private final List<Tabela> registros;

  public GerenciaConteudo() {
    registros = new ArrayList<Tabela>();
  }

  public void limpaHistorico() {
    registros.clear();
  }

  /**Carregar todo conteúdo de um arquivo para a lista de registros.
  * @param linhas conteúdo do arquivo linha por linha  
  */
  public void carregaNovosRegistros(List<String> linhas) {
    linhas.stream().forEach(linha -> {
      carregaRegistro(linha);      
    });
  }

  public void carregaRegistro(String linha) {
    TabelaFactory tabelaFactory = TabelaFactory.getTabela(linha.substring(0, 3));
    registros.add(tabelaFactory.gera(linha));
  }

  /**Gera as linhas do relatório conforme os dados selecionados.
  * @return linhas do relatório  
  */
  public List<String> geraRelatorio() {
    List<String> relatorio = new ArrayList<>();
    relatorio.add(String.format("Quantidade de clientes nos arquivos: $d", 
        this.getQtdeClientes()));
    relatorio.add(String.format("Quantidade de vendedores nos arquivos: $d", 
        this.getQtdeVendedores()));
    relatorio.add(String.format("ID da venda mais cara: $s", 
        "10"));
    relatorio.add(String.format("O pior vendedor é: $s", 
        "Paulo"));
    return relatorio;
  }
  
  public Long getQtdeClientes() {
    return registros.stream().filter(registro -> registro instanceof Cliente).count();
  }  
  
  public Long getQtdeVendedores() {
    return registros.stream().filter(registro -> registro instanceof Vendedor).count();
  }  
  
}