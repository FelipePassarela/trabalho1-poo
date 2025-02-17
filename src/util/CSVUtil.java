package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CSVUtil {
    public static BufferedReader getReader(String filePath) throws IOException {
        return Files.newBufferedReader(Paths.get(filePath), StandardCharsets.ISO_8859_1);
    }

    public static Map<String, Integer> parseHeader(String headerLine) {
        Map<String, Integer> map = new HashMap<>();
        String[] headers = headerLine.split(";");
        for (int i = 0; i < headers.length; i++) {
            headers[i] = headers[i].replace("\"", "").trim();
            map.put(headers[i], i);
        }
        return map;
    }
    
    public static String[] cleanFields(String[] fields) {
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].replace("\"", "").trim();
        }
        return fields;
    }
}