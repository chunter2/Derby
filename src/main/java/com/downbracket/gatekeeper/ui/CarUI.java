package com.downbracket.gatekeeper.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.downbracket.gatekeeper.db.entity.Car;
import com.downbracket.gatekeeper.db.repository.CarRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;

@SpringUI(path="/cars")
@Theme("valo")
public class CarUI extends UI {
	
	CarRepository repo;
	Grid<Car> grid;

	@Autowired
	public CarUI(CarRepository repo) {
	    this.repo = repo;
	    this.grid = new Grid<>(Car.class);
	    this.grid.setWidth("100%");
	}

	@Override
	protected void init(VaadinRequest request) {
	    setContent(grid);
	    listItems();
	}

	private void listItems() {
	    grid.setItems(repo.findAll());
//	    grid.setItems(repo.findByGateUniqueId( RaceCreatorUI.GATE_UUID));
	}
}