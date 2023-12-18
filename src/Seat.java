
public class Seat {
    private int rowNum;
    private int columnNum;
    private boolean isOccupied;
    public Seat() {
    }
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }
    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
    public int getRowNum() {
        return this.rowNum;
    }
    public int getColumnNum() {
        return this.columnNum;
    }
    public boolean getIsOccupied() {
        return isOccupied;
    }
}
