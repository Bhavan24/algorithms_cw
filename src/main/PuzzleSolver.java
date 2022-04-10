package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class PuzzleSolver {

    public List<PuzzleCoordinate> solve(PuzzleMap puzzleMap, int[][] directions) {

        LinkedList<PuzzleCoordinate> nextToVisit = new LinkedList<>();
        PuzzleCoordinate start = puzzleMap.getStart();
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
            PuzzleCoordinate cur = nextToVisit.remove();

            if (!puzzleMap.isValidCoordinate(cur.getX(), cur.getY()) || puzzleMap.isVisited(cur.getX(), cur.getY())) {
                continue;
            }

            if (puzzleMap.isRock(cur.getX(), cur.getY())) {
                puzzleMap.setVisited(cur.getX(), cur.getY(), true);
                continue;
            }

            if (puzzleMap.isEnd(cur.getX(), cur.getY())) {
                return backtrackPath(cur);
            }

            for (int[] direction : directions) {
                PuzzleCoordinate coordinate = new PuzzleCoordinate(cur.getX() + direction[0], cur.getY() + direction[1], cur);
                nextToVisit.add(coordinate);
                puzzleMap.setVisited(cur.getX(), cur.getY(), true);
            }
        }
        return Collections.emptyList();
    }

    private List<PuzzleCoordinate> backtrackPath(PuzzleCoordinate cur) {
        List<PuzzleCoordinate> path = new ArrayList<>();
        PuzzleCoordinate iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.parent;
        }

        return path;
    }

}
