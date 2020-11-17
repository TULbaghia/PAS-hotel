package pl.lodz.p.pas.model.apartment;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.lodz.p.pas.model.apartment.exception.ApartmentException;

import java.util.UUID;

@Getter
public abstract class Apartment {
    private final String apartmentId;
    private final int howManyBeds;
    private final int doorNumber;
    private final double basePricePerDay;

    public Apartment(String apartmentId, int howManyBeds, int doorNumber, double basePricePerDay) throws ApartmentException {
        if (howManyBeds <= 0) {
            throw new ApartmentException("Apartment beds number is not correct.");
        }
        if (basePricePerDay <= 0) {
            throw new ApartmentException("Base price per day must be greater than 0");
        }
        this.apartmentId = apartmentId;
        this.howManyBeds = howManyBeds;
        this.doorNumber = doorNumber;
        this.basePricePerDay = basePricePerDay;
    }

    public Apartment(int howManyBeds, int doorNumber, double basePricePerDay) throws ApartmentException {
        this(UUID.randomUUID().toString(), howManyBeds, doorNumber, basePricePerDay);
    }

    public abstract double actualPricePerDay();

    @Override
    public String toString() {
        return "Apartment{" + "apartmentId=" + apartmentId + ", howManyBeds=" + howManyBeds + ", doorNumber=" + doorNumber + ", basePricePerDay=" + basePricePerDay + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Apartment apartment = (Apartment) o;

        return new EqualsBuilder()
                .append(apartmentId, apartment.apartmentId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(apartmentId)
                .toHashCode();
    }
}
