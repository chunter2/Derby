package com.downbracket.gatekeeper.db.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "RaceData")
@Table(name = "race_data")
public class RaceData {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="unique_id", unique=true, length=36)
	private String uniqueId ;
	
	@JoinColumn(name = "gate")
	@ManyToOne(targetEntity = Gate.class, fetch = FetchType.EAGER)
//	@NotNull(message = "Gate not set")
	private Gate gate;

	private Date timeStamp;

	// added fetchtype here to avoid session closing when lazy loading.
	@OneToMany(mappedBy = "raceData", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LaneData> lanes = new ArrayList<>();

	public RaceData() {
		timeStamp = Calendar.getInstance().getTime() ;
	}
	
	public RaceData( Gate gate, String uniqueId )
	{
		this();
		this.gate = gate ;
		this.uniqueId = uniqueId ;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void addLane(LaneData lane) {
		lanes.add(lane);
		lane.setRaceData(this);
	}

	public List<LaneData> getLanes() {
		return lanes ;
	}

	public Gate getGate() {
		return gate;
	}

	public void setGate(Gate gate) {
		this.gate = gate;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Override
	public String toString() {
		return String.format("Race[id=%s, timestamp='%s', %s]", id, timeStamp.toString(), lanes.toString());
	}


}