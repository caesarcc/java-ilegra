package com.ilegra.prova.servicos;

import com.ilegra.prova.tabelas.Cliente;
import com.ilegra.prova.tabelas.Tabela;
import com.ilegra.prova.tabelas.TabelaFactory;
import com.ilegra.prova.tabelas.Venda;
import com.ilegra.prova.tabelas.Vendedor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GerenciaConteudo {

  private final List<Tabela> registros;

  public GerenciaConteudo() {
    registros = new ArrayList<Tabela>();
  }

  public void limpaHistorico() {
    registros.clear();
  }

  /**
   * Carregar todo conteúdo de um arquivo para a lista de registros.
   * 
   * @param linhas conteúdo do arquivo linha por linha
   */
  public void carregaNovosRegistros(final List<String> linhas) {
    linhas.stream().forEach(linha -> {
      carregaRegistro(linha);
    });
  }

  public void carregaRegistro(final String linha) {
    final TabelaFactory tabelaFactory = TabelaFactory.getTabela(linha.substring(0, 3));
    registros.add(tabelaFactory.gera(linha));
  }

  /**
   * Gera as linhas do relatório conforme os dados selecionados.
   * 
   * @return linhas do relatório
   */
  public List<String> geraRelatorio() {
    final List<String> relatorio = new ArrayList<>();
    relatorio.add(String.format("Quantidade de clientes nos arquivos: %d", 
        this.getQtdeClientes()));
    relatorio.add(String.format("Quantidade de vendedores nos arquivos: %d", 
        this.getQtdeVendedores()));
    relatorio.add(String.format("ID da venda mais cara: %d", 
        this.getMaiorVenda()));
    relatorio.add(String.format("O pior vendedor é: %s", 
        this.getPiorVendedor()));
    return relatorio;
  }

  public Long getQtdeClientes() {
    return registros.stream().filter(registro -> registro instanceof Cliente).count();
  }

  public Long getQtdeVendedores() {
    return registros.stream().filter(registro -> registro instanceof Vendedor).count();
  }

  /** Busca ID da maior venda. */
  public Long getMaiorVenda() {
    final List<Venda> vendas = registros.stream()
        .filter(registro -> registro instanceof Venda)
        .map(venda -> (Venda) venda).collect(Collectors.toList());

    return vendas.stream()
        .max(Comparator.comparingDouble(venda -> venda.getValorTotalCalculado().doubleValue()))
        .map(venda -> venda.getId()).orElse(0L);
  }

  /** Busca nome do vendedor com menor soma de vendas. */
  public String getPiorVendedor() {
    final List<Venda> vendas = registros.stream()
        .filter(registro -> registro instanceof Venda)
        .map(venda -> (Venda) venda).collect(Collectors.toList());

    final Map<String, Double> vendasAgrupadas = vendas.stream()
        .collect(Collectors.groupingBy(Venda::getNomeVendedor,
        Collectors.summingDouble(venda -> venda.getValorTotalCalculado().doubleValue())));

    return vendasAgrupadas.entrySet().stream()
        .min(Map.Entry.comparingByValue())
        .map(chave -> chave.getKey()).orElse("");
  }  
}