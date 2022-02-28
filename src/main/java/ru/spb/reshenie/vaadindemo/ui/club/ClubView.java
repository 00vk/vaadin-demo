package ru.spb.reshenie.vaadindemo.ui.club;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.spb.reshenie.vaadindemo.ui.MainLayout;

/**
 * Created by vkondratiev on 25.02.2022
 * Description:
 */

@Route(value = "clubs", layout = MainLayout.class)
@PageTitle(value = "Speaking Club | Clubs")
public class ClubView extends VerticalLayout {

    public ClubView() {
    }
}
