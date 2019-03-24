package DOM;

import org.w3c.dom.*;

class DocumentChanger {

    private Document document;

    public DocumentChanger(Document document) {
        this.document = document;
    }

    public void setX(int pos, double value) {
        document.getDocumentElement().getElementsByTagName("x").item(pos).setTextContent(value + "");
    }

    public void setY(int pos, double value) {
        document.getDocumentElement().getElementsByTagName("y").item(pos).setTextContent(value + "");
    }

    public Element createElement(String date, double x, double y) {
        Element element = document.createElement("data");

        Attr attr = document.createAttribute("date");

        attr.setValue(date);
        element.setAttributeNode(attr);

        Element elementX = document.createElement("x");
        elementX.setTextContent(x + "");
        element.appendChild(elementX);

        Element elementY = document.createElement("y");
        elementY.setTextContent(y + "");
        element.appendChild(elementY);

        return element;
    }

    public void addElement(Element data) {
        document.getDocumentElement().appendChild(data);
    }

    public void insertElement(int pos, Node node) {
        Node item = document.getDocumentElement().getElementsByTagName("data").item(pos);
        document.getDocumentElement().insertBefore(node, item);
    }

    public void replaceElement(int pos, Node node) {
        Node item = document.getDocumentElement().getElementsByTagName("data").item(pos);
        document.getDocumentElement().replaceChild(node, item);
    }

    public void removeElement(int pos) {
        Node node = document.getDocumentElement().getElementsByTagName("data").item(pos);
        document.getDocumentElement().removeChild(node);
    }

}
