package org.vinogradov.common.commonClasses;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

    public static List<String> generatePaths(Path srcPathDirectory, Path dstPathDirectory) {
        List<String> listPaths = new ArrayList<>();
        Consumer<Path> pathEntryConsumer = (srcPathEntryFile) -> {
            String newDstPathFile = createNewDstPath(srcPathDirectory, srcPathEntryFile, dstPathDirectory);
            listPaths.add(newDstPathFile);
        };
        filesWalk(srcPathDirectory, pathEntryConsumer);
        return listPaths;
    }

    public static String editingPath(Path path, String name) {
        String strPath = path.toString();
        int s = strPath.indexOf(name);
        String newPath = strPath.substring(s);
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

    private static void filesWalk(Path directory, Consumer<Path> pathConsumer) {
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

    private static String createNewDstPath(Path srcPathDirectory, Path srcPathEntryFile, Path dstPathDirectory) {
        String pathSrc = srcPathDirectory.toString();
        String pathSrcEntryFile = srcPathEntryFile.toString();
        String pathDst = dstPathDirectory.toString();
        String partString = pathSrcEntryFile.replace(pathSrc, "");
        String newPath = pathDst.concat(partString);
        return newPath;
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

    public static long sumSizeFiles(Path directory) {
        long sumSize = 0;
        List<Long> sizeList = new ArrayList<>();
        Consumer<Path> sumSizeFile = (path) -> {
            try {
                Long size = Files.size(path);
                sizeList.add(size);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        filesWalk(directory, sumSizeFile);
        for (Long sizeFile : sizeList) {
            sumSize += sizeFile;
        }
        return sumSize;
    }

    private static byte[] getNewByteArr(byte[] filePart, int size) {
        byte[] newArr = new byte[size];
        System.arraycopy(filePart, 0, newArr, 0, size);
        return newArr;
    }


}
