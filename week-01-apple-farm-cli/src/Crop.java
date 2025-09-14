import java.time.LocalDate;

public abstract class Crop {
    // 필드
    // 생성자
    // 공통 메서드
    // 추상 메서드

    // 심은 날짜
    private final LocalDate plantedAt;
    // 현재 수분량
    private int waterLevel;
    // 현재 비료 상태
    FertilizerType fertilizerType;
    // 아이디 (농부가 관리 쉽게)
    private String cropId;
    public abstract Variety getVariety();

    public abstract int getAppleCount();
    public abstract double getRipeness();


    // <-------생성자--------->
    Crop(String cropId) {
        this.cropId = cropId;
        plantedAt = LocalDate.now();
        waterLevel = 0;
        //this.treeType = treeType;
        fertilizerType = FertilizerType.NONE;
    }

    // <-------공통 메서드--------->
    public void water(int amount){
        // 물 추가, 범위 제한
        if (amount < 0){
            System.out.println("물의 양은 0보다 많아야 합니다.");
            return;
        }
        waterLevel += amount;
        if (waterLevel > 100){
            waterLevel = 100;
        }
    }
    public void fertilize(FertilizerType fertilizerType){
        this.fertilizerType = fertilizerType;
    }

    public void grow(int days){
        //  ㅈㅏㅅㅣㄱ ㅋㅡㄹㄹㅐㅅㅡㅇㅔㅅㅓ ㅇㅗㅂㅓㄹㅏㅇㅣㄷㅣㅇ ㄱㅏㄴㅡㅇ, ㄱㅣㅂㅗㄴ ㄱㅗㄹㄱㅕㄱ ㅈㅔㄱㅗㅇ
        // 모든 작물은 시간이 지나면 기본적으로 나이가 든다
        // 세부적인 열매 증가나 익음도 변화는 자식 클래스(AppleTree 등)에서 구현
        System.out.println(cropId + " 은 " + days + "일 만큼 시간이 흘렀습니다.");
    }

    // <-------추상 메서드 (자식 클래스에서 반드시 구현해야함)--------->
    public abstract int harvest(); // 수확 시 반환 값(열매 개수 등)
    public abstract double checkGrowth(); // 성장률 (0 - 10) 반환


    public LocalDate getPlantedAt() {
        return plantedAt;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public String getCropId() {
        return cropId;
    }
    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public FertilizerType getFertilizerType() {
        return fertilizerType;
    }

    public void setFertilizerType(FertilizerType fertilizerType) {
        this.fertilizerType = fertilizerType;
    }
}
