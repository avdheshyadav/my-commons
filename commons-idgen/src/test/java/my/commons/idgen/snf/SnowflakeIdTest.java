package my.commons.idgen.snf;

import my.commons.idgen.snf.impl.DefaultSnfIdConfigImpl;
import my.commons.idgen.snf.impl.MachineId;
import my.commons.idgen.snf.impl.NetworkInterfaceMachineIdProvider;

public class SnowflakeIdTest {

    public static void main(String[] args) throws Exception{
        System.out.println("---- Inside snowflake Id Test Enter------");
        MachineId machineId = new MachineId(MachineId.DEF_MACHINE_ID_BITS, NetworkInterfaceMachineIdProvider.INSTANCE);
        SnfIdConfig snfIdConfig = new DefaultSnfIdConfigImpl(DefaultSnfIdConfigImpl.DEF_EPOCH, machineId, DefaultSnfIdConfigImpl.DEF_SEQUENCE_BITS);
        SnfIdGen.registerIdCreator(snfIdConfig);
        Long id = SnfIdGen.getId();
        System.out.println("id:" + id);
        SnfId snfId = SnfIdGen.getSnfId(id);
        System.out.println("snfId:" + snfId);
        id = SnfIdGen.getId(snfId);
        System.out.println("id::::::" + id);

        System.out.println("---- Inside snowflake Id Test Exit------");
    }
}
