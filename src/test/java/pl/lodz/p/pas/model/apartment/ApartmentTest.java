package pl.lodz.p.pas.model.apartment;

import org.junit.jupiter.api.Test;
import pl.lodz.p.pas.model.apartment.exception.ApartmentException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApartmentTest {

    @Test
    public void fiveStarApartmentCreationTestCase() throws ApartmentException {
        Apartment apartment = new FiveStarApartment(1, 23, 32, "Darmowe napoje", "Komputer_23");

        assertEquals(apartment.getHowManyBeds(), 1);
        assertEquals(apartment.getBasePricePerDay(), 32);
        assertEquals(apartment.actualPricePerDay(), 2 * apartment.getBasePricePerDay());
        assertEquals(apartment.getDoorNumber(), 23);
    }

    @Test
    public void threeStarApartmentCreationTestCase() throws ApartmentException {
        Apartment apartment = new ThreeStarApartment(1, 23, 32, "Darmowe napoje");

        assertEquals(apartment.getHowManyBeds(), 1);
        assertEquals(apartment.getBasePricePerDay(), 32);
        assertEquals(apartment.actualPricePerDay(), 1.5 * apartment.getBasePricePerDay());
        assertEquals(apartment.getDoorNumber(), 23);
    }

    @Test
    public void uniqueIdTestCase() throws ApartmentException {
        Set<Apartment> apartments = new HashSet<>();

        int size = 25;
        for (int i = 0; i < size; i++) {
            apartments.add(new FiveStarApartment(1, 23, 32, "Darmowe napoje", "Komputer"));
            apartments.add(new ThreeStarApartment(1, 23, 32, "Darmowe napoje"));
        }

        assertEquals(apartments.size(), (size * 2));
    }

}
