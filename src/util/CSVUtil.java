package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilitário para operações com arquivos CSV.
 */
public class CSVUtil {

    /**
     * Retorna um BufferedReader para o arquivo CSV com codificação ISO-8859-1.
     *
     * @param filePath caminho do arquivo CSV
     * @return BufferedReader para leitura do arquivo
     * @throws IOException se ocorrer erro na leitura
     */
    public static BufferedReader getReader(String filePath) throws IOException {
        return Files.newBufferedReader(Paths.get(filePath), StandardCharsets.ISO_8859_1);
    }

    /**
     * Realiza o parse da linha de cabeçalho e mapeia os nomes de campos com seus índices.
     *
     * @param headerLine linha de cabeçalho do CSV
     * @return mapa contendo nomes de campos e seus respectivos índices
     */
    public static Map<String, Integer> parseHeader(String headerLine) {
        Map<String, Integer> map = new HashMap<>();
        String[] headers = headerLine.split(";");
        for (int i = 0; i < headers.length; i++) {
            headers[i] = headers[i].replace("\"", "").trim();
            map.put(headers[i], i);
        }
        return map;
    }
    
    /**
     * Remove aspas e espaços dos campos de uma linha.
     *
     * @param fields array de campos extraídos do CSV
     */
    public static void cleanFields(String[] fields) {
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].replace("\"", "").trim();
        }
    }
}