package de.exentra.vaadin.tutorial;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_TIME;

/**
 * The main view contains a button and a template element.
 */
@HtmlImport("styles/shared-styles.html")
@Route("")
public class MainView extends VerticalLayout {
  private CustomerService _service     = CustomerService.getInstance();
  private Grid<Customer>  _grid        = new Grid<>();
  private TextField       _filterText  = new TextField("", "Filter by name ...");
  private Button          _clearFilter = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
  private Button          _addCustomer = new Button("Add new customer");
  private CustomerForm    _form        = new CustomerForm(this);

  public MainView() {
    _filterText.setValueChangeMode(ValueChangeMode.EAGER);
    _filterText.addValueChangeListener(this::updateList);

    _clearFilter.addClickListener(e -> _filterText.clear());

    _addCustomer.addClickListener(e -> {
      _grid.asSingleSelect().clear();
      _form.setCustomer(new Customer());
    });


    _grid.addColumn(Customer::getFirstName).setHeader("First name").setSortProperty("firstName");
    _grid.addColumn(Customer::getLastName).setHeader("Last name").setSortProperty("lastName");
    _grid.addColumn(Customer::getStatus).setHeader("Status");

    _grid.setSelectionMode(SelectionMode.SINGLE);
    _grid.asSingleSelect().addValueChangeListener(event -> _form.setCustomer(event.getValue()));
//    _grid.setSelectionMode(SelectionMode.MULTI);
//    _grid.asMultiSelect().addSelectionListener(event -> out.println("multi selection " + event.getValue()));
    _grid.setItems(_service.findAll());

    HorizontalLayout main = new HorizontalLayout(_grid, _form);
    main.setAlignItems(Alignment.START);
    main.setSizeFull();

    add(new HorizontalLayout(_filterText, _clearFilter, _addCustomer),
                             main
    );

    setHeight("100vh");

    add(new Span("timestamp - ui started .." + now().format(ISO_TIME)));
    CustomerService.getInstance().ensureTestData();
    add(new Span("timestamp - demo data generated .." + now().format(ISO_TIME)));

  }

  void updateList(ComponentValueChangeEvent<TextField, String> event) {
    final String searchTerm = (event != null)
                        ? event.getValue()
                        : "";

    _grid.setItems(_service.findAll(searchTerm));
  }
}
