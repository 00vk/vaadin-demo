package ru.spb.reshenie.vaadindemo.ui.contact;

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
import ru.spb.reshenie.vaadindemo.data.service.CrmService;
import ru.spb.reshenie.vaadindemo.ui.MainLayout;

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
    private final CrmService service;

    public ContactsView(CrmService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        addComponents();
        closeEditor();
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("contacts-editing");
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

        grid.asSingleSelect().addValueChangeListener(e -> openEditor(e.getValue()));
        updateList();
    }

    private void configureForm() {
        form = new ContactForm(service.findAllCompanies(), service.findAllStatuses());
        form.setWidth("25em");
    }

    private void openEditor(Contact contact) {
        if (contact == null) {
            closeEditor();
            return;
        }
        form.setContact(contact);
        form.setVisible(true);
        addClassName("contacts-editing");
    }

    private void updateList() {
        grid.setItems(service.findAllContacts(tfFilter.getValue()));
    }
}

