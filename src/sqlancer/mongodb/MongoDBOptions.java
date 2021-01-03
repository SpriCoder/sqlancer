package sqlancer.mongodb;

import static sqlancer.mongodb.MongoDBOptions.MongoDBOracleFactory.QUERY_PARTITIONING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.Parameter;

import sqlancer.DBMSSpecificOptions;
import sqlancer.OracleFactory;
import sqlancer.common.oracle.CompositeTestOracle;
import sqlancer.common.oracle.TestOracle;
import sqlancer.mongodb.test.MongoDBQueryPartitioningWhereTester;

public class MongoDBOptions implements DBMSSpecificOptions<MongoDBOptions.MongoDBOracleFactory> {

    @Parameter(names = "--test-validation", description = "Enable/Disable validation of schema with Schema Validation", arity = 1)
    public boolean testValidation = true;

    @Parameter(names = "--oracle")
    public List<MongoDBOracleFactory> oracles = Arrays.asList(QUERY_PARTITIONING);

    @Override
    public List<MongoDBOracleFactory> getTestOracleFactory() {
        return oracles;
    }

    public enum MongoDBOracleFactory implements OracleFactory<MongoDBProvider.MongoDBGlobalState> {
        QUERY_PARTITIONING {
            @Override
            public TestOracle create(MongoDBProvider.MongoDBGlobalState globalState) throws Exception {
                List<TestOracle> oracles = new ArrayList<>();
                oracles.add(new MongoDBQueryPartitioningWhereTester(globalState));
                return new CompositeTestOracle(oracles, globalState);
            }
        }
    }
}
