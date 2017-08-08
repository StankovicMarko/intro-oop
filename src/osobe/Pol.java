package osobe;

public enum Pol {
	ZENSKI, // 1
	MUSKI;  // 2
	
	public static Pol fromInt(int a) {
		switch (a) {
		case 1:
			return ZENSKI;
		default:
			return MUSKI;
		}
	}
	
	public static int toInt(Pol pol) {
		switch (pol) {
		case ZENSKI:
			return 1;
		default:
			return 2;
		}
	}
}