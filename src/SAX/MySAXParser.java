package SAX;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * @author Syniuk Valentyn
 */
public class MySAXParser {

    private static DataHandler handler;
    private static SAXParser parser;

    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {

        handler = new DataHandler();
        parser = createParser();

        String nameFile = "src/XML/DataWithXSD.xml";
        parsingXMLFile(nameFile);
        System.out.println(createFunction());

        nameFile = "src/XML/DataWithDTD.xml";
        parsingXMLFile(nameFile);
        System.out.println(createFunction());
    }

    private static SAXParser createParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);

        SAXParser parser = factory.newSAXParser();
        parser.setProperty(
                "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");

        return parser;
    }

    private static void parsingXMLFile(String file) throws SAXException, IOException {
        parser.parse(new File(file), handler);
    }

    private static String createFunction() {
        FunctionCreator function = new FunctionCreator();
        function.calculation(handler.getList());
        return function.toString();
    }

}
