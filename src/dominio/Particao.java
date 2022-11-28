package dominio;

import java.util.ArrayList;

public class Particao {
    private ArrayList<Ligacao> ligacoesObrigatorias;
    private ArrayList<Ligacao> ligacoesRestritas;

    public Particao(ArrayList<Ligacao> ligacoesObrigatorias, ArrayList<Ligacao> ligacoesRestritas) {
        this.ligacoesObrigatorias = ligacoesObrigatorias;
        this.ligacoesRestritas = ligacoesRestritas;
    }

    public Particao diferenca(Particao particao) {
        ArrayList<Ligacao> ligacoesObrigatorias = new ArrayList<Ligacao>();
        ArrayList<Ligacao> ligacoesRestritas = new ArrayList<Ligacao>();

        for (var ligacao : this.ligacoesObrigatorias) {
            if (!particao.getLigacoesObrigatorias().contains(ligacao)) {
                ligacoesObrigatorias.add(ligacao);
            }
        }

        for (var ligacao : this.ligacoesRestritas) {
            if (!particao.getLigacoesRestritas().contains(ligacao)) {
                ligacoesRestritas.add(ligacao);
            }
        }

        return new Particao(ligacoesObrigatorias, ligacoesRestritas);
    }

    public ArrayList<Ligacao> ligacoesOpcionais(ArrayList<Ligacao> ligacoes) {
        ArrayList<Ligacao> ligacoesOpcionais = new ArrayList<Ligacao>();

        for (Ligacao ligacao : ligacoes) {
            if (!ligacoesObrigatorias.contains(ligacao)
                    && !ligacoesRestritas.contains(ligacao)) {
                ligacoesOpcionais.add(ligacao);
            }
        }

        return ligacoesOpcionais;
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
