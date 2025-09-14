public class AppleTree extends Crop {
    // 매서드 (grow, harvest, checkGrowth)

    // 품종 이름
    private Variety variety;
    // 나이 (개월 수)
    private int ageMonths;
    // 익음도 ( 0.0-100.0 ** 80 이상일 때 수확 가능)
    private double ripeness;
    // 현재 열린 사과 개수
    private int appleCount;

    // 생성자
    AppleTree(String id, Variety variety, int ageMonths) {
        super(id);
        this.variety = variety;
        this.ageMonths = ageMonths;
        this.ripeness = 0;
        this.appleCount = 0;

    }

    // 성장
    @Override
    public void grow(int days){
        for (int i = 0; i < days; i++) {
            // 나이 증가
            this.ageMonths += 1;

            // 물 부족 검사
            if (getWaterLevel() < 20) {
                // 경고(노랑)
                System.out.println("\u001B[33m[WARN] " + this.getCropId() + " 은 물이 부족해 성장이 멈췄습니다.\u001B[0m");
                continue; // 성장 안 함
            }
            // 기본 성장량
            double appleIncrease = 5;
            double ripenessIncrease = 0.05;

            // 물이 충분하지 않을 때 (20-49) -> 절반만 성장
            if (getWaterLevel() < 50) {
                appleIncrease *= 0.5;
                ripenessIncrease *= 0.5;
            }

            // 비료 효과 적용
            switch (getFertilizerType()) {
                case BALANCED:
                    appleIncrease *= 1.2;
                    ripenessIncrease *= 1.2;
                    break;
                case POTASSIUM:
                    appleIncrease *= 2; // 열매 추가 보너스
                    break;
                case ORGANIC:
                    ripenessIncrease *= 0.5; // 익음도 추가 보너스
                    break;
                default:
                    break; // NONE 일땐 보너스 없음
            }

            // 성장 반영
            appleCount += (int) appleIncrease;
            ripeness += ripenessIncrease;

            if (ripeness > 1.0) ripeness = 1.0;

           if (appleCount >= 10 && ripeness >= 0.5) {
                System.out.println("\u001B[35m[" + getCropId() + "] "
                        + getVariety().getDisplayName()
                        + " 가 수확 가능합니다!\u001B[0m");
            }
        }
        // 물 사용 (성장하면서 수분 감소)
        setWaterLevel(getWaterLevel()-days);
        if (getWaterLevel()< 0) setWaterLevel(0);

        // 성장 메시지 출력 (일반 성장 로그: 흰색)
//        String logMsg = String.format(
//                "[INFO] [%-8s] %-10s가(이) 성장했습니다. (익음도: %.1f%%, 열매 수: %d)",
//                this.getCropId(),
//                getVariety().getDisplayName(),
//                this.ripeness ,
//                this.appleCount
//        );
//        System.out.println("\u001B[37m" + logMsg + "\u001B[0m");
    }

    // 수확
    @Override
    public int harvest() {
        if (appleCount >= 10 && ripeness >= 0.5) {
            int harvested = appleCount;
            appleCount = 0;
            ripeness = 0;
            return harvested;
        } else {
            return 0;
        }
    }

    // 성장 체크
    @Override
    public double checkGrowth() {
        return ripeness;
    }



    public int getAgeMonths() {
        return ageMonths;
    }

    public void setAgeMonths(int ageMonths) {
        this.ageMonths = ageMonths;
    }

    public double getRipeness() {
        return ripeness;
    }

    public void setRipeness(double ripeness) {
        this.ripeness = ripeness;
    }

    @Override // 품종은 사과나무 클래스에서 정의를 하지만
    // farmer 클래스에서 (사과나무의 상위클래스인)crop 클래스를 부를 때가 있는데,
    // 나무의 품종이 무엇인지를 요청할 때 바로 접근을 할 수 없어 crop 클래스에는 추상 클래스를 정의하고
    // 사과나무 클래스에서 오버라이딩한다.
    public Variety getVariety() {
        return variety;
    }

    public Variety setVariety(Variety variety) {
        return this.variety = variety;
    }

    public int getAppleCount() {
        return appleCount;
    }

    public void setAppleCount(int appleCount) {
        this.appleCount = appleCount;
    }

}
