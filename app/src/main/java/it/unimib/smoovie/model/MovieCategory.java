package it.unimib.smoovie.model;


public enum MovieCategory {

    ACTION("ACTION", 28),
    ADVENTURE("ADVENTURE", 12),
    FANTASY("FANTASY", 14);

    private final String category;
    private final Integer code;

    MovieCategory(final String category, final Integer code) {
        this.category = category;
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public Integer getCode() {
        return code;
    }
}
