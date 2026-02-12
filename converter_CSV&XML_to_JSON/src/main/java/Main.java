import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;



public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        //задача с CSV

        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        writeString(json, "data.json");

//        //задача с XML
//        List<Employee> list1 = parseXML("data.xml");
//        String json1 = listToJson(list1);
//        writeString(json1, "data1.json");
    }


        // реализация первой задачи
    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        Type listType = new TypeToken<List<Employee>>() {}.getType();
        return gson.toJson(list, listType);
    }

    public static void writeString(String json, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // реализация второй задачи

//    public static List<Employee> parseXML(String fileName) {
//        List<Employee> employees = new ArrayList<>();
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//
//            //у меня почему-то горит красным (((
//            // builder.setPrettyPrinting();
//
//            Document document = builder.parse(new File(fileName));
//            Element root = document.getDocumentElement();
//            NodeList nodeList = root.getChildNodes();
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("employee")) {
//                    Element employeeElement = (Element) node;
//                    long id = Long.parseLong(getTagValue("id", employeeElement));
//                    String firstName = getTagValue("firstName", employeeElement);
//                    String lastName = getTagValue("lastName", employeeElement);
//                    String country = getTagValue("country", employeeElement);
//                    int age = Integer.parseInt(getTagValue("age", employeeElement));
//                    Employee employee = new Employee(id, firstName, lastName, country, age);
//                    employees.add(employee);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return employees;
//    }
//
//    private static String getTagValue(String tagName, Element element) {
//        NodeList nodeList = element.getElementsByTagName(tagName);
//        if (nodeList.getLength() > 0) {
//            Node node = nodeList.item(0);
//            return node.getTextContent();
//        }
//        return "";
//    }
//
//    public static String listToJson(List<Employee> list1) {
//        JSONArray jsonArray = new JSONArray();
//        for (Employee employee : list1) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("id", employee.getId());
//            jsonObject.put("firstName", employee.getFirstName());
//            jsonObject.put("lastName", employee.getLastName());
//            jsonObject.put("country", employee.getCountry());
//            jsonObject.put("age", employee.getAge());
//            jsonArray.add(jsonObject);
//        }
//
//        return jsonArray.toJSONString();
//    }
//
//
//    public static void writeString(String text, String fileName) {
//        try (FileWriter writer = new FileWriter(fileName)) {
//            writer.write(text);
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}