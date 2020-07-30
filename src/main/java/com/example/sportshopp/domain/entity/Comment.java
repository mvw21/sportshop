package com.example.sportshopp.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{

    private String author;   //private User author ?  v na chocho springworkshop ima primer s comment
    private String description;


    //tova trqbva da go doizmislq dali nqma Item , koito da ima list s comentari

}
