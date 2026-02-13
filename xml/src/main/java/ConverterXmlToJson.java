import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConverterXmlToJson {
    public static void main(String[] args) {
        //String filePath = "/Converter/xml/data.xml";
        List<Employee> list = parseXML("data.xml");
        String json = listToJson(list);
        writeString(json, "data1.json");
    }

    public static List<Employee> parseXML(String fileName) {
        List<Employee> employees = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            Element root = document.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("employee")) {
                    Element employeeElement = (Element) node;
                    long id = Long.parseLong(getTagValue("id", employeeElement));
                    String firstName = getTagValue("firstName", employeeElement);
                    String lastName = getTagValue("lastName", employeeElement);
                    String country = getTagValue("country", employeeElement);
                    int age = Integer.parseInt(getTagValue("age", employeeElement));
                    Employee employee = new Employee(id, firstName, lastName, country, age);
                    employees.add(employee);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    private static String getTagValue(String tagName, Element element) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return "";
    }

    public static String listToJson(List<Employee> list) {
        JSONArray jsonArray = new JSONArray();
        for (Employee employee : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", employee.getId());
            jsonObject.put("firstName", employee.getFirstName());
            jsonObject.put("lastName", employee.getLastName());
            jsonObject.put("country", employee.getCountry());
            jsonObject.put("age", employee.getAge());
            jsonArray.add(jsonObject);
        }

        return jsonArray.toJSONString();
    }


    public static void writeString(String text, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
