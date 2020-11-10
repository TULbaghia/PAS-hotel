package pl.lodz.p.pas.model.apartment;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import pl.lodz.p.pas.model.apartment.exception.ApartmentException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreeStarApartmentTest {

    @Test
    public void threeStarApartmentCreationTestCase() throws ApartmentException {
        ThreeStarApartment apartment = new ThreeStarApartment(1, 23, 32, "Darmowe napoje");

        assertEquals(apartment.getHowManyBeds(), 1);
        assertEquals(apartment.getBasePricePerDay(), 32);
        assertEquals(apartment.actualPricePerDay(), 1.5 * apartment.getBasePricePerDay());
        assertEquals(apartment.getDoorNumber(), 23);
    }


    @Test
    public void uniqueIdTestCase() throws IllegalAccessException, ApartmentException {
        Set<ThreeStarApartment> apartments = new HashSet<>();

        ThreeStarApartment apartment1 = new ThreeStarApartment(1, 23, 32, "Darmowe napoje");
        ThreeStarApartment apartment2 = new ThreeStarApartment(1, 23, 32, "Darmowe napoje");
        FieldUtils.writeField(apartment1, "apartmentId", new UUID(12345678, 87654321), true);
        FieldUtils.writeField(apartment2, "apartmentId", new UUID(12345678, 87654321), true);
        apartments.add(apartment1);
        apartments.add(apartment2);

        int size = 50;
        for (int i = 0; i < size; i++) {
            apartments.add(new ThreeStarApartment(1, 23, 32, "Darmowe napoje"));
        }

        assertEquals(apartments.size(), size + 1);
    }

}
