package de.ebader.Politify.controller.spring;

public class JwtResponse {
    private String token;
    private Long id;
    private String benutzername;
    private String email;
    private Long permission;

    public JwtResponse(String token, Long id, String benutzername, String email, Long permission) {
        this.token = token;
        this.id = id;
        this.benutzername = benutzername;
        this.email = email;
        this.permission = permission;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

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

	public Long getPermission() {
		return permission;
	}

	public void setPermission(Long permission) {
		this.permission = permission;
	}

    
}

