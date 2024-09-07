package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRatings_shouldReturnRatings() {
        Rating rating = Rating.builder().id(1).moodysRating("AAA").build();
        when(ratingRepository.findAll()).thenReturn(Collections.singletonList(rating));

        List<Rating> result = ratingService.getRatings();

        assertEquals(1, result.size());
        assertEquals(rating, result.get(0));
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void saveRating_shouldReturnSavedRating() {
        Rating rating = Rating.builder().id(1).moodysRating("AAA").build();
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.saveRating(rating);

        assertEquals(rating, result);
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void getRatingById_shouldReturnRating() {
        Rating rating = Rating.builder().id(1).moodysRating("AAA").build();
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating result = ratingService.getRatingById(1);

        assertEquals(rating, result);
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void getRatingById_shouldThrowExceptionWhenNotFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ratingService.getRatingById(1));
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void deleteRating_shouldCallDeleteById() {
        doNothing().when(ratingRepository).deleteById(1);

        ratingService.deleteRating(1);

        verify(ratingRepository, times(1)).deleteById(1);
    }
}
