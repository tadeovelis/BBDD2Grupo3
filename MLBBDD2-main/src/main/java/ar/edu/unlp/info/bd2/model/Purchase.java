package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Purchase")
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@ManyToOne
	@JoinColumn(name = "productOnSale_id")
	private ProductOnSale productOnSale;
	private Integer quantity;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User client;
	@ManyToOne
	@JoinColumn(name = "deliveryMethod_id")
	private DeliveryMethod deliveryMethod;
	@ManyToOne
	@JoinColumn(name = "paymentMethod_id")
	private PaymentMethod paymentMethod;
	private String address;
	private Float coordX;
	private Float coordY;
	@Column(nullable=false)
	private Date dateOfPurchase;
	
	public Purchase() {};
	
	public Purchase(ProductOnSale productOnSale, Integer quantity, User client, DeliveryMethod deliveryMethod,
			PaymentMethod paymentMethod, String address, Float coordX, Float coordY, Date dateOfPurchase) {
		this.setProductOnSale(productOnSale);
		this.setQuantity(quantity);
		this.setClient(client);
		this.setDeliveryMethod(deliveryMethod);
		this.setPaymentMethod(paymentMethod);
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setDateOfPurchase(dateOfPurchase);
	}
	

	public Long getId() {
		return id;
	}

	public ProductOnSale getProductOnSale() {
		return productOnSale;
	}

	public void setProductOnSale(ProductOnSale productOnSale) {
		this.productOnSale = productOnSale;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getCoordX() {
		return coordX;
	}

	public void setCoordX(Float coordX) {
		this.coordX = coordX;
	}

	public Float getCoordY() {
		return coordY;
	}

	public void setCoordY(Float coordY) {
		this.coordY = coordY;
	}

	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}
	
	public Float getAmount() {
		return ( (this.quantity * this.productOnSale.getPrice()) + this.deliveryMethod.getCost() );
	}
	public float getTotalWeight() {
		return (this.quantity * this.productOnSale.getProduct().getWeight());
	}
}
