package dominio;

import java.util.ArrayList;

public class InformacoesArquivo {
    private final Integer numeroCasas;
    private final Integer maximoLigacoes;
    private final ArrayList<Ligacao> ligacoes;

    public InformacoesArquivo(Integer numeroCasas, Integer maximoLigacoes, ArrayList<Ligacao> ligacoes) {
        this.numeroCasas = numeroCasas;
        this.maximoLigacoes = maximoLigacoes;
        this.ligacoes = ligacoes;
    }

    public Integer getNumeroCasas() {
        return numeroCasas;
    }

    public Integer getMaximoLigacoes() {
        return maximoLigacoes;
    }

    public ArrayList<Ligacao> getLigacoes() {
        return ligacoes;
    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();

        resultado.append("Máximo de ligações: ").append(maximoLigacoes).append("\n");

        for (Ligacao ligacao : ligacoes) {
            resultado.append(ligacao).append("\n");
        }

        return resultado.toString();
    }
}
