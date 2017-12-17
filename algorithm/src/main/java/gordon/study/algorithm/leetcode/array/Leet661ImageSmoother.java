package gordon.study.algorithm.leetcode.array;

public class Leet661ImageSmoother {

    public static void main(String[] args) {
        Leet661ImageSmoother inst = new Leet661ImageSmoother();
        System.out.println(inst.imageSmoother(new int[][] {{1,1,1},{1,0,1},{1,1,1}}));
        System.out.println(inst.imageSmoother(new int[][] {{41}}));
    }

    int[][] imageSmoother(int[][] M) {
        int xSize = M.length;
        int ySize = M[0].length;
        int[][] result = new int[xSize][ySize];
        int[][] count = new int[xSize][ySize];
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                result[x][y] = M[x][y];
                count[x][y] = 1;
                if (y > 0) {
                    result[x][y] += M[x][y - 1];
                    ++count[x][y];
                    if (x > 0) {
                        result[x][y] += M[x - 1][y - 1];
                        ++count[x][y];
                    }
                    if (x < xSize - 1) {
                        result[x][y] += M[x + 1][y - 1];
                        ++count[x][y];
                    }
                }
                if (y < ySize - 1) {
                    result[x][y] += M[x][y + 1];
                    ++count[x][y];
                    if (x > 0) {
                        result[x][y] += M[x - 1][y + 1];
                        ++count[x][y];
                    }
                    if (x < xSize - 1) {
                        result[x][y] += M[x + 1][y + 1];
                        ++count[x][y];
                    }
                }
                if (x > 0) {
                    result[x][y] += M[x - 1][y];
                    ++count[x][y];
                }
                if (x < xSize - 1) {
                    result[x][y] += M[x + 1][y];
                    ++count[x][y];
                }
            }
        }
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                result[x][y] /= count[x][y];
            }
        }
        return result;
    }
}
