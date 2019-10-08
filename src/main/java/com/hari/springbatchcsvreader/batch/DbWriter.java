package com.hari.springbatchcsvreader.batch;


import com.hari.springbatchcsvreader.model.Movie;
import com.hari.springbatchcsvreader.repository.MovieRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbWriter implements ItemWriter<Movie> {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public void write(List<? extends Movie> list) throws Exception {

        System.out.println("In Item Writer");
        movieRepository.saveAll(list);
    }
}
