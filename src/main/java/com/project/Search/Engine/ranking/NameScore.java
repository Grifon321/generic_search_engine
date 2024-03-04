package com.project.Search.Engine.ranking;

/**
 * Structure used to communicate the contents to HTML
 */
public class NameScore {
    public String name;
    public double ranking;
    public String text;
    public NameScore(){}
    public NameScore(String name, Double ranking, String text){
        this.name = name;
        this.ranking = ranking;
        this.text = text;
    }
}
