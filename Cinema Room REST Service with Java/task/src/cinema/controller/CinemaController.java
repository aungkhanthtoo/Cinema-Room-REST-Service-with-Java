package cinema.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CinemaController {

    private final CinemaService service;

    public CinemaController(CinemaService service) {
        this.service = service;
    }

    @GetMapping("/seats")
    public SeatsResponse getAllSeats() {
        return service.getSeats();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody SeatNumber seat) {
        try {
            Ticket ticket = service.purchaseSeat(seat);
            return ResponseEntity.ok(
                    Map.of(
                            "token", ticket.getToken(),
                            "ticket", ticket.getSeat()
                    )
            );
        } catch (InvalidSeatNumberException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("The number of a row or a column is out of bounds!"));
        } catch (SeatAlreadyTakenException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("The ticket has been already purchased!"));
        }
    }

    @PostMapping("/return")
    public Map<String, ?> returnSeat(@RequestBody TokenRequest request) {
        final Seat returned = service.returnSeat(request.getToken());
        return Map.of("ticket", returned);
    }

    @GetMapping("/stats")
    public Stats getStats(@RequestParam(required = false) String password) {
        return service.getStats(password);
    }
}
