package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BidListService {
    private final BidListRepository bidListRepository;

    @Transactional
    public List<BidList> getBidLists() {
        return bidListRepository.findAll();
    }

    @Transactional
    public BidList saveBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Transactional
    public BidList getBidListById(Integer id) {
        return bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id:" + id));
    }

    @Transactional
    public void deleteBidList(Integer id) {
        bidListRepository.deleteById(id);
    }
}

