# Path-Finding-Algorithm-for-a-Robot

## PROBLEM 
   Find the shortest path to move a rover from the start point to the goal point by avoiding the obstacles. Given that the directions in which the rover can move is only either diagonally left direction or diagonally right direction or leave it straight according to the angle specified (the current angle of the rover).   
    
> NOTE: The angle in which the rover is currently facing places a very important role in creating the rules.
    
## INPUT
   The input is given as a nxm matrix where 1's denote as nodes are not blocked, 0's denote that the nodes are blocked. The starting node is considered as random start node. 
  
## OUTPUT
   The rover reach the goal.
  
## SOLUTION
   A* is the basic algorithm used to find the shortest path. which is stored in an array of size equal to the environment provided.
     
   The IF-THEN rules are basically created based on the direction in which the rover is currently facing. This is decided by considering that at zero degrees the rover is facing downwards. For example, if the rover is facing in the East direction then it is considered that the angle of the rover is 90deg with respect to 0deg. This makes the rover to move either at an angle of 135deg NE or at an angle of 45deg SE or move in the same direction i.e East. This reduces the number of rules to 8.
     
   By using the [Euclidean](http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html) Heuristic Distance, the distance between the current node and the goal node, is calculated and added with the cost of the previous node, to compare the distance obtained and the distance calculated by considering the other two possible nodes. This comparison for every possible node by looking back leads to the shortest path. 
     
   Each node once visited is marked as visited and thus cannot be observed again . If the node does not lead to goal then the path is backtracked. Marking it as visited reduces the time of finding all the possible paths.
     
## A* Algorithm
 
   The [Algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm) is a combination of:
       
   ###### BEST-FIRST-SEARCH
   Given a tree the work is done only on the nodes which are close to the goal. In [BFS](https://en.wikipedia.org/wiki/Best-first_search) the path is formed by taking only the nodes which are close to the goal. The node closest to the goal is found out by finding the total cost or the distance between the current node and the target node. The heuristic approach is taken to find the distance between the nodes.
       
   ###### HEURISTIC APPROACH
   The admissible heauristic distance formula used in the program is Euclidean distance even if it is quite expensive the shortest path obtained is more accurate compared to other distance formulas.
       
   The algorithm also incorporates the Dead Horse Principle which is a combination of [Branch and Bound](https://en.wikipedia.org/wiki/Branch_and_bound) Technique and visited(extended) list where the node already visited if does not lead to the goal, is not visited again and taken out of the path. The Best-First Search also implements the backtracking method of Depth-First-Search for the current node to remember its parent to backtrack if it finds the current node cannot reach the goal.
