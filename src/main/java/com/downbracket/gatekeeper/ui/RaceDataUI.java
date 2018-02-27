package com.downbracket.gatekeeper.ui;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.downbracket.gatekeeper.db.entity.RaceData;
import com.downbracket.gatekeeper.db.repository.RaceDataRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;

@SpringUI(path="/races")
@Theme("valo")
public class RaceDataUI extends UI {
	
	RaceDataRepository repo;
	Grid<RaceData> grid;

	@Autowired
	public RaceDataUI(RaceDataRepository repo) {
	    this.repo = repo;
	    this.grid = new Grid<>(RaceData.class);
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