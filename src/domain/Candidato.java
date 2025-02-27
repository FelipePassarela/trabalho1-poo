package domain;

import java.time.LocalDate;

import enums.Cargo;
import enums.Genero;
import enums.Situacao;

public class Candidato implements Comparable<Candidato> {
    private String codigoMunicipio;
    private Cargo cargo;
    private int numero;
    private String nomeUrna;
    private Partido partido;
    private int numFederacao;
    private LocalDate dataNascimento;
    private Situacao situacao;
    private Genero genero;
    private int numVotos;

    public Candidato(
        String codigoMunicipio, 
        Cargo cargo, 
        int numero, 
        String nomeUrna, 
        Partido partido,  
        int numFederacao, 
        LocalDate dataNascimento, 
        Situacao situacao,
        Genero genero
    ) {
        this.codigoMunicipio = codigoMunicipio;
        this.cargo = cargo;
        this.numero = numero;
        this.nomeUrna = nomeUrna;
        this.partido = partido;
        this.numFederacao = numFederacao;
        this.dataNascimento = dataNascimento;
        this.situacao = situacao;
        this.genero = genero;
    }

    public void incrementaVotos(int numVotos) {
        this.numVotos += numVotos;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public int getNumero() {
        return numero;
    }

    public String getNomeUrna() {
        return nomeUrna;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public int getNumFederacao() {
        return numFederacao;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public Genero getGenero() {
        return genero;
    }

    public int getNumVotos() {
        return numVotos;
    }

    public void setNumVotos(int numVotos) {
        this.numVotos = numVotos;
    }

    @Override
    public int compareTo(Candidato o) {
        int diff = Integer.compare(o.numVotos, numVotos);
        if (diff == 0) {
            diff = dataNascimento.compareTo(o.dataNascimento);
        }
        return diff;
    }
}
