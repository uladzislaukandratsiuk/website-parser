package com.website.parser.controller;

import com.website.parser.dto.ReviewDTO;
import com.website.parser.service.ReviewsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
public class ReviewsController {

    private ReviewsService reviewsService;

    @GetMapping("/{domain}")
    private Mono<ReviewDTO> getReview(@PathVariable String domain) {
        return Mono.just(reviewsService.getReview(domain));
    }
}
