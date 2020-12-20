package moneyOK.transaction;

import java.util.Date;

public class FixedTransaction extends Transaction{
	public Date startDate;
	public int  frequency;
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	
}
