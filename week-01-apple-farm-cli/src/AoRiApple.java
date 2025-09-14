public class AoRiApple extends AppleTree {
    AoRiApple(String id, int ageMonths) {
        super(id, Variety.아오리사과나무, ageMonths);
        // ageMonth 는 어떻게 할지 나중에 생각
    }
    // 아오리 사과의 성장 특징 : 여름 사과 -> 열매 빨리 맺고, 빨리 먹는다.
    // 열매 개수 증가 속도 빠르게 (하루에 +2 이상)
    // 익음도 상승 뻐르게 (+ 0.3)

    @Override
    public void grow(int days) {
        super.grow(days);

        // 아오리 사과의 빨리 자라는 특징
        int appleIncrease = 7 * days;
        double ripenessIncrease = 0.09 * days;

        setAppleCount(getAppleCount() + appleIncrease);
        setRipeness(getRipeness() + ripenessIncrease);

        String logMsg = String.format(
                "[INFO] [%s] %s가(이) 성장했습니다. (익음도: %.1f%%, 열매 수: %d)",
                this.getCropId(),
                getVariety().getDisplayName(),
                this.getRipeness() * 100,
                this.getAppleCount()
        );
        System.out.println("\u001B[37m" + logMsg + "\u001B[0m");
    }

    @Override
    public int harvest() {
        if (getAppleCount() >= 10 && getRipeness() >= 0.5) {

            int harvested = getAppleCount();
            setAppleCount(0);
            setRipeness(0);
            // 초록색 문구 출력
            System.out.println("\u001B[32m[Aori] " + getVariety().getDisplayName() +
                    "에서 " + harvested + "개를 수확했습니다!\u001B[0m");

            return harvested;
        } else {
            // 경고 색상 (노란색)
            System.out.println("\u001B[33m아직 수확할 수 없습니다.\u001B[0m");
            return 0;
        }
    }

    @Override
    public double checkGrowth() {
        // 아오리사과 성장 속도에 나이,  수분량, 비료 반영
        double growth = 0.0;

        growth += getAgeMonths() * 0.5;
        growth += getWaterLevel() * 0.3;

        switch (getFertilizerType()) {
            case BALANCED:
                growth += 1.0;
                break;
            case POTASSIUM:
                growth += 1.5;
                break;
            case ORGANIC:
                growth += 1.3;
                break;
        }

        if (growth > 10.0) {
            growth = 10.0;
        }

        return growth;

    }
}
