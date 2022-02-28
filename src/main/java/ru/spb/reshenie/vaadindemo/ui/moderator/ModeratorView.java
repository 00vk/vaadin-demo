package ru.spb.reshenie.vaadindemo.ui.moderator;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.spb.reshenie.vaadindemo.data.entity.Moderator;
import ru.spb.reshenie.vaadindemo.data.service.ClubService;
import ru.spb.reshenie.vaadindemo.data.service.ModeratorService;
import ru.spb.reshenie.vaadindemo.ui.MainLayout;

/**
 * Created by vkondratiev on 03.02.2022
 * Description:
 */

@Route(value = "moderators", layout = MainLayout.class)
@PageTitle(value = "Speaking Club | Moderators")
public class ModeratorView extends VerticalLayout {

    private final Grid<Moderator> grid = new Grid<>(Moderator.class);
    private ModeratorForm form;
    private final TextField tfFilter = new TextField();
    private final ModeratorService moderatorService;
    private final ClubService clubService;

    public ModeratorView(ModeratorService moderatorService,
                         ClubService clubService) {
        this.moderatorService = moderatorService;
        this.clubService = clubService;

        addClassName("moderator-view");
        setSizeFull();

        configureGrid();
        configureForm();

        addComponents();
        closeEditor();
        updateList();
    }

    private void addComponents() {
        add(createToolbar());
        add(createContentLayout());
    }

    private Component createToolbar() {
        configureFilterTextField();

        Button addModerator = new Button(new Icon(VaadinIcon.PLUS));
        addModerator.addClickListener(e -> addModerator());

        HorizontalLayout toolbar = new HorizontalLayout(tfFilter, addModerator);
        toolbar.setClassName("moderator-toolbar");
        return toolbar;
    }

    private void configureFilterTextField() {
        tfFilter.setPlaceholder("search...");
        tfFilter.setClearButtonVisible(true);
        tfFilter.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        tfFilter.setValueChangeMode(ValueChangeMode.LAZY);
        tfFilter.addValueChangeListener(e -> updateList());
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
        ValueProvider<Moderator, String> clubByModer = moderator -> moderator.getClub().getName();
        grid.addColumn(clubByModer).setComparator(clubByModer).setHeader("Club");
        grid.setPageSize(10);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.asSingleSelect().addValueChangeListener(e -> openEditor(e.getValue()));
    }

    private void configureForm() {
        form = new ModeratorForm(clubService.findAll());
        form.setWidth("25em");

        form.addListener(ModeratorFormEvent.SaveEvent.class, event -> {
            moderatorService.save(event.getModerator());
            updateList();
            closeEditor();
        });
        form.addListener(ModeratorFormEvent.CloseEvent.class, event -> closeEditor());
        form.addListener(ModeratorFormEvent.DeleteEvent.class, event -> {
            moderatorService.delete(event.getModerator());
            updateList();
            closeEditor();
        });
    }

    private void openEditor(Moderator moderator) {
        if (moderator == null) {
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
        grid.setItems(moderatorService.findAll(tfFilter.getValue()));
    }
}

