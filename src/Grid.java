public class Grid {
    // 4 * 4 matrix
    private int max = 0;
    private int row;
    private int col;
    private int[][] elements;

    enum DIRECTION {
        UP, DOWN, LEFT, RIGHT
    }

    public Grid(int row, int col) {
        this.elements = new int[row][col];
        this.row = row;
        this.col = col;
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // random generates 0 - 1
                int rand = 2 * (int)(Math.random() * 2);
                this.elements[i][j] = rand;
            }
        }
    };

    public void printGrid() {
        System.out.print("\n");
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                System.out.print(this.elements[i][j] + "     ");
            }
            System.out.print("\n");
        }
    }

    // not random enough
    private void addRandomElement() {
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // random generates 0 - 1
                if (this.elements[i][j] == 0) {
                    int rand = 2 * (int)(Math.random() * 2) + 2;
                    this.elements[i][j] = rand;
                    return;
                }
            }
        }
    }

    public void move(DIRECTION direction) {
        switch (direction) {
            case UP:
                this.moveUp();
                this.mergeUp();
                this.moveUp();
                break;
            case DOWN:
                this.moveDown();
                this.mergeDown();
                this.moveDown();
                break;
            case LEFT:
                this.moveDown();
                this.mergeLeft();
                this.moveDown();
                break;
            case RIGHT:
                this.moveRight();
                this.mergeRight();
                this.moveRight();
                break;
            default:
                System.out.println("Invalid Direction");
        }
        this.cleanUpGrid();
        this.addRandomElement();

        if (this.isFull() || this.isWinner()) {
            System.out.println("Game Over!");
        }
    }

    private void cleanUpGrid() {
        for(int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (this.elements[i][j] == -1) {
                    this.elements[i][j] = 0;
                }
            }
        }
    }

    // Every element can only move once and merge once
    // Move to erase 0 then flagging
    // Flag the cell -1 if merged once already
    private void moveUp() {
        // to move:
        for(int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (this.elements[j][i] != 0) {
                    int temp = j - 1;
                    int cur = 0;

                    while (temp >= 0 && (elements[temp][i] == 0 || elements[temp][i] == -1)) {
                        if (this.elements[j][i] != 0) {
                            cur = this.elements[j][i];
                        }

                        this.elements[temp][i] = cur;
                        this.elements[temp + 1][i] = 0;
                        temp --;
                        // printGrid();
                    }
                }
            }
        }
    }

    private void mergeLeft() {
        for (int i = 0; i < row; i++) {
            this.elements[i] = this.mergeRowLeft(this.elements[i]);
        }
    }

    private void mergeRight() {
        for (int i = 0; i < row; i++) {
            this.elements[i] = this.mergeRowRight(this.elements[i]);
        }
    }

    private int[] mergeRowRight(int[] element) {
        for (int j = col - 2; j >= 0; j--) {
            if (element[j] != 0 && element[j] == element[j + 1]) {
                int temp = 2 * element[j];
                element[j + 1] = temp;
                element[j] = -1;
            }
        }
        return element;
    }

    private int[] mergeRowLeft(int[] element) {
        for (int j = 1; j < col; j++) {
            if (element[j] != 0 && element[j] == element[j - 1]) {
                int temp = 2 * element[j];
                element[j - 1] = temp;
                element[j] = -1;
            }

        }
        return element;
    }

    private void mergeUp() {
        for (int i = 1; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (this.elements[i][j] != 0 && this.elements[i][j] == this.elements[i - 1][j]) {
                    int temp = 2 * this.elements[i][j];
                    this.elements[i - 1][j] = temp;
                    this.elements[i][j] = -1;
                }
            }
        }
    }

    private void mergeDown() {
        for (int i = col - 2; i >=0; i--) {
            for (int j = row - 1; j >= 0; j--) {
                if (this.elements[i][j] != 0 && this.elements[i][j] == this.elements[i + 1][j]) {
                    int temp = 2 * this.elements[i][j];
                    this.elements[i + 1][j] = temp;
                    this.elements[i][j] = -1;
                }
            }
        }

    }

    private void moveDown() {
        // to move:
        for(int i = 0; i < col; i++) {
            for (int j = row - 1; j >= 0; j--) {
                if (this.elements[j][i] != 0) {
                    int temp = j + 1;
                    int cur = 0;

                    while (temp < row && (elements[temp][i] == 0 || elements[temp][i] == -1)) {
                        if (this.elements[j][i] != 0) {
                            cur = this.elements[j][i];
                        }

                        this.elements[temp][i] = cur;
                        this.elements[temp - 1][i] = 0;
                        temp ++;
                        // printGrid();
                    }
                }
            }
        }
    }

    // Edge case: 2 2 4 2 --> move left --> 4 4 2 0
    private void moveLeft() {
        // to move
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (this.elements[i][j] != 0) {
                    int temp = j - 1;
                    int cur = 0;
                    while (temp >= 0 && (elements[i][temp] == 0 || elements[i][temp] == -1)) {
                        if (this.elements[i][j] != 0) {
                            cur = this.elements[i][j];
                        }

                        this.elements[i][temp] = cur;
                        this.elements[i][temp + 1] = 0;
                        temp --;
                        // printGrid();
                    }
                }
            }
        }
    }

    private void moveRight() {
        // to move
        for(int i = 0; i < row; i++) {
            for (int j = col - 1; j >= 0; j--) {
                if (this.elements[i][j] != 0) {
                    int temp = j + 1;
                    int cur = 0;

                    while (temp < col && (elements[i][temp] == 0 || elements[i][temp] == -1)) {
                        if (this.elements[i][j] != 0) {
                            cur = this.elements[i][j];
                        }

                        this.elements[i][temp] = cur;
                        this.elements[i][temp - 1] = 0;
                        temp ++;
                        // printGrid();
                    }
                }
            }
        }
    }

    public boolean isFull() {
        for(int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                boolean isFull = this.elements[i][j] > 0;
                if (!isFull) return false;
            }
        }
        return true;
    }

    public boolean isWinner() {
        for(int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                boolean isWinner = this.elements[i][j] > 2048;
                if (isWinner) return true;
            }
        }
        return false;
    }




}
