package com.website.parser.service;

import com.website.parser.dto.ReviewDTO;
import com.website.parser.exception.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.website.parser.cache.CacheConfig.REVIEWS;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:parser.properties")
public class ReviewService {

    @Value("${link.to.parse}")
    private String linkToParse;

    @Value("${review.main.element.class.id}")
    private String reviewMainElementClassId;

    @Value("${review.count.element.class}")
    private String reviewCountElementClass;

    @Value("${review.rating.element.class}")
    private String reviewRatingElementClass;

    private final CacheManager cacheManager;

    @Cacheable(value = REVIEWS, key = "domain")
    public ReviewDTO getReview(String domain) {
        return Optional.ofNullable(cacheManager.getCache(REVIEWS))
                .map(cache -> cache.get(domain))
                .map(valueWrapper -> (ReviewDTO) valueWrapper.get())
                .orElseGet(() -> getReviews(domain));

    }

    private ReviewDTO getReviews(String domain) {

        String linkToPageWithReview = linkToParse + domain;
        String reviewsCount;
        String rating;

        try {
            Document reviewPage = Jsoup.connect(linkToPageWithReview).get();
            Element mainReviewElement = reviewPage.getElementById(reviewMainElementClassId);
            Optional<Element> reviewsCountElement =
                    Optional.of(mainReviewElement.getElementsByClass(reviewCountElementClass))
                            .map(Elements::first);
            Optional<Element> ratingElement =
                    Optional.of(mainReviewElement.getElementsByClass(reviewRatingElementClass))
                            .map(Elements::first);

            reviewsCount = reviewsCountElement.map(Element::text)
                    .map(text -> text.split("\\s+"))
                    .map(strings -> strings[0]).orElseThrow();
            rating = ratingElement.map(Element::text).orElseThrow();

        } catch (IOException | NoSuchElementException e) {
            throw new ReviewNotFoundException(
                    String.format("Couldn't parse the weblink with error message = %s", e.getMessage()));
        }

        ReviewDTO reviewDTO = ReviewDTO.builder().rating(rating).reviewsCount(reviewsCount).build();

        Optional.ofNullable(cacheManager.getCache(REVIEWS))
                .ifPresent(cache -> cache.put(domain, reviewDTO));

        return reviewDTO;
    }
}
