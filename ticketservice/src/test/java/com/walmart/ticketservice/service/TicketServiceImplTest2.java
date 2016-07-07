package com.walmart.ticketservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

import com.walmart.ticketservice.model.SeatHold;
import com.walmart.ticketservice.util.ConfigUtil;


public class TicketServiceImplTest2 extends ConfigUtil {

	static TicketService ticketService;
	String seatHoldId = null;

	@BeforeClass
	public static void init() {
		ticketService = new TicketServiceImpl();
	}

	/**
	 * Test Reserve held seats
	 */
	@Test
	public void testFindAndHoldSeatsAndReserveSeats() {
		SeatHold seatHold = ticketService.findAndHoldSeats(2000, Optional.of(1), Optional.of(2), "star.wars@google.com");
		assertNotNull(seatHold);
		assertEquals(seatHold.getCustomer().getCustEmail(), "star.wars@google.com");
		seatHoldId = seatHold.getSeatHoldId();

		String reservationConfirmCode = ticketService.reserveSeats(seatHoldId, "star.wars@google.com");
		assertNotNull(reservationConfirmCode);
		System.out.println("reservationConfirmCode = " + reservationConfirmCode );
	}
}
