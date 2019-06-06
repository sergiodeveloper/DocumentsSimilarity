package documentssimilarity;

public class ArrayUtils {

	private ArrayUtils() {
	}

	public static double euclidianDistance(final double[] v1, final double[] v2) {
		if (v1.length != v2.length) {
			throw new IllegalArgumentException();
		}
		double accumulated = 0;
		for (int i = 0; i < v1.length; i++) {
			accumulated += Math.pow(v2[i] - v1[i], 2);
		}
		return Math.sqrt(accumulated);
	}

}
