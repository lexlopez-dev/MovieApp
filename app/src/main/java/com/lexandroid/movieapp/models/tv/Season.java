package com.lexandroid.movieapp.models.tv;

public class Season {
    private String air_date, name, overview, poster_path;

    private int episode_count, id, season_number;

    public Season(String air_date, String name, String overview, String poster_path, int episode_count, int id, int season_number) {
        this.air_date = air_date;
        this.name = name;
        this.overview = overview;
        this.poster_path = poster_path;
        this.episode_count = episode_count;
        this.id = id;
        this.season_number = season_number;
    }

    public String getAir_date() {
        return air_date;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getEpisode_count() {
        return episode_count;
    }

    public int getId() {
        return id;
    }

    public int getSeason_number() {
        return season_number;
    }
}
