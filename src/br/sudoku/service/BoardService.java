package br.sudoku.service;

import br.sudoku.model.Board;
import br.sudoku.model.GameStatusEnum;
import br.sudoku.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




public class BoardService {

    private final static int Board_LIMIT = 9;

    private Board board;

    public BoardService(final Map<String, String> gameConfig) {
        this.board = new Board(initBoard(gameConfig));
    }

    public List<List<Space>> getSpace() {
        return this.board.getSpaces();
    }

    public void reset(){
        this.board.reset();
    }

    public boolean hasErros(){
        return this.board.hasErros();
    }

    public GameStatusEnum getGameStatus(){
        return this.board.getGameStatus();
    }

    public boolean gameIsFinished(){
        return this.board.gameIsFinished();
    }

    private List<List<Space>> initBoard(final Map<String, String> gameConfig){
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < Board_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < Board_LIMIT; j++) {
                var positionConfig = gameConfig.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space( expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        return spaces;


    }
}
