package enums;

/**
 * Enumeração que representa os gêneros.
 */
public enum Genero {
    MASCULINO(2, "Masculino"),
    FEMININO(4, "Feminino"),
    INDEFINIDO(0, "Indefinido");

    private int codigo;
    private String descricao;

    Genero(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    /**
     * Retorna o código do gênero.
     *
     * @return o código
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Retorna a descrição do gênero.
     *
     * @return a descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o gênero a partir do código informado.
     *
     * @param codigo o código do gênero
     * @return o gênero correspondente ou INDEFINIDO se não encontrado
     */
    public static Genero valueOfCodigo(int codigo) {
        for (Genero genero : values()) {
            if (genero.codigo == codigo) {
                return genero;
            }
        }
        return INDEFINIDO;
    }
}
