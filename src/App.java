import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import domain.Candidato;
import domain.Eleicao;
import domain.Voto;
import io.CandidatoReader;
import io.VotoReader;
import report.Relatorio;
import services.VotacaoService;

public class App {
    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Uso: java App <candidatos.csv> <votos.csv> <data da eleição>");
        }
        
        String candidatosCSV = args[0];
        String votosCSV = args[1];
        LocalDate dataEleicao = LocalDate.parse(args[2], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        Eleicao eleicao = Eleicao.getInstance();
        Set<Candidato> candidatos = CandidatoReader.readCandidatos(candidatosCSV);
        Set<Voto> votos = VotoReader.readVotos(votosCSV);

        eleicao.addCandidatos(candidatos);
        VotacaoService votacaoService = new VotacaoService(eleicao);
        votacaoService.computaVotos(votos);

        Relatorio relatorio = new Relatorio(eleicao);
        relatorio.imprimeNumVagas();
        relatorio.imprimeVereadoresEleitos();
        relatorio.imprimeCandidatosMaisVotados();
        relatorio.imprimeCandidatosNaoEleitos();
        relatorio.imprimeCandidatosBeneficiados();
        relatorio.imprimeRankingPartidos();
        relatorio.imprimePrimeiroUltimoCandidatoPorPartido();
        relatorio.imprimeDistribuicaoFaixaEtaria(dataEleicao);
        relatorio.imprimeDistribuicaoGenero();
        relatorio.imprimeTotalVotos();
    }
}
