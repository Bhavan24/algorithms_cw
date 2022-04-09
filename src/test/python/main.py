# Python program to find the shortest
# path between a given source cell
# to a destination cell.

from collections import deque
ROW = 9
COL = 10

# To store matrix cell coordinates


class Point:
    def __init__(self, x: int, y: int):
        self.x = x
        self.y = y

# A data structure for queue used in BFS


class queueNode:
    def __init__(self, pt: Point, dist: int):
        self.pt = pt  # The coordinates of the cell
        self.dist = dist  # Cell's distance from the source

# Check whether given cell(row,col)
# is a valid cell or not


def isValid(row: int, col: int):
    return (row >= 0) and (row < ROW) and (col >= 0) and (col < COL)


# These arrays are used to get row and column
# numbers of 4 neighbours of a given cell
rowNum = [-1, 0, 0, 1]
colNum = [0, -1, 1, 0]

# Function to find the shortest path between
# a given source cell to a destination cell.


def BFS(mat, src: Point, dest: Point):

    # check source and destination cell
    # of the matrix have value 1
    if mat[src.x][src.y] != 1 or mat[dest.x][dest.y] != 1:
        return -1

    visited = [[False for i in range(COL)]
               for j in range(ROW)]

    # Mark the source cell as visited
    visited[src.x][src.y] = True

    # Create a queue for BFS
    q = deque()

    # Distance of source cell is 0
    s = queueNode(src, 0)
    q.append(s)  # Enqueue source cell

    # Do a BFS starting from source cell
    while q:

        curr = q.popleft()  # Dequeue the front cell

        # If we have reached the destination cell,
        # we are done
        pt = curr.pt
        if pt.x == dest.x and pt.y == dest.y:
            return curr.dist

        # Otherwise enqueue its adjacent cells
        for i in range(4):
            row = pt.x + rowNum[i]
            col = pt.y + colNum[i]

            # if adjacent cell is valid, has path
            # and not visited yet, enqueue it.
            if (isValid(row, col) and
                mat[row][col] == 1 and
                    not visited[row][col]):
                visited[row][col] = True
                Adjcell = queueNode(Point(row, col),
                                    curr.dist+1)
                q.append(Adjcell)

    # Return -1 if destination cannot be reached
    return -1


def read_file(file_path):
    file = open(file_path, "r")
    content = file.read()
    maze = []
    for line in content.split("\n"):
        temp = []
        for i in range(len(line)):
            temp.append(line[i])
        maze.append(temp)
    return maze


def index_2d(myList, v):
    for i, x in enumerate(myList):
        if v in x:
            return (i, x.index(v))

# Driver code


def main():
    lines = read_file("test.txt")

    maze = []

    for line in lines:
        temp_maze = []
        for char in line:
            if(char == '0'):
                temp_maze.append(1)
            elif(char == 'S'):
                temp_maze.append(1)
                start = index_2d(lines, char)
            elif(char == 'F'):
                temp_maze.append(1)
                end = index_2d(lines, char)
            else:
                temp_maze.append(0)
        maze.append(temp_maze)
    source = Point(start[0], start[1])
    dest = Point(end[0], end[1])

    maze = [[1, 0, 1, 1, 1, 1, 0, 1, 1, 1],
            [1, 0, 1, 0, 1, 1, 1, 0, 1, 1],
            [1, 1, 1, 0, 1, 1, 0, 1, 0, 1],
            [0, 0, 0, 0, 1, 0, 0, 0, 0, 1],
            [1, 1, 1, 0, 1, 1, 1, 0, 1, 0],
            [1, 0, 1, 1, 1, 1, 0, 1, 0, 0],
            [1, 0, 0, 0, 0, 0, 0, 0, 0, 1],
            [1, 0, 1, 1, 1, 1, 0, 1, 1, 1],
            [1, 1, 0, 0, 0, 0, 1, 0, 0, 1]]
    source = Point(0, 0)
    dest = Point(3, 4)

    dist = BFS(maze, source, dest)

    if dist != -1:
        print("Shortest Path is", dist)
    else:
        print("Shortest Path doesn't exist")


main()

# This code is contributed by stutipathak31jan
