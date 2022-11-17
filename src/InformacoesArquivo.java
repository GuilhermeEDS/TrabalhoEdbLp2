public class InformacoesArquivo {
    private Ligacao[] ligacoes;
    private Integer d;

    InformacoesArquivo() {
        this.ligacoes = new Ligacao[] {};
        this.d = 0;
    }

    InformacoesArquivo(Ligacao[] ligacoes, Integer d) {
        this.ligacoes = ligacoes;
        this.d = d;
    }

    public Ligacao[] getLigacoes() {
        return ligacoes;
    }

    public void setLigacoes(Ligacao[] ligacoes) {
        this.ligacoes = ligacoes;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }
}
