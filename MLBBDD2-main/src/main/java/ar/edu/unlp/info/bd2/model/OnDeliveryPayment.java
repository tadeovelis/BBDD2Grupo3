package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OnDeliveryPayment")
public class OnDeliveryPayment extends PaymentMethod {
	@Column(nullable=false)
	private Float promisedAmount;
	
	public OnDeliveryPayment() {};
	
	public OnDeliveryPayment(String name, Float promisedAmount) {
		super(name);
		this.setPromisedAmount(promisedAmount);
	}

	public Float getPromisedAmount() {
		return promisedAmount;
	}

	public void setPromisedAmount(Float promisedAmount) {
		this.promisedAmount = promisedAmount;
	}
}
