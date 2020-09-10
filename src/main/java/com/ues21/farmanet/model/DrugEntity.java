package com.ues21.farmanet.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "drug", schema = "farmanet", catalog = "")
public class DrugEntity {
    private Integer id;
    private String name;
    private Collection<ItemDrugEntity> itemDrugsById;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
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

        DrugEntity that = (DrugEntity) o;

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

    @OneToMany(mappedBy = "drugByDrugId")
    public Collection<ItemDrugEntity> getItemDrugsById() {
        return itemDrugsById;
    }

    public void setItemDrugsById(Collection<ItemDrugEntity> itemDrugsById) {
        this.itemDrugsById = itemDrugsById;
    }
}
