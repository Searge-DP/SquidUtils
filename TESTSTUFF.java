public class Test {

	public static void Init() {
		int A = 0;
		while (A < Item.itemRegistry.size()) {
			item.itemRegistry.get(A).setMaxStackSize(32);
			}
		A++;
		}
	}
}
