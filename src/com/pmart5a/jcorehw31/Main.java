package com.pmart5a.jcorehw31;

import java.io.*;

public class Main {
    static private final StringBuilder builder = new StringBuilder();

    static void makeDirectory(String pathDir) {
        File dir = new File(pathDir);
        try {
            builder.append(dir.mkdirs() ? "Стуктура каталогов создана " + dir.getCanonicalPath() + "\n" :
                    "Ошибка при создании структуры каталогов " + dir.getCanonicalPath() +
                            ". (Возможно, каталоги уже существуют).\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            builder.append(ex.getMessage());
        }
    }

    static void createFile(String nameFile) {
        File newFile = new File(nameFile);
        try {
            builder.append(newFile.createNewFile() ? "Файл создан " + newFile.getCanonicalFile() + "\n" :
                    "Ошибка при создании файла " + newFile.getCanonicalFile() + ". (Возможно, файл уже существует).\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            builder.append(ex.getMessage());
        }
    }

    public static void writeLog(String nameFile, String logOut) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nameFile, true))) {
            bw.write(logOut);
            bw.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            builder.append(ex.getMessage());
        }
    }

    static void saveLog(String nameFile) {
        String logOut = builder.toString();
        writeLog(nameFile, logOut);
    }

    public static void main(String[] args) {
        makeDirectory("./Games/src/main");
        makeDirectory("./Games/src/test");
        makeDirectory("./Games/res/drawables");
        makeDirectory("./Games/res/vectors");
        makeDirectory("./Games/res/icons");
        makeDirectory("./Games/savegames");
        makeDirectory("./Games/temp");
        createFile("./Games/src/main/Main.java");
        createFile("./Games/src/main/Utils.java");
        createFile("./Games/temp/temp.txt");
        saveLog("./Games/temp/temp.txt");
    }
}