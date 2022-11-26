import java.util.*;

import dominio.*;
import abstrato.ProcessadorLigacoes;

public class ProcessadorComplexo extends ProcessadorLigacoes {
    public ProcessadorComplexo(InformacoesArquivo informacoesArquivo) {
        super(informacoesArquivo);
    }

    public ArrayList<ArrayList<LigacaoParticao>> particoes(ArrayList<Ligacao> ligacoes) {
        ArrayList<ArrayList<LigacaoParticao>> particoes = new ArrayList<ArrayList<LigacaoParticao>>();

        for (int index = 0; index < ligacoes.size(); index++) {
            ArrayList<LigacaoParticao> particao = new ArrayList<LigacaoParticao>();
            for (int j = 0; j < index; j++) {
                Ligacao ligacao = ligacoes.get(j);
                particao.add(new LigacaoParticao(
                        ligacao.getCasa1(), ligacao.getCasa2(),
                        ligacao.getCusto(), TipoLigacaoParticao.OBRIGATORIA));
            }
            Ligacao ligacao = ligacoes.get(index);
            particao.add(new LigacaoParticao(
                    ligacao.getCasa1(), ligacao.getCasa2(),
                    ligacao.getCusto(), TipoLigacaoParticao.RESTRITO));

            // Verificar se é válido pra adicionar em partições
            particoes.add(particao);
        }

        return particoes;
    }

    public ArrayList<ArrayList<Ligacao>> processar() {
        /*
         * kruskal de tudo
         * fazer partições
         * fazer kruskal dessas partições, pegar a de menor custo e adicionar na lista
         * fazer partições da menor e por aí vai
         */

        return null;
    }
}