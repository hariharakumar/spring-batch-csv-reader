package com.hari.springbatchcsvreader.batch;

import com.hari.springbatchcsvreader.model.Movie;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class DbProcessor implements ItemProcessor<Movie, Movie> {

    private static final HashMap<String, String> REVIEW_MAPPING = new HashMap<>();

    public DbProcessor() {
        REVIEW_MAPPING.put("1", "BAD");
        REVIEW_MAPPING.put("2", "OK");
        REVIEW_MAPPING.put("3", "AVERAGE");
        REVIEW_MAPPING.put("4", "GOOD");
        REVIEW_MAPPING.put("5", "EXCELLENT");
    }

    @Override
    public Movie process(Movie movie) throws Exception {

        String movieReview = movie.getReview();

        String reviewString = REVIEW_MAPPING.get(movieReview);
        System.out.println("Movie Review String : " + reviewString);

        movie.setReview(reviewString);
        movie.setTime(new Date());

        return movie;
    }
}
