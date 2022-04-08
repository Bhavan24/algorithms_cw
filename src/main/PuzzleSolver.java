package main;

import java.util.*;

import static main.GameConstants.DIRECTIONS;

public class PuzzleSolver {

    public List<PuzzleCoordinate> solve(PuzzleMap puzzleMap) {

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

            for (int[] direction : DIRECTIONS) {
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

    public List<PuzzleCoordinate> solveAStar(PuzzleMap puzzleMap) {
        LinkedList<PuzzleCoordinate> list = new LinkedList<>();
        /*
         1. Create Graph
            Graph data structure ahouls be used, integrated with coordinates
            should maintain a stack / heap for that
            if it is final path need to say path is there
         2. Send the created graph to an algorithm and get the result
         */

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

            for (int[] direction : DIRECTIONS) {
                PuzzleCoordinate coordinate = new PuzzleCoordinate(cur.getX() + direction[0], cur.getY() + direction[1], cur);
                while (canTravel(puzzleMap, coordinate)) {
                    coordinate = new PuzzleCoordinate(coordinate.getX() + direction[0], coordinate.getY() + direction[1], coordinate);
                }
                nextToVisit.add(coordinate);
                puzzleMap.setVisited(cur.getX(), cur.getY(), true);
            }

        }
        return Collections.emptyList();
    }

    public boolean canTravel(PuzzleMap puzzleMap, PuzzleCoordinate coordinate) {
        return puzzleMap.isValidCoordinate(coordinate.getX(), coordinate.getY()) && !puzzleMap.isRock(coordinate.getX(), coordinate.getY());
    }

}
