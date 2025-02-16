import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        numVotosNominais += numVotos;
    }

    public void incrementaVotosLegenda(int numVotos) {
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
    public String toString() {
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        String msgTotalVotos = getNumVotosTotais() > 1 ? " votos" : " voto";
        String msgVotosNominais = numVotosNominais > 1 ? " nominais e " : " nominal e ";
        String msgVotosLegenda = numVotosLegenda > 1 ? " de legenda)" : " de legenda)";
        String msgCandidatosEleitos = candidatosEleitos.size() > 1 ? " candidatos eleitos" : " candidato eleito";

        return sigla + " - " + numero + ", " + nf.format(getNumVotosTotais()) + msgTotalVotos + " (" + 
            nf.format(numVotosNominais) + msgVotosNominais + nf.format(numVotosLegenda) + msgVotosLegenda +
            ", " + candidatosEleitos.size() + msgCandidatosEleitos;
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
