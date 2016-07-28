package com.jianfei.core.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * CustomFloatSerialize
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/7/27 16:53
 */
public class CustomFloatSerialize extends JsonSerializer<Float> {
    private DecimalFormat df = new DecimalFormat("0.00");
    @Override
    public void serialize(Float aFloat, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        df.setRoundingMode(RoundingMode.HALF_UP);
        jsonGenerator.writeString(df.format(aFloat));
    }
}
