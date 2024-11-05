package my.commons.idgen.snf;

import my.commons.idgen.snf.impl.DefaultSnfIdConfigImpl;
import my.commons.idgen.snf.impl.MachineId;
import my.commons.idgen.snf.impl.NetworkInterfaceMachineIdProvider;

import java.util.ArrayList;
import java.util.concurrent.*;

public class SnowflakeIdTest {

    public static void main(String[] args) throws Exception {
        System.out.println("---- Inside snowflake Id Test Enter------");
        //basic();
        initTest();
        System.out.println("---- Inside snowflake Id Test Exit------");
    }

    public static void initTest() throws Exception {
        CountDownLatch idInitLatch = new CountDownLatch(1);
        int noOfIds = 1;


        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        ArrayList<Future<ArrayList<SnfId>>> futures = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Future<ArrayList<SnfId>> future = threadPool.submit(new IdGenInitializationWorker("Worker " + i , idInitLatch, noOfIds ));
            futures.add(future);
        }
        Thread.sleep(2000);
        idInitLatch.countDown();

        ArrayList<Long> masterList = new ArrayList<>();
        ArrayList<Long> duplicates = new ArrayList<>();

        for(Future<ArrayList<SnfId>> future : futures) {
            ArrayList<SnfId> ids = future.get();
            for(SnfId snfId : ids){
                Long id = SnfIdGen.getId(snfId);
                if(masterList.contains(id)){
                    duplicates.add(id);
                }
                masterList.add(id);
            }
        }
        System.out.println("All Data returned");

        System.out.println("Master Size: " + masterList.size());
        System.out.println("Duplicates Size: " + duplicates.size());

    }

    public static void basic() throws Exception {
        MachineId machineId = new MachineId(MachineId.DEF_MACHINE_ID_BITS, NetworkInterfaceMachineIdProvider.INSTANCE);
        SnfIdConfig snfIdConfig = new DefaultSnfIdConfigImpl(DefaultSnfIdConfigImpl.DEF_EPOCH, machineId, DefaultSnfIdConfigImpl.DEF_SEQUENCE_BITS);
        SnfIdGen.registerIdCreator(snfIdConfig);
        Long id = SnfIdGen.getId();
        System.out.println("id:" + id);
        System.out.println("snfId:" + SnfIdGen.getSnfId(id));
        id = SnfIdGen.getId(SnfIdGen.getSnfId(id));
        System.out.println("id::::::" + id);
    }
}
