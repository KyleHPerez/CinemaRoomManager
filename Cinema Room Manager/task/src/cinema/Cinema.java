package cinema;

import java.util.Scanner;

public class Cinema {

    static int rows = 0;
    static int seatsPerRow = 0;
    static int seatsPurchased = 0;
    static int currentIncome = 0;
    static int totalSeats = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean proceed = false;
        char[][] seats = initializeCinema(scanner);
        do {
            System.out.print("""
                    \n
                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    0. Exit\n
                    """);
            switch (scanner.nextLine()) {
                case "1" : {printSeats(seats); break;}
                case "2" : {selectSeat(scanner, seats); break;}
                case "3" : {printStatistics(); break;}
                case "0" : {proceed = true; break;}
            }
        } while (!proceed);
    }

    private static char[][] initializeCinema(Scanner scanner) {
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seatsPerRow = scanner.nextInt();
        char[][] seats = new char[rows + 1][seatsPerRow + 1];
        //INITIALIZE COLUMN HEADERS
        for (int i = 0; i < seatsPerRow + 1; i++) {
            if (0 == i) {
                seats[0][0] = ' ';
            } else {
                seats[0][i] = (char) (48 + i);
            }
        }
        //INITIALIZE ROW HEADERS
        for (int i = 0; i < rows + 1; i++) {
            if (0 != i) {
                seats[i][0] = (char) (48 + i);
            }
        }
        //INITIALIZE SEATS
        for (int i = 1; i < rows + 1; i++) {
            for (int j = 1; j < seatsPerRow + 1; j++) {
                seats[i][j] = 'S';
            }
        }
        totalSeats = rows * seatsPerRow;
        return seats;
    }

    private static void printSeats(char[][] seats) {
        System.out.println("\nCinema:");
        for (char[] row : seats) {
            for (char seat : row) {
                System.out.print(seat + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void selectSeat(Scanner scanner, char[][] seats) {
        boolean proceed = false;
        do {
            int rowNumber = 0;
            int seatNumber = 0;
            do {
                try {
                    System.out.println("Enter a row number:");
                    rowNumber = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter a seat number in that row:");
                    seatNumber = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Wrong input!");
                }
                if ((1 > rowNumber || rows < rowNumber) || (1 > seatNumber || seatsPerRow < seatNumber)) {
                        System.out.println("Wrong input!");
                }
            } while ((1 > rowNumber || rows < rowNumber) || (1 > seatNumber || seatsPerRow < seatNumber));
            if (seats[rowNumber][seatNumber] == 'B') {
                System.out.println("That ticket has already been purchased!");
                continue;
            }
            seats[rowNumber][seatNumber] = 'B';
            //Calculate Ticket Price
            int ticketPrice;
            if (60 >= (seats.length - 1) * (seats[0].length - 1)) {
                ticketPrice = 10;
            } else {
                if (rowNumber <= Math.floorDiv(seats.length - 1, 2)) {
                    ticketPrice = 10;
                } else {
                    ticketPrice = 8;
                }
            }
            System.out.printf("\nTicket price: $%d\n\n", ticketPrice);
            proceed = true;
            seatsPurchased++;
            currentIncome += ticketPrice;
        } while (!proceed);
    }

    private static int calculateIncome() {
        if (60 >= rows * seatsPerRow) {
            return rows * seatsPerRow * 10;
        } else {
            int frontHalfSeats = Math.floorDiv(rows, 2) * seatsPerRow;
            int backHalfSeats = rows * seatsPerRow - frontHalfSeats;
            return frontHalfSeats * 10 + backHalfSeats * 8;
        }
    }

    private static void printStatistics() {
        double percentage = ((double) seatsPurchased / (double) totalSeats) * 100;
        System.out.println(totalSeats);
        int totalIncome = calculateIncome();
        System.out.printf("""
                Number of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%d
                Total income: $%d""", seatsPurchased, percentage, currentIncome, totalIncome);
    }
}