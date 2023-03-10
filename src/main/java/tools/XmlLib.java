package tools;

import enums.FileTypes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class XmlLib {
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
        Element root = doc.createElement(ToolsLib.getFileName(fileName));
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
        fileName = ToolsLib.getFileName(fileName) + "." + FileTypes.xml;
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult file = new StreamResult(new File(ToolsLib.formPathToTmpDir(tmpDir, fileName)));
        try {
            transformer.transform(source, file);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        reader.close();
        return fileName;
    }
    public static ArrayList<String> xmlParse(String fileName) {
        ArrayList<String> res = new ArrayList<>();
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document;
        try {
                document = builder.parse(fileName);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        NodeList expressions = document.getElementsByTagName("exp");
        for (int i = 0; i < expressions.getLength(); i++) {
        Node exp = expressions.item(i);
        res.add(exp.getTextContent());
        }
        return res;
    }

}
