package kz.Akseleu.enam;
public enum Status {
    DRAFT("Черновик"),
    POSTED("Проведен"),
    DELETED("Удален");
    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getStatus() {
        return displayName;
    }
}
