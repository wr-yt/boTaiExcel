package com.botai;

import java.io.*;
import java.util.List;

/**
 * 关于文件的复制
 */
public class CopyFile {
    /**
     * 将指定路径下的文件复制到指定文件夹中
     */
    public static String copyFiles(String sourceFileName, String newname) {
        //被复制文件
        File copyFile = new File(sourceFileName);
        //指定文件夹路径
        String copiedFolderPath = "excels";
        //指定文件夹
        File copiedFolder = new File(copiedFolderPath);

        try {
            //判断文件夹是否存在,不存在需要创建，否则无法正常创建该文件夹下的文件
            if (!copiedFolder.exists()) {
                copiedFolder.mkdirs();
            }
            //复制后文件的路径与命名
            String copiedFilePath = copiedFolderPath + "/" + newname + ".xls";
            File copiedFile = new File(copiedFilePath);
            if (!copiedFile.exists()) {
                copiedFile.createNewFile();
            }
            System.err.println(copiedFile.getAbsolutePath());
            //复制内容到指定文件中
            copyFileContent(copyFile, copiedFile);
            return copiedFile.getAbsolutePath();
        } catch (IOException e) {
            System.out.println("error" + e);
        }
        return null;
    }

    public static void copyFileContent(File fromFile, File toFile) throws IOException {
        FileInputStream ins = new FileInputStream(fromFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n = 0;
        while ((n = ins.read(b)) != -1) {
            out.write(b, 0, n);
        }
        ins.close();
        out.close();
    }

}