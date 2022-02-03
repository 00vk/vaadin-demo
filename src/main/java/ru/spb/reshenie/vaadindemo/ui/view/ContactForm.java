package ru.spb.reshenie.vaadindemo.ui.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import ru.spb.reshenie.vaadindemo.data.entity.Company;
import ru.spb.reshenie.vaadindemo.data.entity.Status;

import java.util.List;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */
public class ContactForm extends FormLayout {

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField email = new EmailField("Email");
    private final ComboBox<Status> status = new ComboBox<>("Status");
    private final ComboBox<Company> company = new ComboBox<>("Company");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

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
        return new HorizontalLayout(save, delete, close);
    }
}
