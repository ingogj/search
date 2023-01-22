package org.ingoltda.utils;

public class ArgsUtil {

    public static String concatenateArgs(String[] args){
        StringBuilder result = new StringBuilder();
        for(String arg : args){
            result.append(arg).append(" ");
        }
        return result.delete(result.length()-1, result.length()).toString();
    }
}
