package util;

import java.text.NumberFormat;
import java.util.Locale;

import domain.Partido;

public class PartidoFormatter {

    public static String format(Partido partido) {
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        String totalVotosStr = nf.format(partido.getNumVotosTotais());
        String votosNominaisStr = nf.format(partido.getNumVotosNominais());
        String votosLegendaStr = nf.format(partido.getNumVotosLegenda());

        String pluralTotal = partido.getNumVotosTotais() > 1 ? " votos" : " voto";
        String pluralNominal = partido.getNumVotosNominais() > 1 ? " nominais" : " nominal";
        String legendaTexto = " de legenda";
        String pluralEleitos = partido.getCandidatosEleitos().size() > 1 ? " candidatos eleitos" : " candidato eleito";

        return String.format("%s - %d, %s%s (%s%s e %s%s), %d%s",
            partido.getSigla(),
            partido.getNumero(),
            totalVotosStr, pluralTotal,
            votosNominaisStr, pluralNominal,
            votosLegendaStr, legendaTexto,
            partido.getCandidatosEleitos().size(), pluralEleitos
        );
    }
}
