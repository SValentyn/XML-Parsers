package SAX;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

class DataHandler extends DefaultHandler {

    private static ArrayList<DataNode> list;
    private String date;
    private double x, y;
    private boolean isX, isY;

    public ArrayList<DataNode> getList() {
        return list;
    }

    @Override
    public void startDocument() {
        System.out.println("Start parsing!\n");
        list = new ArrayList<>();
        isX = false;
        isY = false;
    }

    @Override
    public void endDocument() {
        System.out.println("\nEnd parsing!");
        System.out.println("\nData:");
        System.out.println(list.toString());
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        System.out.println("Start element \"" + qName + "\"");

        if (qName.equals("x")) isX = true;
        else if (qName.equals("y")) isY = true;

        if (attributes.getLength() > 0) {
            for (int i = 0; i < attributes.getLength(); i++) {
                date = attributes.getValue(i);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        System.out.println("End element \"" + qName + "\"");
        switch (qName) {
            case "x":
                isX = false;
                break;
            case "y":
                isY = false;
                break;
            case "data":
                list.add(new DataNode(date, x, y));
                break;
        }
    }

    @Override
    public void characters(char[] chars, int start, int length) {
        String string = new String(chars, start, length).trim();
        System.out.println(string);

        if (string.length() != 0) {
            if (isX) {
                x = Double.parseDouble(string);
            } else if (isY) {
                y = Double.parseDouble(string);
            }
        }
    }

}
