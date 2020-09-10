package com.ues21.farmanet.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "farmanet", catalog = "")
public class UserEntity {
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Byte active;
    private Integer roleId;
    private Collection<InputRequestEntity> inputRequestsById;
    private Collection<InputRequestEntity> inputRequestsById_0;
    private Collection<OutputEntity> outputsById;
    private LocationEntity locationByDefaultLocationId;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 255)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 255)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "active", nullable = false)
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    @Basic
    @Column(name = "role_id", nullable = true)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (active != null ? !active.equals(that.active) : that.active != null) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByRequesterId")
    public Collection<InputRequestEntity> getInputRequestsById() {
        return inputRequestsById;
    }

    public void setInputRequestsById(Collection<InputRequestEntity> inputRequestsById) {
        this.inputRequestsById = inputRequestsById;
    }

    @OneToMany(mappedBy = "userByApproverId")
    public Collection<InputRequestEntity> getInputRequestsById_0() {
        return inputRequestsById_0;
    }

    public void setInputRequestsById_0(Collection<InputRequestEntity> inputRequestsById_0) {
        this.inputRequestsById_0 = inputRequestsById_0;
    }

    @OneToMany(mappedBy = "userByRequesterId")
    public Collection<OutputEntity> getOutputsById() {
        return outputsById;
    }

    public void setOutputsById(Collection<OutputEntity> outputsById) {
        this.outputsById = outputsById;
    }

    @ManyToOne
    @JoinColumn(name = "default_location_id", referencedColumnName = "id")
    public LocationEntity getLocationByDefaultLocationId() {
        return locationByDefaultLocationId;
    }

    public void setLocationByDefaultLocationId(LocationEntity locationByDefaultLocationId) {
        this.locationByDefaultLocationId = locationByDefaultLocationId;
    }
}
