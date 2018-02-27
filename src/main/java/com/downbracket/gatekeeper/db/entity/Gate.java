package com.downbracket.gatekeeper.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Gate")
@Table(name = "gate")
public class Gate {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column( length=36 )
	private String uniqueId;
	
	private String name ;

	public Gate()
	{
		
	}
	
	public Gate( String uniqueId, String name )
	{
		this.uniqueId = uniqueId ;
		this.name = name ;		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gate )) return false;
        return id != null && id.equals(((Gate) o).id);
    }
    @Override
    public int hashCode() {
        return id.intValue() ;
    }
    
    @Override
    public String toString()
    {
		return String.format("Gate %d[name=%s]", id, name);
    }

}