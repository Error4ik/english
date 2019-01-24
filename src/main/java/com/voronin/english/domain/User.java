package com.voronin.english.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import javax.persistence.Column;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * User.
 *
 * @author Alexey Voronin.
 * @since 07.09.2018.
 */
@Entity(name = "users")
public class User {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * User email.
     */
    private String email;

    /**
     * User password.
     */
    private String password;

    /**
     * User roles.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    /**
     * The date the user was created.
     */
    @Column(name = "create_date")
    private Timestamp createDate;

    /**
     * Date of last visit.
     */
    @Column(name = "last_visit")
    private Timestamp lastVisit;

    /**
     * Empty constructor.
     */
    public User() {
        this.createDate = Timestamp.valueOf(LocalDateTime.now());
        this.lastVisit = Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * Constructor.
     *
     * @param email    user email.
     * @param password user password.
     * @param roles    user roles.
     */
    public User(final String email, final String password, final Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.createDate = Timestamp.valueOf(LocalDateTime.now());
        this.lastVisit = Timestamp.valueOf(LocalDateTime.now());
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(final Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(final Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(getId(), user.getId())
                && Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }

    @Override
    public String toString() {
        return String.format("User {id=%s email=%s}", getId(), getEmail());
    }
}
