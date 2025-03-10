package report;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import domain.Candidato;
import domain.Eleicao;
import domain.Partido;
import enums.Genero;
import util.CandidatoFormatter;
import util.PartidoFormatter;

/**
 * Responsável por gerar e imprimir diversos relatórios referentes à eleição.
 */
public class Relatorio {
    Eleicao eleicao;

    public Relatorio(Eleicao eleicao) {
        this.eleicao = eleicao;
    }

    /**
     * Imprime o número de vagas disponíveis.
     */
    public void imprimeNumVagas() {
        System.out.println("Número de vagas: " + eleicao.getNumVagas() + "\n");
    }

    /**
     * Imprime os vereadores eleitos.
     */
    public void imprimeVereadoresEleitos() {
        System.out.println("Vereadores eleitos:");

        int index = 0;
        for (Candidato candidato : eleicao.getCandidatosEleitos()) {
            String candidatoFormatado = CandidatoFormatter.format(candidato);
            System.out.println(++index + " - " + candidatoFormatado);
        }
    }

    /**
     * Imprime os candidatos mais votados (respeitando o número de vagas).
     */
    public void imprimeCandidatosMaisVotados() {
        System.out.print("\nCandidatos mais votados ");
        System.out.println("(em ordem decrescente de votação e respeitando número de vagas):");

        int index = 0;
        for (Candidato candidato : eleicao.getCandidatosMaisVotados()) {
            String candidatoFormatado = CandidatoFormatter.format(candidato);
            System.out.println(++index + " - " + candidatoFormatado);
            if (index >= eleicao.getNumVagas()) {
                break;
            }
        }
    }

    /**
     * Imprime os candidatos que não foram eleitos, mas teriam sido majoritariamente.
     */
    public void imprimeCandidatosNaoEleitos() {
        System.out.println("\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");

        int index = 1;
        for (Candidato candidato : eleicao.getCandidatosMaisVotados()) {
            if (!eleicao.isEleito(candidato)) {
                String candidatoFormatado = CandidatoFormatter.format(candidato);
                System.out.println(index + " - " + candidatoFormatado);
            }
            if (++index > eleicao.getNumVagas()) {
                break;
            }
        }
    }

    /**
     * Imprime os candidatos eleitos que se beneficiaram do sistema proporcional.
     */
    public void imprimeCandidatosBeneficiados() {
        System.out.println("\nEleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");

        List<Candidato> candidatosMaisVotados = eleicao.getCandidatosMaisVotados();
        Candidato ultimoCandidatoMaisVotado = candidatosMaisVotados.get(eleicao.getNumVagas() - 1);
        int index = 1;

        for (Candidato candidato : candidatosMaisVotados) {
            if (eleicao.isEleito(candidato) && 
                candidato.getNumVotos() < ultimoCandidatoMaisVotado.getNumVotos()) {
                String candidatoFormatado = CandidatoFormatter.format(candidato);
                System.out.println(index + " - " + candidatoFormatado);
            }
            index++;
        }
    }

    /**
     * Imprime o ranking dos partidos de acordo com a votação.
     */
    public void imprimeRankingPartidos() {
        System.out.println("\nVotação dos partidos e número de candidatos eleitos:");

        List<Partido> partidos = eleicao.getPartidos();
        Collections.sort(partidos);
        int index = 0;

        for (Partido partido : partidos) {
            System.out.println(++index + " - " + PartidoFormatter.format(partido));
        }
    }

    /**
     * Imprime o primeiro e o último candidato de cada partido.
     */
    public void imprimePrimeiroUltimoCandidatoPorPartido() {
        System.out.println("\nPrimeiro e último colocados de cada partido:");

        List<Partido> partidos = eleicao.getPartidos();
        sortPartidosPorMaiorCandidato(partidos);

        int index = 0;
        for (Partido partido : partidos) {
            List<Candidato> candidatosPartido = partido.getCandidatos();
            
            if(candidatosPartido.isEmpty() || partido.getNumVotosNominais() <= 0){
                continue;
            }

            Collections.sort(candidatosPartido);
            Candidato primeiroColocado = candidatosPartido.get(0);
            Candidato ultimoColocado = candidatosPartido.get(candidatosPartido.size() - 1);
            NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));

            System.out.print(++index + " - " + partido.getSigla() + " - " + partido.getNumero() + ", ");
            System.out.printf("%s (%d, %s votos) / %s (%d, %s votos)%n", 
                primeiroColocado.getNomeUrna(), primeiroColocado.getNumero(), nf.format(primeiroColocado.getNumVotos()),
                ultimoColocado.getNomeUrna(), ultimoColocado.getNumero(), nf.format(ultimoColocado.getNumVotos()));
        }
    }

    private void sortPartidosPorMaiorCandidato(List<Partido> partidos) {
        Collections.sort(partidos, new Comparator<Partido>() {
            @Override
            public int compare(Partido p1, Partido p2) {
                List<Candidato> candidatos1 = p1.getCandidatos();
                List<Candidato> candidatos2 = p2.getCandidatos();

                if (candidatos1.isEmpty() && candidatos2.isEmpty())
                    return 0;
                if (candidatos1.isEmpty())
                    return 1;
                if (candidatos2.isEmpty())
                    return -1;

                Collections.sort(candidatos1);
                Collections.sort(candidatos2);

                Candidato top1 = candidatos1.get(0);
                Candidato top2 = candidatos2.get(0);

                int cmp = top1.compareTo(top2);
                if (cmp == 0) {
                    cmp = Integer.compare(p1.getNumero(), p2.getNumero());
                }
                return cmp;
            }
        });
    }

    /**
     * Imprime a distribuição dos eleitos por faixa etária, considerando a data da eleição.
     *
     * @param dataEleicao data da eleição para cálculo da idade
     */
    public void imprimeDistribuicaoFaixaEtaria(LocalDate dataEleicao) {
        System.out.println("\nEleitos, por faixa etária (na data da eleição):");

        List<Candidato> candidatosEleitos = eleicao.getCandidatosEleitos();

        int[] faixasEtarias = new int[5];
        for (Candidato candidato : candidatosEleitos) {
            int idade = candidato.getDataNascimento().until(dataEleicao).getYears();
            
            if (idade < 30) {
                faixasEtarias[0]++;
            } else if (idade < 40) {
                faixasEtarias[1]++;
            } else if (idade < 50) {
                faixasEtarias[2]++;
            } else if (idade < 60) {
                faixasEtarias[3]++;
            } else {
                faixasEtarias[4]++;
            }
        }

        String[] faixas = {
            "      Idade < 30", 
            "30 <= Idade < 40", 
            "40 <= Idade < 50", 
            "50 <= Idade < 60", 
            "60 <= Idade     ",
        };

        NumberFormat nf = NumberFormat.getPercentInstance(Locale.forLanguageTag("pt-BR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        for (int i = 0; i < faixasEtarias.length; i++) {
            String percentual = nf.format(faixasEtarias[i] / (double)candidatosEleitos.size());
            System.out.println(faixas[i] + ": " + faixasEtarias[i] + " (" + percentual + ")");
        }
    }

    /**
     * Imprime a distribuição dos eleitos por gênero.
     */
    public void imprimeDistribuicaoGenero() {
        List<Candidato> candidatosEleitos = eleicao.getCandidatosEleitos();
        int femininoCount = 0;
        int masculinoCount = 0;

        for (Candidato candidato : candidatosEleitos) {
            if (candidato.getGenero() == Genero.FEMININO) {
                femininoCount++;
            } else if (candidato.getGenero() == Genero.MASCULINO) {
                masculinoCount++;
            }
        }

        NumberFormat nf = NumberFormat.getPercentInstance(Locale.forLanguageTag("pt-BR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        String percentualFeminino = nf.format(femininoCount / (double)candidatosEleitos.size());
        String percentualMasculino = nf.format(masculinoCount / (double)candidatosEleitos.size());

        System.out.println("\nEleitos, por gênero:");
        System.out.printf("Feminino:  %d (%s)%n", femininoCount, percentualFeminino);  
        System.out.printf("Masculino: %d (%s)%n", masculinoCount, percentualMasculino);  
    }

    /**
     * Imprime o total de votos válidos, nominais e de legenda.
     */
    public void imprimeTotalVotos() {        
        int totalVotos = eleicao.getTotalVotos();
        int totalVotosNominais = eleicao.getTotalVotosNominais();
        int totalVotosLegenda = eleicao.getTotalVotosLegenda();
        
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        NumberFormat nfPercent = NumberFormat.getPercentInstance(Locale.forLanguageTag("pt-BR"));
        nfPercent.setMinimumFractionDigits(2);
        nfPercent.setMaximumFractionDigits(2);

        String percentualVotosNominais = nfPercent.format(totalVotosNominais / (double)totalVotos);
        String percentualVotosLegenda = nfPercent.format(totalVotosLegenda / (double)totalVotos);

        System.out.println();
        System.out.printf("Total de votos válidos:    %s%n", nf.format(totalVotos));
        System.out.printf("Total de votos nominais:   %s (%s)%n", nf.format(totalVotosNominais), percentualVotosNominais);
        System.out.printf("Total de votos de legenda: %s (%s)%n", nf.format(totalVotosLegenda), percentualVotosLegenda);
    }
}
