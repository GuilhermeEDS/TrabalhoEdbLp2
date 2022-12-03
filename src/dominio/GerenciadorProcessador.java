package dominio;

import excecao.ArquivoInvalido;
import main.LeitorArquivos;
import main.ProcessadorComplexo;
import main.ProcessadorSimples;

import java.util.ArrayList;

public class GerenciadorProcessador {
    InformacoesArquivo informacoesArquivo;
    boolean executarProcessadorComplexo;
    Long tempoExecucao = null;

    ArrayList<Ligacao> ligacoes = null;
    Integer custo;
    private boolean isProcessado = false;

    public GerenciadorProcessador(String[] args, boolean executarProcessadorComplexo) {
        this.executarProcessadorComplexo = executarProcessadorComplexo;
        String caminhoArquivo;
        try {
            caminhoArquivo = LeitorArquivos.parseArgs(args);
        } catch (Exception e) {
            System.out.println("Uso correto: java -jar arquivo <caminhoArquivo>\n\t<caminhoArquivo>: O caminho para o arquivo contendo " + "o máximo de ligações e o custo de cada ligação possível");
            return;
        }

        try {
            informacoesArquivo = LeitorArquivos.lerArquivo(caminhoArquivo);
        } catch (ArquivoInvalido e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void executar() throws Exception {
        long inicio = System.nanoTime();
        ArrayList<ArrayList<Ligacao>> res;
        if (executarProcessadorComplexo) {
            ProcessadorComplexo processadorComplexo = new ProcessadorComplexo(informacoesArquivo);
            res = processadorComplexo.processar();
        } else {
            ProcessadorSimples processadorSimples = new ProcessadorSimples(informacoesArquivo);
            res = processadorSimples.processar();
        }
        long fim = System.nanoTime();
        if (res.size() == 0) {
            throw new Exception("Não foi possível encontrar uma árvore válida");
        }
        isProcessado = true;

        ligacoes = res.get(0);
        tempoExecucao = (fim - inicio) / 1000000;
    }

    public Integer getCusto() throws Exception {
        if (!isProcessado) {
            throw new Exception("Não foi processado");
        }

        return custo;
    }

    public ArrayList<Ligacao> getLigacoes() throws Exception {
        if (!isProcessado) {
            throw new Exception("Não foi processado");
        }

        return ligacoes;
    }
}
