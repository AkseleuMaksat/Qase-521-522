package kz.Akseleu.enums;

public enum Status {
    DRAFT("Черновик"),
    PROVIDED("Проведен"),
    DELETED("Удален");
    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String get() {
        return displayName;
    }
}
