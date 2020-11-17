package pl.lodz.p.pas.repository.reservation;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.pas.model.reservation.Reservation;
import pl.lodz.p.pas.repository.IRepository;

public class ReservationRepository implements IRepository<Reservation> {

 private List<Reservation> reservations = new ArrayList<>();

    public ReservationRepository() {
    }

    @Override
    public void add(Reservation item) {
        reservations.add(item);
    }

    @Override
    public Reservation get(String id) {
        for (Reservation res : reservations) {
            if (res.getRentId().equals(id)) {
                return res;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<Reservation> getAll() {
       return reservations;
    }

    @Override
    public void update(String id, Reservation item) {
         for (Reservation res : reservations) {
            if (res.getRentId().equals(id)) {
                int index = reservations.indexOf(res);
                reservations.set(index, item);
                return;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public void delete(String id) {
        for (Reservation res : reservations) {
            if (res.getRentId().equals(id)) {
                reservations.remove(res);
            }
            return;
        }

        throw new IllegalArgumentException();
    }

}
