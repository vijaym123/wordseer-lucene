/**
 * Created by vijaysm on 2/21/16.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLParser {
    File inputFile = null;
    Document doc = null;
    Boolean successFlag = Boolean.FALSE;

    XMLParser(String fileName) {
        inputFile = new File(fileName);
        System.out.println(inputFile.exists());
        if (inputFile.exists() && inputFile.getName().toLowerCase().endsWith(".xml")){
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();
                successFlag = Boolean.TRUE;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getData(String path) {

        System.out.println(doc);
        NodeList nList = doc.getElementsByTagName("tweet");
        List<String> tweetData = new ArrayList<String>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                tweetData.add(eElement.getElementsByTagName(path).item(0).getTextContent());
            }
        }
        return tweetData;
    }
}