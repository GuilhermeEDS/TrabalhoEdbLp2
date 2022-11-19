package dominio;

import java.util.ArrayList;

public class InformacoesArquivo {
    private ArrayList<Ligacao> ligacoes;
    private Integer maximoLigacoes;

    public InformacoesArquivo() {
        this.ligacoes = new ArrayList<Ligacao>();
        this.maximoLigacoes = 0;
    }

    public InformacoesArquivo(ArrayList<Ligacao> ligacoes, Integer d) {
        this.ligacoes = ligacoes;
        this.maximoLigacoes = d;
    }

    public ArrayList<Ligacao> getLigacoes() {
        return ligacoes;
    }

    public void setLigacoes(ArrayList<Ligacao> ligacoes) {
        this.ligacoes = ligacoes;
    }

    public Integer getMaximoLigacoes() {
        return maximoLigacoes;
    }

    public void setMaximoLigacoes(Integer maximoLigacoes) {
        this.maximoLigacoes = maximoLigacoes;
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
