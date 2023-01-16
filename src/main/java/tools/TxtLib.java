package tools;

import java.io.*;
import java.util.ArrayList;

public class TxtLib {
    public static String txtForm(ArrayList<String> data) {
        BufferedWriter writer;
        String path = "data.txt";
        try {
            writer = new BufferedWriter(new FileWriter(path));
            for (String t: data) {
                writer.write(t + "\n");
            }
            writer.flush();
            writer.close();
            return path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   public static ArrayList<String> txtParse(String fileName) throws IOException {
        ArrayList<String> res = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String nextLine;
        while((nextLine = reader.readLine()) != null) {
            res.add(nextLine);
        }
        return res;
    }
}
