import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    public List<TestCase> readTestCases(String filePath) {
        List<TestCase> testCases = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TestCase testCase = new TestCase(
                        jsonObject.getString("findElementBy"),
                        jsonObject.getString("value"),
                        jsonObject.getString("expectedData"),
                        jsonObject.optString("actualData", ""), // optString for optional fields
                        jsonObject.getString("assertionType")
                );
                testCases.add(testCase);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read test cases from JSON file", e);
        }
        return testCases;
    }
}
