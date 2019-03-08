package app.controller;

import app.bean.ApiBean;
import app.repository.ApiRepository;
import com.couchbase.client.java.document.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  REST Web Service API controller to handle CRUD operations
 */
@RestController
@RequestMapping("/api")
public class ApiController implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Autowired
    ApiRepository repository;

    @Autowired
    ApiBean bean;

    /**
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Object getAll() {
        return repository.getAll(bean.bucket());
    }

    /**
     * @param document_id
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Object getByDocumentId(@RequestParam String document_id) {
        if (document_id.equals("")) {
            return new ResponseEntity<String>(JsonObject.create().put("message", "A document id is required").toString(), HttpStatus.BAD_REQUEST);
        }
        return repository.getByDocumentId(bean.bucket(), document_id);
    }

    /**
     * @param json
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody String json) {
        JsonObject jsonData = JsonObject.fromJson(json);
        if (jsonData.getString("document_id") == null || jsonData.getString("document_id").equals("")) {
            return new ResponseEntity<String>(JsonObject.create().put("message", "A document id is required").toString(), HttpStatus.BAD_REQUEST);
        }
        return repository.delete(bean.bucket(), jsonData.getString("document_id"));
    }

    /**
     * @param json
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@RequestBody String json) {
        JsonObject jsonData = JsonObject.fromJson(json);
        if (jsonData.getString("firstname") == null || jsonData.getString("firstname").equals("")) {
            return new ResponseEntity<String>(JsonObject.create().put("message", "A firstname is required").toString(), HttpStatus.BAD_REQUEST);
        } else if (jsonData.getString("lastname") == null || jsonData.getString("lastname").equals("")) {
            return new ResponseEntity<String>(JsonObject.create().put("message", "A lastname is required").toString(), HttpStatus.BAD_REQUEST);
        } else if (jsonData.getString("email") == null || jsonData.getString("email").equals("")) {
            return new ResponseEntity<String>(JsonObject.create().put("message", "An email is required").toString(), HttpStatus.BAD_REQUEST);
        }
        return repository.save(bean.bucket(), jsonData);
    }
}
