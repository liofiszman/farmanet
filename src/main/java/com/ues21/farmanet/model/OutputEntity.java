package com.ues21.farmanet.model;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "output", schema = "farmanet")
public class OutputEntity {

    private Integer id;
    private String notes;
    private Timestamp updateTime;
    private LocationEntity locationByLocationId;
    private UserEntity userByRequesterId;
    private ReasonEntity reasonByReasonId;
    private Collection<OutputItemEntity> outputItemsById;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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
    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    @UpdateTimestamp
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutputEntity that = (OutputEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
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
    @JoinColumn(name = "reason_id", referencedColumnName = "id", nullable = false)
    public ReasonEntity getReasonByReasonId() {
        return reasonByReasonId;
    }

    public void setReasonByReasonId(ReasonEntity reasonByReasonId) {
        this.reasonByReasonId = reasonByReasonId;
    }

    @OneToMany(mappedBy = "outputByOutputId")
    public Collection<OutputItemEntity> getOutputItemsById() {
        return outputItemsById;
    }

    public void setOutputItemsById(Collection<OutputItemEntity> outputItemsById) {
        this.outputItemsById = outputItemsById;
    }
}
