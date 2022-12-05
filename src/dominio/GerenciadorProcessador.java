package dominio;

import excecao.ArquivoInvalido;
import main.LeitorArquivos;
import main.ProcessadorComplexo;
import main.ProcessadorLigacoes;
import main.ProcessadorSimples;

import java.util.ArrayList;

public class GerenciadorProcessador {
    InformacoesArquivo informacoesArquivo;
    boolean executarProcessadorComplexo;
    Long tempoExecucao = null;

    ArrayList<ArrayList<Ligacao>> solucoes = null;
    ArrayList<Integer> custos = null;
    private boolean isProcessado = false;

    public GerenciadorProcessador(String[] args) {
        if ( args.length != 2) {
            System.out.println("Uso correto: java -jar arquivo <caminhoArquivo> <tipoSolucao>\n\t<caminhoArquivo>: O caminho para o arquivo contendo " + "o máximo de ligações e o custo de cada ligação possível\n\t<tipoSolucao>: O tipo de solução que deverá ser executado, sendo --simples para executar a primeira forma e --complexo para a segunda forma\n\t\tSe for passado sem este executará a segunda forma");
            System.exit(-1);
        }

        if ( args[1].equals("--simples") ) {
            this.executarProcessadorComplexo = false;
        } else if ( args[1].equals("--complexo") ) {
            this.executarProcessadorComplexo = true;
        } else {
            System.out.println("2 Uso correto: java -jar arquivo <caminhoArquivo> <tipoSolucao>\n\t<caminhoArquivo>: O caminho para o arquivo contendo " + "o máximo de ligações e o custo de cada ligação possível\n\t<tipoSolucao>: O tipo de solução que deverá ser executado, sendo --simples para executar a primeira forma e --complexo para a segunda forma\n\t\tSe for passado sem este executará a segunda forma");
            System.exit(-1); 
        }

        String caminhoArquivo = args[0];
        
        try {
            informacoesArquivo = LeitorArquivos.lerArquivo(caminhoArquivo);
        } catch (ArquivoInvalido e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void executar() throws Exception {
        long inicio = System.nanoTime();
        if (executarProcessadorComplexo) {
            ProcessadorComplexo processadorComplexo = new ProcessadorComplexo(informacoesArquivo);
            solucoes = processadorComplexo.processar();
            custos = new ArrayList<>(solucoes.size());
            for (ArrayList<Ligacao> solucao : solucoes) {
                custos.add(ProcessadorLigacoes.calcularCusto(solucao));
            }
        } else {
            ProcessadorSimples processadorSimples = new ProcessadorSimples(informacoesArquivo);
            solucoes = processadorSimples.processar();
            custos = new ArrayList<>(solucoes.size());
            for (ArrayList<Ligacao> solucao : solucoes) {
                custos.add(ProcessadorLigacoes.calcularCusto(solucao));
            }
        }
        long fim = System.nanoTime();
        if (solucoes.size() == 0) {
            throw new Exception("Não foi possível encontrar uma árvore válida");
        }
        isProcessado = true;

        tempoExecucao = (fim - inicio) / 1000000;
    }

    public InformacoesArquivo getInformacoesArquivo() {
        return informacoesArquivo;
    }

    public ArrayList<Integer> getCustos() throws Exception {
        if (!isProcessado) {
            throw new Exception("Não foi processado");
        }

        return custos;
    }

    public ArrayList<ArrayList<Ligacao>> getSolucoes() throws Exception {
        if (!isProcessado) {
            throw new Exception("Não foi processado");
        }

        return solucoes;
    }
}
