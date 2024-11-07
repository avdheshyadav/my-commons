package my.commons.idgen.snf;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SnowflakeIdTest {

    public static void main(String[] args) throws Exception {
        System.out.println("---- Inside snowflake Id Test Enter------");
        initTest();
        System.out.println("---- Inside snowflake Id Test Exit------");
    }

    public static void initTest() throws Exception {
        CountDownLatch idInitLatch = new CountDownLatch(1);
        int noOfIds = 1_00_000;
        ExecutorService threadPool = Executors.newVirtualThreadPerTaskExecutor();
        ArrayList<Future<ArrayList<SnfId>>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Future<ArrayList<SnfId>> future = threadPool.submit(new IdGenInitializationWorker("Worker " + i, idInitLatch, noOfIds));
            futures.add(future);
        }
        Thread.sleep(2000);
        idInitLatch.countDown();

        ArrayList<SnfId> masterList = new ArrayList<>();
        for (Future<ArrayList<SnfId>> future : futures) {
            ArrayList<SnfId> ids = future.get();
            masterList.addAll(ids);
        }
        threadPool.close();
        // Group by Duplicates
        List<SnfId> duplicates = masterList.stream()
                .collect(Collectors.groupingBy(snfId -> SnfIdGen.getId(snfId)))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .flatMap(entry -> entry.getValue().stream())
                .toList();

        for (SnfId snfId : duplicates) {
            System.out.println("Duplicate Id: " + SnfIdGen.getId(snfId) + " snfId:" + snfId);
        }

        for (SnfId snfId : masterList) {
            System.out.println("Master Id: " + SnfIdGen.getId(snfId) + " snfId:" + snfId);
        }
        System.out.println("Master Size: " + masterList.size());
        System.out.println("Duplicates:" + duplicates.size());
        System.out.println("All Data returned");
    }
}

class IdGenInitializationWorker implements Callable<ArrayList<SnfId>> {
    private final CountDownLatch latch;
    private final int noOfIds;
    private final ArrayList<SnfId> idList;
    private final String name;

    public IdGenInitializationWorker(String name, CountDownLatch latch, int noOfIds) {
        this.latch = latch;
        this.noOfIds = noOfIds;
        this.idList = new ArrayList<>(noOfIds);
        this.name = name;
    }

    @Override
    public ArrayList<SnfId> call() throws InterruptedException {
        System.out.println("Thread:" + name);
        latch.await();
        for (int i = 0; i < noOfIds; i++) {
            long id = SnfIdGen.getId();
            SnfId snfId = SnfIdGen.getSnfId(id);
            idList.add(snfId);
        }
        return idList;
    }
}
