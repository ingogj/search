package org.ingoltda;

import java.io.IOException;
import java.util.Set;

import static org.ingoltda.SearchParallelStreamService.*;

public class Main {
    public static void main(String[] args) throws IOException {
        final String DATA_PATH = "data/";
        long start = System.currentTimeMillis();

        Set<String> result = searchWords(getFiles(DATA_PATH), args);
        System.out.println(result.size() + " " + result);
        System.out.println(System.currentTimeMillis() - start + " ms");
    }
}