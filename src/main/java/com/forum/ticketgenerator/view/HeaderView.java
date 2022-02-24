package com.forum.ticketgenerator.view;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.atmosphere.config.service.Post;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HeaderView extends HorizontalLayout {

    private Text titre;

    public HeaderView() {}

    @PostConstruct
    public void init() {
        setWidth("100%");
        Div titre = new Div();
        titre.setWidth("100%");
        titre.getElement().getStyle().set("font-size", "40px");
        titre.getElement().getStyle().set("font-weight", "bold");
        titre.getElement().getStyle().set("text-align", "center");
        Span span = new Span();
        Text html = new Text("Forum Détecter les nouveau talents");
        span.add(html);
        titre.add(span);
        add(titre);

    }
}
