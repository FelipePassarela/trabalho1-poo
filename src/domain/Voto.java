package domain;

import enums.Cargo;

public class Voto {
    private final Cargo cargo;
    private final String codigoMunicipio;
    private final int numVotavel;
    private final int quantidade;

    public Voto(Cargo cargo, String codigoMunicipio, int numVotavel, int quantidade) {
        this.cargo = cargo;
        this.codigoMunicipio = codigoMunicipio;
        this.numVotavel = numVotavel;
        this.quantidade = quantidade;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public int getNumVotavel() {
        return numVotavel;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public boolean isNominal() {
        return numVotavel > 99;
    }
}
