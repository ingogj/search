package org.ingoltda;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main2 {
    public static void main(String[] args) throws IOException, InterruptedException {


        long start = System.currentTimeMillis();
        //parse the arguments
        String[] searchStrings = Arrays.copyOfRange(args, 0, args.length - 1);
        String folderPath = "data1/";
        //Create the thread pool
        ForkJoinPool executor = ForkJoinPool.commonPool();
        ConcurrentHashMap<String, Boolean> foundFile = new ConcurrentHashMap<>();
        //iterate through the files
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".txt"))
                    .forEach(p -> {
                        String fileName = p.toString();
                        foundFile.put(fileName,false);
                        executor.submit(() -> {
                            if (searchInFile(p, searchStrings)) {
                                foundFile.put(fileName,true);
                            }
                        });
                    });
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        foundFile.forEach((k,v)->{
            if(v)
                System.out.println("Search strings found in file: " + k);
        });

        long total = System.currentTimeMillis() - start;
        System.out.println(total);
    }

    //Search for the strings in the file using Boyer-Moore Algorithm
    public static boolean searchInFile(Path path, String[] searchStrings) {
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String data = br.lines().collect(Collectors.joining("\n"));
            for (String searchString : searchStrings) {
                if (!boyerMoore(data, searchString)) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean boyerMoore(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int[] badChar = new int[256];
        Arrays.fill(badChar, -1);
        for (int i = 0; i < m; i++) {
            badChar[pattern.charAt(i)] = i;
        }
        int s = 0;
        while (s <= n - m) {
            int j = m - 1;
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j)) {
                j--;
            }
            if (j < 0) {
                return true;
            } else {
                s += Math.max(1, j - badChar[text.charAt(s + j)]);
            }
        }
        return false;
    }
}