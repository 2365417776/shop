package com.yuan.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "county")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class County {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "countyid")
    private String countyid;

    @ManyToOne
    @JoinColumn(name = "cityid")
    private City city;

    private String name;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountyid() {
        return countyid;
    }

    public void setCountyid(String countyid) {
        this.countyid = countyid;
    }
}
