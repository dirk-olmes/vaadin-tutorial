package de.exentra.vaadin.tutorial;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;

public class CustomerForm extends FormLayout {
  private MainView        _mainView;
  private CustomerService _service = CustomerService.getInstance();

  @PropertyId("firstName") private TextField                _firstName = new TextField("First name");
  @PropertyId("lastName") private  TextField                _lastName  = new TextField("Last name");
  @PropertyId("status") private    ComboBox<CustomerStatus> _status    = new ComboBox<>("Status", CustomerStatus.values());
  ;

  private Button _save   = new Button("Save");
  private Button _delete = new Button("Delete");

  private Binder<Customer> _binder = new Binder<>(Customer.class);
  private Customer         _customer;

  //TODO refactoring - remove params from constructor -> events
  public CustomerForm(MainView mainView) {
    super();
    _mainView = mainView;

    _save.getElement().setAttribute("theme", "primary");
    _save.addClickListener(e -> save());

    _delete.addClickListener(e -> delete());

    HorizontalLayout buttons = new HorizontalLayout(_save, _delete);
    add(_firstName, _lastName, _status, buttons);

    _binder.bindInstanceFields(this);

    setCustomer(null);
  }

  public void setCustomer(Customer customer) {
    _customer = customer;
    _binder.setBean(customer);

    boolean enabled = customer != null;
    _save.setEnabled(enabled);
    _delete.setEnabled(enabled);

    if (enabled) {
      _firstName.focus();
    }
  }

  private void delete() {
    _service.delete(_customer);
    _mainView.updateList(null);
    setCustomer(null);
  }

  private void save() {
    _service.save(_customer);
    _mainView.updateList(null);
    setCustomer(null);
  }
}
