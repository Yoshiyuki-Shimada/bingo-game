import java.util.Set;
import java.util.HashSet;

/**
 * ビンゴカードの処理を行うクラス
 * 
 * @author 島田 佳幸
 * @version 1.0
 */
public class BingoCard {

    /** ビンゴカードの二次元配列 */
    BingoCell[][] bingoCard = new BingoCell[5][5];

    /** ビンゴカードに格納した数字を保管する配列（重複判定用） */
    private Set<Integer> bingoCardNumberList = new HashSet<Integer>();

    /** カードの乱数指定の計算ルール */
    private final int RANGE = 15;

    /** FREEの場所指定（行） */
    private final int FREE_ROW = 2;

    /** FREEの場所指定（列） */
    private final int FREE_COLUMN = 2;

    /** ビンゴ数 */
    private int bingoCount = 0;

    /** リーチ数 */
    private int reachCount = 0;

    /** ビンゴのマーク条件数 */
    private final int BINGO = 5;

    /** リーチのマーク条件数 */
    private final int REACH = 4;

    /** カードの生成の行列指定 */
    public void createCard() {
        bingoCardNumberList.clear();
        for (int column = 0; column < bingoCard[0].length; column++) {
            for (int row = 0; row < bingoCard.length; row++) {
                createCell(row, column);
            }
        }
    }

    /**
     * セルの判定処理（FREEの箇所には数字ではなくFREEを代入）
     * 
     * @param row    行
     * @param column 列
     */
    private void createCell(int row, int column) {

        if (row == FREE_ROW && column == FREE_COLUMN) {
            bingoCard[row][column] = new BingoCell();
        } else {
            createCellNumber(row, column);
        }

    }

    /**
     * FREE以外のセルの数字代入処理
     * 
     * @param row    行
     * @param column 列
     */
    private void createCellNumber(int row, int column) {

        // 列ごとの乱数の出現数字範囲の指定
        int min = (column * RANGE) + 1;
        int max = (column * RANGE) + 15;

        boolean createSuccess = false;
        int createNumber = -1;

        while (!createSuccess) {
            createNumber = RandomGenerator.rdm(min, max);
            createSuccess = bingoCardNumberList.add(createNumber);
        }

        bingoCard[row][column] = new BingoCell(createNumber);

    }

    /**
     * 該当の番号を探索し、マークを行う（穴を開ける）
     * 
     * @param number 探索する番号
     */
    public void numberSearch(int number) {
        for (int row = 0; row < bingoCard.length; row++) {
            for (int column = 0; column < bingoCard[row].length; column++) {
                BingoCell cell = bingoCard[row][column];
                if (number == cell.getNumber()) {
                    cell.setMark();
                    return;
                }
            }
        }
    }

    /** ビンゴ数・リーチ数のカウント */
    public void markCount() {
        bingoCount = 0;
        reachCount = 0;
        rowMarkCount();
        columnMarkCount();
        diagonalMarkCount();
    }

    /** 行方向のビンゴ数・リーチ数のカウント */
    private void rowMarkCount() {

        for (int row = 0; row < bingoCard.length; row++) {
            int markedCount = 0;
            for (int column = 0; column < bingoCard[row].length; column++) {
                BingoCell cell = bingoCard[row][column];
                if (cell.getMarked()) {
                    markedCount++;
                }
            }
            countUp(markedCount);

        }
    }

    /** 列方向のビンゴ数・リーチ数のカウント */
    private void columnMarkCount() {

        for (int column = 0; column < bingoCard[0].length; column++) {
            int markedCount = 0;
            for (int row = 0; row < bingoCard.length; row++) {
                BingoCell cell = bingoCard[row][column];
                if (cell.getMarked()) {
                    markedCount++;
                }
            }
            countUp(markedCount);

        }
    }

    /** 斜め方向のビンゴ数・リーチ数のカウント */
    private void diagonalMarkCount() {
        int markedCount = 0;
        int size = bingoCard.length;
        for (int index = 0; index < size; index++) {
            BingoCell cell = bingoCard[index][index];
            if (cell.getMarked()) {
                markedCount++;
            }
        }

        countUp(markedCount);

        markedCount = 0;

        for (int row = 0; row < size; row++) {
            BingoCell cell = bingoCard[row][(size - 1) - row];
            if (cell.getMarked()) {
                markedCount++;
            }
        }

        countUp(markedCount);

    }

    /**
     * ビンゴ数・リーチ数のカウントアップ
     * 
     * @param markedCount マーク数（穴が開いている数）
     */
    private void countUp(int markedCount) {
        if (markedCount == BINGO) {
            bingoCount++;
        } else if (markedCount == REACH) {
            reachCount++;
        }
    }

    /**
     * ビンゴ数を返す
     * 
     * @return ビンゴ数
     */
    public int getBingoCount() {
        return bingoCount;
    }

    /**
     * リーチ数を返す
     * 
     * @return
     */
    public int getReachCount() {
        return reachCount;
    }

    /**
     * 指定されたセルの数字を返す
     * 
     * @param row    行
     * @param column 列
     * @return 数字
     */
    public int getBingoCellNumber(int row, int column) {
        BingoCell cell = bingoCard[row][column];
        return cell.getNumber();
    }

    /**
     * 指定されたセルがFREEのセルか否かを返す
     * 
     * @param row    行
     * @param column 列
     * @return FREEセルのフラグ
     */
    public boolean getBingoCellFree(int row, int column) {
        BingoCell cell = bingoCard[row][column];
        return cell.getFree();
    }

    /**
     * 指定されたセルが穴が開いているか否かを返す
     * 
     * @param row    行
     * @param column 列
     * @return 穴が開いているか否かのフラグ
     */
    public boolean getBingoCellMarked(int row, int column) {
        BingoCell cell = bingoCard[row][column];
        return cell.getMarked();
    }

    /**
     * ビンゴカードの配列の長さを返す
     * 
     * @return ビンゴカードの配列の長さ
     */
    public int getLength() {
        return bingoCard.length;
    }

}
