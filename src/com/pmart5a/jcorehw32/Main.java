package com.pmart5a.jcorehw32;

import com.pmart5a.jcorehw32.myclass.GameProgress;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.pmart5a.jcorehw31.Main.writeLog;

public class Main {
    static private final List<String> listNameFile = new ArrayList<>();
    static private final StringBuilder builder = new StringBuilder();

    static void saveGame(String pathFilesSave) {
        if (GameProgress.listGameProgress.isEmpty()) {
            System.out.println("Нечего записывать в файл. Отсутствуют сохранённые данные.");
        } else {
            for (int i = 0; i < GameProgress.listGameProgress.size(); i++) {
                String nameFile = pathFilesSave + "save" + (i + 1) + ".dat";
                try (FileOutputStream fos = new FileOutputStream(nameFile);
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(GameProgress.listGameProgress.get(i));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    builder.append(ex.getMessage());
                }
                String nameFileCon = getCanonicalFileName(nameFile);
                if (checkExistsFile(nameFileCon)) {
                    builder.append("Файл ")
                            .append(nameFileCon)
                            .append(" записан на диск.\n");
                    listNameFile.add(nameFileCon);
                } else {
                    builder.append("По указаному пути ")
                            .append(nameFileCon)
                            .append(" файл отсутствует.\n");
                }
            }
        }
    }

    static void zipFiles(String pathFileZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathFileZip))) {
            for (String pathFile : listNameFile) {
                int lastSlash = pathFile.lastIndexOf(File.separator) + 1;
                String nameFile = pathFile.substring(lastSlash);
                FileInputStream fis = new FileInputStream(pathFile);
                ZipEntry entry = new ZipEntry(nameFile);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
                builder.append("Файл ")
                        .append(pathFile)
                        .append(" добавлен в архив.\n");
            }
            String pathFileZipCon = getCanonicalFileName(pathFileZip);
            if (checkExistsFile(pathFileZipCon)) {
                builder.append("Файл ")
                        .append(pathFileZipCon)
                        .append(" записан на диск.\n");
            } else {
                builder.append("По указаному пути ")
                        .append(pathFileZipCon)
                        .append(" файл отсутствует.\n");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            builder.append(ex.getMessage());
        }
    }

    public static boolean checkExistsFile(String nameFile) {
        File fileOnDisk = new File(nameFile);
        return fileOnDisk.exists();
    }

    public static String getCanonicalFileName(String nameFile) {
        File fileOnDisk = new File(nameFile);
        String nameCanonicalFile = "";
        try {
            nameCanonicalFile = String.valueOf(fileOnDisk.getCanonicalFile());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            builder.append(ex.getMessage());
        }
        return nameCanonicalFile;
    }

    static void deleteFiles(List<String> listNameFile) {
        for (String nameFile : listNameFile) {
            File delFile = new File(nameFile);
            builder.append(delFile.delete() ? "Файл " + nameFile + " удалён.\n" :
                    "Ошибка при удалении файла " + nameFile + ".\n");
        }
    }

    static void saveLog(String nameFile) {
        String logOut = builder.toString();
        writeLog(nameFile, logOut);
    }

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(2, 3, 4, 5.6);
        GameProgress gameProgress2 = new GameProgress(7, 8, 9, 10.11);
        GameProgress gameProgress3 = new GameProgress(12, 13, 14, 15.16);
        saveGame("./Games/savegames/");
        zipFiles("./Games/savegames/savegame.zip");
        deleteFiles(listNameFile);
        listNameFile.clear();
        saveLog("./Games/temp/temp.txt");
    }
}