public enum Cargo {
    VEREADOR(13);

    private int codigo;

    Cargo(int codigo) {
        this.codigo = codigo;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public static Cargo valueOfCodigo(int codigo) {
        for (Cargo cargo : Cargo.values()) {
            if (cargo.getCodigo() == codigo) {
                return cargo;
            }
        }
        return null;
    }
}
