package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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

class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCurvePoints_shouldReturnCurvePoints() {
        CurvePoint curvePoint = CurvePoint.builder().id(1).curveId(10).build();
        when(curvePointRepository.findAll()).thenReturn(Collections.singletonList(curvePoint));

        List<CurvePoint> result = curvePointService.getCurvePoints();

        assertEquals(1, result.size());
        assertEquals(curvePoint, result.get(0));
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    void saveCurvePoint_shouldReturnSavedCurvePoint() {
        CurvePoint curvePoint = CurvePoint.builder().id(1).curveId(10).build();
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint result = curvePointService.saveCurvePoint(curvePoint);

        assertEquals(curvePoint, result);
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void getCurvePointById_shouldReturnCurvePoint() {
        CurvePoint curvePoint = CurvePoint.builder().id(1).curveId(10).build();
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.getCurvePointById(1);

        assertEquals(curvePoint, result);
        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    void getCurvePointById_shouldThrowExceptionWhenNotFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> curvePointService.getCurvePointById(1));
        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    void deleteCurvePoint_shouldCallDeleteById() {
        doNothing().when(curvePointRepository).deleteById(1);

        curvePointService.deleteCurvePoint(1);

        verify(curvePointRepository, times(1)).deleteById(1);
    }
}
