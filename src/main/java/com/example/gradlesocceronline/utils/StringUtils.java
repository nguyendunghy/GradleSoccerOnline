package com.example.gradlesocceronline.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;

@Slf4j
public class StringUtils {
    private StringUtils() {
    }

    public static String toJson(Object obj) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(obj);
            return json;
        } catch (Exception e) {
            log.error("have error", e);
        }
        return "";
    }

    public static boolean isValidEmailAddress(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
