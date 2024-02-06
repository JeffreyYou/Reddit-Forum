package com.beaconfire.userservice.util;

import com.beaconfire.userservice.domain.message.EmailMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializeUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String serialize(EmailMessage message){

        String result = null;

        try {
            result = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

}
