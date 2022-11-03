package org.vinogradov.mysupport;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static void filesWalk(Path directory, Consumer<Path> pathConsumer) {
        try {
            List<Path> list = Files.list(directory).collect(Collectors.toList());
            for (Path filePathEntry : list) {
                if (Files.isDirectory(filePathEntry)) {
                    filesWalk(filePathEntry, pathConsumer);
                } else {
                    pathConsumer.accept(filePathEntry);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path createNewPath(Path src, Path srcEntryFile, Path dst) {
        String pathSrc = src.toString();
        String pathSrcEntryFile = srcEntryFile.toString();
        String pathDst = dst.toString();
        String partString = pathSrcEntryFile.replace(pathSrc, "");
        String newPath = pathDst.concat(partString);
        return Paths.get(newPath);
    }

    public static void createNewDirectory(Path path) {
        Path pathChild = null;
        if (!Files.exists(path)) {
            pathChild = path;
            createNewDirectory(path.getParent());
        }
        try {
            if (pathChild != null) {
                Files.createDirectory(pathChild);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteUserFile(Path srcPath) {
        try (Stream<Path> walk = Files.walk(srcPath)) {
            walk.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            System.out.println("Удаленный файл или папка: " + srcPath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }




}
