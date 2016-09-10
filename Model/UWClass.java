package naomi.me.spotopen.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by naomikoo on 2016-08-09.
 */
public class UWClass implements Serializable {

    @SerializedName("subject")
    private String subject;

    @SerializedName("catalog_number")
    private String number;

    @SerializedName("section")
    private String section;

    @SerializedName("enrollment_capacity")
    private int totalCapacity;

    @SerializedName("enrollment_total")
    private int totalEnrolled;
    private String time;

    private String term; // might be with uwclasswrapper

    private String location;

    @SerializedName("title")
    private String name;

//    public UWClass() {
//        // no-op
//    }
//
//    public UWClass(String subject, String number, int totalCapacity, int totalEnrolled, String time, String term, String location, String name, String section) {
//        this.subject = subject;
//        this.number = number;
//        this.totalCapacity = totalCapacity;
//        this.totalEnrolled = totalEnrolled;
//        this.time = time;
//        this.term = term;
//        this.location = location;
//        this.name = name;
//        this.section = section;
//    }

    public String getSubject() {
        return subject;
    }

    public String getNumber() {
        return number;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getTotalEnrolled() {
        return totalEnrolled;
    }

    public String getTime() {
        return time;
    }

    public String getTerm() {
        return term;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public void setTotalEnrolled(int totalEnrolled) {
        this.totalEnrolled = totalEnrolled;
    }
}
