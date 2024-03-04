package com.project.Search.Engine.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.Search.Engine.processing.ArrayListKeywords;
import com.project.Search.Engine.ranking.NameScore;
import com.project.Search.Engine.ranking.TFIDF;

/**
 * Web Controller
 */
@Controller
public class WebController {
    private String userQueue;
    private ArrayList<NameScore> NameScoresArray;
    private ArrayListKeywords ListKeywords = new ArrayListKeywords();

    /**
     * Opens starting page which implements communication Controller -> HTML
     * @param model
     * @return
     */
    @GetMapping("/")
    public String startThePage(Model model) {
        model.addAttribute("NameScore", NameScoresArray);
        return "search";
    }

    /**
     * Communicates the contents of the file to HTML
     * @param inputPayload
     * @return
     */
    @PostMapping("/open")
    @ResponseBody
    public ResponseEntity<String> openDataFile(@RequestBody InputPayload inputPayload) {
        userQueue = inputPayload.getUserInput();
        if (ListKeywords.getPreprocessingUnit().FileNameArray.indexOf(userQueue) != -1)
            return ResponseEntity.ok(ListKeywords.getFileFilterArray().get(ListKeywords.getPreprocessingUnit().FileNameArray.indexOf(userQueue)).getText());
        return ResponseEntity.ok("No file found");
    }

    /**
     * Processes Queue and assing each of the files the ranking score,
     * which determines the appearance order
     * @param inputPayload
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveInput(@RequestBody InputPayload inputPayload) {
        userQueue = inputPayload.getUserInput();
        

        // Assing each of the Files ranking score based of the userQueue
        TFIDF tfidfTest = new TFIDF(ListKeywords.getFileFilterArray(), userQueue);
        ArrayList<Double> RankingScores = tfidfTest.getRankingScores();
        List<Tuple<Integer, Double>> OrderOfAppearanceTupleArray = new ArrayList<>();
        for (int i = 0; i < RankingScores.size(); i++)
            OrderOfAppearanceTupleArray.add(new Tuple<>(i, RankingScores.get(i)));
        
        
        // Sort the tuples based on the second value (Double)
        Collections.sort(OrderOfAppearanceTupleArray, new Comparator<Tuple<Integer, Double>>() {
            @Override
            public int compare(Tuple<Integer, Double> tuple1, Tuple<Integer, Double> tuple2) {
                return tuple1.y.compareTo(tuple2.y);
            }
        });
        
        // Organizes the outputs which are transmitted to HTML
        NameScoresArray = new ArrayList<>();
        for(int i = OrderOfAppearanceTupleArray.size() - 1; i >= 0; i--) {
            NameScoresArray.add(new NameScore( ListKeywords.getFileNameArray().get(OrderOfAppearanceTupleArray.get(i).x), OrderOfAppearanceTupleArray.get(i).y, ListKeywords.getPreprocessingUnit().FileFilterArray.get(i).getText() ));
        }

        return ResponseEntity.ok("Input saved successfully");
    }


    // Define a class for the input payload
    public static class InputPayload {
        private String userInput;

        public String getUserInput() {
            return userInput;
        }

        public void setUserInput(String userInput) {
            this.userInput = userInput;
        }
    }

    // Define class for Tupel sorting
    static class Tuple<X, Y> {
        public final X x;
        public final Y y;

        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }
} 