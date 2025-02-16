import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CandidatoReader {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Set<Candidato> readCandidatos(String filePath) {
        Set<Candidato> candidatos = new HashSet<>();

        try (
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
            BufferedReader br = new BufferedReader(isr);
        ) {
            String headerLine = br.readLine();
            Map<String, Integer> headerIndexMap = criaMap(headerLine);
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");

                for (int i = 0; i < fields.length; i++) {
                    fields[i] = fields[i].replace("\"", "").trim();
                }

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
        
                Candidato candidato = new Candidato(
                    codigoMunicipio, cargo, numero, 
                    nomeUrna, partido, numFederacao, 
                    dataNascimento, situacao, genero
                );
                candidatos.add(candidato);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de candidatos: " + e.getMessage());
        }

        return candidatos;
    }

    public static Map<String, Integer> criaMap(String headerLine) {
        Map<String, Integer> map = new HashMap<>();
        String[] headers = headerLine.split(";");

        for (int i = 0; i < headers.length; i++) {
            headers[i] = headers[i].replace("\"", "").trim();
            map.put(headers[i], i);
        }

        return map;
    }
}
