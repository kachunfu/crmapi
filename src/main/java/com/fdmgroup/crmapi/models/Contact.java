package com.fdmgroup.crmapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_gen")
	@SequenceGenerator(name = "contact_gen", sequenceName = "contact_seq", allocationSize = 1)
	private int contactId;

    @NotBlank(message = "First Name is required")
	@Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
	private String firstName;

    @NotBlank(message = "Last Name is required")
	@Size(min = 2, max = 100, message = "Last Name must be between 2 and 100 characters")
	private String lastName;

    @NotBlank(message = "Email is required")
	@Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters")
	private String email;

    @NotBlank(message = "Phone is required")
	@Size(min = 5, max = 15, message = "Phone must be between 5 and 15 characters")
	private String phone;

    public Contact() {}
    
    @Builder
    public Contact(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
	public String toString() {
		return "Contact Details: [full name=" + getFirstName() + " " + getLastName() + ", email=" + getEmail() + ", phone= " + getPhone() + "]";
	}
}
