package services;

import domain.Candidato;
import domain.Eleicao;
import domain.Partido;
import domain.Voto;
import enums.Cargo;

public class VotacaoService {
    private Eleicao eleicao;

    public VotacaoService(Eleicao eleicao) {
        this.eleicao = eleicao;
    }

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

    private static boolean isValido(Voto voto) {
        boolean isNumValido = !(voto.getNumVotavel() >= 95 && voto.getNumVotavel() <= 98);
        return voto.getCargo() == Cargo.VEREADOR && isNumValido;
    }
}
