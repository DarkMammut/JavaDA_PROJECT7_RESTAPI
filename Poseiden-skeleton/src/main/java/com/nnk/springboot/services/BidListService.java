package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidListService {
    private final BidListRepository bidListRepository;

    @Transactional
    public BidList GetBidList() {
        return BidListRepository.findAll();
    }

}
