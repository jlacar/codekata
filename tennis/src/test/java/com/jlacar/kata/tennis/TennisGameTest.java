package com.jlacar.kata.tennis;

import org.junit.jupiter.api.*;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TennisGameTest {

    private TennisGame game;

    // reuseable/composable game scenarios
    private Scenario serverWinsPoint;
    private Scenario receiverWinsPoint;

    @BeforeEach
    void setUp() {
        game = new TennisGame();

        serverWinsPoint = () -> game.serverWinsPoint();
        receiverWinsPoint = () -> game.receiverWinsPoint();
    }

    @Nested
    class Basic_Scoring {

        @Test
        void it_starts_at_love_all() {
            assertEquals("Love all", game.getScore());
        }

        @Test
        void it_gives_server_score_first() {
            serverWinsPoint.play();
            assertEquals("15 - Love", game.getScore());

            serverWinsPoint.play();
            assertEquals("30 - Love", game.getScore());

            serverWinsPoint.play();
            assertEquals("40 - Love", game.getScore());
        }

        @Test
        void it_gives_receiver_score_second() {
            receiverWinsPoint.play();
            assertEquals("Love - 15", game.getScore());

            receiverWinsPoint.play();
            assertEquals("Love - 30", game.getScore());

            receiverWinsPoint.play();
            assertEquals("Love - 40", game.getScore());
        }

        @Test
        void it_uses_all_when_score_is_tied() {
            receiverWinsPoint.andThen(serverWinsPoint).play();
            assertEquals("15 all", game.getScore());

            serverWinsPoint.andThen(receiverWinsPoint).play();
            assertEquals("30 all", game.getScore());
        }

    }

    @Nested
    class Basic_Win {
        @Test
        void server_wins_with_four_points() {
            serverWinsPoint.times(4).play();

            assertEquals("Game, Server", game.getScore());
        }

        @Test
        void receiver_wins_with_four_points() {
            receiverWinsPoint.times(4).play();

            assertEquals("Game, Receiver", game.getScore());
        }

        @Test
        void at_40_30_server_wins_game_with_next_point() {
            play(serverWinsPoint.times(3), receiverWinsPoint.times(2));

            serverWinsPoint.play();

            assertEquals("Game, Server", game.getScore());
        }

        @Test
        void at_30_40_receiver_wins_game_with_next_point() {
            play(serverWinsPoint.times(2), receiverWinsPoint.times(3));

            receiverWinsPoint.play();

            assertEquals("Game, Receiver", game.getScore());
        }

    }

    @Nested
    class Deuce {
        private Scenario deuce;
        private Scenario adIn;
        private Scenario adOut;

        @BeforeEach
        void setUp() {
            deuce = serverWinsPoint.times(3)
                    .andThen(receiverWinsPoint.times(3));

            adIn = deuce.andThen(serverWinsPoint);
            adOut = deuce.andThen(receiverWinsPoint);
        }

        @Test
        void it_is_Deuce_when_each_player_wins_3_points() {
            deuce.play();

            assertEquals("Deuce", game.getScore());
        }

        @Test
        void it_is_Ad_in_when_deuce_and_server_wins_point() {
            play(deuce, serverWinsPoint);

            assertEquals("Ad in", game.getScore());
        }

        @Test
        void it_is_Ad_out_when_deuce_and_receiver_wins_point() {
            play(deuce, receiverWinsPoint);

            assertEquals("Ad out", game.getScore());
        }

        @Test
        void server_wins_game_when_Ad_in_and_wins_point() {
            play(adIn, serverWinsPoint);

            assertEquals("Game, Server", game.getScore());
        }

        @Test
        void receiver_wins_game_when_Ad_out_and_wins_point() {
            play(adOut, receiverWinsPoint);

            assertEquals("Game, Receiver", game.getScore());
        }

        @Test
        void it_goes_back_to_Deuce_when_Ad_in_and_receiver_wins_point() {
            play(adIn, receiverWinsPoint);

            assertEquals("Deuce", game.getScore());
        }

        @Test
        void it_goes_back_to_Deuce_when_Ad_out_and_server_wins_point() {
            play(adOut, serverWinsPoint);

            assertEquals("Deuce", game.getScore());
        }

        @Test
        void it_goes_back_to_Deuce_with_multiple_ties() {
            play(adOut,
                 serverWinsPoint,      // Deuce
                 serverWinsPoint,      // Ad in
                 receiverWinsPoint,    // Deuce
                 receiverWinsPoint,    // Ad out
                 serverWinsPoint);     // Deuce

            assertEquals("Deuce", game.getScore());
        }
    }

    @Nested
    class Named_Players {
        private TennisGame game;
        private Scenario deuce;

        @BeforeEach
        void setUp() {
            game = new TennisGame("Bob", "Fred");
            serverWinsPoint = () -> game.serverWinsPoint();
            receiverWinsPoint = () -> game.receiverWinsPoint();
            deuce = serverWinsPoint.times(3).andThen(receiverWinsPoint.times(3));
        }

        @Test
        void it_gives_server_name_at_Ad_in() {
            play(deuce, serverWinsPoint);

            assertEquals("Advantage Bob", game.getScore());
        }

        @Test
        void it_gives_receiver_name_at_Ad_out() {
            play(deuce, receiverWinsPoint);

            assertEquals("Advantage Fred", game.getScore());
        }

        @Test
        void it_gives_server_name_when_won() {
            serverWinsPoint.times(4).play();

            assertEquals("Game, Bob", game.getScore());
        }

        @Test
        void it_gives_receiver_name_when_won() {
            receiverWinsPoint.times(4).play();

            assertEquals("Game, Fred", game.getScore());
        }
    }

    @Nested
    class Exception_Conditions {
        @Test
        void it_throws_IllegalStateException_if_point_awarded_after_player_wins() {
            serverWinsPoint.times(4).play();

            assertThrows(IllegalStateException.class, () -> game.receiverWinsPoint());
            assertThrows(IllegalStateException.class, () -> game.serverWinsPoint());
        }
    }

    private static void play(Scenario... sequence) {
        for (Scenario point : sequence) {
            point.play();
        }
    }
}

@FunctionalInterface
interface Scenario {
    void play();

    default Scenario times(int count) {
        Scenario sequence = this;
        for (int i = 1; i < count; i++) {
            sequence = sequence.andThen(this);
        }
        return sequence;
    }

    default Scenario andThen(Scenario after) {
        return () -> {
            this.play();
            after.play();
        };
    }

    default Scenario compose(Scenario before) {
        return () -> {
            before.play();
            this.play();
        };
    }
}