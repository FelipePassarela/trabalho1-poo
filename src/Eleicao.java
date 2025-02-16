import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Eleicao {
    private static final Eleicao INSTANCE = new Eleicao();
    private static Map<String, Candidato> candidatos = new HashMap<>();
    private static Map<Integer, Partido> partidos = new HashMap<>();
    private int numVagas;

    private Eleicao() {}

    public static Eleicao getInstance() {
        return INSTANCE;
    }

    public Candidato criaCandidato(
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
        Candidato candidato = new Candidato(
            codigoMunicipio, cargo, numero, 
            nomeUrna, partido, numFederacao, 
            dataNascimento, situacao, genero
        );

        addCandidato(candidato);
        
        return candidato;       
    }

    public void addCandidato(Candidato candidato) {
        addPartido(candidato.getPartido());
        
        if (!isValido(candidato)) {
            return;
        }
        
        String candidatoKey = candidato.getCodigoMunicipio() + candidato.getNumero();
        candidatos.put(candidatoKey, candidato);
        
        Partido partido = findPartido(candidato.getPartido().getNumero());
        partido.addCandidato(candidato);
        candidato.setPartido(partido);  // Partidos são únicos e não devem haver cópias do mesmo partido

        if (isEleito(candidato)) {
            partido.addCandidatoEleito(candidato);
            numVagas++;
        }
    }

    public void addCandidatos(Iterable<Candidato> candidatos) {
        for (Candidato candidato : candidatos) {
            addCandidato(candidato);
        }
    }

    public void addPartido(Partido partido) {
        boolean partidoJaExiste = partidos.containsKey(partido.getNumero());
        if (!partidoJaExiste) {
            partidos.put(partido.getNumero(), partido);
        }
    }

    public Candidato findCandidato(String codigoMunicipio, int numero) {
        return candidatos.get(codigoMunicipio + numero);
    }

    public Partido findPartido(int numero) {
        return partidos.get(numero);
    }
    
    public void computaVotos(Iterable<Voto> votos) {
        for (Voto voto : votos) {
            if (voto.isNominal()) {
                Candidato candidato = findCandidato(voto.getCodigoMunicipio(), voto.getNumVotavel());
                if (candidato != null) {
                    Partido partido = candidato.getPartido();
                    candidato.incrementaVotos(voto.getQuantidade());
                    partido.incrementaVotosNominais(voto.getQuantidade());
                }
            } else {
                Partido partido = findPartido(voto.getNumVotavel());
                if (partido != null) {
                    partido.incrementaVotosLegenda(voto.getQuantidade());
                }
            }
        }
    }

    public List<Candidato> getCandidatosEleitos() {
        List<Candidato> eleitos = new ArrayList<>(numVagas);

        for (Candidato candidato : candidatos.values()) {
            if (isEleito(candidato)) {
                eleitos.add(candidato);
            }
        }
        
        Collections.sort(eleitos);
        return eleitos;
    }

    public List<Candidato> getCandidatosMaisVotados() {
        List<Candidato> maisVotados = new ArrayList<>(numVagas);

        for (Candidato candidato : candidatos.values()) {
            if (candidato.getSituacao() != Situacao.INVALIDO) {
                maisVotados.add(candidato);
            }
        }

        Collections.sort(maisVotados);
        return maisVotados;
    }

    public boolean isValido(Candidato candidato) {
        return candidato.getCargo() == Cargo.VEREADOR && 
            candidato.getCodigoMunicipio().equals("57053") && // Debug
            candidato.getSituacao() != Situacao.INVALIDO;
    }

    public boolean isEleito(Candidato candidato) {
        return candidato.getSituacao() == Situacao.ELEITO_POR_MEDIA ||
            // candidato.getSituacao() == Situacao.ELEITO ||  // Essa situação não é considerada
            candidato.getSituacao() == Situacao.ELEITO_POR_QP;
    }

    public Set<Candidato> getCandidatos() {
        return new HashSet<>(candidatos.values());
    }

    public List<Partido> getPartidos() {
        return new ArrayList<>(partidos.values());
    }

    public int getNumCandidatos() {
        return candidatos.size();
    }

    public int getNumVagas() {
        return numVagas;
    }

    public int getTotalVotos() {
        int totalVotos = 0;
        for (Partido partido : partidos.values()) {
            totalVotos += partido.getNumVotosTotais();
        }
        return totalVotos;
    }

    public int getTotalVotosNominais() {
        int totalVotosNominais = 0;
        for (Partido partido : partidos.values()) {
            totalVotosNominais += partido.getNumVotosNominais();
        }
        return totalVotosNominais;
    }

    public int getTotalVotosLegenda() {
        int totalVotosLegenda = 0;
        for (Partido partido : partidos.values()) {
            totalVotosLegenda += partido.getNumVotosLegenda();
        }
        return totalVotosLegenda;
    }
}
