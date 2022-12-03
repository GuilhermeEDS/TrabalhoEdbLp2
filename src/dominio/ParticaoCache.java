package dominio;

import main.ProcessadorLigacoes;

import java.util.ArrayList;

public class ParticaoCache {
    private final Particao particao;
    private final ArrayList<Ligacao> ligacoes;
    private final int custo;

    public ParticaoCache(Particao particao, InformacoesArquivo informacoesArquivo) throws Exception {
        this.particao = particao;
        ligacoes = ProcessadorLigacoes.kruskal(particao, informacoesArquivo);
        custo = ProcessadorLigacoes.calcularCusto(ligacoes);
    }

    public Particao getParticao() {
        return particao;
    }

    public ArrayList<Ligacao> getLigacoes() {
        return ligacoes;
    }

    public int getCusto() {
        return custo;
    }
}
