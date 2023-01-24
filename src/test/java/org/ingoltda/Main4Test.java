package org.ingoltda;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.ingoltda.SearchService.*;

public class Main4Test {
    @Test
    public void testGetFiles() throws IOException {
        String folder = "data/";
        Set<String> files = getFiles(folder);
        Assert.assertEquals(11876, files.size());
    }
    @Test
    public void testSearchWords() throws IOException, ExecutionException, InterruptedException {
        String folder = "data/";
        String[] words = {"walt", "disney"};
        Set<String> files = getFiles(folder);
        Set<String> result = searchWords(files, words);
        Assert.assertEquals(53, result.size());
    }
}