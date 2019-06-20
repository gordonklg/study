import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private SearchNode node;

    private SearchNode twinNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial board is null!");
        }

        node = new SearchNode(initial, null);
        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(node);

        twinNode = new SearchNode(initial.twin(), null);
        MinPQ<SearchNode> twinPq = new MinPQ<>();
        twinPq.insert(twinNode);

        while (true) {
            node = pq.delMin();
            if (node.board.isGoal()) break;
            for (Board neighbor : node.board.neighbors()) {
                if (node.predecessor == null || !neighbor.equals(node.predecessor.board)) {
                    pq.insert(new SearchNode(neighbor, node));
                }
            }

            twinNode = twinPq.delMin();
            if (twinNode.board.isGoal()) break;
            for (Board neighbor : twinNode.board.neighbors()) {
                if (twinNode.predecessor == null || !neighbor.equals(twinNode.predecessor.board)) {
                    twinPq.insert(new SearchNode(neighbor, twinNode));
                }
            }
        }
    }

    // is the initial board solvable?

    public boolean isSolvable() {
        return node.board.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return node.moves;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            Stack<Board> result = new Stack<>();
            SearchNode p = node;
            while (p != null) {
                result.push(p.board);
                p = p.predecessor;
            }
            return result;
        } else {
            return null;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
//        In in = new In(ClassLoader.getSystemResource("algs4/heap/8puzzle/puzzle2x2-unsolvable1.txt"));
        In in = new In(ClassLoader.getSystemResource("algs4/heap/8puzzle/puzzle05.txt"));
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class SearchNode implements Comparable<SearchNode> {

        private final Board board;

        private final int moves;

        private final SearchNode predecessor;

        private final int priority;

        public SearchNode(Board board, SearchNode predecessor) {
            this.board = board;
            this.predecessor = predecessor;
            if (predecessor == null) {
                moves = 0;
            } else {
                moves = predecessor.moves + 1;
            }
            this.priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(SearchNode other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

}