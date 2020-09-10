package com.ues21.farmanet.model;

import javax.persistence.*;

@Entity
@Table(name = "item_drug", schema = "farmanet", catalog = "")
public class ItemDrugEntity {
    private Integer id;
    private DrugEntity drugByDrugId;
    private ItemEntity itemByItemId;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemDrugEntity that = (ItemDrugEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @ManyToOne
    @JoinColumn(name = "drug_id", referencedColumnName = "id", nullable = false)
    public DrugEntity getDrugByDrugId() {
        return drugByDrugId;
    }

    public void setDrugByDrugId(DrugEntity drugByDrugId) {
        this.drugByDrugId = drugByDrugId;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public ItemEntity getItemByItemId() {
        return itemByItemId;
    }

    public void setItemByItemId(ItemEntity itemByItemId) {
        this.itemByItemId = itemByItemId;
    }
}
