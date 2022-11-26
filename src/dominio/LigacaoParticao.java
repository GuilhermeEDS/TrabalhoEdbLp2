package dominio;

public class LigacaoParticao extends Ligacao {
    private TipoLigacaoParticao tipoLigacaoParticao;

    public LigacaoParticao(Casa casa1, Casa casa2,
            int custo, TipoLigacaoParticao tipoLigacaoParticao) {
        super(casa1, casa2, custo);
        this.tipoLigacaoParticao = tipoLigacaoParticao;
    }

    public TipoLigacaoParticao getTipoLigacaoParticao() {
        return tipoLigacaoParticao;
    }
}