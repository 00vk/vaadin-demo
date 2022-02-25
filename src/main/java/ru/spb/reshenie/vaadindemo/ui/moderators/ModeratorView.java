package ru.spb.reshenie.vaadindemo.ui.moderators;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.spb.reshenie.vaadindemo.data.entity.Moderator;
import ru.spb.reshenie.vaadindemo.data.service.CrmService;
import ru.spb.reshenie.vaadindemo.ui.MainLayout;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */

@Route(value = "moderators", layout = MainLayout.class)
@PageTitle(value = "Vaadin Demo | Moderators")
public class ModeratorView extends VerticalLayout {

    private final Grid<Moderator> grid = new Grid<>(Moderator.class);
    private ModeratorForm form;
    private final TextField tfFilter = new TextField();
    private final CrmService service;

    public ModeratorView(CrmService service) {
        this.service = service;
        addClassName("moderator-view");
        setSizeFull();

        configureGrid();
        configureForm();

        addComponents();
        closeEditor();
    }

    private void addComponents() {
        add(createToolbar());
        add(createContentLayout());
    }

    private Component createToolbar() {
        tfFilter.setPlaceholder("search...");
        tfFilter.setClearButtonVisible(true);
        tfFilter.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        tfFilter.setValueChangeMode(ValueChangeMode.LAZY);

        Button addModerator = new Button(new Icon(VaadinIcon.PLUS));
        addModerator.addClickListener(e -> addModerator());

        HorizontalLayout toolbar = new HorizontalLayout(tfFilter, addModerator);
        toolbar.setClassName("moderator-toolbar");
        return toolbar;
    }

    private void addModerator() {
        grid.asSingleSelect().clear();
        openEditor(new Moderator());
    }

    private Component createContentLayout() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setClassName("moderator-content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.addClassName("moderator-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");
        grid.addColumn(moderator -> moderator.getClub().getName()).setHeader("Club");

        grid.asSingleSelect().addValueChangeListener(e -> openEditor(e.getValue()));
        updateList();
    }

    private void configureForm() {
        form = new ModeratorForm(service.findAllCompanies());
        form.setWidth("25em");

        form.addListener(ModeratorFormEvent.SaveEvent.class, event1 -> {
            service.saveModerator(event1.getModerator());
            updateList();
            closeEditor();
        });
        form.addListener(ModeratorFormEvent.CloseEvent.class, event -> closeEditor());
        form.addListener(ModeratorFormEvent.DeleteEvent.class, event -> {
            service.deleteModerator(event.getModerator());
            updateList();
            closeEditor();
        });
    }

    private void openEditor(Moderator moderator) {
        if (moderator == null) {
            closeEditor();
            return;
        }
        form.setModerator(moderator);
        form.setVisible(true);
        addClassName("moderator-editing");
    }

    private void closeEditor() {
        form.setModerator(null);
        form.setVisible(false);
        removeClassName("moderator-editing");
    }

    private void updateList() {
        grid.setItems(service.findAllModerators(tfFilter.getValue()));
    }
}

