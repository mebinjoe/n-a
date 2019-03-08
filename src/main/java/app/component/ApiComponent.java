package app.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Component to read couchbase configuration parameters as property values
 */
@Component
public class ApiComponent {
    @Value("${couchbase_host}")
    private String hostname;

    @Value("${couchbase_bucket}")
    private String bucket;

    @Value("${couchbase_bucket_password}")
    private String password;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
