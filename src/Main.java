public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid(4,4);
        //while(!grid.isWinner() && !grid.isFull()) {
        grid.printGrid();
        grid.move(Grid.DIRECTION.RIGHT);
        grid.printGrid();
        grid.move(Grid.DIRECTION.UP);
        grid.printGrid();
        grid.move(Grid.DIRECTION.RIGHT);
        grid.printGrid();
        grid.move(Grid.DIRECTION.DOWN);
        grid.printGrid();
        //}

    }
}
