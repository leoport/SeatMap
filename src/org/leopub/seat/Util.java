package org.leopub.seat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {
    public static List<String> readFile(File file) throws SeatFileException {
        List<String> res = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                String line = reader.readLine().trim();
                if (!line.isEmpty()) {
                    res.add(line);
                }
            }
            if (reader != null) {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            throw new SeatFileException(e.getMessage());
        } catch (IOException e) {
            throw new SeatFileException(e.getMessage());
        }
        return res;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void randomizeList(List list) {
        Random rand = new Random();
        int n = list.size();
        for (int i = n; i > 0; i--) {
            int r = rand.nextInt(i);
            list.add(list.get(r));
            list.remove(r);
        }
    }
}
