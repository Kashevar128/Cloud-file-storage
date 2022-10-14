package org.vinogradov.mysupport;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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

    public static void saw(Path path, Consumer<byte[]> filePartConsumer) {
        byte[] filePart = new byte[MB_100];
        try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
            while (fileInputStream.read(filePart) != -1) {
                filePartConsumer.accept(filePart);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
