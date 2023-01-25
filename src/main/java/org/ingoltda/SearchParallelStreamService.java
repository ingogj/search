package org.ingoltda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe que oferece serviços de busca em arquivos utilizando streams paralelos.
 *
 * A classe SearchParallelStreamService contém dois métodos públicos getFiles e
 * searchWords que são utilizados para obter uma lista de arquivos em uma pasta específica e
 * para buscar arquivos que contenham todas as palavras especificadas, respectivamente.
 * Esses métodos utilizam streams paralelos para melhorar o desempenho das operações de busca.
 * A classe retorna conjunto ordenado Set para evitar retornos de arquivos duplicados (o que não vai ocorrer).
 */
public class SearchParallelStreamService {

    /**
     * Obtém uma lista de arquivos em uma pasta específica.
     * @param folder o caminho para a pasta
     * @return uma lista de arquivos na pasta
     * @throws IOException se ocorrer um erro ao ler os arquivos
     */
    public static Set<String> getFiles(String folder) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(folder))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    /**
     * Busca arquivos que contenham todas as palavras especificadas.
     * @param files a lista de arquivos a serem pesquisados
     * @param words as palavras a serem pesquisadas
     * @return uma lista ordenada alfabeticamente de arquivos que contenham todas as palavras especificadas
     * @throws IOException se ocorrer um erro ao ler os arquivos
     */
    public static Set<String> searchWords(Set<String> files, String[] words) throws IOException {
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
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
