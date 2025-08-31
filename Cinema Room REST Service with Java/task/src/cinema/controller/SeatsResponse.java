package cinema.controller;

import java.util.List;

public class SeatsResponse {

    private final int rows;
    private final int columns;
    private final List<Seat> seats;

    public SeatsResponse(int rows, int columns, List<Seat> seats) {
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
