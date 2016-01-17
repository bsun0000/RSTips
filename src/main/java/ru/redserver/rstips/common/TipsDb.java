package ru.redserver.rstips.common;

import java.io.*;
import java.util.ArrayList;
// import java.util.Scanner;

/**
 * Created by BlackSun on 16.01.16.
 */
public class TipsDb {
    private static ArrayList<String> list = new ArrayList<String>();
    public static String format = "";
/*
    public static void loadFromFile(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file);
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();
    }
*/
    public static void loadFromResources(String path) throws IOException {
        InputStream is = TipsDb.class.getResourceAsStream(path);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        format = br.readLine(); // Первая строчка файла - шаблон для форматирования

        String line;
        while ((line = br.readLine()) != null) {
            list.add(line);
        }

        br.close();
        isr.close();
        is.close();
    }

    public static String getFormatted(int index) {
        String message = format;
        return (message != null) ? message
            .replace("{%curr%}", String.valueOf(index + 1))
            .replace("{%total%}", String.valueOf(size()))
            .replace("{%msg%}", list.get(index)) : "";
    }

    public static String getRandomMessage() {
        return getFormatted((int)(Math.random() * list.size()));
    }

    public static String getNextMessage() {
        Config.lastUsed++;
        if (Config.lastUsed >= (size() - 1)) {
            Config.lastUsed = 0;
        }
        Config.save();

        return getFormatted(Config.lastUsed);
    }

    public static String getMessage() {
        return (Config.isRandom) ? getRandomMessage() : getNextMessage();
    }

    public static String get(int index) {
        return list.get(index);
    }

    public static int size() {
        return list.size();
    }
}
