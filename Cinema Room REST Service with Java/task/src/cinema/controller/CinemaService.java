package cinema.controller;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CinemaService {

    private static final int ROW_COUNT = 9;
    private static final int COLUMN_COUNT = 9;
    private final List<Seat> allSeats;
    private final Map<SeatNumber, Ticket> purchasedTickets = new ConcurrentHashMap<>();

    public CinemaService() {
        allSeats = new ArrayList<>(ROW_COUNT * COLUMN_COUNT);

        for (int i = 0; i < ROW_COUNT; i++)
            for (int j = 0; j < COLUMN_COUNT; j++)
                allSeats.add(new Seat(i + 1, j + 1));
    }

    public SeatsResponse getSeats() {
        final List<Seat> availableSeats = allSeats.stream()
                .filter(seat -> !purchasedTickets.containsKey(Seats.getNumber(seat)))
                .toList();

        return new SeatsResponse(ROW_COUNT, COLUMN_COUNT, availableSeats);
    }

    public Ticket purchaseSeat(final SeatNumber number) {
        if (isInvalidSeatNumber(number)) throw new InvalidSeatNumberException();

        final Seat seat = allSeats.stream()
                .filter(s -> Seats.equals(s, number))
                .findFirst()
                .orElseThrow(InvalidSeatNumberException::new);

        final Ticket ticket = new Ticket(seat);
        // Only put if not already present (atomic)
        Ticket existing = purchasedTickets.putIfAbsent(number, ticket);
        if (existing != null) {
            throw new SeatAlreadyTakenException();
        }

        return ticket;
    }

    public Seat returnSeat(final String token) {
        for (Map.Entry<SeatNumber, Ticket> entry : purchasedTickets.entrySet()) {
            if (Objects.equals(entry.getValue().getToken(), token)) {
                // Atomic remove only if mapping still exists
                if (purchasedTickets.remove(entry.getKey(), entry.getValue())) {
                    return entry.getValue().getSeat();
                }
            }
        }

        throw new TokenNotFoundException();
    }

    public Stats getStats(String password) {
        if (!Objects.equals(password, "super_secret")) throw new WrongPasswordException();
        // Copy a snapshot to avoid contradictory values
        final Map<SeatNumber, Ticket> purchasedTickets = new HashMap<>(this.purchasedTickets);
        Stats stats = new Stats();
        stats.setAvailable(allSeats.size() - purchasedTickets.size());
        stats.setPurchased(purchasedTickets.size());

        for (Ticket ticket : purchasedTickets.values()) {
            stats.setIncome(stats.getIncome() + ticket.getSeat().getPrice());
        }

        return stats;
    }

    private static boolean isInvalidSeatNumber(SeatNumber seatNumber) {
        return seatNumber.getRow() == null ||
                seatNumber.getRow() < 1 ||
                seatNumber.getRow() > ROW_COUNT ||
                seatNumber.getColumn() == null ||
                seatNumber.getColumn() < 1 ||
                seatNumber.getColumn() > COLUMN_COUNT;
    }
}
