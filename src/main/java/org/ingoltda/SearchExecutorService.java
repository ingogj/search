package org.ingoltda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchExecutorService {

    public static Set<String> getFiles(String folder) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(folder))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    public static Set<String> searchWords(Set<String> files, String[] words) throws ExecutionException, InterruptedException {
        int threads = Runtime.getRuntime().availableProcessors();
        //ystem.out.println("Threads available: "+ threads);
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Future<String>> futures = new ArrayList<>();

        for (String file : files) {
            //long start = System.currentTimeMillis();
            Callable<String> task = () -> {
                String contents = new String(Files.readAllBytes(Paths.get(file)));
                for (String word : words) {
                    if (!contents.contains(word)) {
                        return null;
                    }
                }
                return file;
            };
            //System.out.println(System.currentTimeMillis() - start);
            futures.add(executor.submit(task));
        }

        Set<String> result = new HashSet<>();
        for (Future<String> future : futures) {
            String file = future.get();
            if (file != null) {
                result.add(file);
            }
        }

        executor.shutdown();
        return result;
    }
}
