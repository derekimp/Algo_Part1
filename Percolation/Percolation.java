/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF normalQU;
    private WeightedQuickUnionUF backwashQU;
    private boolean[] isOpen;
    private int topIndex;
    private int btmIndex;
    private int n;
    private int openCount;

    public Percolation(int n)
    {
        if (n <= 0 ) throw new IllegalArgumentException("Invalid number of grid!");
        this.n = n;
        topIndex = 0;
        btmIndex = n * n + 1;
        backwashQU = new WeightedQuickUnionUF(n * n + 2);
        normalQU =  new WeightedQuickUnionUF(n * n + 1);
        isOpen = new boolean[n * n + 2];
        isOpen[topIndex] = true;
        isOpen[btmIndex] = true;
    }

    public int indexOf(int row, int col)
    {
        if (row < 1 || row > n)
            throw new IllegalArgumentException("Row is out of bounds.");
        if (col < 1 || col > n)
            throw new IllegalArgumentException("Col is out of bounds.");
        return (row - 1) * n +col;
    }

    public void open(int row, int col) {
        int currIndex = indexOf(row, col);
        isOpen[currIndex] = true;
        openCount++;

        if (row == 1) {
            backwashQU.union(currIndex, topIndex);
            normalQU.union(currIndex, topIndex);
        }

        if (row == n) {
            backwashQU.union(currIndex, btmIndex);
        }

        tryUnion(row, col, row - 1, col);
        tryUnion(row, col, row, col - 1);
        tryUnion(row, col, row, col + 1);
        tryUnion(row, col, row + 1, col);

    }

    private void tryUnion(int rowA, int colA, int rowB, int colB) {
        if (0 < rowB && rowB <= n && 0 < colB && colB <= n && isOpen(rowB, colB)) {
            backwashQU.union(indexOf(rowA, colA), indexOf(rowB, colB));
            normalQU.union(indexOf(rowA, colA), indexOf(rowB, colB));
        }
    }

    public int numberOfOpenSites(){
        return openCount;
    }

    public boolean isOpen(int row, int col) {
        return isOpen[indexOf(row, col)];
    }

    public boolean isFull(int row, int col) {
        return normalQU.connected(topIndex, indexOf(row, col));
    }

    public boolean percolates() {
        return backwashQU.connected(topIndex, btmIndex);
    }

    public static void main(String[] args) {
        StdOut.println("Please run PercolationStats instead.");
    }
}
