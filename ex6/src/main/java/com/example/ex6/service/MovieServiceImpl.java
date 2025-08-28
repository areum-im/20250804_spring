package com.example.ex6.service;

import com.example.ex6.dto.MovieDTO;
import com.example.ex6.dto.PageRequestDTO;
import com.example.ex6.dto.PageResultDTO;
import com.example.ex6.entity.Movie;
import com.example.ex6.entity.MovieImage;
import com.example.ex6.entity.Review;
import com.example.ex6.repository.MovieImageRepository;
import com.example.ex6.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
  private final MovieRepository movieRepository;
  private final MovieImageRepository movieImageRepository;

  @Transactional
  @Override
  public Long register(MovieDTO movieDTO) {
    Map<String, Object> entityMap = dtoToEntity(movieDTO);
    Movie movie = (Movie) entityMap.get("movie");
    List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");
    movieRepository.save(movie);
    movieImageList.forEach(movieImage -> movieImageRepository.save(movieImage));
    return movie.getMno();
  }

  @Override
  public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
    Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());
    Page<Object[]> result = movieRepository.getListPageMaxMi(pageable);
    Function<Object[], MovieDTO> fn = new Function<Object[], MovieDTO>() {
      @Override
      public MovieDTO apply(Object[] arr) {
        return entitiesToDTO(
            (Movie) arr[0],
            (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
            (Double) arr[2],
            (Long) arr[3]
        );
      }
    };
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public MovieDTO get(Long mno) {
    List<Object[]> result = movieRepository.getMovieWithAll(mno);
    Movie movie = (Movie) result.get(0)[0];
    List<MovieImage> movieImages = new ArrayList<>();
    result.forEach(new Consumer<Object[]>() {
      @Override
      public void accept(Object[] objects) {
        movieImages.add((MovieImage) objects[1]);
      }
    });
    double avg = (Double) result.get(0)[2];
    Long reviewCnt = (Long) result.get(0)[3];
    return entitiesToDTO(movie, movieImages, avg, reviewCnt);
  }

  @Override
  public MovieDTO modify(MovieDTO movieDTO) {
    Optional<Review> result = movieRepository.findById(movieDTO.getMno());
    if (result.isPresent()) {
      Movie movie = result.get().getMovie();
      movie.changeTitle(movieDTO.getTitle());
      movieRepository.save(movie);
    }
    List<MovieImage> existingImages = movieImageRepository.findByMovie()
  }
}
