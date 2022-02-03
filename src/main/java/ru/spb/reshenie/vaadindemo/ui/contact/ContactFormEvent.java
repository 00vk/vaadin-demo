package ru.spb.reshenie.vaadindemo.ui.contact;

import com.vaadin.flow.component.ComponentEvent;
import ru.spb.reshenie.vaadindemo.data.entity.Contact;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */ // Events
public abstract class ContactFormEvent extends ComponentEvent<ContactForm> {

    private final Contact contact;

    protected ContactFormEvent(ContactForm source, Contact contact) {
        super(source, false);
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public static class SaveEvent extends ContactFormEvent {

        SaveEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {

        DeleteEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends ContactFormEvent {

        CloseEvent(ContactForm source) {
            super(source, null);
        }
    }

}
