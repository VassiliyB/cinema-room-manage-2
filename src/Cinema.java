import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    private static final Scanner SC = new Scanner(System.in);
    private static final int MAX_COST = 10;
    private static final int MIN_COST = 8;
    private static final int EXCESS_THRESHOLD = 60;
    private int purchasedTickets = 0;
    private int currentIncome = 0;
    private char[][] room;
    private int rows;
    private int numSeats;

    public Cinema(int rows, int numSeats) {
        this.rows = rows;
        this.numSeats = numSeats;
        this.room = new char[rows][numSeats];
        initArray();
    }

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        int rows = SC.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numSeats = SC.nextInt();

        Cinema cinema = new Cinema(rows, numSeats);
        cinema.run();
    }

    private void run() {
        boolean flag = true;

        while (flag) {
            System.out.println();
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int menu = SC.nextInt();
            switch (menu) {
                case 1 -> printSeats();
                case 2 -> buyTicket();
                case 3 -> printStatistics();
                case 0 -> flag = false;
                default -> System.out.println("Wrong input!");
            }
        }
    }

    private void initArray() {
        for (char[] row : room) {
            Arrays.fill(row, 'S');
        }
    }

    private void printSeats() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCinema:\n ");
        for (int i = 1; i <= numSeats; ++i) {
            sb.append(" ").append(i);
        }
        sb.append("\n");

        for (int i = 0; i < rows; i++) {
            sb.append(i + 1);
            for (int j = 0; j < numSeats; j++) {
                sb.append(" ").append(room[i][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    private void buyTicket() {
        System.out.println();
        while (true) {
            System.out.println("Enter a row number:");
            int userRow = SC.nextInt();
            System.out.println("Enter a seat number in that row:");
            int userSeat = SC.nextInt();
            if (userRow > 0 && userRow <= rows && userSeat > 0 && userSeat <= numSeats) {
                if (room[userRow - 1][userSeat - 1] != 'B') {
                    room[userRow - 1][userSeat - 1] = 'B';
                    purchasedTickets++;
                    int price = calculateTicketPrice(userRow);
                    currentIncome += price;
                    System.out.println("Ticket price: $" + price);
                    break;
                } else {
                    System.out.println("That ticket has already been purchased!");
                }
            } else {
                System.out.println("Wrong input!");
            }
        }
    }

    private int calculateTicketPrice(int userRow) {
        int totalSeats = rows * numSeats;
        int frontSeats = rows / 2;
        return (totalSeats <= EXCESS_THRESHOLD || userRow <= frontSeats) ? MAX_COST : MIN_COST;
    }

    private void calculateProfit() {
        int totalSeats = rows * numSeats;
        int frontRows = rows / 2;
        int backRows = rows - frontRows;
        int profit = (totalSeats < EXCESS_THRESHOLD)
                ? totalSeats * MAX_COST
                : (frontRows * numSeats * MAX_COST) + (backRows * numSeats * MIN_COST);
        System.out.println("Total income: $" + profit);
    }

    private void getPercentage() {
        double totalSeats = rows * numSeats;
        double percentage = (purchasedTickets / totalSeats) * 100;
        System.out.printf("Percentage: %.2f%%%n", percentage);
    }

    private void printStatistics() {
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        getPercentage();
        System.out.println("Current income: $" + currentIncome);
        calculateProfit();
    }
}
