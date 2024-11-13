package de.ebader.Politify.controller.spring;

public class LoginRequest {
	private String benutzername;
    private String passwort;
    
	public String getBenutzername() {
		return benutzername;
	}
	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
    
    

}
