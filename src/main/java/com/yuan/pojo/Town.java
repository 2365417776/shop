package com.yuan.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "town")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "townid")
    private String townid;

    @ManyToOne
    @JoinColumn(name = "countyid")
    private County county;

    private String name;


    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTownid() {
        return townid;
    }

    public void setTownid(String townid) {
        this.townid = townid;
    }
}
