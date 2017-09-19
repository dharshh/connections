package com.example.priyadharshinim.connection;

/**
 * Created by priyadharshinim on 02/06/17.
 */

import java.util.List;

public class Data {

    String name;
    List<String> cast;

    public String getMovieName() {
        return name;
    }

    public void setMovieName(String name) {
        this.name = name;
    }

    public List<String> getCasts() {
        return cast;
    }

    public void setCasts(List<String> cast) {
        this.cast = cast;
    }

}
