package tools;

import enums.FileTypes;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class JsonLib {
        public static String jsonForm(String fileName, Path tmpDir) throws IOException {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(fileName));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            fileName = ToolsLib.getFileName(fileName) + "." + FileTypes.json;
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter(ToolsLib.formPathToTmpDir(tmpDir, fileName)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            JSONArray expressions = new JSONArray();
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                JSONObject nextExp = new JSONObject();
                nextExp.put("exp", nextLine);
                expressions.add(nextExp);
            }
            writer.write(expressions.toJSONString());
            writer.flush();
            return fileName;
        }
        public static ArrayList<String> jsonParse(String fileName) {
            ArrayList<String> res = new ArrayList<>();
            JSONParser parser = new JSONParser();
            try {
                JSONArray expressions = (JSONArray) parser.parse(new FileReader(fileName));
                for (Object expression : expressions) {
                    JSONObject exp = (JSONObject) expression;
                    res.add((String) exp.get("exp"));
                }
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
            return res;
        }
}
