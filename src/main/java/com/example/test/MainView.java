package com.example.test;

import java.util.List;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a template element.
 */
@HtmlImport("styles/shared-styles.html")
@Route("")
public class MainView extends VerticalLayout
{
	private CustomerService _service;
	private Grid<Customer> _grid;
	private TextField _filterText;
	private CustomerForm _form;

	public MainView()
	{
		super();

		_service = CustomerService.getInstance();
		_form = new CustomerForm(this);

		_filterText = new TextField();
		_filterText.setPlaceholder("Filter by name ...");
		_filterText.setValueChangeMode(ValueChangeMode.EAGER);
		_filterText.addValueChangeListener(e -> {
			updateList(e);
		});

		Button clearFilter = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
		clearFilter.addClickListener(e -> _filterText.clear());

		Button addCustomer = new Button("Add new customer");
		addCustomer.addClickListener(e -> {
			_grid.asSingleSelect().clear();
			_form.setCustomer(new Customer());
		});

		HorizontalLayout filtering = new HorizontalLayout(_filterText, clearFilter, addCustomer);

		_grid = new Grid<>();
		_grid.addColumn(Customer::getFirstName).setHeader("First name").setSortProperty("firstName");
		_grid.addColumn(Customer::getLastName).setHeader("Last name").setSortProperty("lastName");
		_grid.addColumn(Customer::getStatus).setHeader("Status");
		_grid.asSingleSelect().addValueChangeListener(event -> {
			_form.setCustomer(event.getValue());
		});
		_grid.setSelectionMode(SelectionMode.MULTI);
		_grid.asMultiSelect().addSelectionListener(event -> {
			System.out.println("multi selection " + event.getValue());
		});

		HorizontalLayout main = new HorizontalLayout(_grid, _form);
		main.setAlignItems(Alignment.START);
		main.setSizeFull();
		add(filtering, main);

		setHeight("100vh");
		updateList(null);
	}

	void updateList(ComponentValueChangeEvent<TextField, String> event)
	{
		String searchTerm = "";
		if (event != null)
		{
			searchTerm = event.getValue();
		}

		List<Customer> customers = _service.findAll(searchTerm);
		_grid.setItems(customers);
	}
}
