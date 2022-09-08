package com.juju.sudoku.layer.data.object;

public class GameLevel {
    public static final String key = "GameLevel";

    public enum Value {
        EASY, MEDIUM, HARD; //TODO RENAME

        private static Value[] valArray = values();
        public Value next()
        {
            return valArray[(this.ordinal()+1) % valArray.length];
        }
    }

    private GameLevel() {};
}
