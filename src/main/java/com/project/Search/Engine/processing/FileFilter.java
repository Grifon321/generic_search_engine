package com.project.Search.Engine.processing;

/**
 * Datastructure which has text and the contents of each File
 */
public class FileFilter {
    private String FileText;
    public FilteredList ListOfContents;
    public FileFilter(){}
    public FileFilter(String FileText){
        this.FileText = FileText;
        ListOfContents = new FilteredList(FileText);
    }
    public void setText(String FileText){
        this.FileText = FileText;
    }
    public String getText(){
        return FileText;
    }
    public FilteredList getListOfContets(){
        return this.ListOfContents;
    }
}
