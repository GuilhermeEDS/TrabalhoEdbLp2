package dominio;

import java.util.ArrayList;

public class InformacoesArquivo {
    private Integer numeroCasas;
    private Integer maximoLigacoes;

    private ArrayList<Ligacao> ligacoes;

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
        String resultado = "";

        resultado += "Máximo de ligações: " + maximoLigacoes + "\n";

        for (Ligacao ligacao : ligacoes) {
            resultado += ligacao + "\n";
        }

        return resultado;
    }
}
