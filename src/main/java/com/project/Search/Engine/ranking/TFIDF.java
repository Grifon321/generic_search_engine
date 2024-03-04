package com.project.Search.Engine.ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.project.Search.Engine.processing.FileFilter;
import com.project.Search.Engine.processing.FilteredList;

/**
 * Term frequency–inverse document frequency algorithm
 */
public class TFIDF {
    private ArrayList<FileFilter> FileFilterArray;
    private ArrayList<Double> RankingScores = new ArrayList<>();
    private Map<String, Integer> DictionaryOfTokensQuery = new HashMap<>();

    public TFIDF(ArrayList<FileFilter> FileFilterArray, String Query){
        this.FileFilterArray = FileFilterArray;
        preprocessQuery(Query);
        setRankingScores();
    }

    /**
     * Assings ranking scores each of the files
     */
    public void setRankingScores(){
        for(FileFilter ff : this.FileFilterArray){
            Map<String, Integer> DictionaryOfTokensOfFF = ff.getListOfContets().getDictionaryOfTokens();
            RankingScores.add(calculateTermFrequency(DictionaryOfTokensOfFF));
        }
    }

    public ArrayList<Double> getRankingScores (){
        return this.RankingScores;
    }

    /**
     * Calculates TermFrequency
     * @return ranking score
     */
    private double calculateTermFrequency(Map<String, Integer> DictionaryOfTokensOfFilteredFilterElement) {
        double tfidf = 0;
        int TotalAmountOfTokens = 0;
        for(Integer value : DictionaryOfTokensOfFilteredFilterElement.values())
            TotalAmountOfTokens += value;
        for(String key : DictionaryOfTokensQuery.keySet()) {
            double tf = 0.0;
            double idf = 0.0;
            if (DictionaryOfTokensOfFilteredFilterElement.containsKey(key))
                tf = DictionaryOfTokensOfFilteredFilterElement.get(key) / (double) TotalAmountOfTokens;
            if (DictionaryOfTokensQuery.get(key) != 0)
                idf = Math.log(FileFilterArray.size() / (double) DictionaryOfTokensQuery.get(key));
            tfidf += tf * idf;
        }
        return tfidf;
    }

    /**
     * Preprocesses Query eluminating stop words
     * Puts the number of in how many documents the words is contained
     * @param Query
     */
    private void preprocessQuery(String Query){
        FilteredList FilteredQueryList = new FilteredList(Query);
        this.DictionaryOfTokensQuery = FilteredQueryList.getDictionaryOfTokens();
        for (String key : DictionaryOfTokensQuery.keySet())
            DictionaryOfTokensQuery.put(key, 0);
        for (FileFilter ff : this.FileFilterArray) {
            for (String key : DictionaryOfTokensQuery.keySet())
                if (ff.getListOfContets().getDictionaryOfTokens().containsKey(key))
                    DictionaryOfTokensQuery.put(key, DictionaryOfTokensQuery.get(key) + 1);
        }
    }

} 