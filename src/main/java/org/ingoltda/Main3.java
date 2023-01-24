package org.ingoltda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main3 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        final String DATA_PATH = "data/";

        List<String> files = getFiles(DATA_PATH);
        List<String> result = searchWords(files, args);
        System.out.println(result.size() + " " + result);
        System.out.println(System.currentTimeMillis() - start);
    }

    private static List<String> getFiles(String folder) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(folder))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }

    private static List<String> searchWords(List<String> files, String[] words) throws IOException {
        return files.parallelStream()
                .filter(file -> {
                    try {
                        String contents = new String(Files.readAllBytes(Paths.get(file)));
                        for (String word : words) {
                            if (!contents.contains(word)) {
                                return false;
                            }
                        }
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
}
