package WFA;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class WordFrequencyAnalyzer {

    public static void main(String[] args) {
        try {

            ClassLoader classLoader = WordFrequencyAnalyzer.class.getClassLoader();
            URL resource = classLoader.getResource("input.txt");
            if (resource == null) {
                throw new FileNotFoundException("input.txt not found in resources");
            }
            Path inputPath = Paths.get(resource.toURI());
            String content = new String(Files.readAllBytes(inputPath));


            String cleaned = content.toLowerCase().replaceAll("[^a-zA-Z\\s]", " ");
            String[] words = cleaned.split("\\s+");

            // Count word frequencies
            Map<String, Integer> wordCount = new HashMap<>();
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }


            List<Map.Entry<String, Integer>> sorted = new ArrayList<>(wordCount.entrySet());
            sorted.sort((a, b) -> b.getValue().compareTo(a.getValue()));


            Path outputDir = Paths.get("target/output");
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }


            Path outputPath = outputDir.resolve("output.txt");
            try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
                for (Map.Entry<String, Integer> entry : sorted) {
                    writer.write(entry.getKey() + ": " + entry.getValue());
                    writer.newLine();
                }
            }

            System.out.println("Word frequency analysis complete. Output written to target/output/output.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}