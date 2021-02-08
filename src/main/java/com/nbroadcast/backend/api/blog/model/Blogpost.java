package com.nbroadcast.backend.api.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Blogpost {
    String author;
    Category category;
    String content;
    DateTime date;
    String id;
    String imageUrl;
    String name;
    String contentPreview;
    List<String> tags;
    String title;
    Integer views;
}
