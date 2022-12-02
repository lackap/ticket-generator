package com.forum.ticketgenerator.view.ticket;

import com.forum.ticketgenerator.security.SecurityService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class HeaderView extends HorizontalLayout implements BeforeEnterObserver {

    @Autowired
    private SecurityService securityService;

    private Image imageLogo;

    private String title;

    private Div titreDiv;
    private Div imageDiv;

    public HeaderView() {
        imageLogo = new Image("img/metropole.png", "Orleans Metropole 2");
        title = "Forum Détecter les nouveaux talents";
    }

    @PostConstruct
    public void init() {
        setWidth("100%");
        titreDiv = new Div();
        titreDiv.setWidth("100%");
        titreDiv.getElement().getStyle().set("font-size", "40px");
        titreDiv.getElement().getStyle().set("font-weight", "bold");
        titreDiv.getElement().getStyle().set("text-align", "center");
        imageDiv = new Div();
        imageDiv.setWidth("150px");
        Button logout = new Button("Se déconnecter", e -> securityService.logout());
        logout.setWidth("150px");
        add(imageDiv);
        add(titreDiv);
        add(logout);
    }

    public void customizeHeader(Image imageLogo, String title) {
        this.imageLogo = imageLogo;
        this.title = title;
    }

    public void customizeHeader(String title) {
        this.title = title;
    }

    private void updateDisplay() {
        if (imageLogo != null) {
            imageDiv.removeAll();
            imageDiv.add(imageLogo);
        }

        titreDiv.removeAll();
        Span span = new Span();
        Text html = new Text(title);
        span.add(html);
        titreDiv.add(span);

    }

    @Override
    public void beforeEnter (BeforeEnterEvent beforeEnterEvent) {
        updateDisplay();
    }
}
