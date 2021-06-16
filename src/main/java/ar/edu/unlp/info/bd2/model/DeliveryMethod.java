package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DeliveryMethod")
public class DeliveryMethod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	private Float cost;
	private Float startWeight;
	private Float endWeight;
	
	public DeliveryMethod() {};
	
	public DeliveryMethod(String name, Float cost, Float startWeight, Float endWeight) {
		this.setName(name);
		this.setCost(cost);
		this.setStartWeight(startWeight);
		this.setEndWeight(endWeight);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public Float getStartWeight() {
		return startWeight;
	}

	public void setStartWeight(Float startWeight) {
		this.startWeight = startWeight;
	}

	public Float getEndWeight() {
		return endWeight;
	}

	public void setEndWeight(Float endWeight) {
		this.endWeight = endWeight;
	}

	public Long getId() {
		return id;
	}
}
