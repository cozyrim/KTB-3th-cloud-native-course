public class GoldenApple extends AppleTree {
    GoldenApple(String id, int ageMonths) {
        super(id, Variety.황금사과나무, ageMonths);
    }
    // 황금 사과 성장 특징 : 더디지만 단단하게 자라는 품종 -> 늦게 자라지만 더 오래 두면 가치 있음)
    // 열매 개수 증가 속도 느리게
    // 익음도 상승 느리게
    // 물 비료 효과는 동일하게 반영


    @Override
    public void grow(int days) {
        super.grow(days);

        // 황금 사과 자라는 특징

        double appleIncrease = 5 * days;
        double ripenessIncrease = 0.05 * days;

        setAppleCount(getAppleCount() + (int)appleIncrease);
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
        if (getAppleCount() >= 10 && getRipeness() >= 0.7) {
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
        double growth = 0.0;

        growth += getAgeMonths() * 0.6 ;
        growth += getWaterLevel() * 0.1;

        switch (getFertilizerType()) {
            case BALANCED:
                growth += 0.8;
                break;
            case POTASSIUM:
                growth += 1.3;
                break;
            case ORGANIC:
                growth += 1.1;
                break;
        }

        if (growth > 10.0) {
            growth = 10.0;
        }

        return growth;
    }
}