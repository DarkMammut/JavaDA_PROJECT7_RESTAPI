package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    @Transactional
    public List<CurvePoint> getCurvePoints() {
        return curvePointRepository.findAll();
    }

    @Transactional
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    @Transactional
    public CurvePoint getCurvePointById(Integer id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));
    }

    @Transactional
    public void deleteCurvePoint(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
