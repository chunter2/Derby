package com.downbracket.gatekeeper.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.downbracket.gatekeeper.db.entity.Gate;
import com.downbracket.gatekeeper.db.repository.GateRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;

@SpringUI(path="/gates")
@Theme("valo")
public class GateDataUI extends UI {
	
	GateRepository repo;
	Grid<Gate> grid;

	@Autowired
	public GateDataUI(GateRepository repo) {
	    this.repo = repo;
	    this.grid = new Grid<>(Gate.class);
	    this.grid.setWidth("100%");
	}

	@Override
	protected void init(VaadinRequest request) {
	    setContent(grid);
	    listRaces();
	}

	private void listRaces() {
	    grid.setItems(repo.findAll());
//	    grid.setItems(repo.findByGateUniqueId( RaceCreatorUI.GATE_UUID));
	}
}