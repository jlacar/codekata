package com.jlacar.kata.tennis;

import java.util.Scanner;

import static com.jlacar.kata.tennis.GameView.*;

public class TennisGame {
    private static final String[] SCORE = {"Love", "15", "30", "40"};

    private static final String DEFAULT_SERVER = "Server";
    private static final String DEFAULT_RECEIVER = "Receiver";

    private final String server;
    private final String receiver;

    private int serverPoints;
    private int receiverPoints;

    private String winner;

    public TennisGame() {
        server = DEFAULT_SERVER;
        receiver = DEFAULT_RECEIVER;
    }

    public TennisGame(String server, String receiver) {
        this.server = server;
        this.receiver = receiver;
    }

    public String getScore() {
        if (hasWinner()) {
            return winner();

        } else if (isTied()) {
            return isDeuce() ? "Deuce" : tiedScore();

        } else if (serverHasAdvantage()) {
            return adInScore();

        } else if (receiverHasAdvantage()) {
            return adOutScore();

        } else {
            return normalScore();
        }
    }

    public boolean inPlay() {
        return !hasWinner();
    }

    private String tiedScore() {
        return SCORE[serverPoints] + " all";
    }
    // Implementation Note: Comparisons to player names below use == so that
    // if the TennisGame(String, String) constructor is used, object still
    // behaves as one might expect even if the names provided equals() the

    // default names defined by DEFAULT_SERVER and DEFAULT_RECEIVER.

    private String adInScore() {
        return (server == DEFAULT_SERVER) ? "Ad in" : ("Advantage " + server);
    }

    private String adOutScore() {
        return (receiver == DEFAULT_RECEIVER) ? "Ad out" : ("Advantage " + receiver);
    }

    private String normalScore() {
        return String.format("%s - %s", SCORE[serverPoints], SCORE[receiverPoints]);
    }

    private String winner() {
        return "Game, " + winner;
    }

    private boolean hasWinner() {
        return winner != null;
    }

    private boolean isTied() {
        return serverPoints == receiverPoints;
    }

    private boolean isDeuce() {
        return serverPoints == 3 && receiverPoints == 3;
    }

    private boolean receiverHasAdvantage() {
        return receiverPoints == 4;
    }

    private boolean serverHasAdvantage() {
        return serverPoints == 4;
    }

    private boolean serverHasGamePoint() {
        return serverPoints == 3 && receiverPoints < 3 || serverPoints == 4;
    }

    private boolean receiverHasGamePoint() {
        return receiverPoints == 3 && serverPoints < 3 || receiverPoints == 4;
    }

    public void serverWinsPoint() {
        ensureGameIsNotOver();
        if (serverHasGamePoint()) {
            serverWins();
        } else if (receiverHasAdvantage()) {
            receiverLosesAdvantage();
        } else {
            serverPoints++;
        }
    }

    public void receiverWinsPoint() {
        ensureGameIsNotOver();
        if (receiverHasGamePoint()) {
            receiverWins();
        } else if (serverHasAdvantage()) {
            serverLosesAdvantage();
        } else {
            receiverPoints++;
        }
    }

    private void ensureGameIsNotOver() {
        if (hasWinner()) {
            throw new IllegalStateException("Game is over");
        }
    }

    private void serverWins() {
        winner = server;
    }

    private void receiverWins() {
        winner = receiver;
    }
    // Implementation Note:

    // Deuce always goes back to 3-3
    // Ad-in is 4-3, Ad-out is 3-4

    private void receiverLosesAdvantage() {
        receiverPoints--;
    }

    private void serverLosesAdvantage() {
        serverPoints--;
    }

}

class GameController {
    private String[] playerNames;
    private TennisGame game;
    private final GameView view = new GameView();

    public static void main(String[] args) {
        new GameController().play();
    }

    public void play() {
        newPlayers();
        startNewGame();

        int option;
        do {
            option = view.getAction();
            switch (option) {
                case SERVER_POINT:
                    if (game.inPlay()) game.serverWinsPoint();
                    else view.display("Game is already over.");
                    break;

                case RECEIVER_POINT:
                    if (game.inPlay()) game.receiverWinsPoint();
                    else view.display("Game is already over.");
                    break;

                case SHOW_SCORE:
                    view.display(game.getScore());
                    break;

                case NEW_GAME:
                    swapPlayers();
                    startNewGame();
                    break;

                case NEW_GAME_PLAYERS:
                    newPlayers();
                    startNewGame();
                    break;

                case EXIT:
                    view.display("Exiting.");
                    break;

                default:
                    view.display("Invalid option");
            }
        } while (option != EXIT);
    }

    private void swapPlayers() {
        String temp = playerNames[0];
        playerNames[0] = playerNames[1];
        playerNames[1] = temp;
    }

    private void newPlayers() {
        playerNames = new String[]{"", ""};
        getPlayerNames();
    }

    private void startNewGame() {
        if (noPlayerNames()) {
            game = new TennisGame();
            view.display(String.format("%nStarting new game - %s", game.getScore()));
        } else {
            game = new TennisGame(serverName(), receiverName());
            view.display(String.format("%nStarting new game (%s v %s) - %s", serverName(), receiverName(), game.getScore()));
        }
    }

    private String receiverName() {
        return playerNames[1];
    }

    private String serverName() {
        return playerNames[0];
    }

    private void getPlayerNames() {
        String[] names = view.getPlayerNames();
        if (!names[0].isBlank()) playerNames[0] = names[0];
        if (!names[1].isBlank()) playerNames[1] = names[1];
    }

    private boolean noPlayerNames() {
        return serverName().isBlank() || receiverName().isBlank();
    }
}

class GameView {
    static final int SERVER_POINT = 1;
    static final int RECEIVER_POINT = 2;
    static final int SHOW_SCORE = 3;
    static final int NEW_GAME = 4;
    static final int NEW_GAME_PLAYERS = 5;
    static final int EXIT = 6;
    private Scanner input = new Scanner(System.in);

    void showMenu() {
        System.out.println();
        System.out.println("Your options:");
        showOption(SERVER_POINT, "Award point to server");
        showOption(RECEIVER_POINT, "Award point to receiver");
        showOption(SHOW_SCORE, "Display score");
        showOption(NEW_GAME, "Start a new game, same players swap");
        showOption(NEW_GAME_PLAYERS, "Start a new game, different players");
        showOption(EXIT, "Exit program");
    }

    private void showOption(int option, String description) {
        System.out.printf("%d - %s%n", option, description);
    }

    int getAction() {
        showMenu();
        System.out.printf("Select an option (%d-%d): ", SERVER_POINT, EXIT);

        int selection = input.nextInt();
        input.nextLine();

        return selection;
    }

    void display(String msg) {
        System.out.printf("%s%n", msg);
    }

    String[] getPlayerNames() {
        String[] names = new String[2];
        System.out.print("Who is serving? ");
        names[0] = input.nextLine();
        System.out.print("Who is receiving? ");
        names[1] = input.nextLine();
        return names;
    }
}