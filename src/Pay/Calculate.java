package Pay;

//TODO 221217 03:52 메소드 내용 간결하게 변경

public class Calculate {
    public static String calculateTime(long diffMin) {
        int sec = (int) diffMin*60;
        int min = sec/60;
        int hour = min/60;
        sec = sec%60;
        min = min%60;

        String HOURS = String.format("%02d", hour)+":"+String.format("%02d", min)+":"+String.format("%02d", sec);
        return HOURS;
    }
    public static int calculateFee(long min) {
        int TotalFee = (int) (min / 10 * 500);

        return TotalFee;
    }
}