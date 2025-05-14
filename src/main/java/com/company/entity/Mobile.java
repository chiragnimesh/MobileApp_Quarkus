package com.company.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;


@Entity
public class Mobile{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String mobileName;
    public String brand;
    public String ram;
    public double price;
    public String externalStorage;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    public String image;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileName() {
        return mobileName;
    }

    public void setMobileName(String mobileName) {
        this.mobileName = mobileName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getExternalStorage() {
        return externalStorage;
    }

    public void setExternalStorage(String externalStorage) {
        this.externalStorage = externalStorage;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "id=" + id +
                ", mobileName='" + mobileName + '\'' +
                ", brand='" + brand + '\'' +
                ", ram='" + ram + '\'' +
                ", price=" + price +
                ", externalStorage='" + externalStorage + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
