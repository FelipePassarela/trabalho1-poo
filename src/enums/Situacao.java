package enums;

/**
 * Enumeração que representa as situações de um candidato.
 */
public enum Situacao {
    ELEITO(1, "Eleito"),
    ELEITO_POR_QP(2, "Eleito por QP"),
    ELEITO_POR_MEDIA(3, "Eleito por média"),
    NAO_ELEITO(4, "Não eleito"),
    SUPLENTE(5, "Suplente"),
    SEGUNDO_TURNO(6, "Segundo turno"),
    INVALIDO(-1, "Inválido");

    private final int codigo;
    private final String descricao;

    Situacao(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    /**
     * Retorna o código da situação.
     * 
     * @return o código
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Retorna a descrição da situação.
     * 
     * @return a descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna a situação a partir do código informado.
     * 
     * @param codigo o código da situação
     * @return a situação correspondente ou INVALIDO se não encontrado
     */
    public static Situacao valueOfCodigo(int codigo) {
        for (Situacao situacao : values()) {
            if (situacao.codigo == codigo) {
                return situacao;
            }
        }

        return INVALIDO;
    }
}
