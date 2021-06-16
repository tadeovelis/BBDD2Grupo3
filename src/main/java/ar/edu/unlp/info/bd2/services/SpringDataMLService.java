package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import ar.edu.unlp.info.bd2.model.Category;
import ar.edu.unlp.info.bd2.model.CreditCardPayment;
import ar.edu.unlp.info.bd2.model.DeliveryMethod;
import ar.edu.unlp.info.bd2.model.OnDeliveryPayment;
import ar.edu.unlp.info.bd2.model.PaymentMethod;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.ProductOnSale;
import ar.edu.unlp.info.bd2.model.Provider;
import ar.edu.unlp.info.bd2.model.Purchase;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.repositories.CategoryRepository;
import ar.edu.unlp.info.bd2.repositories.CreditCardPaymentRepository;
import ar.edu.unlp.info.bd2.repositories.DeliveryMethodRepository;
import ar.edu.unlp.info.bd2.repositories.MLException;
import ar.edu.unlp.info.bd2.repositories.OnDeliveryPaymentRepository;
import ar.edu.unlp.info.bd2.repositories.PaymentMethodRepository;
import ar.edu.unlp.info.bd2.repositories.ProductOnSaleRepository;
import ar.edu.unlp.info.bd2.repositories.ProductRepository;
import ar.edu.unlp.info.bd2.repositories.ProviderRepository;
import ar.edu.unlp.info.bd2.repositories.PurchaseRepository;
import ar.edu.unlp.info.bd2.repositories.UserRepository;

public class SpringDataMLService implements MLService {
	
	// Repositorios
	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CreditCardPaymentRepository creditCardPaymentRepository;
	@Autowired
	private DeliveryMethodRepository deliveryMethodRepository;
	@Autowired
	private OnDeliveryPaymentRepository onDeliveryPaymentRepository;
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	@Autowired
	private ProductOnSaleRepository productOnSaleRepository;
	@Autowired
	private ProviderRepository providerRepository;
	

	@Override
	public List<Purchase> getAllPurchasesMadeByUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersSpendingMoreThanInPurchase(Float amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersSpendingMoreThan(Float amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Provider> getTopNProvidersInPurchases(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getTop3MoreExpensiveProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getTopNUsersMorePurchase(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Purchase> getPurchasesInPeriod(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductForCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Purchase> getPurchasesForProvider(Long cuit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getBestSellingProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsOnePrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductWithMoreThan20percentDiferenceInPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Provider getProviderLessExpensiveProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Provider> getProvidersDoNotSellOn(Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductOnSale> getSoldProductsOn(Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsNotSold() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeliveryMethod getMostUsedDeliveryMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OnDeliveryPayment getMoreChangeOnDeliveryMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getHeaviestProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category getCategoryWithLessProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category createCategory(String name) throws MLException {
		Category category = new Category(name);
		try {
			this.categoryRepository.save(category);
		}
		catch(DataIntegrityViolationException e) {
			throw new MLException("Constraint Violation");
		}
		return category;
	}

	@Override
	public Product createProduct(String name, Float weight, Category category) throws MLException {
		Product product = new Product(name, weight, category);
		try {
			this.productRepository.save(product);
		}
		catch(DataIntegrityViolationException e) {
			throw new MLException("Constraint Violation");
		}
		return product;
	}

	@Override
	public User createUser(String email, String fullname, String password, Date dayOfBirth) throws MLException {
		User user = new User(email, fullname, password, dayOfBirth);
		try {
			this.userRepository.save(user);
		}
		catch(DataIntegrityViolationException e) {
			throw new MLException("Constraint Violation");
		}
		return user;
	}

	@Override
	public Provider createProvider(String name, Long cuit) throws MLException {
		Provider provider = new Provider(name, cuit);
		try {
			this.providerRepository.save(provider);
		}
		catch(DataIntegrityViolationException e) {
			throw new MLException("Constraint Violation");
		}
		return provider;
	}

	@Override
	public DeliveryMethod createDeliveryMethod(String name, Float cost, Float startWeight, Float endWeight)
			throws MLException {
		DeliveryMethod deliveryMethod = new DeliveryMethod(name, cost, startWeight, endWeight);
		this.deliveryMethodRepository.save(deliveryMethod);
		return deliveryMethod;
	}

	@Override
	public CreditCardPayment createCreditCardPayment(String name, String brand, Long number, Date expiry, Integer cvv,
			String owner) throws MLException {
		CreditCardPayment creditCardPayment = new CreditCardPayment(name, brand, number, expiry, cvv, owner);
		try {
			this.creditCardPaymentRepository.save(creditCardPayment);
		}
		catch(DataIntegrityViolationException e) {
			throw new MLException("Constraint Violation");
		}
		return creditCardPayment;
	}

	@Override
	public OnDeliveryPayment createOnDeliveryPayment(String name, Float promisedAmount) throws MLException {
		OnDeliveryPayment onDeliveryPayment = new OnDeliveryPayment(name, promisedAmount);
		this.onDeliveryPaymentRepository.save(onDeliveryPayment);
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
	public Optional<User> getUserByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	@Override
	public Optional<Provider> getProviderByCuit(long cuit) {
		return this.providerRepository.findByCuit(cuit);
	}

	@Override
	public Optional<Category> getCategoryByName(String name) {
		return this.categoryRepository.findByName(name);
	}

	@Override
	public Optional<Product> getProductByName(String name) {
		return this.productRepository.findByName(name);
	}

	@Override
	public ProductOnSale getProductOnSaleById(Long id) {
		return this.productOnSaleRepository.findById(id).get();
	}

	@Override
	public Optional<DeliveryMethod> getDeliveryMethodByName(String name) {
		return this.deliveryMethodRepository.findByName(name);
	}

	@Override
	public Optional<CreditCardPayment> getCreditCardPaymentByName(String name) {
		return this.creditCardPaymentRepository.findByName(name);
	}

	@Override
	public Optional<OnDeliveryPayment> getOnDeliveryPaymentByName(String name) {
		return this.onDeliveryPaymentRepository.findByName(name);
	}

	@Override
	public Optional<Purchase> getPurchaseById(Long id) {
		return this.purchaseRepository.findById(id);
	}

}
