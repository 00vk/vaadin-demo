package ru.spb.reshenie.vaadindemo.ui.moderator;

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
import ru.spb.reshenie.vaadindemo.data.entity.Club;
import ru.spb.reshenie.vaadindemo.data.entity.Moderator;

import java.util.Collection;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */
public class ModeratorForm extends FormLayout {

    private final Binder<Moderator> binder = new BeanValidationBinder<>(Moderator.class);

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField email = new EmailField("Email");
    private final ComboBox<Club> clubs = new ComboBox<>("Club");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");
    private Moderator moderator;

    public ModeratorForm(Collection<Club> clubs) {
        addClassName("moderator-form");
        configure(clubs);

        addComponents();
    }

    private void configure(Collection<Club> clubs) {
        this.clubs.setItems(clubs);
        this.clubs.setItemLabelGenerator(Club::getName);
        this.clubs.setAllowCustomValue(false);

        binder.bindInstanceFields(this);
    }

    public void setModerator(Moderator moderator) {
        this.moderator = moderator;
        binder.readBean(moderator);
    }

    private void addComponents() {
        add(firstName, lastName, email, clubs);
        add(createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new ModeratorFormEvent.DeleteEvent(this, moderator)));
        close.addClickListener(e -> fireEvent(new ModeratorFormEvent.CloseEvent(this)));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(moderator);
            fireEvent(new ModeratorFormEvent.SaveEvent(this, moderator));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
