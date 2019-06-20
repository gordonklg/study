package gordon.study.algorithm.algs4.heap.coursera;

import edu.princeton.cs.algs4.Queue;

public class Board {

    private static final int BLANK = 0;

    private int[][] blocks;

    private final int dim;

    // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.dim = blocks.length;
        this.blocks = new int[dim][dim];
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                this.blocks[row][col] = blocks[row][col];
            }
        }
    }

    // board dimension n
    public int dimension() {
        return dim;
    }

    // number of blocks out of place
    public int hamming() {
        int result = 0;
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                if (blocks[row][col] != BLANK && blocks[row][col] != row * dim + col + 1) {
                    result++;
                }
            }
        }
        return result;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int result = 0;
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                if (blocks[row][col] != BLANK && blocks[row][col] != row * dim + col + 1) {
                    // row: (val-1)/3  column: (val-1)%3
                    result += Math.abs(row - (blocks[row][col] - 1) / dim) + Math.abs(col - (blocks[row][col] - 1) % dim);
                }
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                if (blocks[row][col] != row * dim + col + 1) {
                    return row == dim - 1 && col == dim - 1;
                }
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board twin = new Board(blocks);
        if (twin.blocks[0][0] != BLANK && twin.blocks[0][1] != BLANK) {
            int temp = twin.blocks[0][0];
            twin.blocks[0][0] = twin.blocks[0][1];
            twin.blocks[0][1] = temp;
        } else {
            int temp = twin.blocks[1][0];
            twin.blocks[1][0] = twin.blocks[1][1];
            twin.blocks[1][1] = temp;
        }
        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (!(y instanceof Board)) {
            return false;
        }

        Board other = (Board) y;
        if (other.dimension() != dim) {
            return false;
        }
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                if (blocks[row][col] != other.blocks[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> result = new Queue<>();
        LOOP:
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                if (blocks[row][col] == BLANK) {
                    if (row > 0) {
                        Board neighbor = new Board(blocks);
                        neighbor.blocks[row - 1][col] = BLANK;
                        neighbor.blocks[row][col] = blocks[row - 1][col];
                        result.enqueue(neighbor);
                    }
                    if (row < dim - 1) {
                        Board neighbor = new Board(blocks);
                        neighbor.blocks[row + 1][col] = BLANK;
                        neighbor.blocks[row][col] = blocks[row + 1][col];
                        result.enqueue(neighbor);
                    }
                    if (col > 0) {
                        Board neighbor = new Board(blocks);
                        neighbor.blocks[row][col - 1] = BLANK;
                        neighbor.blocks[row][col] = blocks[row][col - 1];
                        result.enqueue(neighbor);
                    }
                    if (col < dim - 1) {
                        Board neighbor = new Board(blocks);
                        neighbor.blocks[row][col + 1] = BLANK;
                        neighbor.blocks[row][col] = blocks[row][col + 1];
                        result.enqueue(neighbor);
                    }
                    break LOOP;
                }
            }
        }
        return result;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dim + "\n");
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                sb.append(String.format("%2d ", blocks[row][col]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] blocks = new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.isGoal());
        System.out.println(board);
        for (Board b : board.neighbors()) {
            System.out.println(b);
        }
        System.out.println(board.twin());
    }
}