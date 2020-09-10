package com.ues21.farmanet.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "status", schema = "farmanet", catalog = "")
public class StatusEntity {
    private Integer id;
    private String name;
    private Collection<InputRequestEntity> inputRequestsById;
    private Collection<InputRequestItemEntity> inputRequestItemsById;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 80)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusEntity that = (StatusEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "statusByStatusId")
    public Collection<InputRequestEntity> getInputRequestsById() {
        return inputRequestsById;
    }

    public void setInputRequestsById(Collection<InputRequestEntity> inputRequestsById) {
        this.inputRequestsById = inputRequestsById;
    }

    @OneToMany(mappedBy = "statusByStatusId")
    public Collection<InputRequestItemEntity> getInputRequestItemsById() {
        return inputRequestItemsById;
    }

    public void setInputRequestItemsById(Collection<InputRequestItemEntity> inputRequestItemsById) {
        this.inputRequestItemsById = inputRequestItemsById;
    }
}
