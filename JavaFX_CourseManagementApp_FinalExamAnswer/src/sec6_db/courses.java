/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sec6_db;

/**
 *
 * @author LeaderTech
 */
public class courses {
  private int id;
  private String name;
  private int hours;
  private String prof;
  private int no;

    public courses(int id, String name, int hours, String prof, int no) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.prof = prof;
        this.no = no;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public String getProf() {
        return prof;
    }

    public int getNo() {
        return no;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public void setNo(int no) {
        this.no = no;
    }
    
}
