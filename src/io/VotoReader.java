package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import domain.Voto;
import enums.Cargo;
import util.CSVUtil;

/**
 * Leitor de votos a partir de arquivo CSV.
 */
public class VotoReader {

    /**
     * Lê os votos de um arquivo CSV e retorna um conjunto de votos
     * filtrados pelo código do município.
     *
     * @param filePath o caminho do arquivo CSV
     * @param codigoMunicipio o código do município a ser filtrado
     * @return um conjunto de votos
     */
    public static Set<Voto> readVotos(String filePath, String codigoMunicipio) {
        Set<Voto> votos = new HashSet<>();

        try (BufferedReader br = CSVUtil.getReader(filePath)) {
            String line = br.readLine();
            Map<String, Integer> headerIndexMap = CSVUtil.parseHeader(line);

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                CSVUtil.cleanFields(fields);
                Voto voto = parseVoto(headerIndexMap, fields, codigoMunicipio);
                
                if (voto != null) votos.add(voto);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de votos: " + e.getMessage());
        }

        return votos;
    }

    /**
     * Processa uma linha de dados do CSV e cria um objeto Voto.
     *
     * @param headerIndexMap mapeamento de cabeçalhos para índice
     * @param fields os campos da linha
     * @param codigoMunicipio o código do município para validação
     * @return um objeto Voto ou null se o município não coincidir
     */
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
