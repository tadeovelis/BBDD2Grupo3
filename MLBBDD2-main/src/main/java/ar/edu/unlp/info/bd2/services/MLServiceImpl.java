package ar.edu.unlp.info.bd2.services;
import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.MLRepository;
import ar.edu.unlp.info.bd2.repositories.MLException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class MLServiceImpl implements MLService {

	@Autowired
	private MLRepository repository;

	public MLServiceImpl(MLRepository repository) {
		this.repository = repository;
	}
	
	
	@Override
	@Transactional
	public User createUser(String email, String fullname, String password, Date dayOfBirth) throws MLException {
		User user = new User(email, fullname, password, dayOfBirth);
		try {
			repository.save(user);
		}
		catch(ConstraintViolationException e) {
			throw new MLException("Constraint Violation");
		}
        return user;
	}

	@Override
	@Transactional
	public Category createCategory(String name) throws MLException {
		Category category = new Category(name);
		try {
			repository.save(category);
		}
		catch(ConstraintViolationException e) {
			throw new MLException("Constraint Violation");
		}
		return category;
	}

	@Override
	@Transactional
	public Product createProduct(String name, Float weight, Category category) throws MLException {
		Product product = new Product(name, weight, category);
		try {
			repository.save(product);
		}
		catch(ConstraintViolationException e) {
			throw new MLException("Constraint Violation");
		}
		return product;
	}

	@Override
	@Transactional
	public Provider createProvider(String name, Long cuit) throws MLException {
		Provider provider = new Provider(name, cuit);
		try {
			repository.save(provider);
		}
		catch(ConstraintViolationException e) {
			throw new MLException("Constraint Violation");
		}
		return provider;
	}

	@Override
	@Transactional
	public DeliveryMethod createDeliveryMethod(String name, Float cost, Float startWeight, Float endWeight)
			throws MLException {
		DeliveryMethod deliveryMethod = new DeliveryMethod(name, cost, startWeight, endWeight);
		repository.save(deliveryMethod);
		return deliveryMethod;
	}

	@Override
	@Transactional
	public CreditCardPayment createCreditCardPayment(String name, String brand, Long number, Date expiry, Integer cvv,
			String owner) throws MLException {
		CreditCardPayment creditCardPayment = new CreditCardPayment(name, brand, number, expiry, cvv, owner);
		repository.save(creditCardPayment);
		return creditCardPayment;
	}

	@Override
	@Transactional
	public OnDeliveryPayment createOnDeliveryPayment(String name, Float promisedAmount) throws MLException {
		OnDeliveryPayment onDeliveryPayment = new OnDeliveryPayment(name, promisedAmount);
		repository.save(onDeliveryPayment);
		return onDeliveryPayment;
	}

	@Override
	@Transactional
	public ProductOnSale createProductOnSale(Product product, Provider provider, Float price, Date initialDate)
			throws MLException {
		// Chequear si el producto ya tiene un precio para ese proveedor
		Optional<List<ProductOnSale>> pos = this.getProductsOnSaleByProductAndProvider(product, provider);
		// Si ya tiene tengo que fijarme que la initialDate sea posterior a la initialDate actual
		// Después, si la initialDate está bien, le actualizo la finalDate de null a initialDate - 1 día
		if (pos.isPresent()) {
			// Me agarro el precio más nuevo
			ProductOnSale p = pos.get().get(pos.get().size()-1);
			// Comparo las dates
			if (initialDate.after(p.getInitialDate()) || initialDate.equals(p.getInitialDate())) { 
				// Le pongo como finalDate la initialDate - 1 día
				Calendar cal = Calendar.getInstance();
				cal.setTime(initialDate);
				cal.add(Calendar.DATE, -1);
				Date newFinalDate = cal.getTime();
				p.setFinalDate(newFinalDate);
			}
			// Si la initialDate es anterior a la initialDate 
			// del ProductOnSale entonces lanzo la excepción
			else {
				throw new MLException("Ya existe un precio para el producto con fecha de inicio "
										+ "de vigencia posterior a la fecha de inicio dada");
			}
		}
		// Creo el nuevo ProductOnSale (si no tiré excepción obvio)
		ProductOnSale productOnSale = new ProductOnSale(product, provider, price, initialDate);
		try {
			repository.save(productOnSale);
		}
		catch(ConstraintViolationException e) {
			throw new MLException("Constraint Violation");
		}
		return productOnSale;
	}
	
	public Optional<List<ProductOnSale>> getProductsOnSaleByProductAndProvider(Product product, Provider provider) {
		return Optional.ofNullable(this.repository.findProductsOnSaleByProductAndProvider(product, provider));
	}

	@Override
	@Transactional
	public Purchase createPurchase(ProductOnSale productOnSale, Integer quantity, User client,
			DeliveryMethod deliveryMethod, PaymentMethod paymentMethod, String address, Float coordX, Float coordY,
			Date dateOfPurchase) throws MLException {
		
		Purchase purchase = new Purchase(productOnSale, quantity, client, deliveryMethod, paymentMethod,
				address, coordX, coordY, dateOfPurchase);
		repository.save(purchase);
		return purchase;
	}

	@Override
	@Transactional
	public Optional<User> getUserByEmail(String email) {
		return Optional.ofNullable(this.repository.findUserByEmail(email));
	}

	@Override
	@Transactional
	public Optional<Provider> getProviderByCuit(long cuit) {
		return Optional.ofNullable(this.repository.findProviderByCuit(cuit));
	}

	@Override
	@Transactional
	public Optional<Category> getCategoryByName(String name) {
		return Optional.ofNullable(this.repository.findCategoryByName(name));
	}

	@Override
	@Transactional
	public Optional<Product> getProductByName(String name) {
		return Optional.ofNullable(this.repository.findProductByName(name));
	}

	@Override
	@Transactional
	public ProductOnSale getProductOnSaleById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Optional<DeliveryMethod> getDeliveryMethodByName(String name) {
		return Optional.ofNullable(this.repository.findDeliveryMethodByName(name));
	}

	@Override
	@Transactional
	public Optional<CreditCardPayment> getCreditCardPaymentByName(String name) {
		return Optional.ofNullable(this.repository.findCreditCardPaymentByName(name));
	}

	@Override
	@Transactional
	public Optional<OnDeliveryPayment> getOnDeliveryPaymentByName(String name) {
		return Optional.ofNullable(this.repository.findOnDeliveryPaymentByName(name));
	}

	@Override
	@Transactional
	public Optional<Purchase> getPurchaseById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}