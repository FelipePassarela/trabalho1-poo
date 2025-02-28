package enums;

/**
 * Enumeração que representa os cargos.
 */
public enum Cargo {
    VEREADOR(13);

    private int codigo;

    Cargo(int codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Retorna o código do cargo.
     *
     * @return o código
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Retorna o cargo a partir do código informado.
     *
     * @param codigo o código do cargo
     * @return o cargo correspondente ou null se não encontrado
     */
    public static Cargo valueOfCodigo(int codigo) {
        for (Cargo cargo : Cargo.values()) {
            if (cargo.getCodigo() == codigo) {
                return cargo;
            }
        }
        return null;
    }
}
