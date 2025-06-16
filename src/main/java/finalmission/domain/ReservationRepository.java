package finalmission.domain;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void deleteById(long id);

    List<Reservation> findAll();

    List<Reservation> findAllByMemberId(long memberId);

    Optional<Reservation> findById(long id);
}
