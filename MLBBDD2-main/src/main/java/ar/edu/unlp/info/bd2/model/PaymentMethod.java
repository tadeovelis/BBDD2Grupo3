package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.InheritanceType;


@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(name = "PaymentMethod")
public class PaymentMethod {
	@Id
	@GeneratedValue
    private Long id;
	@Column(nullable=false)
	private String name;
	
	public PaymentMethod() {};
	
	public PaymentMethod(String name) {
		this.setName(name);
	};
	

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
