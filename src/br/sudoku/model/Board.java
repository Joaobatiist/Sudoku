package br.sudoku.model;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {

    private final List<List<Space>> spaces;

    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getGameStatus() {
        if(spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActualValue()))){
            return GameStatusEnum.NON_STAR;
        }
        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActualValue())) ? GameStatusEnum.INCOMPLETE : GameStatusEnum.COMPLETE;
    }


    public boolean hasErros(){
        if(getGameStatus() == GameStatusEnum.NON_STAR){
            return false;
        }

        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> nonNull(s.getActualValue()) && !s.getActualValue().equals(s.getExpectedValue()));
    }

    public boolean changeValeu(final int col, final int row, final Integer value){
        var space = spaces.get(col).get(row);
        if (space.isFixed()){
            return false;
        }
        space.setActualValue(value);
        return true;
    }

    public boolean clearValeu(final int col, final int row){
        var space = spaces.get(col).get(row);
        if (space.isFixed()){
            return false;
        }
        space.clearActualValue();
        return true;
    }

    public void reset(){
        spaces.forEach(c -> c.forEach(Space::clearActualValue));
    }

    public boolean gameIsFinished(){
        return !hasErros() && getGameStatus() == GameStatusEnum.COMPLETE;
    }
}
