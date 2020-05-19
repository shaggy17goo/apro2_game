package Server.Model.Map;


public class GameMap {
    private final int maxX;
    private final int maxY;
    private Field[][] map;//FIRST Y SECOND X
    public GameMap(int maxY, int maxX) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.map = new Field[maxY][maxX];
        //Constructing all fields
        for (int y = 0; y < maxY; y++)
            for (int x = 0; x < maxX; x++)
                this.map[y][x] = new Field(y, x);
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                builder.append(this.map[y][x]); // this appends a 2-char block
                //builder.append(" ");
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
    public int getMaxX() { return maxX; }
    public int getMaxY() { return maxY; }

    public Field[][] getMap() {
        return map;
    }
    public Field getFieldAt(int y, int x){
        return map[y][x];
    }

    public void setField(Field field, int y, int x) {
        this.map[y][x] = field;
    }
}
