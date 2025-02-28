package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import domain.Candidato;
import domain.Partido;
import enums.Cargo;
import enums.Genero;
import enums.Situacao;
import util.CSVUtil;

public class CandidatoReader {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Set<Candidato> readCandidatos(String filePath) {
        Set<Candidato> candidatos = new HashSet<>();

        try (BufferedReader br = CSVUtil.getReader(filePath)) {
            String line = br.readLine();
            Map<String, Integer> headerIndexMap = CSVUtil.parseHeader(line);
            
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                fields = CSVUtil.cleanFields(fields);
                Candidato candidato = parseCandidato(headerIndexMap, fields);
                
                candidatos.add(candidato);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de candidatos: " + e.getMessage());
        }

        return candidatos;
    }

    private static Candidato parseCandidato(Map<String, Integer> headerIndexMap, String[] fields) {
        String codigoMunicipio = fields[headerIndexMap.get("SG_UE")];
        Cargo cargo = Cargo.valueOfCodigo(Integer.parseInt(fields[headerIndexMap.get("CD_CARGO")]));
        int numero = Integer.parseInt(fields[headerIndexMap.get("NR_CANDIDATO")]);
        String nomeUrna = fields[headerIndexMap.get("NM_URNA_CANDIDATO")];
        Partido partido = new Partido(
            Integer.parseInt(fields[headerIndexMap.get("NR_PARTIDO")]), 
            fields[headerIndexMap.get("SG_PARTIDO")]);
        int numFederacao = Integer.parseInt(fields[headerIndexMap.get("NR_FEDERACAO")]);
        LocalDate dataNascimento = LocalDate.parse(fields[headerIndexMap.get("DT_NASCIMENTO")], FORMATTER);
        Situacao situacao = Situacao.valueOfCodigo(Integer.parseInt(fields[headerIndexMap.get("CD_SIT_TOT_TURNO")]));
        Genero genero = Genero.valueOfCodigo(Integer.parseInt(fields[headerIndexMap.get("CD_GENERO")]));
      
        return new Candidato(
            codigoMunicipio, cargo, numero, 
            nomeUrna, partido, numFederacao, 
            dataNascimento, situacao, genero
        );
    }
}
