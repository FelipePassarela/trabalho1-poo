import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VotoReader {
    
    public static Set<Voto> readVotos(String filePath) {
        Set<Voto> votos = new HashSet<>();

        try (
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
            BufferedReader br = new BufferedReader(isr);
        ) {
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
        return voto.getCargo() == Cargo.VEREADOR && 
            voto.getCodigoMunicipio().equals("57053") && // Debug
            isNumValido;
    }
}
