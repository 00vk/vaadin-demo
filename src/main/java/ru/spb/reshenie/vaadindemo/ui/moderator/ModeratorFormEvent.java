package ru.spb.reshenie.vaadindemo.ui.moderator;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.notification.Notification;
import ru.spb.reshenie.vaadindemo.data.entity.Moderator;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */ // Events
public abstract class ModeratorFormEvent extends ComponentEvent<ModeratorForm> {

    private final Moderator moderator;

    protected ModeratorFormEvent(ModeratorForm source, Moderator moderator, String notificationLabel) {
        this(source, moderator);
        if (moderator != null) {
            String notificationText = String.join(" ", notificationLabel, moderator.getFirstName(), moderator.getLastName());
            Notification.show(notificationText, 800, Notification.Position.MIDDLE);
        }
    }

    public ModeratorFormEvent(ModeratorForm source, Moderator moderator) {
        super(source, false);
        this.moderator = moderator;
    }

    public Moderator getModerator() {
        return moderator;
    }

    public static class SaveEvent extends ModeratorFormEvent {

        protected SaveEvent(ModeratorForm source, Moderator moderator) {
            super(source, moderator, "Saved:");
        }
    }

    public static class DeleteEvent extends ModeratorFormEvent {

        DeleteEvent(ModeratorForm source, Moderator moderator) {
            super(source, moderator, "Deleted:");
        }
    }

    public static class CloseEvent extends ModeratorFormEvent {

        CloseEvent(ModeratorForm source) {
            super(source, null);
        }
    }

}
