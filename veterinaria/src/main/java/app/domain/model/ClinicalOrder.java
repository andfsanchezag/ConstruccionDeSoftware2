package app.domain.model;

import java.sql.Date;

public class ClinicalOrder {
	
	private long id;
	private Pet pet;
	private User owner;
	private User veterinarian;
	private String medicine;
	private String doce;
	private Date date;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public User getVeterinarian() {
		return veterinarian;
	}
	public void setVeterinarian(User veterinarian) {
		this.veterinarian = veterinarian;
	}
	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public String getDoce() {
		return doce;
	}
	public void setDoce(String doce) {
		this.doce = doce;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	

}
