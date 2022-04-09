import psutil

class Node():
    def __init__(self, parent=None, position=None):
        self.parent = parent
        self.position = position

        self.g = 0
        self.h = 0
        self.f = 0

    def __eq__(self, o):
        return self.position == o.position


def astar(maze, start, end):
    start_node = Node(None, start)
    start_node.g = start_node.h = start_node.f = 0
    end_node = Node(None, end)
    end_node.g = end_node.h = end_node.f = 0
    open_list = []
    closed_list = []
    open_list.append(start_node)

    while(len(open_list) > 0):
        current_node = open_list[0]
        current_index = 0
        for index, item in enumerate(open_list):
            if item.f < current_node.f:
                current_node = item
                current_index = index
        open_list.pop(current_index)
        closed_list.append(current_node)
        if current_node == end_node:
            path = []
            current = current_node
            while current is not None:
                path.append(current.position)
                current = current.parent
            return path[::-1]
        children = []
        all_positions = [(-1, -1), (-1, 0), (-1, 1), (0, -1),
                         (0, 0), (0, 1), (1, -1), (1, 0), (1, 1)]
        all_positions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
        for new_position in all_positions:
            node_position = (
                current_node.position[0] + new_position[0], current_node.position[1] + new_position[1])
            x_valid = node_position[0] > (
                len(maze) - 1) or node_position[0] < 0
            y_valid = node_position[1] > (
                len(maze[len(maze) - 1]) - 1) or node_position[1] < 0
            if x_valid or y_valid:
                continue
            if maze[node_position[0]][node_position[1]] != 0:
                continue
            new_node = Node(current_node, node_position)
            children.append(new_node)
        for child in children:
            for closed_child in closed_list:
                if child == closed_child:
                    continue
            child.g = current_node.g + 1
            child.h = ((child.position[0] - end_node.position[0]) **
                       2) + ((child.position[1] - end_node.position[1]) ** 2)
            child.f = child.g + child.h
            for open_node in open_list:
                if child == open_node and child.g > open_node.g:
                    continue
            open_list.append(child)

        print('RAM memory % used:', psutil.virtual_memory()[2])


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

    '''
    for m in maze:
        print(m)
    maze = [
        [0, 1, 0, 0, 0, 0],
        [1, 0, 1, 0, 1, 0],
        [0, 1, 0, 0, 0, 1],
        [0, 0, 0, 0, 1, 0],
        [0, 1, 0, 1, 0, 0],
        [0, 0, 1, 0, 0, 0]
    ]
    start = (0, 0)
    end = (5, 5)
    '''

    path = astar(maze, start, end)
    print(path)


main()
