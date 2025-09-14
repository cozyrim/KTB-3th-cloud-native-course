public enum Variety {
    아오리사과나무("아오리사과나무", "aori"),
    황금사과나무("황금사과나무", "golden");

    private final String displayName;
    private final String shortName;

    Variety(String displayName, String shortName) {
        this.displayName = displayName;
        this.shortName = shortName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getShortName() {
        return shortName;
    }

}
