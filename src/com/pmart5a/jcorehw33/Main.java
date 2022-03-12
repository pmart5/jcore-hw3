package com.pmart5a.jcorehw33;

import com.pmart5a.jcorehw32.myclass.GameProgress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.pmart5a.jcorehw31.Main.writeLog;
import static com.pmart5a.jcorehw32.Main.checkExistsFile;
import static com.pmart5a.jcorehw32.Main.getCanonicalFileName;

public class Main {
    static private final List<String> listNameFile = new ArrayList<>();
    static private final StringBuilder builder = new StringBuilder();

    static void openZip(String pathFileZip, String pathDir) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathFileZip))) {
            ZipEntry entry;
            String nameFile;
            while ((entry = zin.getNextEntry()) != null) {
                nameFile = pathDir + entry.getName();
                FileOutputStream fout = new FileOutputStream(nameFile);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
                builder.append("Файл ")
                        .append(entry.getName())
                        .append(" распакован.\n");
                String nameFileCon = getCanonicalFileName(nameFile);
                listNameFile.add(nameFileCon);
                if (checkExistsFile(nameFileCon)) {
                    builder.append("Файл ")
                            .append(nameFileCon)
                            .append(" записан на диск.\n");
                } else {
                    builder.append("По указаному пути ")
                            .append(nameFileCon)
                            .append(" файл отсутствует.\n");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            builder.append(ex.getMessage());
        }
    }

    static GameProgress openProgress(String pathFileSave) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(pathFileSave);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            builder.append(ex.getMessage());
        }
        return gameProgress;
    }

    static void saveLog(String nameFile) {
        String logOut = builder.toString();
        writeLog(nameFile, logOut);
    }

    public static void main(String[] args) {
        openZip("./Games/savegames/savegame.zip", "./Games/savegames/");
        for (String nameFile : listNameFile) {
            System.out.println(openProgress(nameFile).toString());
        }
        saveLog("./Games/temp/temp.txt");
    }
}