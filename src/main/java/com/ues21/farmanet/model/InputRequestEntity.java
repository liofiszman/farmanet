package com.ues21.farmanet.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "input_request", schema = "farmanet", catalog = "")
public class InputRequestEntity {
    private Integer id;
    private String notes;
    private Timestamp creationDate;
    private Timestamp updateTime;
    private LocationEntity locationByLocationId;
    private UserEntity userByRequesterId;
    private UserEntity userByApproverId;
    private StatusEntity statusByStatusId;
    private Collection<InputRequestItemEntity> inputRequestItemsById;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "notes", nullable = true, length = 255)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "creation_date", nullable = false)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "update_time", nullable = false)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InputRequestEntity that = (InputRequestEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    public LocationEntity getLocationByLocationId() {
        return locationByLocationId;
    }

    public void setLocationByLocationId(LocationEntity locationByLocationId) {
        this.locationByLocationId = locationByLocationId;
    }

    @ManyToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserByRequesterId() {
        return userByRequesterId;
    }

    public void setUserByRequesterId(UserEntity userByRequesterId) {
        this.userByRequesterId = userByRequesterId;
    }

    @ManyToOne
    @JoinColumn(name = "approver_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserByApproverId() {
        return userByApproverId;
    }

    public void setUserByApproverId(UserEntity userByApproverId) {
        this.userByApproverId = userByApproverId;
    }

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    public StatusEntity getStatusByStatusId() {
        return statusByStatusId;
    }

    public void setStatusByStatusId(StatusEntity statusByStatusId) {
        this.statusByStatusId = statusByStatusId;
    }

    @OneToMany(mappedBy = "inputRequestByInputRequestId",  fetch = FetchType.EAGER)
    public Collection<InputRequestItemEntity> getInputRequestItemsById() {
        return inputRequestItemsById;
    }

    public void setInputRequestItemsById(Collection<InputRequestItemEntity> inputRequestItemsById) {
        this.inputRequestItemsById = inputRequestItemsById;
    }
}
