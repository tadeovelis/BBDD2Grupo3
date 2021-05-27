package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CreditCardPayment")
public class CreditCardPayment extends PaymentMethod {
	/*
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    */
	@Column(nullable=false)
	private String brand;
	@Column(unique=true)
	private Long number;
	@Column(nullable=false)
	private Date expiry;
	@Column(nullable=false)
	private Integer cvv;
	@Column(nullable=false)
	private String owner;
	
	public CreditCardPayment() {}

	public CreditCardPayment(String name, String brand, Long number, Date expiry, Integer cvv, String owner) {
		this.setName(name);
		this.setBrand(brand);
		this.setNumber(number);
		this.setExpiry(expiry);
		this.setCvv(cvv);
		this.setOwner(owner);
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public Integer getCvv() {
		return cvv;
	}

	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	};
	
}
