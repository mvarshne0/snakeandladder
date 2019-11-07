package Entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Board {
    private int size;
    private List<Snake> snakeList;
    private List<Ladder> ladderList;
    private Map<String, Integer> playersCurrentPositions;

    public Board(int size) {
        this.size = size;
        this.snakeList = new ArrayList<Snake>();
        this.ladderList = new ArrayList<Ladder>();
        this.playersCurrentPositions = new HashMap<String, Integer>();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Snake> getSnakeList() {
        return snakeList;
    }

    public void setSnakeList(List<Snake> snakeList) {
        this.snakeList = snakeList;
    }

    public List<Ladder> getLadderList() {
        return ladderList;
    }

    public void setLadderList(List<Ladder> ladderList) {
        this.ladderList = ladderList;
    }

    public Map<String, Integer> getPlayersCurrentPositions() {
        return playersCurrentPositions;
    }

    public void setPlayersCurrentPositions(Map<String, Integer> playersCurrentPositions) {
        this.playersCurrentPositions = playersCurrentPositions;
    }
}
