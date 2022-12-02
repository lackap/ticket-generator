package com.forum.ticketgenerator.view.ticket;

import com.forum.ticketgenerator.security.SecurityService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@UIScope
public class HeaderView extends HorizontalLayout {

    @Autowired
    private SecurityService securityService;

    public HeaderView() {}

    @PostConstruct
    public void init() {
        setWidth("100%");
        Image image2 = new Image("img/metropole.png", "Orleans Metropole 2");
        image2.setWidth("150px");
        add(image2);
        Div titre = new Div();
        titre.setWidth("100%");
        titre.getElement().getStyle().set("font-size", "40px");
        titre.getElement().getStyle().set("font-weight", "bold");
        titre.getElement().getStyle().set("text-align", "center");
        Span span = new Span();
        Text html = new Text("Forum Détecter les nouveaux talents");
        span.add(html);
        titre.add(span);
        add(titre);
        Button logout = new Button("Log out", e -> securityService.logout());
        add(logout);

    }
}
