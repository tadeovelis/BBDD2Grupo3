package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(
	name = "ProductOnSale",
	indexes = @Index(columnList = "product_id, provider_id, initialDate", unique = true)
)
public class ProductOnSale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;
	private Float price;
	private Date initialDate;
	private Date finalDate;
	
	
	public ProductOnSale() {};
	
	public ProductOnSale(Product product, Provider provider, Float price, Date initialDate) {
		this.setProduct(product);
		this.setProvider(provider);
		this.setPrice(price);
		this.setInitialDate(initialDate);
		// La final date se setea en null por defecto
		this.setFinalDate(null);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Long getId() {
		return id;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}
}
