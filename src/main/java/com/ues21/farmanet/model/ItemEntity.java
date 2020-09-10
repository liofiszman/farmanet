package com.ues21.farmanet.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "item", schema = "farmanet", catalog = "")
public class ItemEntity {
    private Integer id;
    private String name;
    private String description;
    private String presentation;
    private Integer quatity;
    private Collection<InputRequestItemEntity> inputRequestItemsById;
    private CategoryEntity categoryByCategoryId;
    private VendorEntity vendorByVendorId;
    private UnitEntity unitByUnitId;
    private Collection<ItemDrugEntity> itemDrugsById;
    private Collection<OutputItemEntity> outputItemsById;
    private Collection<StockEntity> stocksById;

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

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "presentation", nullable = false, length = 255)
    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    @Basic
    @Column(name = "quatity", nullable = false)
    public Integer getQuatity() {
        return quatity;
    }

    public void setQuatity(Integer quatity) {
        this.quatity = quatity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemEntity that = (ItemEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (presentation != null ? !presentation.equals(that.presentation) : that.presentation != null) return false;
        if (quatity != null ? !quatity.equals(that.quatity) : that.quatity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (presentation != null ? presentation.hashCode() : 0);
        result = 31 * result + (quatity != null ? quatity.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "itemByItemId")
    public Collection<InputRequestItemEntity> getInputRequestItemsById() {
        return inputRequestItemsById;
    }

    public void setInputRequestItemsById(Collection<InputRequestItemEntity> inputRequestItemsById) {
        this.inputRequestItemsById = inputRequestItemsById;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    public CategoryEntity getCategoryByCategoryId() {
        return categoryByCategoryId;
    }

    public void setCategoryByCategoryId(CategoryEntity categoryByCategoryId) {
        this.categoryByCategoryId = categoryByCategoryId;
    }

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    public VendorEntity getVendorByVendorId() {
        return vendorByVendorId;
    }

    public void setVendorByVendorId(VendorEntity vendorByVendorId) {
        this.vendorByVendorId = vendorByVendorId;
    }

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    public UnitEntity getUnitByUnitId() {
        return unitByUnitId;
    }

    public void setUnitByUnitId(UnitEntity unitByUnitId) {
        this.unitByUnitId = unitByUnitId;
    }

    @OneToMany(mappedBy = "itemByItemId")
    public Collection<ItemDrugEntity> getItemDrugsById() {
        return itemDrugsById;
    }

    public void setItemDrugsById(Collection<ItemDrugEntity> itemDrugsById) {
        this.itemDrugsById = itemDrugsById;
    }

    @OneToMany(mappedBy = "itemByItemId")
    public Collection<OutputItemEntity> getOutputItemsById() {
        return outputItemsById;
    }

    public void setOutputItemsById(Collection<OutputItemEntity> outputItemsById) {
        this.outputItemsById = outputItemsById;
    }

    @OneToMany(mappedBy = "itemByItemId")
    public Collection<StockEntity> getStocksById() {
        return stocksById;
    }

    public void setStocksById(Collection<StockEntity> stocksById) {
        this.stocksById = stocksById;
    }
}
