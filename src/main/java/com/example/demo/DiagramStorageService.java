package com.example.demo;



import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.zip.Deflater;

@Service
public class DiagramStorageService {

    public String renderAndSaveDiagram(String rawMermaidText, String fileNameWithoutExtension) {
        if (rawMermaidText == null || rawMermaidText.trim().isEmpty()) {
            return "EMPTY_DIAGRAM_SPECIFICATION";
        }
        
        try {
            String cleanText = rawMermaidText.trim();

            // Determine the routing path token
            String firstLine = cleanText.split("\n")[0].toLowerCase().trim();
            String diagramType = "mermaid";
            if (firstLine.contains("erdiagram")) {
                diagramType = "erd";
            }

            // Deflate compression
            byte[] inputBytes = cleanText.getBytes(StandardCharsets.UTF_8);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
            deflater.setInput(inputBytes);
            deflater.finish();
            
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                os.write(buffer, 0, count);
            }
            deflater.end();
            byte[] compressedBytes = os.toByteArray();

            String encodedText = Base64.getUrlEncoder().encodeToString(compressedBytes);
            String krokiUrl = "https://kroki.io" + diagramType + "/svg/" + encodedText;
            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(krokiUrl))
                    .GET()
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            Path resourcesPath = System.getenv("AWS_LAMBDA_FUNCTION_NAME") != null ? Paths.get("/tmp/") : Paths.get("src", "main", "resources", "static", "diagrams");
          //  Path resourcesPath = Paths.get("src", "main", "resources", "static", "diagrams");
            if (!Files.exists(resourcesPath)) {
                Files.createDirectories(resourcesPath);
            }

            // 🚀 THE DEFENSIVE FIX: If Kroki breaks (400, 500, etc.), handle it gracefully instead of crashing
            if (response.statusCode() != 200) {
                String errorBody = new String(response.body(), StandardCharsets.UTF_8);
                System.err.println("❌ [Kroki Render Warning] Diagram " + fileNameWithoutExtension + " failed with status " + response.statusCode() + ": " + errorBody);
                
                // Write out just the raw text source configuration file so the UI can still render it client-side
                Files.writeString(resourcesPath.resolve(fileNameWithoutExtension + "-raw.mmd"), cleanText);
                return "FALLBACK_CLIENT_SIDE_ONLY";
            }

            // Standard successful image export paths
            Path imageFile = resourcesPath.resolve("/tmp/"+fileNameWithoutExtension + ".svg");
            Files.write(imageFile, response.body());

            Path rawSourceFile = resourcesPath.resolve("/tmp/"+fileNameWithoutExtension + ".mmd");
            Files.writeString(rawSourceFile, cleanText);

            return imageFile.toAbsolutePath().toString();

        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Critical IO exception during diagram processing: " + e.getMessage());
            return "IO_RENDER_ERROR";
        }
    }
}
