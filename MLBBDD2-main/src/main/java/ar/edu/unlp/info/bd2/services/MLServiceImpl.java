package ar.edu.unlp.info.bd2.services;
import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.MLRepository;
import ar.edu.unlp.info.bd2.repositories.MLException;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


public class MLServiceImpl implements MLService {

	@Autowired
	private MLRepository repository;

	public MLServiceImpl(MLRepository repository) {
		this.repository = repository;
	}
	
	public User createUser(String email, String fullname, String password, Date dayOfBirth) throws MLException {
		User user = new User(email, password, fullname, dayOfBirth);
		return repository.persistUser(user);
	}

	@Override
	public Category createCategory(String name) throws MLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product createProduct(String name, Float weight, Category category) throws MLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Provider createProvider(String name, Long cuit) throws MLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeliveryMethod createDeliveryMethod(String name, Float cost, Float startWeight, Float endWeight)
			throws MLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreditCardPayment createCreditCardPayment(String name, String brand, Long number, Date expiry, Integer cvv,
			String owner) throws MLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OnDeliveryPayment createOnDeliveryPayment(String name, Float promisedAmount) throws MLException {
		// TODO Auto-generated method stub
		return null;
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
	public Optional<User> getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Provider> getProviderByCuit(long cuit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Category> getCategoryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Product> getProductByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductOnSale getProductOnSaleById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<DeliveryMethod> getDeliveryMethodByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<CreditCardPayment> getCreditCardPaymentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<OnDeliveryPayment> getOnDeliveryPaymentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Purchase> getPurchaseById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}