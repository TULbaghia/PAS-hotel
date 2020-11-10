package pl.lodz.p.pas.model.apartment.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApartmentExceptionTest {

    @Test
    public void exceptionsTestCase() {
        assertThrows(ApartmentException.class, () -> {
            throw new ApartmentException();
        });
        assertThrows(ApartmentException.class, () -> {
            throw new ApartmentException("TEST");
        });
        assertThrows(ApartmentException.class, () -> {
            throw new ApartmentException(new NullPointerException());
        });
        assertThrows(ApartmentException.class, () -> {
            throw new ApartmentException("TEST", new NullPointerException());
        });
    }

    @Test
    public void getMessageTestCase() {
        ApartmentException e = assertThrows(ApartmentException.class, () -> {
            throw new ApartmentException("TEST");
        });

        assertEquals("TEST", e.getMessage());
    }
}
