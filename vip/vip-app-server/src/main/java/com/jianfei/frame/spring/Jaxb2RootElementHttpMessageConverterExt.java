
package com.jianfei.frame.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;

import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

/**
 * @author libinsong1204@gmail.com
 */
public class Jaxb2RootElementHttpMessageConverterExt extends
        Jaxb2RootElementHttpMessageConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Jaxb2RootElementHttpMessageConverterExt.class);

    protected void customizeMarshaller(Marshaller marshaller) {
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        } catch (PropertyException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {

        return super.canWrite(mediaType);
    }
}
