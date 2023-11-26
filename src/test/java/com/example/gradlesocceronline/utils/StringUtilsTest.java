package com.example.gradlesocceronline.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    public void test_isValidEmailAddress_success(){
        boolean isValid = StringUtils.isValidEmailAddress("dung.kstncnttk56@gmail.com");
        Assertions.assertTrue(isValid);
    }

    @Test
    public void test_isValidEmailAddress_failure(){
        boolean isValid = StringUtils.isValidEmailAddress("jack");
        Assertions.assertFalse(isValid);
    }

}
