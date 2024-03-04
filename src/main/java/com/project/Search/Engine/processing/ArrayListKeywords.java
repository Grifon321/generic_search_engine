package com.project.Search.Engine.processing;

import java.util.ArrayList;

/**
 * DataBase workaround, which saves File Names and processed File Contents
 */
public class ArrayListKeywords {
    private static ArrayList<FileFilter> FileFilterArray = new ArrayList<>();
    private static ArrayList<String> FileNameArray = new ArrayList<>();
    public static PreprocessingUnit newUnit;

    public ArrayListKeywords(){
        newUnit = new PreprocessingUnit();
        FileFilterArray = newUnit.FileFilterArray;
        FileNameArray = newUnit.FileNameArray;
    }

    public void setFileFilterArray(ArrayList<FileFilter> NewFileFilterArray){
        FileFilterArray = NewFileFilterArray;
    }

    public ArrayList<FileFilter> getFileFilterArray(){
        return FileFilterArray;
    }

    public void setFileNameArray(ArrayList<String> NewFileNameArray){
        FileNameArray = NewFileNameArray;
    }

    public ArrayList<String> getFileNameArray(){
        return FileNameArray;
    }
    
    public PreprocessingUnit getPreprocessingUnit(){
        return newUnit;
    }


}
