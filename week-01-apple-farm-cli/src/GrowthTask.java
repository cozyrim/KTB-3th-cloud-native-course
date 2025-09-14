public class GrowthTask implements Runnable {
    private volatile boolean running = true;
    private Crop crop;
    private final Thread thread;

    public GrowthTask(Crop crop) {
        this.crop = crop;
        this.thread = new Thread(this, crop.getCropId() + "-growth");
        this.thread.start();
    }

    @Override
    public void run() {
        try {
            while (running) {
                Thread.sleep(10000); // 10초마다 하루가 흐르는 것처럼 처리
                crop.grow(1); // 하루 성장
                //System.out.println("[" + crop.getCropId() + "] 하루 성장했습니다.");
            }
        } catch (InterruptedException e) {
            // 인터럽트가 오면 루프 종료
        }
        System.out.println("[" + crop.getCropId() + "] 성장 스레드 종료됨");
    }

    public void stop() {
        running = false;
        // ✅ sleep 중이라도 즉시 깨어나도록 interrupt
        thread.interrupt(); // sleep 상태라도 바로 깨워서 종료되도록
    }
    public void join() throws InterruptedException {
        thread.join();
    }
}
