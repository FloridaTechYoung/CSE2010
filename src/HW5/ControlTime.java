
public class ControlTime {
	
	public int controlTime(int time) { // 101922
		
		if (time%100 > 24) {
			time = time + 100 - 24;
		}
		
		if ((time%10000)/100 > 31) {
			time = time + 10000 - 3100;
		}
		
		
		
		return time;
	}
}