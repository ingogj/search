//package org.ingoltda;
//
//import org.apache.http.HttpHost;
////import org.elasticsearch.client.RestClient;
////import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.*;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//import static org.ingoltda.Main4.getFiles;
//
//public class Main5 {
//    public static void main(String[] args) throws IOException {
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("localhost", 9200, "http")));
//        Elasticsearch elasticsearch = new Elasticsearch(client);
//
//        String folder = "data/";
//        String[] words = {"disney", "walt"};
//
//        List<String> files = getFiles(folder);
//        indexFiles(elasticsearch, files);
//        List<String> result = searchWords(elasticsearch, words);
//        System.out.println(result);
//    }
//
//    private static void indexFiles(Elasticsearch elasticsearch, List<String> files) throws IOException {
//        for (String file : files) {
//            elasticsearch.indexFile(file);
//        }
//    }
//
//    private static List<String> searchWords(Elasticsearch elasticsearch, String[] words) throws IOException {
//        return elasticsearch.searchWords(words);
//    }
//}