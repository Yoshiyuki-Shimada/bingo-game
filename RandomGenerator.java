
import java.util.Random;

/**
 * 乱数を発生させるクラス
 * 
 * @version 1.0
 * @author 島田佳幸
 */
public class RandomGenerator {

	/**
	 * 0から指定した数値（未満）の範囲内で乱数を発生させるメソッド
	 * 
	 * @param max 乱数の最大値（未満）
	 * @return 乱数の結果
	 */
	public static int rdm(int max) {

		return new Random().nextInt(max);

	}

	/**
	 * 指定した数値の範囲内で乱数を発生させるメソッド
	 * 
	 * @param min 乱数の最小値（以上）
	 * @param max 乱数の最大値（以下）
	 * @return 乱数の結果
	 */
	public static int rdm(int min, int max) {

		if (max <= min) {

			throw new IllegalArgumentException("乱数の最小値・最大値の値が正しくありません。");

		}
		max = max - min;
		max++;
		return new Random().nextInt(max) + min;

	}

}