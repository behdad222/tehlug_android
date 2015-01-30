package org.tehlug.app;

import java.util.List;

/*
 * Created by behdad on 1/30/15.
 */
public class MeetingsResponse {
    private Results results;

    public Results getResults(){
        return this.results;
    }
    public void setResults(Results results){
        this.results = results;
    }
}