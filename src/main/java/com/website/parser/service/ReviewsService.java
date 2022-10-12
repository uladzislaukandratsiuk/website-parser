package com.website.parser.service;

import com.website.parser.dto.ReviewDTO;
import com.website.parser.exception.ReviewNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@PropertySource("classpath:parser.properties")
public class ReviewsService {

    @Value("${link.to.parse}")
    private String linkToParse;

    @Value("${review.count.element.class}")
    private String reviewCountElementClass;

    @Value("${review.rating.element.class}")
    private String reviewRatingElementClass;

    public ReviewDTO getReview(String domain) {

        String linkToPageWithReview = linkToParse + domain;

        return getReviews(linkToPageWithReview);
    }

    private ReviewDTO getReviews(String linkToPageWithReview) {

        String reviewsCount;
        String rating;

        try {
            Document document = Jsoup.connect(linkToPageWithReview).get();
            Element mainReviewDiv = document.getElementById("business-unit-title");

            Optional<Element> reviewsCountElement = Optional.of(mainReviewDiv.getElementsByClass(reviewCountElementClass)).map(Elements::first);
            Optional<Element> ratingElement = Optional.of(mainReviewDiv.getElementsByClass(reviewRatingElementClass)).map(Elements::first);
            reviewsCount = reviewsCountElement.map(Element::text).map(text -> text.split("\\s+")).map(strings -> strings[0]).orElseThrow();
            rating = ratingElement.map(Element::text).orElseThrow();

        } catch (IOException | NoSuchElementException e) {
            throw  new ReviewNotFoundException(String.format("Couldn't parse the weblink = %s, with error message = %s", linkToPageWithReview, e.getMessage()));
        }

        return ReviewDTO.builder().rating(rating).reviewsCount(reviewsCount).build();
    }
}
