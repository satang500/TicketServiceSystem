package com.walmart.ticketservice.model;

import java.time.LocalDateTime;

import com.walmart.ticketservice.util.ConfigUtil;
import com.walmart.ticketservice.util.Util;

/**
 * @author user
 * Class for SeatHold
 */
public class SeatHold extends ConfigUtil {
	// after 10 sec. expire the held seat: hold becomes available
	static final long HOLD_EXPIRE_SEC = ConfigUtil.HOLD_EXPIRE_SEC;		
	
	private boolean seatHoldSuccess;
	private String seatHoldId;
	private Customer customer;				
	private LocalDateTime holdStartTime;
	private LocalDateTime holdEndTime;
	private boolean isExpired;
	private Util util = new Util();
	
	public SeatHold() {
		setSeatHoldSuccess(false);
	}
	
	public SeatHold( boolean seatHoldSuccess, Customer customer ) {
		this.seatHoldSuccess = seatHoldSuccess;
		this.seatHoldId = util.createId();
		this.customer = customer;
		holdStartTime = LocalDateTime.now( );   
		holdEndTime = holdStartTime.plusSeconds(HOLD_EXPIRE_SEC);
	}
	
	public boolean getIsExpired() {
		if( LocalDateTime.now().isAfter(holdEndTime) ) {
			setIsExpired(true);
			return true;
		}
		else 
			return false;
	}
	
	public String getSeatHoldId() {
		return seatHoldId;
	}
	public void setSeatHoldId(String seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public boolean getSeatHoldSuccess() {
		return seatHoldSuccess;
	}
	public void setSeatHoldSuccess(boolean seatHoldSuccess) {
		this.seatHoldSuccess = seatHoldSuccess;
	}
	
	public void setIsExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}
	

	public String toString() {
		return "[SestHold.toString] SEAT HOLD: customer = " + customer.getCustEmail() + " held " + customer.getSeatNeeded() + " numbers of seats, " + 
				"seatHoldId = " + seatHoldId + ", customer.getHoldSeatList().size() = " + customer.getHoldSeatList().size();
	}

	public void print() {
		System.out.println( this );
	}
}
