package com.downbracket.gatekeeper.db.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "LaneData")
@Table(name = "lane_data")
public class LaneData implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	private Long laneId ;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "race_unique_id", referencedColumnName = "unique_id" )
	private RaceData raceData; 

	private Long time;
	
	protected LaneData()
	{		
	}

	public LaneData( Long laneId, Long time ) {
		this.setLaneId(laneId) ;
		this.time = time ;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RaceData getRaceData() {
		return raceData;
	}

	public void setRaceData(RaceData race) {
		this.raceData = race;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LaneData )) return false;
        return id != null && id.equals(((LaneData) o).id);
    }
    @Override
    public int hashCode() {
        return id.intValue() ;
    }

	@Override
	public String toString() {
		return String.format("Lane %d[time=%d]", laneId, time);
	}

	public Long getLaneId() {
		return laneId;
	}

	public void setLaneId(Long laneId) {
		this.laneId = laneId;
	}

}