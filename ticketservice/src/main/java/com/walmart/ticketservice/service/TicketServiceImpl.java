package com.walmart.ticketservice.service;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.walmart.ticketservice.model.SeatHold;
import com.walmart.ticketservice.repository.TicketServiceRepository;
import com.walmart.ticketservice.repository.TicketServiceRepositoryImpl;

public class TicketServiceImpl implements TicketService {
    TicketServiceRepository ticketServiceRepository = new TicketServiceRepositoryImpl();
    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    /* (non-Javadoc)
     * @see com.walmart.ticketservice.service.TicketService#numSeatsAvailable(java.util.Optional)
     */
    public int numSeatsAvailable(Optional<Integer> venueLevel) {
		return ticketServiceRepository.getNumberOfAvailableSeats(venueLevel);
	}
    

    /* (non-Javadoc)
     * @see com.walmart.ticketservice.service.TicketService#numSeatsAvailable(int, java.util.Optional, java.util.Optional, String)
     */
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {
		if( numSeats <= 0 ) {
			logger.error("Number of Seats you type was {}, but it should be larger than 0.", numSeats);
			return null;
		}
		
		if( customerEmail == null || customerEmail.isEmpty() ) {
			logger.error("Customer Email is not provided.");
			return null;
		}
		
		return ticketServiceRepository.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
	}

    /* (non-Javadoc)
     * @see com.walmart.ticketservice.service.TicketService#reserveSeats(java.lang.String, java.lang.String)
     */
    public String reserveSeats(String seatHoldId, String customerEmail) {
		String confNumber = "";
		
		if( seatHoldId == null || seatHoldId.isEmpty() ) {
			logger.error("Seat Hold Id is not provided.");
			return confNumber;
		}
		
		if( customerEmail == null || customerEmail.isEmpty() ) {
			logger.error("Customer Email is not provided.");
			return confNumber;
		}
		
		confNumber = ticketServiceRepository.reserveSeats( String.valueOf(seatHoldId), customerEmail);
		if( confNumber.isEmpty() )
			logger.info("Seat Hold Id {} has been ALREADY reserved and Committed! ", seatHoldId);
		else 
			logger.info("Researvation Confirmation Code {} ", confNumber);
	
		return confNumber;
	}

	public Set<String> getSeatHoldIdSet() {
		Set<String> seatHoldIdSet = ticketServiceRepository.getSeatHoldIdSet();
		return seatHoldIdSet;
	}
	
	public void viewTicketSystem() {
		ticketServiceRepository.viewTicketSystem();
	}
	
    public static void main(String[] args) {
        logger.debug("debug");
        logger.info("info");
    }	
}

