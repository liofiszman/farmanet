package com.ues21.farmanet.model;

import javax.persistence.*;

@Entity
@Table(name = "output_item", schema = "farmanet", catalog = "")
public class OutputItemEntity {
    private Integer id;
    private Integer quantity;
    private String notes;
    private OutputEntity outputByOutputId;
    private ItemEntity itemByItemId;

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
    @Column(name = "quantity", nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "notes", nullable = true, length = 255)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutputItemEntity that = (OutputItemEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "output_id", referencedColumnName = "id", nullable = false)
    public OutputEntity getOutputByOutputId() {
        return outputByOutputId;
    }


    public void setOutputByOutputId(OutputEntity outputByOutputId) {
        this.outputByOutputId = outputByOutputId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public ItemEntity getItemByItemId() {
        return itemByItemId;
    }

    public void setItemByItemId(ItemEntity itemByItemId) {
        this.itemByItemId = itemByItemId;
    }
}
