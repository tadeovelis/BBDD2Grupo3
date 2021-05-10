package ar.edu.unlp.info.bd2.services;
import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.MLRepository;
import ar.edu.unlp.info.bd2.repositories.MLException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
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

	@Transactional(propagation=Propagation.REQUIRED, noRollbackFor=MLException.class)
	public Category createCategory(String name) throws MLException {
		Category category = new Category(name);
		repository.save(category);
		return category;
	}

	@Transactional(propagation=Propagation.REQUIRED, noRollbackFor=MLException.class)
	public Product createProduct(String name, Float weight, Category category) throws MLException {
		Product product = new Product(name, weight, category);
		repository.save(product);
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

	@Transactional(propagation=Propagation.REQUIRED, noRollbackFor=MLException.class)
	public DeliveryMethod createDeliveryMethod(String name, Float cost, Float startWeight, Float endWeight)
			throws MLException {
		DeliveryMethod deliveryMethod = new DeliveryMethod(name, cost, startWeight, endWeight);
		repository.save(deliveryMethod);
		return deliveryMethod;
	}

	@Override
	public CreditCardPayment createCreditCardPayment(String name, String brand, Long number, Date expiry, Integer cvv,
			String owner) throws MLException {
		CreditCardPayment creditCardPayment = new CreditCardPayment(name, brand, number, expiry, cvv, owner);
		repository.save(creditCardPayment);
		return creditCardPayment;
	}

	@Override
	public OnDeliveryPayment createOnDeliveryPayment(String name, Float promisedAmount) throws MLException {
		OnDeliveryPayment onDeliveryPayment = new OnDeliveryPayment(name, promisedAmount);
		repository.save(onDeliveryPayment);
		return onDeliveryPayment;
	}

	@Override
	public ProductOnSale createProductOnSale(Product product, Provider provider, Float price, Date initialDate)
			throws MLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Purchase createPurchase(ProductOnSale productOnSale, Integer quantity, User client,
			DeliveryMethod deliveryMethod, PaymentMethod paymentMethod, String address, Float coordX, Float coordY,
			Date dateOfPurchase) throws MLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, noRollbackFor=MLException.class)
	public Optional<User> getUserByEmail(String email) {
		return Optional.ofNullable(this.repository.findUserByEmail(email));
	}

	@Override
	public Optional<Provider> getProviderByCuit(long cuit) {
		return Optional.ofNullable(this.repository.findProviderByCuit(cuit));
	}

	@Override
	public Optional<Category> getCategoryByName(String name) {
		return Optional.ofNullable(this.repository.findCategoryByName(name));
	}

	@Override
	public Optional<Product> getProductByName(String name) {
		return Optional.ofNullable(this.repository.findProductByName(name));
	}

	@Override
	public ProductOnSale getProductOnSaleById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<DeliveryMethod> getDeliveryMethodByName(String name) {
		return Optional.ofNullable(this.repository.findDeliveryMethodByName(name));
	}

	@Override
	public Optional<CreditCardPayment> getCreditCardPaymentByName(String name) {
		return Optional.ofNullable(this.repository.findCreditCardPaymentByName(name));
	}

	@Override
	public Optional<OnDeliveryPayment> getOnDeliveryPaymentByName(String name) {
		return Optional.ofNullable(this.repository.findOnDeliveryPaymentByName(name));
	}

	@Override
	public Optional<Purchase> getPurchaseById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}