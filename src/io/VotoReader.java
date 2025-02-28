package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import domain.Voto;
import enums.Cargo;
import util.CSVUtil;

public class VotoReader {
    
    public static Set<Voto> readVotos(String filePath, String codigoMunicipio) {
        Set<Voto> votos = new HashSet<>();

        try (BufferedReader br = CSVUtil.getReader(filePath)) {
            String line = br.readLine();
            Map<String, Integer> headerIndexMap = CSVUtil.parseHeader(line);

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                fields = CSVUtil.cleanFields(fields);
                Voto voto = parseVoto(headerIndexMap, fields, codigoMunicipio);
                
                if (voto != null) votos.add(voto);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de votos: " + e.getMessage());
        }

        return votos;
    }

    private static Voto parseVoto(Map<String, Integer> headerIndexMap, String[] fields, String codigoMunicipio) {
        Cargo cargo = Cargo.valueOfCodigo(Integer.parseInt(fields[headerIndexMap.get("CD_CARGO")]));
        String cdgMunicipio = fields[headerIndexMap.get("CD_MUNICIPIO")];
        int numVotavel = Integer.parseInt(fields[headerIndexMap.get("NR_VOTAVEL")]);
        int quantidade = Integer.parseInt(fields[headerIndexMap.get("QT_VOTOS")]);

        if (!cdgMunicipio.equals(codigoMunicipio)) {
            return null;
        }

        return new Voto(cargo, codigoMunicipio, numVotavel, quantidade);
    }
}
