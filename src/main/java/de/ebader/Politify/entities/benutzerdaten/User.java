package de.ebader.Politify.entities.benutzerdaten;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "anmeldedaten", schema = "benutzerdaten")
public class User {
	
	@jakarta.persistence.Id
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "benutzername", nullable = false)
	private String benutzername;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "passwort", nullable = false)
	private String passwort;
	
	@Column(name = "permission", nullable = true)
	private Long permission;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public Long getPermission() {
		return permission;
	}

	public void setPermission(Long permission) {
		this.permission = permission;
	}
	

}
