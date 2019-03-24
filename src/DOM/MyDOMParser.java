package DOM;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

/**
 * @author Syniuk Valentyn
 */
public class MyDOMParser {

    private static Document document;
    private static boolean valid;

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        setValid(true);
        String nameFile = "src/XML/DataWithDTD.xml";
        parsingXMLFile(nameFile);
        scanningDOMApproachValid(document);

        nameFile = "src/XML/InitialDataWithoutXSD.xml";
        setValid(false);
        parsingXMLFile(nameFile);
        scanningDOMApproachValid(document);

        changeValues();

        nameFile = "src/XML/ResultDataWithoutXSD.xml";
        recordResultDataInFile(nameFile);

        setValid(false);
        parsingXMLFile(nameFile);
        scanningDOMApproachInvalid(document);
    }

    private static void setValid(boolean flag) {
        valid = flag;
    }

    private static void parsingXMLFile(String file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = createDocumentBuilder();
        document = builder.parse(new File(file));
    }

    private static DocumentBuilder createDocumentBuilder() throws SAXException, ParserConfigurationException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(valid);
        builderFactory.setSchema(getSchema());
        builderFactory.setNamespaceAware(true);
        builderFactory.setIgnoringElementContentWhitespace(true);

        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        builder.setErrorHandler(new MyErrorHandler());
        return builder;
    }

    private static Schema getSchema() throws SAXException {
        String xmlSchema = XMLConstants.W3C_XML_SCHEMA_NS_URI; // "http://www.w3.org/2001/XMLSchema"
        SchemaFactory schemaFactory = SchemaFactory.newInstance(xmlSchema);
        return schemaFactory.newSchema(new File("src/XML/XMLSchema.xsd"));
    }

    private static void changeValues() {
        DocumentChanger changer = new DocumentChanger(document);
        changer.setX(1, 2.23);
        changer.setY(1, 3.29);
        changer.setX(2, 3.35);
        changer.setY(2, 3.1);
        changer.addElement(changer.createElement("26.03.2018", 9.0, 10.2));
        changer.insertElement(4, changer.createElement("11.03.2018", 4.88, 4.99));
        changer.replaceElement(0, changer.createElement("01.03.2018", 0.13, 0.38));
        changer.removeElement(3);
        System.out.println("Data changed!\n");
    }

    private static void recordResultDataInFile(String file) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
        transformer.setOutputProperty(OutputKeys.ENCODING, "windows-1251");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(document), new StreamResult(new File(file)));
        System.out.println("Record complete!\n");
    }

    /* In validation mode - setValidating(true) */
    private static void scanningDOMApproachValid(Document document) {
        Element root = document.getDocumentElement();
        System.out.println("Using: DOM parsing when valid" + "\n" + document.getDocumentURI());
        System.out.println("Root - " + root.getNodeName() + "\n");
        NodeList list = root.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Element child = (Element) list.item(i);
            System.out.println(child.getTagName() + " - date = " + child.getAttribute("date"));

            Element firstChild = (Element) child.getFirstChild();
            System.out.println(firstChild.getTagName() + " - " + ((Text) firstChild.getFirstChild()).getData().trim());

            Element lastChild = (Element) child.getLastChild();
            System.out.println(lastChild.getTagName() + " - " + ((Text) lastChild.getFirstChild()).getData().trim() + "\n");
        }
    }

    /* Not in validation mode - setValidating(false) */
    private static void scanningDOMApproachInvalid(Document document) {
        Element root = document.getDocumentElement();
        System.out.println("Using: DOM parsing when invalid" + "\n" + document.getDocumentURI());
        System.out.println("Root - " + root.getNodeName());
        nextNode(root);
    }

    private static void nextNode(Node next) {
        System.out.println(next.getNodeName() + " = " + next.getNodeValue());

        if (next instanceof Element) {
            NamedNodeMap attributes = next.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node node = attributes.item(i);
                System.out.println("Attribute - " + node.getNodeName() + " = " + node.getNodeValue());
            }
        }

        for (Node child = next.getFirstChild(); child != null; child = child.getNextSibling()) {
            nextNode(child);
        }
    }

}
