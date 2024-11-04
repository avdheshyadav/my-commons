package my.commons.idgen.snf;

public class SnowflakeIdTest {

    public static void main(String[] args) throws Exception{
        System.out.println("---- Inside snowflake Id Test Enter------");
        SnfIdConfig defaultConfig = SnfIdConfig.DEFAULT_CONFIG;
        SnfIdGen.registerIdCreator(defaultConfig);
        Long id = SnfIdGen.getId();
        System.out.println("id:" + id);
        SnfId snfId = SnfIdGen.getSnfId(id);
        System.out.println("snfId:" + snfId);
        id = SnfIdGen.getId(snfId);
        System.out.println("id::::::" + id);

        System.out.println("---- Inside snowflake Id Test Exit------");
    }
}
