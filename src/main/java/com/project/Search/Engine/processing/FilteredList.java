package com.project.Search.Engine.processing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;

/**
 * Class which analyzes the contents of the File and 
 * saves the tokens, lemmas and filtered tokens
 */
public class FilteredList {
    private String[] Tokens;
    private String[] Lemmas;
    private String[] FilteredTokens;
    private Map<String, Integer> DictionaryOfTokens = new HashMap<>();

    public FilteredList(){}
    public FilteredList(String paragraph) {
        String NewParagraph = paragraph.replace("-", "");
        setTokens(NewParagraph);
        setLemmas();
        filterStopWords();
        createDictionary();
    }

    public String[] getTokens(){
        return this.Tokens;
    }

    public String[] getLemmas(){
        return this.Lemmas;
    }

    public String[] getFilteredTokens() {
        return this.FilteredTokens;
    }

    public Map<String, Integer> getDictionaryOfTokens() {
        return this.DictionaryOfTokens;
    }

    /**
     * Tokenizes the text
     * @param paragraph
     */
    public void setTokens(String paragraph) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(paragraph);
        Tokens = tokens;
    }

    /**
     * Lematizes the text based on tokens
     */
    public void setLemmas() {
        try {
            InputStream posModelIn = new FileInputStream ("src/main/resources/static/models/en-pos-maxent.bin");
            POSModel posModel = new POSModel(posModelIn);
            POSTaggerME posTagger = new POSTaggerME(posModel);
            String tags[] = posTagger.tag(Tokens);

            InputStream dictLemmatizer = new FileInputStream ("src/main/resources/static/models/en-lemmatizer.dict");
            DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
            // finding the lemmas
            String[] lemmas = lemmatizer.lemmatize(Tokens, tags);

            Lemmas = lemmas;
        } catch (Exception e) {
            System.out.println("Fail");
        }
    }

    /**
     * Filters stop words from the list of tokens
     */
    public void filterStopWords() {
        // Read the stop words list
        ArrayList<String> StopWordsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/static/stopwords-en.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                StopWordsList.add(line);
            }
        } catch (IOException e) {
            System.out.println("Fail");
        }

        // filter the stop words and create filtered tokens list
        String[] StopWordsArray = StopWordsList.toArray(new String[0]);
        ArrayList<String> FilteredTokensList = new ArrayList<>();
        if (Lemmas.length == Tokens.length) {
            for(int i = 0; i < Lemmas.length; i++) {
                if (Lemmas[i].equals("O")) {
                    if (!Arrays.asList(StopWordsArray).contains(Tokens[i])) {
                        FilteredTokensList.add(Tokens[i]);
                    }
                } else {
                    if (!Arrays.asList(StopWordsArray).contains(Lemmas[i])) {
                        FilteredTokensList.add(Lemmas[i]);
                    }
                }
                
            }
        }
        
        FilteredTokens = FilteredTokensList.toArray(new String[0]);
    }

    /**
     * Calculates the amount of tokens in the text
     */
    public void createDictionary(){
        for(String entity : FilteredTokens) {
            if (DictionaryOfTokens.containsKey(entity)) {
                DictionaryOfTokens.put(entity, DictionaryOfTokens.get(entity) + 1);
            } else {
                DictionaryOfTokens.put(entity, 1);
            }
        }
    }

} 