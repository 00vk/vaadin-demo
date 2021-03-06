package ru.spb.reshenie.vaadindemo.ui;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import ru.spb.reshenie.vaadindemo.ui.moderator.ModeratorView;

/**
 * The main view is a top-level placeholder for other views.
 */
@PageTitle("Speaking Club | Main")
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames("m-0", "text-l");

        Header header = new Header(toggle, viewTitle);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center",
                             "w-full"
        );
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Speaking Club");
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section();
        section.add(appName, createNavigation(), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);

        for (MenuItem menuItem : createMenuItems()) {
            list.add(menuItem);

        }
        return nav;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");
        return footer;
    }

    private MenuItem[] createMenuItems() {
        MenuItem helloWorld = new MenuItem("Hello World", "la la-globe", HelloWorldView.class);
        MenuItem moderators = new MenuItem("Moderators", "la la-list-ul", ModeratorView.class);
        MenuItem about = new MenuItem("About", "la la-file", AboutView.class);
        return new MenuItem[] {helloWorld, moderators, about};
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        String[] split = getCurrentPageTitle().split("\\|");
        String titleText;
        if (split.length == 1) {
            titleText = split[0];
        } else {
            titleText = split[1].strip();
        }
        viewTitle.setText(titleText);
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItem extends ListItem {

        private final Class<? extends Component> view;

        public MenuItem(String menuTitle, String iconClass, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames("font-medium", "text-s");

            link.add(new LineAwesomeIcon(iconClass), text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

        /**
         * Simple wrapper to create icons using LineAwesome iconset. See
         * https://icons8.com/line-awesome
         */
        @NpmPackage(value = "line-awesome", version = "1.3.0")
        public static class LineAwesomeIcon extends Span {

            public LineAwesomeIcon(String lineawesomeClassnames) {
                // Use Lumo classnames for suitable font size and margin
                addClassNames("me-s", "text-l");
                if (!lineawesomeClassnames.isEmpty()) {
                    addClassNames(lineawesomeClassnames);
                }
            }
        }

    }
}
