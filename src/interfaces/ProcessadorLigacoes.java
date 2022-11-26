package interfaces;

import java.util.ArrayList;

import dominio.InformacoesArquivo;
import dominio.Ligacao;

public abstract class ProcessadorLigacoes {
    protected InformacoesArquivo informacoesArquivo;

    public ProcessadorLigacoes(InformacoesArquivo informacoesArquivo) {
        this.informacoesArquivo = informacoesArquivo;
    }

    public abstract ArrayList<ArrayList<Ligacao>> processar();
}
