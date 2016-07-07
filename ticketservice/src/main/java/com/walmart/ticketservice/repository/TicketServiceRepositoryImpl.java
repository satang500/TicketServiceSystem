package com.walmart.ticketservice.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.walmart.ticketservice.model.Customer;
import com.walmart.ticketservice.model.Seat;
import com.walmart.ticketservice.model.SeatHold;
import com.walmart.ticketservice.model.TicketSystem;
import com.walmart.ticketservice.util.EmailUtil;
import com.walmart.ticketservice.util.Util;

public class TicketServiceRepositoryImpl implements TicketServiceRepository {
    private static final Logger logger = LoggerFactory.getLogger(TicketServiceRepositoryImpl.class);

	private static final TicketSystem ticketSystem = new TicketSystem();
	private EmailUtil emailUitl = new EmailUtil();
	private Util util = new Util();
	
	/* (non-Javadoc)
	 * @see com.walmart.ticketservice.repository.TicketServiceRepository#getNumberOfAvailableSeats(java.util.Optional)
	 */
	public int getNumberOfAvailableSeats(Optional<Integer> level) {
		int[] L = util.getRangeOfLevel(level, level);
		int minL = L[0];
		int maxL = L[1];
		return ticketSystem.numSeatAavailable(minL, maxL);
	}
	
	/* (non-Javadoc)
	 * @see com.walmart.ticketservice.repository.TicketServiceRepository#findAndHoldSeats(int, java.util.Optional, java.util.Optional, String)
	 */
	public SeatHold findAndHoldSeats(int numSeatsNeeded, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {
		int[] L = util.getRangeOfLevel(minLevel, maxLevel);
		int minL = L[0];
		int maxL = L[1];
		
		SeatHold seatHold = null;

		int totalAvailableSeats = ticketSystem.numSeatAavailable( minL, maxL );
        
		// if total available seats bet. minLevel and maxLevel < numSeatsNeeded, cannot hold seats
		if( totalAvailableSeats < numSeatsNeeded ) {	
			logger.info("CANNOT held seats: totalAvailableSeats <  numSeatsNeeded");
			return null;
		}
		else {
			// Create HoldSeat and store information in data structure accordingly
			Customer cust1 = new Customer(numSeatsNeeded, "", "", customerEmail);
			Customer cust2 = ticketSystem.holdSeats( cust1, numSeatsNeeded, minL, maxL);
			seatHold = new SeatHold( true,  cust2);

			// Put seatHold in seatHoldMap in TicketSystem 
			Map<String, SeatHold> seatHoldMap = TicketSystem.getSeatHoldMap();
			seatHoldMap.put(seatHold.getSeatHoldId(), seatHold);

			logger.info("Hold seat requested: "+ seatHold.toString());
			return seatHold;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.walmart.ticketservice.repository.TicketServiceRepository#reserveSeats(java.lang.String, java.lang.String)
	 */
	public String reserveSeats(String seatHoldId, String customerEmail) {
		Map<String, SeatHold> seatHoldMap = TicketSystem.getSeatHoldMap();
		
		String confirmCode = null;
		
		if( seatHoldMap.containsKey(seatHoldId)) {
			SeatHold seatHold = seatHoldMap.get(seatHoldId);
			
			confirmCode = ticketSystem.reserveSeat(seatHold);

			// if seatHoldId has been already reserved and committed, skip 
			if( confirmCode.isEmpty())  {
				logger.info("seatHoldId {} has been ALREADY Reserved and Committed", seatHoldId);
				return "";
			}
			
			// create email content to send reservation information to the given customer email
			List<Seat> holdSeatList = seatHold.getCustomer().getHoldSeatList();
			String emailContent = emailUitl.createEmailContent(holdSeatList);
			emailUitl.commit( emailContent, customerEmail);
			logger.info("Reserved for seatHoldId {} for customer email {} and reseravation confirm code is {}", seatHoldId, customerEmail, confirmCode);
			logger.info("The sent email content is {}", emailContent);
			return confirmCode;					
		}
		else {
			logger.error("SeatHoldId {} cannot be found in seatHodMap", seatHoldId);
			return "";
		}
	}

	/**
	 * 
	 * @return
	 * Map<String,SeatHold>
	 *
	 */
	public static Map<String, SeatHold> getSeatHoldlMap() {
		Map<String, SeatHold> seatHoldMap = TicketSystem.getSeatHoldMap();
		return seatHoldMap;
	}
	
	/**
	 * @return
	 * Set<String> a set of held seat identifiers
	 */
	public Set<String> getSeatHoldIdSet() {
		Map<String, SeatHold> seatHoldMap = TicketSystem.getSeatHoldMap();
		Set<String> seatHoldIdSet = seatHoldMap.keySet();
		return seatHoldIdSet;
	}
	
	/**
	 * Displays the current status in the ticket system
	 * void
	 */
	public void viewTicketSystem() {
		ticketSystem.print();
	}
}
