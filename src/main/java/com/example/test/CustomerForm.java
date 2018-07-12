package com.example.test;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;

public class CustomerForm extends FormLayout
{
	private MainView _mainView;
	private CustomerService _service;

	@PropertyId("firstName")
	private TextField _firstName;

	@PropertyId("lastName")
	private TextField _lastName;

	@PropertyId("status")
	private ComboBox<CustomerStatus> _status;

	private Button _save;
	private Button _delete;

	private Binder<Customer> _binder;
	private Customer _customer;

	public CustomerForm(MainView mainView)
	{
		super();
		_service = CustomerService.getInstance();
		_mainView = mainView;

		_firstName = new TextField("First name");
		_lastName = new TextField("Last name");

		_status = new ComboBox<>("Status");
		_status.setItems(CustomerStatus.values());

		_save = new Button("Save");
		_save.getElement().setAttribute("theme", "primary");
		_save.addClickListener(e -> save());

		_delete = new Button("Delete");
		_delete.addClickListener(e -> delete());

		HorizontalLayout buttons = new HorizontalLayout(_save, _delete);
		add(_firstName, _lastName, _status, buttons);

		_binder = new Binder<>(Customer.class);
		_binder.bindInstanceFields(this);

		setCustomer(null);
	}

	public void setCustomer(Customer customer)
	{
		_customer = customer;
		_binder.setBean(customer);

		boolean enabled = customer != null;
		_save.setEnabled(enabled);
		_delete.setEnabled(enabled);

		if (enabled)
		{
			_firstName.focus();
		}
	}

	private void delete()
	{
		_service.delete(_customer);
		_mainView.updateList(null);
		setCustomer(null);
	}

	private void save()
	{
		_service.save(_customer);
		_mainView.updateList(null);
		setCustomer(null);
	}
}
