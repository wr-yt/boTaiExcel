package com.botai;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * 执行系统命令工具类
 *
 * @author Storm
 */
public class CommandUtils {

    private static final String DEFAULT_CHARSET = "GBK";

    /**
     * 执行指定命令
     *
     * @param command 命令
     * @return 命令执行完成返回结果
     * @throws IOException 失败时抛出异常，由调用者捕获处理
     */
    public static String exeCommand(String command) throws IOException {
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            int exitCode = exeCommand(command, out);
            if (exitCode == 0) {
                System.out.println("命令运行成功！");
            } else {
                System.out.println("命令运行失败！");
            }
            return out.toString(DEFAULT_CHARSET);
        }
    }

    /**
     * 执行指定命令，输出结果到指定输出流中
     *
     * @param command 命令
     * @param out     执行结果输出流
     * @return 执行结果状态码：执行成功返回0
     * @throws ExecuteException 失败时抛出异常，由调用者捕获处理
     * @throws IOException      失败时抛出异常，由调用者捕获处理
     */
    public static int exeCommand(String command, OutputStream out) throws ExecuteException, IOException {
        CommandLine commandLine = CommandLine.parse(command);
        PumpStreamHandler pumpStreamHandler = null;
        if (null == out) {
            pumpStreamHandler = new PumpStreamHandler();
        } else {
            pumpStreamHandler = new PumpStreamHandler(out);
        }

        // 设置超时时间为10秒
        ExecuteWatchdog watchdog = new ExecuteWatchdog(10000);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        executor.setWatchdog(watchdog);

        return executor.execute(commandLine);
    }

    public static void delExcelsFiles() throws IOException {

        EPlatform oSname = OSinfo.getOSname();
        System.out.println("： " + oSname);
        if (OSinfo.isMacOS() || OSinfo.isMacOSX() || OSinfo.isLinux()) {
            String result = exeCommand("rm -rf excels");
//            String result = exeCommand("rm -rf " + copiedFolder.getAbsolutePath());
            System.out.println("执行结果：" + result);
        }
        if (OSinfo.isWindows()) {
            String result = exeCommand("rd /s/q ./excels");
            System.out.println("执行结果：" + result);
        }
    }


    /**
     * 根据路径删除指定的目录，无论存在与否
     *
     * @param sPath 要删除的目录path
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        // 判断是否为文件
        if (file.isFile()) {  // 为文件时调用删除文件方法
            return deleteFile(sPath);
        } else {  // 为目录时调用删除目录方法
            return deleteDirectory(sPath);
        }
    }

    /**
     * 删除目录以及目录下的文件
     *
     * @param sPath 被删除目录的路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File("excels");
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件path
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static void main(String[] args) throws IOException {
//        DeleteFolder("excels");
        System.out.println(exeCommand("pwd"));
        System.out.println(exeCommand("ls"));
        System.out.println(exeCommand("rm -rf target"));
        System.out.println(exeCommand("ls"));
    }


}