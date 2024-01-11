/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec6_db;

/**
 *
 * @author JoEkhalid
 */
public class Student {
    
    private String code;
    private String title;
    private String prerequest;

    public Student(String code, String title, String prerequest) {
        this.code = code;
        this.title = title;
        this.prerequest = prerequest;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getPrerequest() {
        return prerequest;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrerequest(String prerequest) {
        this.prerequest = prerequest;
    }
    
    
}
    