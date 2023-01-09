package tools;

import enums.ArchivingTypes;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.jar.*;
import java.util.zip.*;

public class ArchivingLib {
    public static String packJar(String fileName, Path tmpDir) throws IOException {
        String newPath  = ToolsLib.formPathToTmpDir(tmpDir, fileName).replace("\\", "/") + "." + ArchivingTypes.jar;
        JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(ToolsLib.formPathToTmpDir(tmpDir, fileName).replace("\\", "/") + "." + ArchivingTypes.jar));
        jarOut.setLevel(Deflater.DEFAULT_COMPRESSION);
        JarEntry entry = new JarEntry(ToolsLib.deletePath(fileName));
        jarOut.putNextEntry(entry);
        FileInputStream inputStream = new FileInputStream(fileName);
        byte[] buffer = new byte[inputStream.available()];
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            jarOut.write(buffer, 0, len);
        }
        jarOut.closeEntry();
        inputStream.close();
        jarOut.close();
        return ".jar";
    }

    public static  void unpackJar(String jarName) {
        byte[] buffer = new byte[1024];
        try {
            JarInputStream jarIn = new JarInputStream(new FileInputStream(jarName));
            ZipEntry entry = jarIn.getNextEntry();
            String nextFileName;
            while (entry != null) {
                nextFileName = entry.getName();
                File nextFile = new File(nextFileName);
                if (entry.isDirectory()) {
                    nextFile.mkdir();
                } else {
                    FileOutputStream fileOut = new FileOutputStream(nextFile);
                    int length;
                    while ((length = jarIn.read(buffer)) > 0) {
                        fileOut.write(buffer, 0, length);
                    }
                }
                entry = jarIn.getNextEntry();
            }
            jarIn.closeEntry();
            jarIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void packZip(ArrayList<String> fileNames, String zipName) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipName + ".zip"));
        zipOut.setLevel(Deflater.DEFAULT_COMPRESSION);
        for (String file : fileNames) {
            zipOut.putNextEntry(new ZipEntry(file));
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            int len;
            while ((len = inputStream.read(buffer)) > 0)
                zipOut.write(buffer, 0, len);
            zipOut.closeEntry();
            inputStream.close();
        }
        zipOut.close();
    }


    public static  void unpackingZip(String zipName) {
        byte[] buffer = new byte[1024];
        String dstDirectory = zipName.substring(0, zipName.lastIndexOf("."));
        File dstDir = new File(dstDirectory);
        if (!dstDir.exists()) {
            dstDir.mkdir();
        }
        try {
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipName));
            ZipEntry entry = zipIn.getNextEntry();
            String nextFileName;
            while (entry != null) {
                nextFileName = entry.getName();
                File nextFile = new File(dstDirectory + File.separator + nextFileName);
                if (entry.isDirectory()) {
                    nextFile.mkdir();
                } else {
                    new File(nextFile.getParent()).mkdir();
                    FileOutputStream fileOut = new FileOutputStream(nextFile);
                    int length;
                    while ((length = zipIn.read(buffer)) > 0) {
                        fileOut.write(buffer, 0, length);
                    }
                }
                entry = zipIn.getNextEntry();
            }
            zipIn.closeEntry();
            zipIn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

