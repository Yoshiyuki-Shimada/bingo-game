/**
 * セルの数字・マーク状況・FREEセルの有無を管理するクラス
 * 
 * @author 島田 佳幸
 * @version 1.0
 */
public class BingoCell {

    /** BINGOカードの数字 */
    private int number = -1;

    /** 穴が開いたか否かを管理するフラグ */
    private boolean marked = false;

    /** FREE部分を管理するフラグ */
    private boolean free = false;

    /**
     * セルの数字初期化
     * 
     * @param number 数字
     */
    public BingoCell(int number) {
        this.number = number;
        marked = false;
    }

    /** セルのFREE部分の指定 */
    public BingoCell() {
        free = true;
        marked = true;
    }

    /** 穴が開いたのをマークする */
    public void setMark() {
        marked = true;
    }

    /**
     * 穴が開いたか否かを返す
     * 
     * @return 穴が開いているか否かのフラグ
     */
    public boolean getMarked() {
        return marked;
    }

    /**
     * FREEか否かを返す
     * 
     * @return FREEセルのフラグ
     */
    public boolean getFree() {
        return free;
    }

    /**
     * セルの数字を返す
     * 
     * @return 数字
     */
    public int getNumber() {
        return number;
    }

}
