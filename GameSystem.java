import java.util.HashSet;
import java.util.Set;

/**
 * ゲームの進行・表示を管理するクラス
 * @author 島田 佳幸
 * @version 1.0
 */
public class GameSystem {

    /** ビンゴーカード */
    private BingoCard bc = new BingoCard();

    /** ゲーム継続フラグ */
    private boolean gameLoop = true;

    /** ゲーム回数 */
    private int gameCount = 0;

    /** ビンゴゲームのボールを保管 */
    private Set<Integer> ballNumberList = new HashSet<Integer>();

    /** ビンゴゲームの出たボールを記録 */
    private int ballNumber = -1;

    /** 抽選するボールの値の最小値 */
    private final int ROULETTE_MIN = 1;

    /** 抽選するボールの値の最大値 */
    private final int ROULETTE_MAX = 75;

    /** 終了条件（すべてビンゴで終了） */
    private final int CLEAR_BINGO_COUNT = 12;

    /** ゲームの進行 */
    public void gamePlay() {
        createCard();
        while (gameLoop) {
            gameCount++;
            ballRoulette();
            bc.markCount();
            gameVisible();
            endJudge();
        }
    }

    /** ビンゴカードの生成 */
    private void createCard() {
        bc.createCard();
    }

    /** ボールの抽選 */
    private void ballRoulette() {
        boolean rouletteSuccess = false;

        while (!rouletteSuccess) {
            ballNumber = RandomGenerator.rdm(ROULETTE_MIN, ROULETTE_MAX);
            rouletteSuccess = ballNumberList.add(ballNumber);
        }

        bc.numberSearch(ballNumber);

    }

    /** ゲームの表示処理 */
    private void gameVisible() {
        System.out.println("\n--------------------------------------------------------");
        System.out.println(gameCount + "回目");
        System.out.println("今回の数字:" + ballNumber + "\n");
        for (int row = 0; row < bc.getLength(); row++) {
            for (int column = 0; column < bc.getLength(); column++) {
                cardVisible(row, column);
            }
            System.out.print("\n");
        }
        System.out.println("\nビンゴ数:" + bc.getBingoCount());
        System.out.println("リーチ数:" + bc.getReachCount() + "\n");

    }

    /**
     * ビンゴカードの表示
     * 
     * @param row    行
     * @param column 列
     */
    private void cardVisible(int row, int column) {
        if (bc.getBingoCellFree(row, column)) {
            System.out.print("【FREE】");
        } else if (bc.getBingoCellMarked(row, column)) {
            int number = bc.getBingoCellNumber(row, column);
            System.out.print("【" + number + "】\t");
        } else {
            int number = bc.getBingoCellNumber(row, column);
            System.out.print(number + "\t");
        }
    }

    /** 終了判定 */
    private void endJudge() {
        if (bc.getBingoCount() >= CLEAR_BINGO_COUNT) {
            gameLoop = false;
        }
    }

}
