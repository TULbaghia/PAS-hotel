package pl.lodz.p.pas.model.apartment;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Apartment {
    private String apartmentId;
    private int howManyBeds;
    private int doorNumber;
    private double basePricePerDay;

    public Apartment(int howManyBeds, int doorNumber, double basePricePerDay) {
        this(UUID.randomUUID().toString(), howManyBeds, doorNumber, basePricePerDay);
    }

    public abstract double actualPricePerDay();
}
