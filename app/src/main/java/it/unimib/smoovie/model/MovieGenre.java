package it.unimib.smoovie.model;


public enum MovieGenre {

    ACTION("ACTION", 28),
    ADVENTURE("ADVENTURE", 12),
    ANIMATION("ANIMATION", 16),
    COMEDY("COMEDY", 35),
    CRIME("CRIME", 80),
    DOCUMENTARY("DOCUMENTARY", 99),
    DRAMA("DRAMA", 18),
    FAMILY("FAMILY", 10751),
    FANTASY("FANTASY", 14),
    HISTORY("HISTORY", 36),
    HORROR("HORROR", 27),
    MUSIC("MUSIC", 10402),
    MYSTERY("MYSTERY", 9648),
    ROMANCE("ROMANCE", 10749),
    SCIENCEFICTION("SCIENCEFICTION", 878),
    TVMOVIE("TVMOVIE", 10770);
//    THRILLER("THRILLER", 53),
//    WAR("WAR", 10752),
//    WESTERN("WESTERN", 37);

    private final String category;
    private final Integer code;

    MovieGenre(final String category, final Integer code) {
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
