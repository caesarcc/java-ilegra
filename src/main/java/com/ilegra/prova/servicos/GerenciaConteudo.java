package com.ilegra.prova.servicos;

import com.ilegra.prova.tabelas.Tabela;
import com.ilegra.prova.tabelas.TabelaFactory;

import java.util.ArrayList;
import java.util.List;

public class GerenciaConteudo {

  private List<Tabela> registros;

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
      TabelaFactory tabelaFactory = TabelaFactory.getTabela(linha.substring(0, 3));
      registros.add(tabelaFactory.gera(linha));      
    });
  }

  /**Gera as linhas do relatório conforme os dados selecionados.
  * @return linhas do relatório  
  */
  public List<String> geraRelatorio() {
    List<String> relatorio = new ArrayList<>();
    relatorio.add("Teste 1");
    return relatorio;
  }
}