package pl.lodz.p.pas.model.reservation.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationExceptionTest {

    @Test
    public void exceptionsTestCase() {
        assertThrows(ReservationException.class, () -> {
            throw new ReservationException();
        });
        assertThrows(ReservationException.class, () -> {
            throw new ReservationException("TEST");
        });
        assertThrows(ReservationException.class, () -> {
            throw new ReservationException(new NullPointerException());
        });
        assertThrows(ReservationException.class, () -> {
            throw new ReservationException("TEST", new NullPointerException());
        });
    }

    @Test
    public void getMessageTestCase() {
        ReservationException e = assertThrows(ReservationException.class, () -> {
            throw new ReservationException("TEST");
        });

        assertEquals("TEST", e.getMessage());
    }
}
