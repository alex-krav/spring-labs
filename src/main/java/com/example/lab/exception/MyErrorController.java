package com.example.lab.exception;

import com.example.lab.rest.Error;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @GetMapping(value = "/error")
    public Object handleError(HttpServletRequest request) {

        String url = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        boolean restRequest = url.startsWith("/api/");

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {

            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                if (restRequest) {
                    HttpStatus httpStatus = HttpStatus.NOT_FOUND;
                    return ResponseEntity.status(httpStatus).body(
                            new Error(httpStatus.value(), httpStatus.getReasonPhrase(), "Resource not found."));
                } else {
                    return "error/404.html";
                }
            }
        }

        if (restRequest) {
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(
                    new Error(httpStatus.value(), httpStatus.getReasonPhrase(), "Something bad happened."));
        } else {
            return "error/500.html";
        }
    }
}
