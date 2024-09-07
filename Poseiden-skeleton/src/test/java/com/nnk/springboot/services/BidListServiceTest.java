package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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

class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBidLists_shouldReturnBidLists() {
        BidList bidList = BidList.builder().bidListId(1).account("TestAccount").build();
        when(bidListRepository.findAll()).thenReturn(Collections.singletonList(bidList));

        List<BidList> result = bidListService.getBidLists();

        assertEquals(1, result.size());
        assertEquals(bidList, result.get(0));
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    void saveBidList_shouldReturnSavedBidList() {
        BidList bidList = BidList.builder().bidListId(1).account("TestAccount").build();
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList result = bidListService.saveBidList(bidList);

        assertEquals(bidList, result);
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    void getBidListById_shouldReturnBidList() {
        BidList bidList = BidList.builder().bidListId(1).account("TestAccount").build();
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        BidList result = bidListService.getBidListById(1);

        assertEquals(bidList, result);
        verify(bidListRepository, times(1)).findById(1);
    }

    @Test
    void getBidListById_shouldThrowExceptionWhenNotFound() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bidListService.getBidListById(1));
        verify(bidListRepository, times(1)).findById(1);
    }

    @Test
    void deleteBidList_shouldCallDeleteById() {
        doNothing().when(bidListRepository).deleteById(1);

        bidListService.deleteBidList(1);

        verify(bidListRepository, times(1)).deleteById(1);
    }
}
