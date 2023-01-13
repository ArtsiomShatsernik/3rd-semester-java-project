package tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TxtLib {

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
