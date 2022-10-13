package com.website.parser.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ReviewDTO {

    private String reviewsCount;
    private String rating;
}
