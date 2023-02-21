package pe.proxy.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * BasicResponseController
 *
 * @author Kai
 * @version 1.0, 19/05/2022
 */
@RestController
public class BasicResponseController {

    @GetMapping("/")
    public HttpEntity<?> rootDirectory(HttpServletRequest request) {
        BasicResponseEntity entity = new BasicResponseEntity(request);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

}

/**
 * BasicResponseEntity
 *
 * @author Kai
 * @version 1.0, 19/05/2022
 */
@JsonSerialize
class BasicResponseEntity {

    private final HttpServletRequest request;

    public BasicResponseEntity(HttpServletRequest request) {
        this.request = request;
    }

    @SuppressWarnings("unused")
    public String getRemoteAddr() {
        return request.getRemoteAddr();
    }

    @SuppressWarnings("unused")
    public String getProtocol() {
        return request.getProtocol();
    }

    @SuppressWarnings("unused")
    public int getContentLength() {
        return request.getContentLength();
    }

    @SuppressWarnings("unused")
    public String getContentType() {
        return request.getContentType();
    }

    @SuppressWarnings("unused")
    public Cookie[] getCookies() {
        return request.getCookies();
    }

    @SuppressWarnings("unused")
    public String getQueryString() {
        return request.getQueryString();
    }

    @SuppressWarnings("unused")
    public Locale getLocale() {
        return request.getLocale();
    }

    @SuppressWarnings("unused")
    public long getTimestamp() {
        return new Date().getTime();
    }

    @SuppressWarnings("unused")
    public Map<String, String> getHeaders() {
        Map<String, String> map = new HashMap<>();
        Iterator<String> headers = request.getHeaderNames().asIterator();
        while (headers.hasNext()) {
            String headerName = headers.next();
            map.put(headerName, request.getHeader(headerName));
        }
        return map;
    }

}
