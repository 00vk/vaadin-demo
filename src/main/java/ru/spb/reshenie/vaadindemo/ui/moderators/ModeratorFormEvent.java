package ru.spb.reshenie.vaadindemo.ui.moderators;

import com.vaadin.flow.component.ComponentEvent;
import ru.spb.reshenie.vaadindemo.data.entity.Moderator;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */ // Events
public abstract class ModeratorFormEvent extends ComponentEvent<ModeratorForm> {

    private final Moderator moderator;

    protected ModeratorFormEvent(ModeratorForm source, Moderator moderator) {
        super(source, false);
        this.moderator = moderator;
    }

    public Moderator getModerator() {
        return moderator;
    }

    public static class SaveEvent extends ModeratorFormEvent {

        SaveEvent(ModeratorForm source, Moderator moderator) {
            super(source, moderator);
        }
    }

    public static class DeleteEvent extends ModeratorFormEvent {

        DeleteEvent(ModeratorForm source, Moderator moderator) {
            super(source, moderator);
        }
    }

    public static class CloseEvent extends ModeratorFormEvent {

        CloseEvent(ModeratorForm source) {
            super(source, null);
        }
    }

}
