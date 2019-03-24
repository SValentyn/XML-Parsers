package DOM;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

class MyErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException warning) {
        System.err.println("Warning - " + warning);
        System.err.println("line = " + warning.getLineNumber() + " col = " + warning.getColumnNumber());
    }

    @Override
    public void error(SAXParseException error) {
        System.err.println("Error - " + error);
        System.err.println("line = " + error.getLineNumber() + " col = " + error.getColumnNumber());
    }

    @Override
    public void fatalError(SAXParseException fatal) {
        System.err.println("Error - " + fatal);
        System.err.println("line = " + fatal.getLineNumber() + " col = " + fatal.getColumnNumber());
    }

}
