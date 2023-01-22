package org.ingoltda;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main4 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        String folder = "data/";
        String[] words = {"disney", "walt"};

        List<String> files = getFiles(folder);
        List<String> result = searchWords(files, words);
        System.out.println(result.size() + " " + result);
        System.out.println(System.currentTimeMillis() - start);
    }

    public static List<String> getFiles(String folder) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(folder))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }

    public static List<String> searchWords(List<String> files, String[] words) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<String>> futures = new ArrayList<>();

        for (String file : files) {
            Callable<String> task = () -> {
                //try {
                    String contents = new String(Files.readAllBytes(Paths.get(file)));
                    for (String word : words) {
                        if (!contents.contains(word)) {
                            return null;
                        }
                    }
                    return file;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
                //}
            };
            futures.add(executor.submit(task));
        }

        List<String> result = new ArrayList<>();
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
