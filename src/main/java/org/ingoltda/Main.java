package org.ingoltda;

import org.ingoltda.utils.ArgsUtil;

import java.io.*;
import java.nio.file.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //parse the arguments
        String[] searchStrings = Arrays.copyOfRange(args, 0, args.length - 1);
        //String words = ArgsUtil.concatenateArgs(args);
        String folderPath = "data1/"; //args[args.length - 1];
        //Create the thread pool
        ExecutorService executor = Executors.newFixedThreadPool(8);
        AtomicReference<String> foundFile = new AtomicReference<>();
        //iterate through the files
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".txt"))
                    .forEach(p -> executor.submit(() -> {
                        if (searchInFile(p, searchStrings)) {
                            foundFile.set(p.toString());
                        }
                    }));
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        if(foundFile.get()!=null) {
            System.out.println("Search strings found in file: " + foundFile.get());
        } else {
            System.out.println("Search strings not found in any file");
        }
    }
    //Search for the strings in the file
    public static boolean searchInFile(Path path, String[] searchStrings) {
        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            FileChannel fc = fis.getChannel();
            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            buffer.load();
            String data = new String(buffer.array());

            if(path.getFileName().toString() == "a-cowboy-needs-a-horse.txt"){
                int i = 0;
            }

            for (String searchString : searchStrings) {
                if (!data.contains(searchString)) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}