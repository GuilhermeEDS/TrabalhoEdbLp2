package estrutura;

import java.util.ArrayList;

import dominio.Ligacao;

public class Particao {
    private ArrayList<Ligacao> ligacoesObrigatorias;
    private ArrayList<Ligacao> ligacoesRestritas;

    public Particao(ArrayList<Ligacao> ligacoesObrigatorias, ArrayList<Ligacao> ligacoesRestritas) {
        this.ligacoesObrigatorias = ligacoesObrigatorias;
        this.ligacoesRestritas = ligacoesRestritas;
    }

    public ArrayList<Ligacao> getLigacoesObrigatorias() {
        return ligacoesObrigatorias;
    }

    public void setLigacoesObrigatorias(ArrayList<Ligacao> ligacoesObrigatorias) {
        this.ligacoesObrigatorias = ligacoesObrigatorias;
    }

    public ArrayList<Ligacao> getLigacoesRestritas() {
        return ligacoesRestritas;
    }

    public void setLigacoesRestritas(ArrayList<Ligacao> ligacoesRestritas) {
        this.ligacoesRestritas = ligacoesRestritas;
    }
}
