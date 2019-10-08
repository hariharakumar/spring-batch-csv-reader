package com.hari.springbatchcsvreader.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Movie {

    @Id
    private Integer id;

    private String name;
    private String review;
    private Integer revenue;
    private Date time;

    public Movie(Integer id, String name, String review, Integer revenue, Date time) {
        this.id = id;
        this.name = name;
        this.review = review;
        this.revenue = revenue;
        this.time = time;
    }

    public Movie() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", review='" + review + '\'' +
                ", revenue=" + revenue +
                ", time=" + time +
                '}';
    }
}
