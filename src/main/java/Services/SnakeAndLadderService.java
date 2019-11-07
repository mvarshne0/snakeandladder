package Services;

import Entities.Board;
import Entities.Ladder;
import Entities.Player;
import Entities.Snake;

import java.util.*;

public class SnakeAndLadderService {
    private static final int DEFAULT_BOARD_SIZE = 100;
    private Board board;
    private int initialNumberOfPlayers;
    private Queue<Player> playerQueue;

    public SnakeAndLadderService(int boardSize) {
        this.board = new Board(boardSize);
        this.playerQueue = new LinkedList<Player>();
    }

    public SnakeAndLadderService() {
        this(SnakeAndLadderService.DEFAULT_BOARD_SIZE);
    }

    public void setPlayers(List<Player> players) {
        this.playerQueue = new LinkedList<Player>();
        this.initialNumberOfPlayers = players.size();
        Map<String, Integer> playerPieces = new HashMap<String, Integer>();

        for (Player player : players) {
            playerQueue.add(player);
            playerPieces.put(player.getId(), 0);
        }

        board.setPlayersCurrentPositions(playerPieces);
    }

    public void setSnakes(List<Snake> snakes) {
        board.setSnakeList(snakes);
    }

    public void setLadders(List<Ladder> ladders) {
        board.setLadderList(ladders);
    }

    private int getTotalValueAfterDiceRolls() {
        return DiceService.roll();
    }

    private int getNewPositionAfterGoingThroughSnakesAndLadders(Player player, int position) {
        System.out.println("Processing Position " + position + " for " + player.getName());
        while (true) {
            for (Ladder ladder : board.getLadderList()) {
                if (ladder.getStart() == position) {
                    System.out.println(player.getName() + " Ladder Alert at:" + position + " moved from " + position + " to " + ladder.getEnd());
                    position = ladder.getEnd();
                    continue;
                }
            }

            for (Snake snake : board.getSnakeList()) {
                if (snake.getStart() == position) {
                    System.out.println(player.getName() + " Snake Alert at:" + position + " moved from " + position + " to " + snake.getEnd());
                    position = snake.getEnd();
                    continue;
                }
            }
            break;
        }
        return position;
    }

    private void movePlayer(Player player, int diceValue) {
        int oldPosition = board.getPlayersCurrentPositions().get(player.getId());
        int newPosition = oldPosition + diceValue;

        int boardSize = board.getSize();

        if (newPosition > boardSize) {
            newPosition = oldPosition;
        } else {
            newPosition = getNewPositionAfterGoingThroughSnakesAndLadders(player, newPosition);
        }

        board.getPlayersCurrentPositions().put(player.getId(), newPosition);
        System.out.println(player.getName() + " moved from " + oldPosition + " to " + newPosition);
    }

    private boolean hasPlayerWon(Player player) {
        return board.getPlayersCurrentPositions().get(player.getId()) == board.getSize();
    }

    private boolean isGameCompleted() {
        return initialNumberOfPlayers > playerQueue.size();
    }

    public void startGame() {
        while (!isGameCompleted()) {
            int diceValue = getTotalValueAfterDiceRolls();
            Player player = playerQueue.poll();
            System.out.println(player.getName() + " rolled a " + diceValue);
            movePlayer(player, diceValue);
            if (hasPlayerWon(player)) {
                System.out.println(player.getName() + " wins the game");
                board.getPlayersCurrentPositions().remove(player.getId());
            } else {
                playerQueue.add(player);
            }
        }
    }

}
