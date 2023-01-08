package tools;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import enums.FileTypes;

import java.io.*;
import java.nio.file.*;

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
            JsonArray expressions = new JsonArray();
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                JsonObject nextExp = new JsonObject();
                nextExp.put("exp", nextLine);
                expressions.add(nextExp);
            }
            writer.write(expressions.toJson());
            writer.flush();
            return fileName;
        }
}
