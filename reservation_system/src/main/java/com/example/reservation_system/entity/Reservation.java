package com.example.reservation_system.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

    private String status;
    private LocalDate reservationCreationDate;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private int price;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Users customerId;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property propertyId;

    public Reservation() {}

    public Reservation(int reservationId, String status, LocalDate reservationCreationDate, LocalDate reservationStartDate, LocalDate reservationEndDate, int price, Users customerId, Property propertyId) {
        this.reservationId = reservationId;
        this.status = status;
        this.reservationCreationDate = reservationCreationDate;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.price = price;
        this.customerId = customerId;
        this.propertyId = propertyId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReservationCreationDate() {
        return reservationCreationDate;
    }

    public void setReservationCreationDate(LocalDate reservationCreationDate) {
        this.reservationCreationDate = reservationCreationDate;
    }

    public LocalDate getReservationStartDate() {
        return reservationStartDate;
    }

    public void setReservationStartDate(LocalDate reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }

    public LocalDate getReservationEndDate() {
        return reservationEndDate;
    }

    public void setReservationEndDate(LocalDate reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Users getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Users customerId) {
        this.customerId = customerId;
    }

    public Property getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Property propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", status='" + status + '\'' +
                ", reservationCreationDate=" + reservationCreationDate +
                ", reservationStartDate=" + reservationStartDate +
                ", reservationEndDate=" + reservationEndDate +
                ", price=" + price +
                ", customerId=" + customerId +
                ", propertyId=" + propertyId +
                '}';
    }

    public List<LocalDate> getDatesBetween() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = reservationStartDate;

        while(!date.isAfter(reservationEndDate)) {
            dates.add(date);
            date=date.plusDays(1);
        }

        return dates;
    }

    public boolean isValid(){
        return reservationStartDate != null && reservationEndDate != null
                && reservationEndDate.isAfter(reservationStartDate);
    }
}
