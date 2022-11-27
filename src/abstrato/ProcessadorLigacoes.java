package abstrato;

import java.util.ArrayList;

import dominio.*;
import estrutura.*;

public abstract class ProcessadorLigacoes {
    protected InformacoesArquivo informacoesArquivo;

    public ProcessadorLigacoes(InformacoesArquivo informacoesArquivo) {
        this.informacoesArquivo = informacoesArquivo;
    }

    public abstract ArrayList<ArrayList<Ligacao>> processar();

    protected static ArrayList<Conjunto<Casa>> criarConjuntosUnitariosCasas(int numeroCasas) {
        ArrayList<Conjunto<Casa>> conjuntos = new ArrayList<Conjunto<Casa>>();
        for (int i = 0; i < numeroCasas; i++) {
            conjuntos.add(new Conjunto<Casa>(new Casa(i)));
        }

        return conjuntos;
    }
}
