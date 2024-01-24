public class TestCase {
    private String findElementByData;
    private String value;
    private String expectedData;
    private String actualData;
    private String assertionType;

    public TestCase(String findElementByData, String value, String expectedData, String actualData, String assertionType) {
        this.findElementByData = findElementByData;
        this.value = value;
        this.expectedData = expectedData;
        this.actualData = actualData;
        this.assertionType = assertionType;
    }

    public String getFindElementBy() {
        return findElementByData;
    }

    public String getValue() {
        return value;
    }

    public String getExpectedData() {
        return expectedData;
    }

    public String getAssertionType() {
        return assertionType;
    }
}
