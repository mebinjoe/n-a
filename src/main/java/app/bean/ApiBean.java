package app.bean;

import app.component.ApiComponent;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Bean for Couchbase creating cluster and opening bucket
 */
@Component
public class ApiBean {

    @Autowired
    ApiComponent component;

    /**
     * @return
     */
    public @Bean
    Cluster cluster() {
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
                .connectTimeout(30000) //10000ms = 10s, default is 5s
                .build();
        //use the env during cluster creation to apply
        return CouchbaseCluster.create(env, component.getHostname());
    }

    /**
     * @return
     */
    public @Bean
    Bucket bucket() {
        return cluster().openBucket(component.getBucket(), component.getPassword());
    }
}
