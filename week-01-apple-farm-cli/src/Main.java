import java.util.Scanner;



public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Farmer farmer = new Farmer();

        System.out.println("🍎 사과 농장 가꾸기CLI 프로그램");
        System.out.println("명령어를 입력하세요.");
        //System.out.println("심기/전체 농작물 조회하기/성장도 확인/물주기/비료주기/수확하기");
        System.out.println("================================");
        System.out.println("사용법:");
        System.out.println("  심기 [품종|아오리사과나무/황금사과나무] [나이]       → 새 사과나무를 심습니다.");
        System.out.println("  전체조회               → 모든 작물 목록을 확인합니다.");
        System.out.println("  성장확인 [id]          → 특정 작물의 성장도를 확인합니다.");
        System.out.println("  물주기 [id] [양]       → 특정 작물에 물을 줍니다.");
        System.out.println("  비료주기 [id] [종류|BALANCED, POTASSIUM, ORGANIC]   → 특정 작물에 비료를 줍니다.");
        System.out.println("  수확하기 [id]          → 특정 작물을 수확합니다.");
        System.out.println("  종료하기                  → 프로그램을 종료합니다.");
        System.out.println("================================");

        boolean running = true;

        while (running){
            System.out.print("> ");
            String line = sc.nextLine();

            if (line.equalsIgnoreCase("exit")){
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            String[] parts = line.split(" ");
            String command = parts[0];


            switch (command) {
                case "심기":
                    if (parts.length < 3) {
                        System.out.println("사용법: 심기 [품종] [나이]");
                        break;
                    }
                    Variety variety = Variety.valueOf(parts[1]);
                    int ageMonths = Integer.parseInt(parts[2]);
                    farmer.plant(variety, ageMonths);
                    break;

                case "전체조회":
                    farmer.listCrops();
                    break;

                case "성장확인":
                    if (parts.length < 2) {
                        System.out.println("사용법: 성장확인 [id]");
                        break;
                    }
                    String checkId = parts[1];
                    farmer.checkGrowth(checkId);
                    break;

                case "물주기":
                    if (parts.length < 3) {
                        System.out.println("사용법: 물주기 [id] [양]");
                        break;
                    }
                    String waterId = parts[1];
                    int waterLevel = Integer.parseInt(parts[2]);
                    farmer.water(waterId, waterLevel);
                    break;

                case "비료주기":
                    if (parts.length < 3) {
                        System.out.println("사용법: 비료주기 [id] [종류]");
                        break;
                    }
                    String ferId = parts[1];
                    FertilizerType fertilizerType = FertilizerType.valueOf(parts[2]);
                    farmer.fertilize(ferId, fertilizerType);
                    break;

                case "수확하기":
                    if (parts.length < 2) {
                        System.out.println("사용법: 수확하기 [id]");
                        break;
                    }
                    String harvestId = parts[1];
                    farmer.harvest(harvestId);
                    break;

                case "종료하기":
                    System.out.println("프로그램을 종료합니다.");
                    farmer.stopAllTasks();    // 각 task.stop() 호출
                    farmer.waitForAllTasks(); // join()으로 모두 종료될 때까지 대기
                    running = false;
                    break;

                default:
                    System.out.println("알 수 없는 명령어입니다.");
                    break;

            }

        }

    }
}