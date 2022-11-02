package org.vinogradov.mysupport;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.vinogradov.mysupport.Constants.MB_100;

public class HelperMethods {

    public static String delSpace(String str) {
        String newStr = str.trim();
        newStr = newStr.replaceAll(" ", "");
        return newStr;
    }

    public static List<String> generateStringList(Path path) {
        try {
            List<String> listPaths = Files.list(path).map(Objects::toString).collect(Collectors.toList());
            listPaths.add(path.toString());
            return listPaths;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String editingPath(String path, String name) {
        int s = path.indexOf(name);
        String newPath = path.substring(s);
        return newPath;
    }

    public static void split(Path path, long sizeFile, Consumer<byte[]> filePartConsumer) {
        byte[] filePart = new byte[MB_100];
        byte[] lastFilePart = null;
        System.out.println(sizeFile);
        long numberOfPackages = sizeFile / MB_100;
        long size = numberOfPackages * MB_100;
        if (size < sizeFile) {
            numberOfPackages += 1;
            lastFilePart = new byte[(int) (sizeFile - size)];
        };
        System.out.println(numberOfPackages);
        try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
            int counter = 0;
            while (fileInputStream.read(filePart) != -1) {
                counter++;
                if (counter == numberOfPackages) {
                    filePartConsumer.accept(lastFilePart);
                    break;
                }
                filePartConsumer.accept(filePart);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
