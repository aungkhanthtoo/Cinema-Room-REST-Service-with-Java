package cinema.controller;

public class Seats {

    public static SeatNumber getNumber(Seat seat) {
        return new SeatNumber(seat.getRow(), seat.getColumn());
    }

    public static boolean equals(Seat seat, SeatNumber number) {
        return seat.getColumn() == number.getColumn() && seat.getRow() == number.getRow();
    }
}
