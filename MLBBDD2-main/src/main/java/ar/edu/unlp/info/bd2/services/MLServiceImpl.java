package ar.edu.unlp.info.bd2.services;
import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.MLRepository;
import ar.edu.unlp.info.bd2.repositories.MLRepositoryStatistics;
import ar.edu.unlp.info.bd2.repositories.CommonRepository;
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
	@Autowired
	private MLRepositoryStatistics repositoryStatistics;

	public MLServiceImpl(MLRepository repository, MLRepositoryStatistics repositoryStatistics) {
		this.repository = repository;
		this.repositoryStatistics = repositoryStatistics;
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
		Purchase purchase = new Purchase(productOnSale, quantity, client, deliveryMethod, paymentMethod, address, coordX, coordY, dateOfPurchase);

		if (purchase.getTotalWeight() >= deliveryMethod.getStartWeight() &&
				purchase.getTotalWeight() <= deliveryMethod.getEndWeight()) {
			repository.save(purchase);
			return purchase;
		}
		else {
			throw new MLException("Método de delivery no válido");
		} 
		
		
		
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
	
	public List<Purchase> getAllPurchasesMadeByUser(String username){
		return this.repositoryStatistics.getAllPurchasesMadeByUser(username);
	}
	/*
	public List<User> getUsersSpendingMoreThanInPurchase(Float amount){
		return this.repository.getUsersSpendingMoreThanInPurchase(amount);
	}
	*/
	
	public List <Product> getProductForCategory (Category category){
		return this.repositoryStatistics.getProductForCategory(category);
	}
	
	public List <Purchase> getPurchasesForProvider(Long cuit){
		return this.repositoryStatistics.getPurchasesForProvider(cuit);
	}
	
	public List <Purchase> getPurchasesInPeriod(Date startDate, Date endDate){
		return this.repositoryStatistics.getPurchasesInPeriod(startDate, endDate);
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
		return this.repositoryStatistics.getTop3MoreExpensiveProducts();
	}


	@Override
	public List<User> getTopNUsersMorePurchase(int n) {
		if(n>1)
			return this.repositoryStatistics.getTopNUsersMorePurchase(n);
		else return null;
	}


	@Override
	public Product getBestSellingProduct() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Product> getProductsOnePrice() {
		return this.repositoryStatistics.getProductsOnePrice();
	}


	@Override
	public List<Product> getProductWithMoreThan20percentDiferenceInPrice() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Provider getProviderLessExpensiveProduct() {
		return this.repositoryStatistics.getProviderLessExpensiveProduct();
	}


	@Override
	public List<Provider> getProvidersDoNotSellOn(Date day) {
		return this.repositoryStatistics.getProviderDoNotSellOn(day);
	}


	@Override
	public List<ProductOnSale> getSoldProductsOn(Date day) {
		return this.repositoryStatistics.getSoldProductsOn(day);
	}


	@Override
	public List<Product> getProductsNotSold() {
		return this.repositoryStatistics.getProductsNotSold();
	}


	@Override
	public DeliveryMethod getMostUsedDeliveryMethod() {
		return this.repositoryStatistics.getMostUsedDeliveryMethod();
	}


	@Override
	public OnDeliveryPayment getMoreChangeOnDeliveryMethod() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Product getHeaviestProduct() {
		return this.repositoryStatistics.getHeaviestProduct();
	}


	@Override
	public Category getCategoryWithLessProducts() {
		return this.repositoryStatistics.getCategoryWithLessProducts();
	}
}