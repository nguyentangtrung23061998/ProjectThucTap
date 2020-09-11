package com.eleaning.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eleaning.bean.AuthProviderBean;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class UserEntity {
	@Id
	private Long id;

	@NotBlank
	@Size(min = 3, max = 50)
	private String username;

	@NotBlank
	@Size(min = 3, max = 50)
	private String password;

	@NotBlank
	@Size(min = 3, max = 50)
	private String email;

	@Column(nullable = false)
	private boolean emailVerified;

	private String image;

	@Size(min = 3, max = 50)
	private String fullname;

	private String token;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProviderBean provider;

	@Column(name="providerId")
	private String providerId;

	private Timestamp createddate;

	private Timestamp modifieddate;

	private String createdby;

	private String modifiedby;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "roleid"))
	@JsonIgnore
	private Set<RoleEntity> role = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<CourseEntity> course;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "student_course_enrolement", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "courseid"))
	@JsonIgnore
	private Set<CourseEntity> courser_enroll = new HashSet<>();

	public UserEntity() {
	}

	public UserEntity(Long id, @NotBlank @Size(min = 3, max = 50) String username,
			@NotBlank @Size(min = 3, max = 50) String password, @NotBlank @Size(min = 3, max = 50) String email,
			boolean emailVerified, String image, @Size(min = 3, max = 50) String fullname, String token,
			@NotNull AuthProviderBean provider, String providerId, Timestamp createddate, Timestamp modifieddate,
			String createdby, String modifiedby, Set<RoleEntity> role, List<CourseEntity> course,
			Set<CourseEntity> courser_enroll) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.emailVerified = emailVerified;
		this.image = image;
		this.fullname = fullname;
		this.token = token;
		this.provider = provider;
		this.providerId = providerId;
		this.createddate = createddate;
		this.modifieddate = modifieddate;
		this.createdby = createdby;
		this.modifiedby = modifiedby;
		this.role = role;
		this.course = course;
		this.courser_enroll = courser_enroll;
	}
	
	public UserEntity(Long id, @NotBlank @Size(min = 3, max = 50) String username,
			@NotBlank @Size(min = 3, max = 50) String password, @NotBlank @Size(min = 3, max = 50) String email,
			boolean emailVerified, String image, @Size(min = 3, max = 50) String fullname, String token,
			@NotNull AuthProviderBean provider, String providerId, Timestamp createddate, Timestamp modifieddate,
			String createdby, String modifiedby) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.emailVerified = emailVerified;
		this.image = image;
		this.fullname = fullname;
		this.token = token;
		this.provider = provider;
		this.providerId = providerId;
		this.createddate = createddate;
		this.modifieddate = modifieddate;
		this.createdby = createdby;
		this.modifiedby = modifiedby;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Set<RoleEntity> role) {
		this.role = role;
	}

	/**
	 * @return the createddate
	 */
	public Timestamp getCreateddate() {
		return createddate;
	}

	/**
	 * @param createddate the createddate to set
	 */
	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	/**
	 * @return the modifieddate
	 */
	public Timestamp getModifieddate() {
		return modifieddate;
	}

	/**
	 * @param modifieddate the modifieddate to set
	 */
	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}

	/**
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the modifiedby
	 */
	public String getModifiedby() {
		return modifiedby;
	}

	/**
	 * @param modifiedby the modifiedby to set
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	/**
	 * @return the roles
	 */
	public Set<RoleEntity> getRole() {
		return role;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<RoleEntity> role) {
		this.role = role;
	}

	/**
	 * @return the course
	 */
	public List<CourseEntity> getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(List<CourseEntity> course) {
		this.course = course;
	}

	/**
	 * @return the courser_enroll
	 */
	public Set<CourseEntity> getCourser_enroll() {
		return courser_enroll;
	}

	/**
	 * @param courser_enroll the courser_enroll to set
	 */
	public void setCourser_enroll(Set<CourseEntity> courser_enroll) {
		this.courser_enroll = courser_enroll;
	}

	/**
	 * @return the emailVerified
	 */
	public boolean isEmailVerified() {
		return emailVerified;
	}

	/**
	 * @param emailVerified the emailVerified to set
	 */
	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	/**
	 * @return the provider
	 */
	public AuthProviderBean getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(AuthProviderBean provider) {
		this.provider = provider;
	}

	/**
	 * @return the providerId
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId the providerId to set
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	
}