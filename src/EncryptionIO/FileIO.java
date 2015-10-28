package EncryptionIO;

import Exceptions.InvalidPathException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ori on 20/09/2015.
 */
public class FileIO {

    public static int[] readFromFile(String filePath){
        File file = new File(filePath);
        int[] array = new int[(int) file.length()];
        int i = 0;
        try {
            FileInputStream isr = new FileInputStream(filePath);
            int data = isr.read();
            while(data != -1) {
                array[i++] = data;
                data = isr.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void writeToEncryptedFile(int[] array, String filePath){
        try {
            DataOutputStream osw = new DataOutputStream(new FileOutputStream(filePath));
            for(int i = 0; i<array.length; i++) {
                if(array[i] != 0){
                    int value = array[i];
                    osw.writeInt(value);
                }
            }
            osw.close();

            System.out.println("file location: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] readFromEncryptedFile(String filePath){
        ArrayList<Integer> list = new ArrayList<Integer>();
        int[] array = null;
        try {
            DataInputStream osw = new DataInputStream(new FileInputStream(filePath));
            while(osw.available() > 0){
                int data = osw.readInt();
                list.add(data);
            }
            osw.close();

            array = new int[list.size()];
            for(int i = 0; i<array.length;i++)
                array[i] = list.get(i);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return array;
    }

    public static void writeToFile(int[] array, String path){
        try {
            DataOutputStream osw = new DataOutputStream(new FileOutputStream(path));
            for(int i = 0; i<array.length; i++) {
                if(array[i] != 0){
                    int value = array[i];
                    osw.write(value);
                }
            }
            osw.close();

            System.out.println("File location: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeKeyFile(Integer key, String path){
        try {
            FileWriter osw = new FileWriter(path);
            osw.write(key.toString());
            osw.close();

            System.out.println("Key file location: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int readFileKey(String filePath){
        try {
            int x = new Scanner(new File(filePath)).nextInt();
            return x;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean validateFilePath(String path) throws InvalidPathException {
        File file = new File(path);

        if (!file.exists() && !file.isFile()) {
            throw new InvalidPathException("Path " + path + " for file not found!");
        }

        return true;
    }

    public static boolean validateDirectoryPath(String path) throws InvalidPathException {
        File file = new File(path);

        if (!file.isDirectory()) {
            throw new InvalidPathException("Path " + path + " for directory not found!");
        }

        return true;
    }

    public static File[] getTextFilesInDir(String dir){
        File[] files = null;

        try {
        File d = new File(dir);
        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        };

        files = d.listFiles(textFilter);
            System.out.println("Text files found in directory: ");
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.print("directory:");
            } else {
                System.out.print("     file:");
            }

                System.out.println(file.getCanonicalPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    public static String getFileName(String path){
        return new File(path).getName();
    }
}
