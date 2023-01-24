package org.ingoltda;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.ingoltda.SearchService.*;

public class Main4 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        final String DATA_PATH = "data/";
        long start = System.currentTimeMillis();
        //List<String> files = getFiles(DATA_PATH);
        Set<String> result = searchWords(getFiles(DATA_PATH), args);
        System.out.println(result.size() + " " + result);
        System.out.println(System.currentTimeMillis() - start);
    }
}
