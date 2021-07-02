package ar.edu.unlp.info.bd2.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;

@Entity
@Table(name="Product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(unique=true, nullable=false)
	private String name;
	@Column(nullable=false)
	private Float weight;
	@ManyToOne
	private Category category;
	@OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<ProductOnSale> productsOnSale;
	
	public Product() {};
	
	public Product(String name, Float weight, Category category) {
		this.setName(name);
		this.setWeight(weight);
		this.setCategory(category);
		this.productsOnSale=new LinkedList<ProductOnSale>();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getWeigth() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Long getId() {
		return id;
	}
	public List<ProductOnSale> getProductsOnSale(){
		return productsOnSale;
	}

	public void addProductOnSale(ProductOnSale productOnSale) {
		productsOnSale.add(productOnSale);
	}
}
