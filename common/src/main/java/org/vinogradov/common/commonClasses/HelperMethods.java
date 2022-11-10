package org.vinogradov.common.commonClasses;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        int count = 0;
        byte[] filePart = new byte[Constants.MB_100];
        byte[] additionalFilePart = null;
        int numberOfPackages = (int) (sizeFile / (long) Constants.MB_100);
        long newSize = (long) numberOfPackages * (long) Constants.MB_100;

        if (sizeFile > newSize) {
            additionalFilePart = new byte[(int) (sizeFile - newSize)];
            numberOfPackages++;
        }

        try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
            while (fileInputStream.read(filePart) != -1) {
                count++;
                if (count == numberOfPackages && additionalFilePart != null) {
                    System.arraycopy(filePart, 0, additionalFilePart, 0, additionalFilePart.length);
                    filePart = additionalFilePart;
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

    public static void createNewDirectoryRecursion(Path path) {
        Path pathChild = null;
        if (!Files.exists(path)) {
            pathChild = path;
            createNewDirectoryRecursion(path.getParent());
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

    public static boolean createNewUserFile(Path srcPath) {
        if (!srcPath.toFile().exists()) {
            try {
                Files.createDirectory(srcPath);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }


}
