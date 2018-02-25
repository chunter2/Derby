package com.downbracket.gatekeeper;

import org.springframework.beans.factory.annotation.Autowired;

import com.downbracket.gatekeeper.db.entity.RaceData;
import com.downbracket.gatekeeper.db.repository.RaceRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

	
	RaceRepository repo;
	Grid<RaceData> grid;

	@Autowired
	public VaadinUI(RaceRepository repo) {
	    this.repo = repo;
	    this.grid = new Grid<>(RaceData.class);
	}

	@Override
	protected void init(VaadinRequest request) {
	    setContent(grid);
	    listRaces();
	}

	private void listRaces() {
	    grid.setItems(repo.findAll());
	}
}