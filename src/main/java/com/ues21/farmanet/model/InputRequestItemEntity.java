package com.ues21.farmanet.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "input_request_item", schema = "farmanet", catalog = "")
public class InputRequestItemEntity {
    private Integer id;
    private Integer quantity;
    private Timestamp deliveryDate;
    private InputRequestEntity inputRequestByInputRequestId;
    private ItemEntity itemByItemId;
    private StatusEntity statusByStatusId;

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
    @Column(name = "delivery_date", nullable = true)
    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InputRequestItemEntity that = (InputRequestItemEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (deliveryDate != null ? !deliveryDate.equals(that.deliveryDate) : that.deliveryDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (deliveryDate != null ? deliveryDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "input_request_id", referencedColumnName = "id", nullable = false)
    public InputRequestEntity getInputRequestByInputRequestId() {
        return inputRequestByInputRequestId;
    }

    public void setInputRequestByInputRequestId(InputRequestEntity inputRequestByInputRequestId) {
        this.inputRequestByInputRequestId = inputRequestByInputRequestId;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public ItemEntity getItemByItemId() {
        return itemByItemId;
    }

    public void setItemByItemId(ItemEntity itemByItemId) {
        this.itemByItemId = itemByItemId;
    }

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    public StatusEntity getStatusByStatusId() {
        return statusByStatusId;
    }

    public void setStatusByStatusId(StatusEntity statusByStatusId) {
        this.statusByStatusId = statusByStatusId;
    }
}
