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
        int noOfIds = 10;
        ExecutorService threadPool = Executors.newVirtualThreadPerTaskExecutor();
        ArrayList<Future<ArrayList<SnfId>>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
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
                .collect(Collectors.groupingBy(SnfId::hashCode))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .flatMap(entry -> entry.getValue().stream())
                .toList();

        for (SnfId snfId : duplicates) {
            System.out.println("Duplicate Id: " + SnfIdGen.getId(snfId) + " snfId:" + snfId);
        }
        System.out.println("Master Size: " + masterList.size());
        System.out.println("Duplicates:" + duplicates.size());
        System.out.println("All Data returned");
    }
}
