package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import domain.Voto;
import enums.Cargo;

public class VotoReader {
    
    public static Set<Voto> readVotos(String filePath) {
        Set<Voto> votos = new HashSet<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.ISO_8859_1)) {
            String headerLine = br.readLine();
            Map<String, Integer> headerIndexMap = CandidatoReader.criaMap(headerLine);

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");

                for (int i = 0; i < fields.length; i++) {
                    fields[i] = fields[i].replace("\"", "").trim();
                }

                Cargo cargo = Cargo.valueOfCodigo(Integer.parseInt(fields[headerIndexMap.get("CD_CARGO")]));
                String codigoMunicipio = fields[headerIndexMap.get("CD_MUNICIPIO")];
                int numVotavel = Integer.parseInt(fields[headerIndexMap.get("NR_VOTAVEL")]);
                int quantidade = Integer.parseInt(fields[headerIndexMap.get("QT_VOTOS")]);

                Voto voto = new Voto(cargo, codigoMunicipio, numVotavel, quantidade);
                if (isValido(voto)) votos.add(voto);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de votos: " + e.getMessage());
        }

        return votos;
    }

    private static boolean isValido(Voto voto) {
        boolean isNumValido = !(voto.getNumVotavel() >= 95 && voto.getNumVotavel() <= 98);
        return voto.getCargo() == Cargo.VEREADOR && isNumValido;
    }
}
