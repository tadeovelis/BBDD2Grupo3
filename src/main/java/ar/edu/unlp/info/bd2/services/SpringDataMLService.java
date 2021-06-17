package ar.edu.unlp.info.bd2.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
		return this.purchaseRepository.findAllPurchasesMadeByUser(username);
	}

	@Override
	public List<User> getUsersSpendingMoreThanInPurchase(Float amount) {
		return this.userRepository.getUsersSpendingMoreThanInPurchase(amount);
	}

	@Override
	public List<User> getUsersSpendingMoreThan(Float amount) {
		return this.userRepository.getUsersSpendingMoreThan((double) amount);
	}

	@Override
	public List<Provider> getTopNProvidersInPurchases(int n) {
		Pageable pageable = PageRequest.of(0, n);
		List<Provider> providers = this.providerRepository.getTopNProvidersInPurchases(n, pageable); 
		return !providers.isEmpty() ? providers : null;
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
		return this.productRepository.findFirstByOrderByWeightDesc();
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
		try {
			this.deliveryMethodRepository.save(deliveryMethod);
		}
		catch(DataIntegrityViolationException e) {
			throw new MLException("Constraint Violation");
		}
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
		// Chequear si el producto ya tiene un precio para ese proveedor
		ProductOnSale pos = this.getLastProductOnSaleForProductAndProvider(product, provider);
		// Si ya tiene tengo que fijarme que la initialDate sea posterior a la initialDate actual
		// Después, si la initialDate está bien, le actualizo la finalDate de null a initialDate - 1 día
		if (pos != null) {
			// Comparo las dates
			if (initialDate.after(pos.getInitialDate()) || initialDate.equals(pos.getInitialDate())) { 
				// Le pongo como finalDate la initialDate - 1 día
				Calendar cal = Calendar.getInstance();
				cal.setTime(initialDate);
				cal.add(Calendar.DATE, -1);
				Date newFinalDate = cal.getTime();
				pos.setFinalDate(newFinalDate);
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
			productOnSaleRepository.save(productOnSale);
		}
		catch(DataIntegrityViolationException e) {
			throw new MLException("Constraint Violation");
		}
		return productOnSale;
	}
	
	public ProductOnSale getLastProductOnSaleForProductAndProvider(Product product, Provider provider) {
		Pageable pageable = PageRequest.of(0, 1);
		List<ProductOnSale> products = this.productOnSaleRepository.getLastProductOnSaleForProductAndProvider(product, provider, pageable);
		return !products.isEmpty() ? products.get(0) : null;
	}

	@Override
	public Purchase createPurchase(ProductOnSale productOnSale, Integer quantity, User client,
			DeliveryMethod deliveryMethod, PaymentMethod paymentMethod, String address, Float coordX, Float coordY,
			Date dateOfPurchase) throws MLException {
		Purchase purchase = new Purchase(productOnSale, quantity, client, deliveryMethod, paymentMethod, address, coordX, coordY, dateOfPurchase);

		if (purchase.getTotalWeight() >= deliveryMethod.getStartWeight() &&
				purchase.getTotalWeight() <= deliveryMethod.getEndWeight()) {
			purchaseRepository.save(purchase);
			return purchase;
		}
		else {
			throw new MLException("método de delivery no válido");
		}
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
