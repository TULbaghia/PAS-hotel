package pl.lodz.p.pas.model.apartment;

import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import pl.lodz.p.pas.model.apartment.exception.ApartmentException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FiveStarApartmentTest {

    @Test
    public void fiveStarApartmentCreationTestCase() throws ApartmentException {
        FiveStarApartment apartment = new FiveStarApartment(1, 23, 32, "Darmowe napoje", "Komputer_23");

        assertEquals(apartment.getHowManyBeds(), 1);
        assertEquals(apartment.getBasePricePerDay(), 32);
        assertEquals(apartment.actualPricePerDay(), 2 * apartment.getBasePricePerDay());
        assertEquals(apartment.getDoorNumber(), 23);
    }

    @SneakyThrows
    @Test
    public void uniqueIdTestCase() {
        Set<FiveStarApartment> apartments = new HashSet<>();

        FiveStarApartment apartment1 = new FiveStarApartment(1, 23, 32, "Darmowe napoje", "Kakuter");
        FiveStarApartment apartment2 = new FiveStarApartment(1, 23, 32, "Darmowe napoje", "Kakuter");
        FieldUtils.writeField(apartment1, "apartmentId", new UUID(12345678, 87654321).toString(), true);
        FieldUtils.writeField(apartment2, "apartmentId", new UUID(12345678, 87654321).toString(), true);
        apartments.add(apartment1);
        apartments.add(apartment2);

        int size = 50;
        for (int i = 0; i < size; i++) {
            apartments.add(new FiveStarApartment(1, 23, 32, "Darmowe napoje", "Komputer"));
        }

        assertEquals(apartments.size(), size + 1);
    }

}
