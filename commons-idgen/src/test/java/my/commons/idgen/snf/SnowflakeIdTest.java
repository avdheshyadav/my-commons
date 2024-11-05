package my.commons.idgen.snf;

import my.commons.idgen.snf.impl.DefaultSnfIdConfigImpl;
import my.commons.idgen.snf.impl.MachineId;
import my.commons.idgen.snf.impl.NetworkInterfaceMachineIdProvider;

import java.util.concurrent.CountDownLatch;

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
        IdGenInitializationWorker worker1 = new IdGenInitializationWorker("Worker 1", idInitLatch,noOfIds);
        IdGenInitializationWorker worker2 = new IdGenInitializationWorker("Worker 2", idInitLatch,noOfIds);
        IdGenInitializationWorker worker3 = new IdGenInitializationWorker("Worker 3", idInitLatch,noOfIds);
        IdGenInitializationWorker worker4 = new IdGenInitializationWorker("Worker 4", idInitLatch,noOfIds);
        IdGenInitializationWorker worker5 = new IdGenInitializationWorker("Worker 5", idInitLatch,noOfIds);
        worker1.start();
        worker2.start();
        worker3.start();
        worker4.start();
        worker5.start();
        Thread.sleep(2000);
        idInitLatch.countDown();
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
