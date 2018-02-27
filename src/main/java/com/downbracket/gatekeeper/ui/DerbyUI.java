package com.downbracket.gatekeeper.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@SpringUI()
@Theme("valo")
public class DerbyUI extends UI {

	Label label ;

	@Autowired
	public DerbyUI( ) {
	    this.label = new Label("Welcome to the Derby Application...");
	}

	@Override
	protected void init(VaadinRequest request) {
	    setContent(label);
	}
}