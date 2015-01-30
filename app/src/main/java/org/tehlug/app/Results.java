package org.tehlug.app;

/*
 * Created by behdad on 1/30/15.
 */
public class Results {
    private String date_text;
    private String title_link_text;
    private String num_text;
    private String title_link_source;
    private String title_link;
    public String getDate_text (){
        return date_text;
    }

    public void setDate_text (String date_text){
        this.date_text = date_text;
    }

    public String getTitle_link_text (){
        return title_link_text;
    }

    public void setTitle_link_text (String title_link_text){
        this.title_link_text = title_link_text;
    }

    public String getNum_text (){
        return num_text;
    }

public void setNum_text (String num_text)
        {
        this.num_text = num_text;
        }

public String getTitle_link_source ()
        {
        return title_link_source;
        }

public void setTitle_link_source (String title_link_source)
        {
        this.title_link_source = title_link_source;
        }

public String getTitle_link ()
        {
        return title_link;
        }

public void setTitle_link (String title_link)
        {
        this.title_link = title_link;
        }
        }