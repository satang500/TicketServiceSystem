package com.walmart.ticketservice.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.walmart.ticketservice.util.ConfigUtil;

/**
 * @author user
 * Ticket System manages data structure for searching, inserting, updating, deleting operations.
 * 
 */

public class TicketSystem extends ConfigUtil {
    private static final Logger logger = LoggerFactory.getLogger(TicketSystem.class);
	
	private static final  Map<String, SeatHold> seatHoldMap = new ConcurrentHashMap<String, SeatHold>();  
	private static final  Map<String, SeatReserve> seatReserveMap = new ConcurrentHashMap<String, SeatReserve>();  
	private Set<String> seatReservedSet = new HashSet<String>();		// contains setHoldId which was reserved.

	private Level[] levels;
	private int NUM_LEVELS;

	/**
	 * Create Ticket System and Add initial data in them
	 */
	public TicketSystem() {
		NUM_LEVELS = ConfigUtil.MAX_LEVEL + 1;
		levels = new Level[NUM_LEVELS];	
		
		for(int i = 0; i <= ConfigUtil.MAX_LEVEL; i++ ) {
			levels[i] = new Level(i+1, ConfigUtil.LEVEL_NAMES[i], ConfigUtil.LEVEL_PRICES[i], ConfigUtil.LEVEL_ROWS[i], ConfigUtil.LEVEL_SEATS[i]);
		}
	}
	
	/**
	 * Number of available seats between minLevel and maxLevel
	 * @param minLevel
	 * @param maxLevel
	 * @return the number of available seats between minlevel and maxlevel
	 */
	 public int numSeatAavailable( int minLevel, int maxLevel ) {
		makeAvailableOfExpiredHoldSeatsOfAllLevels();				
		
		int numAvailableSeats = 0;

		for( int i = minLevel; i <= maxLevel; i++ ) {
			numAvailableSeats += levels[i].getAvailableSeats();
		}
		return numAvailableSeats;
	}

	/**
	 * Hold the requested number seats between min and max levels 	
	 * @param customer
	 * @param numSeatNeeded
	 * @param minLevel
	 * @param maxLevel
	 * @return 
	 * Customer
	 */	
	public Customer holdSeats(Customer customer, int numSeatNeeded, int minLevel, int maxLevel ) {
		int remainingNumSeatsNeeded = numSeatNeeded;

		for (int i = minLevel; i <= maxLevel; i++) {
			int availSeatsInThisLevel = levels[i].getAvailableSeats();

			if( availSeatsInThisLevel > 0 ) {
				if( remainingNumSeatsNeeded < availSeatsInThisLevel ) {
					levels[i].holdSeatsForCustomer(customer, remainingNumSeatsNeeded); 	
					remainingNumSeatsNeeded = 0;
				}
				else {
					levels[i].holdSeatsForCustomer(customer, availSeatsInThisLevel); 	
					remainingNumSeatsNeeded -= availSeatsInThisLevel;
				}	

				if(remainingNumSeatsNeeded == 0 ) {		// all requested numSeatNeeded are held 
					return customer;
				}
			}
		}
		logger.info("{} seats are held", numSeatNeeded );
		return customer;
	}	

	/**
	 * Reserve seats for the given seatHoldId
	 * @param seatHold
	 * @return reservation confirmation code
	 */
	public String reserveSeat(SeatHold seatHold) {
		if( seatReservedSet.contains(seatHold.getSeatHoldId()) ){
			logger.info("{} has been already Reserved and Committed ", seatHold);
			return "";
		}
		
		String seatReserveConfirmationCode = null;
	
		Customer customer = seatHold.getCustomer();
		List<Seat> holdSeatList = customer.getHoldSeatList();
		
		// Change the status for each seat to be reserved and create a reservation confirmation code
		SeatReserve seatReserve = new SeatReserve(customer, seatHold);
		seatReserveConfirmationCode = seatReserve.getSeatReserveConfirmationCode();
		
		for( Seat seat : holdSeatList ) {
			seat.setSeatStatus(SeatStatus.RESERVED);						// change status for a seat
			customer.addReserveSeatList(seat);								// add a reserved seat to reserve list of a customer
			seatReserveMap.put(seatReserveConfirmationCode, seatReserve);  	// add seatRerve to seatReserveMap in the ticket system
			seatReservedSet.add(seatHold.getSeatHoldId());					// add seatHoldId which was reserved in the seatReservedSet to prevent duplicate servation
		}
		
		return String.valueOf(seatReserveConfirmationCode);
	}
	
	/**
	 * Makes expired held seats available
	 * @return void
	 */
	 public void makeAvailableOfExpiredHoldSeatsOfAllLevels() {
		for( Map.Entry<String, SeatHold> entry : seatHoldMap.entrySet()) {
			String seatHoldId = entry.getKey();
			SeatHold seatHold = entry.getValue();

			if( seatHold.getIsExpired() ) {
				logger.info("seatHoldId {} is expired", seatHold.toString());
				
				// remove holdSeatList from Customer
				List<Seat> holdList = seatHold.getCustomer().getHoldSeatList();
				
				for( Seat seat : holdList ) {
					seat.removeCustomer();					// make seats available
					Level level = seat.getLevel();			// increase number of available seats by 1 in the corresponding level
					level.addAvailableSeats(1);			
					logger.info("Now the number of available seats in level {} is ", level.getLevel(), level.getAvailableSeats() );
				}
				seatHoldMap.remove(seatHoldId);				// remove seatHoldId from seatHoldMap
			}
		}
	}

	public static Map<String, SeatHold> getSeatHoldMap() {
		return seatHoldMap;
	}

	 public void print() {
		for (int i = 0; i < levels.length; i++) {
			System.out.println("\n*** LEVEL: " + levels[i] + "   ***\n" );
			levels[i].print();
		}
		System.out.println();
	}
}	
