package ru.spb.reshenie.vaadindemo.ui.contact;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import ru.spb.reshenie.vaadindemo.data.entity.Company;
import ru.spb.reshenie.vaadindemo.data.entity.Contact;
import ru.spb.reshenie.vaadindemo.data.entity.Status;

import java.util.List;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */
public class ContactForm extends FormLayout {

    private final Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField email = new EmailField("Email");
    private final ComboBox<Status> status = new ComboBox<>("Status");
    private final ComboBox<Company> company = new ComboBox<>("Company");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");
    private Contact contact;

    public ContactForm(List<Company> companies, List<Status> statuses) {
        addClassName("contact-form");

        configure(companies, statuses);
        addComponents();
    }

    private void configure(List<Company> companies, List<Status> statuses) {
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);

        status.setItems(statuses);
        status.setItemLabelGenerator(Status::getName);

        binder.bindInstanceFields(this);
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        binder.readBean(contact);

    }

    private void addComponents() {
        add(firstName, lastName, email, company, status);
        add(createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);
        delete.addClickShortcut(Key.DELETE);

        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new ContactFormEvent.DeleteEvent(this, contact)));
        close.addClickListener(e -> fireEvent(new ContactFormEvent.CloseEvent(this)));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(contact);
            fireEvent(new ContactFormEvent.SaveEvent(this, contact));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
