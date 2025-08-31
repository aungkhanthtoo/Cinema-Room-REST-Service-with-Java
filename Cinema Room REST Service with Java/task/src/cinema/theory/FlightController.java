package cinema.theory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final List<FlightInfo> flights = Collections.synchronizedList(
            List.of(
                    new FlightInfo(1, "Delhi Indira Gandhi", "Stuttgart", "D80"),
                    new FlightInfo(2, "Tokyo Haneda", "Frankfurt", "110"),
                    new FlightInfo(3, "Berlin Schönefeld", "Tenerife", "15"),
                    new FlightInfo(4, "Kilimanjaro Arusha", "Boston", "15")
            )
    );

    @GetMapping("/{id}")
    public FlightInfo getFlight(@PathVariable int id) {
        for (FlightInfo flight : flights) {
            if (flight.id == id) {
                if (Objects.equals(flight.getFrom(), "Berlin Schönefeld")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Berlin Schönefeld is closed for service today");
                } else {
                    return flight;
                }
            }
        }
        throw new FlightNotFoundException("Flight not found for id =" + id);
    }

    public static class FlightInfo {
        private int id;
        private String from;
        private String to;
        private String gate;

        public FlightInfo(int id, String from, String to, String gate) {
            this.id = id;
            this.from = from;
            this.to = to;
            this.gate = gate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getGate() {
            return gate;
        }

        public void setGate(String gate) {
            this.gate = gate;
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class FlightNotFoundException extends RuntimeException {

        public FlightNotFoundException(String message) {
            super(message);
        }
    }
}
