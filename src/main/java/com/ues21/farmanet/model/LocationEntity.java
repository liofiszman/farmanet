package com.ues21.farmanet.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "location", schema = "farmanet", catalog = "")
public class LocationEntity {
    private Integer id;
    private String name;
    private String addressStreet;
    private Integer addressNumber;
    private String postalCode;
    private String city;
    private String province;
    private String phoneNumber;
    private Collection<InputRequestEntity> inputRequestsById;
    private Collection<OutputEntity> outputsById;
    private Collection<PositionEntity> positionsById;
    private Collection<StockEntity> stocksById;
    private Collection<UserEntity> usersById;

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

    @Basic
    @Column(name = "address_street", nullable = false, length = 255)
    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    @Basic
    @Column(name = "address_number", nullable = false)
    public Integer getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(Integer addressNumber) {
        this.addressNumber = addressNumber;
    }

    @Basic
    @Column(name = "postal_code", nullable = false, length = 80)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "city", nullable = false, length = 255)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "province", nullable = false, length = 255)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "phone_number", nullable = false, length = 255)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationEntity that = (LocationEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (addressStreet != null ? !addressStreet.equals(that.addressStreet) : that.addressStreet != null)
            return false;
        if (addressNumber != null ? !addressNumber.equals(that.addressNumber) : that.addressNumber != null)
            return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (addressStreet != null ? addressStreet.hashCode() : 0);
        result = 31 * result + (addressNumber != null ? addressNumber.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "locationByLocationId")
    public Collection<InputRequestEntity> getInputRequestsById() {
        return inputRequestsById;
    }

    public void setInputRequestsById(Collection<InputRequestEntity> inputRequestsById) {
        this.inputRequestsById = inputRequestsById;
    }

    @OneToMany(mappedBy = "locationByLocationId")
    public Collection<OutputEntity> getOutputsById() {
        return outputsById;
    }

    public void setOutputsById(Collection<OutputEntity> outputsById) {
        this.outputsById = outputsById;
    }

    @OneToMany(mappedBy = "locationByLocationId")
    public Collection<PositionEntity> getPositionsById() {
        return positionsById;
    }

    public void setPositionsById(Collection<PositionEntity> positionsById) {
        this.positionsById = positionsById;
    }

    @OneToMany(mappedBy = "locationByLocationId")
    public Collection<StockEntity> getStocksById() {
        return stocksById;
    }

    public void setStocksById(Collection<StockEntity> stocksById) {
        this.stocksById = stocksById;
    }

    @OneToMany(mappedBy = "locationByDefaultLocationId")
    public Collection<UserEntity> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<UserEntity> usersById) {
        this.usersById = usersById;
    }
}
