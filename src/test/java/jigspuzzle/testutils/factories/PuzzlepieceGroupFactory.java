package jigspuzzle.testutils.factories;

import jigspuzzle.model.puzzle.Puzzle;

class PuzzlepieceGroupFactory extends AbstractFactory {

    @Override
    Object createObject() throws Exception {
        Puzzle puzzle = (Puzzle) FactorySlave.build(Puzzle.class).create();
        return puzzle.getPuzzlepieceGroups().get(0);
    }

}
