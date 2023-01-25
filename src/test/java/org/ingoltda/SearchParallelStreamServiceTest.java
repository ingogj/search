package org.ingoltda;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.ingoltda.SearchParallelStreamService.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Files.class)
public class SearchParallelStreamServiceTest {

    @Test
    public void testGetFiles() throws IOException {
        // Arrange
        // Cria uma pasta de teste com alguns arquivos
        Path testFolder = Files.createTempDirectory("test");
        Path file1 = testFolder.resolve("file1.txt");
        Files.createFile(file1);
        Path file2 = testFolder.resolve("file2.txt");
        Files.createFile(file2);
        Path file3 = testFolder.resolve("file3.txt");
        Files.createFile(file3);

        // Act
        // Chama o método getFiles() com o caminho para a pasta de teste
        Set<String> files = getFiles(testFolder.toString());

        // Assert
        // Verifica se a lista de arquivos retornada inclui os arquivos criados
        Assert.assertTrue(files.contains(file1.toString()));
        Assert.assertTrue(files.contains(file2.toString()));
        Assert.assertTrue(files.contains(file3.toString()));

        // Limpa a pasta de teste
        Files.delete(file1);
        Files.delete(file2);
        Files.delete(file3);
        Files.delete(testFolder);
    }

    @Test
    public void testSearchWords() throws IOException {
        // Arrange
        // Cria alguns arquivos de teste com conteúdo específico
        Path testFolder = Files.createTempDirectory("test");
        Path file1 = testFolder.resolve("file1.txt");
        Files.write(file1, "ingo gums junior".getBytes());
        Path file2 = testFolder.resolve("file2.txt");
        Files.write(file2, "ingo walt disney".getBytes());
        Path file3 = testFolder.resolve("file3.txt");
        Files.write(file3, "ingo disney junior walt".getBytes());

        // Act
        // Chama o método searchWords() com a lista de arquivos e palavras de busca
        Set<String> files = getFiles(testFolder.toString());
        Set<String> result = searchWords(files, new String[] {"disney", "walt"});

        // Assert
        // Verifica se a lista de arquivos retornada inclui somente o arquivo 1
        assertEquals(2, result.size());
        Assert.assertTrue(result.contains(file2.toString()));
        Assert.assertTrue(result.contains(file3.toString()));

        // Limpa a pasta de teste
        Files.delete(file1);
        Files.delete(file2);
        Files.delete(file3);
        Files.delete(testFolder);
    }
    @Test
    public void testSearchWordsIOException() throws IOException {
        // Arrange
        Set<String> files = new HashSet<>();
        files.add("file1.txt");
        String[] words = {"word1", "word2"};

        //Mock the Files class
        PowerMockito.mockStatic(Files.class);
        PowerMockito.when(Files.readAllBytes(any(Path.class))).thenThrow(IOException.class);

        // Act
        Set<String> result = searchWords(files, words);

        // Assert
        assertEquals(0, result.size());
    }
}
