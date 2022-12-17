package Pay;

//TODO 221217 03:52 메소드 내용 간결하게 변경

public class Calculate {
    public static String calculateTime(long min) {
        int time = (int) min;
        int hour = time / (60 * 60);
        int minute = time / 60 - (hour * 60);
        int second = time % 60;
        String hours = hour + ":" + minute + ":" + second;
        //System.out.println(time + "초는 " + hour + "시간, " + minute + "분, " + second + "초입니다." + hours);

        return hours;
    }
    public static String calculateFee(long min) {
        int TotalFee = (int) (min / 10 * 500);

        return String.valueOf(TotalFee);
    }
}