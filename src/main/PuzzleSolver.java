package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
                while (puzzleMap.isValidCoordinate(coordinate.getX(), coordinate.getY()) && !puzzleMap.isVisited(coordinate.getX(), coordinate.getY()) && puzzleMap.isIce(coordinate.getX(), coordinate.getY())) {
                    coordinate = new PuzzleCoordinate(coordinate.getX() + direction[0], coordinate.getY() + direction[1], cur);
                    nextToVisit.add(coordinate);
                    puzzleMap.setVisited(cur.getX(), cur.getY(), true);
                }
            }
        }
        return Collections.emptyList();
    }

    public PuzzleCoordinate travelSelectedDirection(PuzzleMap puzzleMap, PuzzleCoordinate cur, int[] direction) {
        PuzzleCoordinate newPoint = new PuzzleCoordinate(cur.getX() + direction[0], cur.getY() + direction[1], cur);
        System.out.println(newPoint);
        if (direction[0] == 0 && direction[1] == 1) {
            while (canTravelTopDirection(puzzleMap, newPoint)) {
                newPoint = new PuzzleCoordinate(newPoint.getX() + direction[0], newPoint.getY() + direction[1], newPoint);
            }
        }
        if (direction[0] == 1 && direction[1] == 0) {
            while (canTravelRightDirection(puzzleMap, newPoint)) {
                newPoint = new PuzzleCoordinate(newPoint.getX() + direction[0], newPoint.getY() + direction[1], newPoint);
            }
        }
        if (direction[0] == 0 && direction[1] == -1) {
            while (canTravelDownDirection(puzzleMap, newPoint)) {
                newPoint = new PuzzleCoordinate(newPoint.getX() + direction[0], newPoint.getY() + direction[1], newPoint);
            }
        }
        if (direction[0] == -1 && direction[1] == 0) {
            while (canTravelLeftDirection(puzzleMap, newPoint)) {
                newPoint = new PuzzleCoordinate(newPoint.getX() + direction[0], newPoint.getY() + direction[1], newPoint);
            }
        }
        return newPoint;
    }

    public boolean canTravelTopDirection(PuzzleMap puzzleMap, PuzzleCoordinate point) {
        if (point.getY() == 0) return false;
        return puzzleMap.isValidCoordinate(point.getX(), point.getY()) && !puzzleMap.isRock(point.getX(), point.getY());
    }

    public boolean canTravelRightDirection(PuzzleMap puzzleMap, PuzzleCoordinate point) {
        if (point.getX() == puzzleMap.getColumns() - 1) return false;
        return puzzleMap.isValidCoordinate(point.getX(), point.getY()) && !puzzleMap.isRock(point.getX(), point.getY());
    }

    public boolean canTravelDownDirection(PuzzleMap puzzleMap, PuzzleCoordinate point) {
        if (point.getY() == puzzleMap.getRows() - 1) return false;
        return puzzleMap.isValidCoordinate(point.getX(), point.getY()) && !puzzleMap.isRock(point.getX(), point.getY());
    }

    public boolean canTravelLeftDirection(PuzzleMap puzzleMap, PuzzleCoordinate point) {
        if (point.getX() == 0) return false;
        return puzzleMap.isValidCoordinate(point.getX(), point.getY()) && !puzzleMap.isRock(point.getX(), point.getY());
    }

}
