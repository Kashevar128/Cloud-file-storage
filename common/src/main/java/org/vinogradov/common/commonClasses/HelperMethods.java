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

    public static void split(Path path, Consumer<byte[]> filePartConsumer) {
        byte[] filePart = new byte[Constants.MB_10];
        try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
            int size;
            while ((size = fileInputStream.read(filePart)) != -1) {
                if (size < filePart.length) {
                    filePart = getNewByteArr(filePart, size);
                } else {
                    filePart = getNewByteArr(filePart, filePart.length);
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

    public static void sendFile(Path dstPath, Path srcPath, FileInfo selectedFile, BiConsumer<Path, Path> biConsumer) {
        FileInfo.FileType fileType = selectedFile.getType();
        switch (fileType) {
            case FILE -> {
                biConsumer.accept(srcPath, dstPath);
            }

            case DIRECTORY -> {
                HelperMethods.filesWalk(srcPath, (filePathEntry) -> {
                        Path newFilePathEntry = HelperMethods.createNewPath(srcPath, filePathEntry, dstPath);
                        biConsumer.accept(filePathEntry, newFilePathEntry);
                });
            }

        }
    }

    private static byte[] getNewByteArr(byte[] filePart, int size) {
        byte[] newArr  = new byte[size];
        System.arraycopy(filePart, 0, newArr, 0, size );
        return newArr;
    }


}
