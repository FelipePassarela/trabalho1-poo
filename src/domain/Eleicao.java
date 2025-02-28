package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import enums.Cargo;
import enums.Genero;
import enums.Situacao;

/**
 * Representa uma eleição, gerenciando candidatos, partidos e contagem de votos.
 */
public class Eleicao {
    private static final Eleicao INSTANCE = new Eleicao(); // Instância única para o padrão Singleton.
    private static Map<String, Candidato> candidatos = new HashMap<>();
    private static Map<Integer, Partido> partidos = new HashMap<>();
    private static String codigoMunicipio;
    private int numVagas;

    /**
     * Construtor privado para implementar o padrão Singleton.
     */
    private Eleicao() {}

    /**
     * Retorna a instância da eleição para o município informado.
     *
     * @param codigoMunicipio código do município
     * @return instância única de Eleicao
     */
    public static Eleicao getInstance(String codigoMunicipio) {
        Eleicao.codigoMunicipio = codigoMunicipio;
        return INSTANCE;
    }

    /**
     * Cria e adiciona um novo candidato à eleição.
     *
     * @param codigoMunicipio código do município
     * @param cargo cargo do candidato
     * @param numero número do candidato
     * @param nomeUrna nome de urna
     * @param partido partido do candidato
     * @param numFederacao número da federação
     * @param dataNascimento data de nascimento
     * @param situacao situação do candidato
     * @param genero gênero do candidato
     * @return o candidato criado
     */
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

    /**
     * Adiciona um candidato à eleição, atualizando o partido e vagas se necessário.
     *
     * @param candidato candidato a ser adicionado
     */
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

    /**
     * Adiciona vários candidatos à eleição.
     *
     * @param candidatos Iterable de candidatos a serem adicionados
     */
    public void addCandidatos(Iterable<Candidato> candidatos) {
        for (Candidato candidato : candidatos) {
            addCandidato(candidato);
        }
    }

    /**
     * Adiciona um partido à eleição, se ainda não estiver cadastrado.
     *
     * @param partido Partido a ser adicionado
     */
    public void addPartido(Partido partido) {
        boolean partidoJaExiste = partidos.containsKey(partido.getNumero());
        if (!partidoJaExiste) {
            partidos.put(partido.getNumero(), partido);
        }
    }

    /**
     * Encontra um candidato pela combinação do código do município e número.
     *
     * @param codigoMunicipio código do município
     * @param numero número do candidato
     * @return Candidato correspondente ou null se não encontrado
     */
    public Candidato findCandidato(String codigoMunicipio, int numero) {
        return candidatos.get(codigoMunicipio + numero);
    }

    /**
     * Encontra um partido pelo número.
     *
     * @param numero número do partido
     * @return Partido correspondente ou null se não encontrado
     */
    public Partido findPartido(int numero) {
        return partidos.get(numero);
    }

    /**
     * Retorna a lista de candidatos eleitos, ordenados.
     *
     * @return Lista de candidatos eleitos
     */
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

    /**
     * Retorna a lista de candidatos mais votados, ordenados.
     *
     * @return Lista de candidatos mais votados
     */
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

    /**
     * Verifica se o candidato é válido para a eleição.
     *
     * @param candidato candidato a ser verificado
     * @return true se o candidato é válido, false caso contrário
     */
    public boolean isValido(Candidato candidato) {
        return candidato.getCargo() == Cargo.VEREADOR && 
            candidato.getSituacao() != Situacao.INVALIDO &&
            candidato.getCodigoMunicipio().equals(codigoMunicipio);
    }

    /**
     * Verifica se o candidato foi eleito.
     *
     * @param candidato candidato a ser verificado
     * @return true se eleito, false caso contrário
     */
    public boolean isEleito(Candidato candidato) {
        return candidato.getSituacao() == Situacao.ELEITO_POR_MEDIA ||
            // candidato.getSituacao() == Situacao.ELEITO ||  // Essa situação não é considerada
            candidato.getSituacao() == Situacao.ELEITO_POR_QP;
    }

    /**
     * Retorna um conjunto com todos os candidatos cadastrados.
     *
     * @return conjunto de candidatos
     */
    public Set<Candidato> getCandidatos() {
        return new HashSet<>(candidatos.values());
    }

    /**
     * Retorna a lista de partidos cadastrados.
     *
     * @return lista de partidos
     */
    public List<Partido> getPartidos() {
        return new ArrayList<>(partidos.values());
    }

    /**
     * Retorna o código do município associado à eleição.
     *
     * @return código do município
     */
    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    /**
     * Retorna o número total de candidatos cadastrados.
     *
     * @return número de candidatos
     */
    public int getNumCandidatos() {
        return candidatos.size();
    }

    public int getNumVagas() {
        return numVagas;
    }

    /**
     * Retorna o total de votos (soma dos votos de todos os partidos).
     *
     * @return total de votos
     */
    public int getTotalVotos() {
        int totalVotos = 0;
        for (Partido partido : partidos.values()) {
            totalVotos += partido.getNumVotosTotais();
        }
        return totalVotos;
    }

    /**
     * Retorna o total de votos nominais.
     *
     * @return total de votos nominais
     */
    public int getTotalVotosNominais() {
        int totalVotosNominais = 0;
        for (Partido partido : partidos.values()) {
            totalVotosNominais += partido.getNumVotosNominais();
        }
        return totalVotosNominais;
    }

    /**
     * Retorna o total de votos na legenda.
     *
     * @return total de votos na legenda
     */
    public int getTotalVotosLegenda() {
        int totalVotosLegenda = 0;
        for (Partido partido : partidos.values()) {
            totalVotosLegenda += partido.getNumVotosLegenda();
        }
        return totalVotosLegenda;
    }
}
