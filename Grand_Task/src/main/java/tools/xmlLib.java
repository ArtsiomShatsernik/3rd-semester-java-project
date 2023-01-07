package tools;

import enums.FileTypes;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Path;

public class xmlLib {
    public static String xmlForm(String fileName, Path tmpDir) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc = builder.newDocument();
        Element root = doc.createElement(toolsLib.getFileName(fileName));
        String nextLine;
        while ((nextLine = reader.readLine()) != null) {
            Element expression = doc.createElement("exp");
            expression.appendChild(doc.createTextNode(nextLine));
            root.appendChild(expression);
        }
        doc.appendChild(root);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        fileName = toolsLib.getFileName(fileName) + "." + FileTypes.xml;
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult file = new StreamResult(new File(tmpDir.toString() + "//" + fileName));
        try {
            transformer.transform(source, file);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

}
