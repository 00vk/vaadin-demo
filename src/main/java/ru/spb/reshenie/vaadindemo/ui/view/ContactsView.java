package ru.spb.reshenie.vaadindemo.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.spb.reshenie.vaadindemo.data.entity.Contact;

import java.util.Collections;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */

@Route(value = "contacts", layout = MainLayout.class)
@PageTitle(value = "Vaadin Demo | Contacts")
public class ContactsView extends VerticalLayout {

    private final Grid<Contact> grid = new Grid<>(Contact.class);
    private ContactForm form;
    private final TextField tfFilter = new TextField();
//    private final CrmService service;

    public ContactsView() {
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        addComponents();
    }

    private void addComponents() {
        add(createToolbar());
        add(createContentLayout());
    }

    private Component createToolbar() {
        tfFilter.setPlaceholder("Найти...");
        tfFilter.setClearButtonVisible(true);
        tfFilter.setPrefixComponent(new Icon(VaadinIcon.SEARCH));

        Button addContact = new Button(new Icon(VaadinIcon.PLUS));
        HorizontalLayout toolbar = new HorizontalLayout(tfFilter, addContact);
        toolbar.setClassName("contacts-toolbar");
        return toolbar;
    }

    private Component createContentLayout() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setClassName("contacts-content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email", "status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");

        grid.setItems();
    }

    private void configureForm() {
        form = new ContactForm(Collections.emptyList(), Collections.emptyList());
        form.setWidth("25em");
    }
}

