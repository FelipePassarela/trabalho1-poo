package util;

import java.text.NumberFormat;
import java.util.Locale;

import domain.Candidato;

public class CandidatoFormatter {
    public static String format(Candidato candidato) {
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        boolean participaFederacao = candidato.getNumFederacao() != -1;

        return String.format("%s %s (%s, %s votos)", 
            participaFederacao ? "*" : "",
            candidato.getNomeUrna().toUpperCase(),
            candidato.getPartido().getSigla(),
            nf.format(candidato.getNumVotos())
        );
    }
}
