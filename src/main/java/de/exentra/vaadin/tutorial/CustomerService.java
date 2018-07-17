package de.exentra.vaadin.tutorial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 * <p>
 * In demos/tutorials/examples, get a reference to this service class with
 * {@link CustomerService#getInstance()}.
 */
// @Helper
public class CustomerService {
  private static       CustomerService instance;
  private static final Logger          LOGGER = Logger.getLogger(CustomerService.class.getName());

  private final Map<Long, Customer> contacts = new HashMap<>();
  private       long                nextId   = 0;

  private CustomerService() {
    super();
  }

  /**
   * @return a reference to an example facade for Customer objects.
   */
  public static CustomerService getInstance() {
    if (instance == null) instance = new CustomerService();
    return instance;
  }

  /**
   * @return all available Customer objects.
   */
  public synchronized List<Customer> findAll() {
    return findAll(null);
  }

  /**
   * Finds all Customer's that match given filter.
   *
   * @param stringFilter filter that returned objects should match or null/empty string
   *                     if all objects should be returned.
   * @return list a Customer objects
   */
  public synchronized List<Customer> findAll(String stringFilter) {
    return contacts
        .values()
        .stream()
//        .parallelStream()
        .filter(c -> (stringFilter == null)
                     || ((stringFilter.isEmpty())
                         || c.toString().toLowerCase().contains(stringFilter.toLowerCase())))
        .map(Customer::copy)
        .collect(toList());
  }

  /**
   * Finds all Customer's that match given filter and limits the resultset.
   *
   * @param stringFilter filter that returned objects should match or null/empty string
   *                     if all objects should be returned.
   * @param start        the index of first result
   * @param maxresults   maximum result count
   * @return list a Customer objects
   */
  public synchronized List<Customer> findAll(String stringFilter, int start, int maxresults) {
    return findAll(stringFilter)
        .stream()
        .sorted((o1, o2) -> (int) (o2.getId() - o1.getId()))
        .skip(start)
        .limit(maxresults)
        .collect(toList());
  }

  /**
   * @return the amount of all customers in the system
   */
  public synchronized long count() {
    return contacts.size();
  }

  /**
   * Deletes a customer from a system
   *
   * @param value the Customer to be deleted
   */
  public synchronized void delete(Customer value) {
    contacts.remove(value.getId());
  }

  /**
   * Persists or updates customer in the system. Also assigns an identifier
   * for new Customer instances.
   *
   * @param entry
   */
  public synchronized void save(Customer entry) {
    if (entry == null) {
      LOGGER.log(Level.SEVERE, "Customer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
      return;
    }
    if (entry.getId() == null) {
      entry.setId(nextId++);
    }
    entry = entry.copy();
    contacts.put(entry.getId(), entry);
  }

  /**
   * Sample data generation
   */
  public void ensureTestData() {
    contacts.clear();
    bulkTestDataStrategy();
  }

  void defaultTestDataStrategy() {
    final String[] names = new String[]{"Gabrielle Patel", "Brian Robinson", "Eduardo Haugen", "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson", "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson", "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith", "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin", "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen", "Jaydan Jackson", "Bernard Nilsen"};
    Random         r     = new Random(0);
    for (String name : names) {
      String[] split = name.split(" ");
      Customer c     = new Customer();
      c.setFirstName(split[0]);
      c.setLastName(split[1]);
      c.setStatus(CustomerStatus.values()[r.nextInt(CustomerStatus.values().length)]);
      save(c);
    }
  }

  private void bulkTestDataStrategy() {
    final String[] firstNames = new String[]{"Gabrielle", "Brian", "Eduardo", "Koen", "Alejandro", "Angel", "Yahir", "Haiden", "Emily", "Corinne", "Ryann", "Yurem", "Kelly", "Eileen", "Katelyn", "Israel", "Quinn", "Makena", "Danielle", "Leland", "Gunner", "Jamar", "Lara", "Ann", "Remington", "Rene", "Elvis", "Solomon", "Jaydan", "Bernard"};
    final int      maxFirst   = firstNames.length - 1;

    final String[] lastNames = new String[]{"Patel", "Robinson", "Haugen", "Johansen", "Macdonald", "Karlsson", "Gustavsson", "Svensson", "Stewart", "Davis", "Jackson", "Walker", "Martin", "Carlsson", "Hansson", "Smith", "Danielle Watson", "Harris", "Karlsen", "Olsson", "Martin", "Andersson", "Carlsson", "Olsen", "Jackson", "Nilsen"};
    final int      maxLast   = lastNames.length - 1;

    final Random r = new Random(0);
    IntStream
        .rangeClosed(1, 1_000_000)
        .mapToObj(i -> {
          Customer customer = new Customer();
          customer.setStatus(CustomerStatus.values()[r.nextInt(CustomerStatus.values().length)]);

          String first = firstNames[r.nextInt(maxFirst)];
          customer.setFirstName(first + " " + String.format("%07d", i));

          String last = lastNames[r.nextInt(maxLast)];
          customer.setLastName(last);
          return customer;
        })
        .forEach(this::save);

  }
}
