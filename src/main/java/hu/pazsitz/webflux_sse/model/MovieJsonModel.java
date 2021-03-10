package hu.pazsitz.webflux_sse.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class MovieJsonModel {
    //private String adult;
    //private String belongs_to_collection;
    //private String budget;
    //private String genres;
    //private String homepage;
    //private String id;
    //@CsvBindByPosition(position = 6)
    @CsvBindByName(column = "imdb_id")
    private String imdb_id;
    //private String original_language;
    @CsvBindByName(column = "original_title")
    //@CsvBindByPosition(position = 8)
    private String original_title;

    public String getRelease_date() {
        return release_date;
    }

    //private String overview;
    //private String popularity;
    //private String poster_path;
    //private String production_companies;
    //private String production_countries;
    @CsvBindByName(column = "release_date")
    //@CsvBindByPosition(position = 14)
    private String release_date;
    //private String revenue;
    //private String runtime;
    //private String spoken_languages;
    //private String status;
    //private String tagline;
    @CsvBindByName(column = "title")
    //@CsvBindByPosition(position = 20)
    private String title;
    //private String video;
    @CsvBindByName(column = "vote_average")
    //@CsvBindByPosition(position = 22)
    private Double vote_average;
    //private String vote_count;

    public MovieJsonModel(String imdb_id, String origin_title, String title, String release_date, String vote_avg) {
        this.imdb_id = imdb_id;
        this.original_title = origin_title;
        this.release_date = release_date;
        this.vote_average = Double.parseDouble(vote_avg);
    }

    public String getImdb_id() {
        return imdb_id;
    }


    public String getOriginal_title() {
        return original_title;
    }

    public String getTitle() {
        return title;
    }

    public Double getVote_average() {
        return vote_average;
    }

    @Override
    public String toString() {
        return "MovieJsonModel{" +
                "imdb_id='" + imdb_id + '\'' +
                ", original_title='" + original_title + '\'' +
                ", release_date='" + release_date + '\'' +
                ", title='" + title + '\'' +
                ", vote_average=" + vote_average +
                '}';
    }
}
