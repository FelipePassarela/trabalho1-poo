package domain;

import java.util.ArrayList;
import java.util.List;

public class Partido implements Comparable<Partido> {
    private int numero;
    private String sigla;
    private int numVotosNominais;
    private int numVotosLegenda;
    private List<Candidato> candidatos = new ArrayList<Candidato>();
    private List<Candidato> candidatosEleitos = new ArrayList<Candidato>();

    public Partido(int numero, String sigla) {
        this.numero = numero;
        this.sigla = sigla;
    }

    public void incrementaVotosNominais(int numVotos) {
        if (numVotos < 0) {
            throw new IllegalArgumentException("Número de votos nominais não pode ser negativo");
        }
        numVotosNominais += numVotos;
    }

    public void incrementaVotosLegenda(int numVotos) {
        if (numVotos < 0) {
            throw new IllegalArgumentException("Número de votos de legenda não pode ser negativo");
        }
        numVotosLegenda += numVotos;
    }

    public void addCandidato(Candidato candidato) {
        candidatos.add(candidato);
    }
    
    public void addCandidatoEleito(Candidato candidato) {
        candidatosEleitos.add(candidato);
    }

    public int getNumero() {
        return numero;
    }

    public String getSigla() {
        return sigla;
    }

    public int getNumVotosNominais() {
        return numVotosNominais;
    }

    public int getNumVotosLegenda() {
        return numVotosLegenda;
    }

    public int getNumVotosTotais() {
        return numVotosNominais + numVotosLegenda;
    }

    public List<Candidato> getCandidatosEleitos() {
        return new ArrayList<>(candidatosEleitos);
    }

    public List<Candidato> getCandidatos() {
        return new ArrayList<>(candidatos);
    }   

    @Override
    public int compareTo(Partido o) {
        int diff = Integer.compare(o.getNumVotosTotais(), getNumVotosTotais());
        if (diff == 0) {
            diff = -Integer.compare(o.getNumero(), numero);
        }
        return diff;
    }
}
