package services;

import domain.Candidato;
import domain.Eleicao;
import domain.Partido;
import domain.Voto;
import enums.Cargo;

/**
 * Serviço responsável por computar os votos e atualizar candidatos e partidos.
 */
public class VotacaoService {
    private Eleicao eleicao;

    public VotacaoService(Eleicao eleicao) {
        this.eleicao = eleicao;
    }

    /**
     * Processa e computa os votos, atualizando candidatos e partidos conforme necessário.
     *
     * @param votos coleção de votos a serem computados
     */
    public void computaVotos(Iterable<Voto> votos) {
        for (Voto voto : votos) {
            if (!isValido(voto)) {
                continue;
            }

            if (voto.isNominal()) {
                Candidato candidato = eleicao.findCandidato(voto.getCodigoMunicipio(), voto.getNumVotavel());
                if (candidato != null) {
                    Partido partido = candidato.getPartido();
                    candidato.incrementaVotos(voto.getQuantidade());
                    partido.incrementaVotosNominais(voto.getQuantidade());
                }
            } else {
                Partido partido = eleicao.findPartido(voto.getNumVotavel());
                if (partido != null) {
                    partido.incrementaVotosLegenda(voto.getQuantidade());
                }
            }
        }
    }

    /**
     * Verifica se o voto está dentro dos parâmetros válidos.
     *
     * @param voto objeto Voto a ser validado
     * @return true se o voto é válido; false caso contrário
     */
    private static boolean isValido(Voto voto) {
        boolean isNumValido = !(voto.getNumVotavel() >= 95 && voto.getNumVotavel() <= 98);
        return voto.getCargo() == Cargo.VEREADOR && isNumValido;
    }
}
