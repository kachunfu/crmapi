package com.fdmgroup.crmapi.models;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class User {
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
	@SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
	private Long userId;

    @NotBlank(message = "Username is required")
	@Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
	private String username;

	private String password;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Contact contactDetails;
	
	public User() {}
	
	public User(String username, String password, Contact contactDetails) {
		super();
		this.username = username;
		this.password = password;
		this.contactDetails = contactDetails;
	}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Contact getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(Contact contactDetails) {
        this.contactDetails = contactDetails;
    }
}
