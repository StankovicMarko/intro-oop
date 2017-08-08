package filmovi;

public enum Medijum {
	VHS, // 1
	DVD,  // 2
	BlueRay; //3
	
	public static Medijum fromInt(int a) {
		switch (a) {
		case 1:
			return VHS;
		case 2:
			return DVD;
		default:
			return BlueRay;
		}
	}
	
	public static int toInt(Medijum medijum) {
		switch (medijum) {
		case VHS:
			return 1;
		case DVD:
			return 2;
		default:
			return 3;
		}
	}
	
}
