import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        try {

            URL urlForGetRequest = new URL("https://jsonplaceholder.typicode.com/users/1");
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            int responseCode = conection.getResponseCode();

            StringBuffer responseApi = new StringBuffer();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                while ((readLine = in.readLine()) != null) {
                    responseApi.append(readLine);
                }
                in.close();
            } else {
                System.out.println("GET NOT WORKED");
            }
            String jsonStr = responseApi.toString();

            ObjectMapper mapper = new ObjectMapper();

            JsonNode rootNode = mapper.readTree(responseApi.toString());
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            System.out.println("\n" + prettyJson + "\n");
            System.out.println(rootNode.get("name").asText());
            JsonNode addressNode = rootNode.get("address");
            System.out.println(addressNode.get("street").asText());
            JsonNode geoNode = addressNode.get("geo");
            System.out.println(geoNode.get("lat"));

            // Student student = mapper.readerFor(Student.class).readValue(jsonStr);
            // System.out.println(student.getProperties().get("name"));
            // System.out.println(student.getProperties().get("email"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Student {
    private Map<String, Object> properties;

    public Student() {
        properties = new HashMap<>();
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @JsonAnySetter
    public void add(String property, Object value) {
        properties.put(property, value);
    }
}