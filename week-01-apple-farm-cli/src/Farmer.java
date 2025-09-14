import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Farmer {
    // 심기 -> 돌보기 -> 수확 전 과정을 관리
    // 직접 작물 상태를 바꾸지 않고, Crop 클래스의 메서드 호출하기
    // 여러 작물을 등록/조회/관리 하는 컨트롤러 만들기


    private int aoriNumber = 1;
    private int goldenNumber = 1;
    private final Map<String, Crop> crops =  new ConcurrentHashMap<>();
    private Map<String, GrowthTask> growthTasks = new HashMap<>();
    private Map<String, Thread> growthThreads = new HashMap<>();

    public String createTreeId(Variety variety) {

        String id;
        if (variety == Variety.아오리사과나무){

            id = variety.getShortName() + aoriNumber;
            aoriNumber++;
        }
        else if (variety == Variety.황금사과나무){
            id = variety.getShortName()+ goldenNumber;
            goldenNumber++;
        }
        else {
            throw new IllegalArgumentException("지원하지 않는 나무 품종입니다." + variety);
        }
        return id;

    }

    // 새로운 작물 객체 생성, map에 등록하기
    public void plant(Variety variety, int ageMonths){
        // 품종에 대한 고유 아이디 생성
        String id = createTreeId(variety);

        // 객체 생성
        Crop crop;
        if (variety == Variety.아오리사과나무){
            crop = new AoRiApple(id, ageMonths);
        }
        else{
            crop = new GoldenApple(id, ageMonths);
        }
        // Map에 등록
        crops.put(id, crop);

        // 성장 스레드 시작
//        Thread t = new Thread(new GrowthTask(crop));
//        t.start();
        // 성장 스레드 시작
        GrowthTask task = new GrowthTask(crop);


        growthTasks.put(crop.getCropId(), task);

        // [golden1] 황금사과나무 를 심었습니다.
        System.out.println("[" + id + "] " + variety + "를 심었습니다.");

    }

    public void stopAllTasks(){
        for (GrowthTask task : growthTasks.values()){
            task.stop(); // running = false로 바꿈

        }
    }

    public void waitForAllTasks(){
        for (Thread thread : growthThreads.values()){
            try {
                thread.join(); // 스레드가 종료될 때까지 기다림
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }


    // 물주기 기능
    public void water(String id, int waterLevel) {

        Crop crop = crops.get(id);
        if (crop == null){
            System.out.println("해당 ID의 작물이 없습니다: " + id);
            return;
        }
        crop.water(waterLevel);
        System.out.println("[" + id + "] "+ "에게 물 "
                + waterLevel + "주었습니다. 현재 수분: "
                + crop.getWaterLevel() );
    }

    public void fertilize(String id, FertilizerType fertilizerType) {

        Crop crop = crops.get(id);
        if (crop == null){
            System.out.println("해당 ID의 작물이 없습니다: " + id);
            return;
        }
        crop.fertilize(fertilizerType);
        System.out.println("[" + id + "] "+ "에게 비료 "
                + fertilizerType + "를 주었습니다.");
    }
    public int harvest(String id) {
        Crop crop = crops.get(id);

        if (crop == null){
            System.out.println("해당 ID의 작물이 없습니다: " + id);
            return 0;
        }
        // 실제 출력은 crop.harvest() (자식 클래스)에서 처리
        int harvested = crop.harvest();

        return harvested;
    }

    public double checkGrowth(String id) {
        Crop crop = crops.get(id);
        if (crop == null){
            System.out.println("해당 ID의 작물이 없습니다: " + id);
            return 0;
        }
        double growth = crop.checkGrowth();
        System.out.println("[" + id + "] " + crop.getVariety().getDisplayName());
        System.out.printf("성장률: %.1f / 익음도: %.1f%% / 열매수: %d%n",
                growth, crop.getRipeness(), crop.getAppleCount());
        return growth;
    }

    public void listCrops(){
        if (crops.isEmpty()) {
            System.out.println("아직 심어진 작물이 없습니다.");
            return;
        }

        for (Map.Entry<String, Crop> entry : crops.entrySet()){
            Crop crop = entry.getValue();
            System.out.println("[" + entry.getKey() + "] " + crop.getVariety().getDisplayName());
            System.out.println(" 물: " + crop.getWaterLevel()
                    + ", 익음도: " + String.format("%.1f", crop.getRipeness()) + "%"
                    + ", 심은 날: " + crop.getPlantedAt());
        }
    }

}
