package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {
		// Create a BidList object
		BidList bid = BidList.builder()
				.account("Account Test")
				.type("Type Test")
				.ask(10d)  // Setting ask, not bidQuantity
				.bidQuantity(15d)  // Added correct field bidQuantity
				.build();

		// Save
		bid = bidListRepository.save(bid);
		assertNotNull(bid.getBidListId());
		assertEquals(10d, bid.getAsk());  // Test 'ask' field correctly
		assertEquals(15d, bid.getBidQuantity());

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		assertEquals(20d, bid.getBidQuantity());

		// Find all
		List<BidList> listResult = bidListRepository.findAll();
		assertFalse(listResult.isEmpty());

		// Delete
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
}
