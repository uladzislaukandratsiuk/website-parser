package com.website.parser.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewDTO {

    private String reviewsCount;
    private String rating;
}
