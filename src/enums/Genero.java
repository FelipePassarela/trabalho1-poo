package enums;

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

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Genero valueOfCodigo(int codigo) {
        for (Genero genero : values()) {
            if (genero.codigo == codigo) {
                return genero;
            }
        }
        return INDEFINIDO;
    }
}
