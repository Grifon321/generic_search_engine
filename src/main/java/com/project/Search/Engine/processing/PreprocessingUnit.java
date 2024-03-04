package com.project.Search.Engine.processing;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Class preprocessing all the files in the /data folder
 */
public class PreprocessingUnit {
    public ArrayList<FileFilter> FileFilterArray;
    public ArrayList<String> FileNameArray = new ArrayList<>();
    
    public PreprocessingUnit(){
        
        FileFilterArray = new ArrayList<>();
        try {
            Path folder = Paths.get("src/main/resources/static/data");
            Files.list(folder).forEach(file -> {
                try {
                    String content = Files.readString(file);
                    FileFilter ff = new FileFilter(content);
                    FileFilterArray.add(ff);
                    FileNameArray.add(file.getFileName().toString());
                } catch (Exception e) {
                    System.out.println("File could not be opened");
                }
            });
        } catch (Exception e) {
            System.out.println("Path could not be found");
        }
    }    

    
}
