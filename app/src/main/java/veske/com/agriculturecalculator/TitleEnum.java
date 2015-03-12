package veske.com.agriculturecalculator;

public enum TitleEnum {
    MAIN_ACTIVITY_TITLE("Põllundus kalkulaator"),
    GERMINATIVE_ACTIVITY_TITLE("Idanevus");

    private final String text;

    private TitleEnum(String text) { this.text = text; }

    @Override
    public String toString() {
        return text;
    }
}
