package org.ingoltda;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.ingoltda.Main4.getFiles;
import static org.ingoltda.Main4.searchWords;

public class Main4Test {
    @Test
    public void testGetFiles() throws IOException {
        String folder = "data/";
        List<String> files = getFiles(folder);
        Assert.assertTrue(files.size() > 0);
    }
    @Test
    public void testSearchWords() throws IOException, ExecutionException, InterruptedException {
        String folder = "data/";
        String[] words = {"walt", "disney"};
        List<String> files = getFiles(folder);
        List<String> result = searchWords(files, words);
        // Assert that the result contains at least one file
        Assert.assertTrue(result.size() > 0);
    }
}