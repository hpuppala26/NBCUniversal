public class AssertionUtility {
    public static void performAssertion(String assertionType, String expected, String actual) {
        switch (assertionType) {
            case "equals":
                assert expected.equals(actual);
                break;
            case "urlEquals":
                assert expected.equalsIgnoreCase(actual);
                break;
            // Adding other cases for different usecases
            default:
                throw new IllegalArgumentException("Unsupported assertion type: " + assertionType);
        }
    }
}
