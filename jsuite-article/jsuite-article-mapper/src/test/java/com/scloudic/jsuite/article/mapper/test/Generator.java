package com.scloudic.jsuite.article.mapper.test;

import com.scloudic.rabbitframework.generator.RabbitGenerator;
import com.scloudic.rabbitframework.generator.RabbitGeneratorBuilder;
import com.scloudic.rabbitframework.core.utils.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;

public class Generator {
    private static final Logger logger = LoggerFactory.getLogger(Generator.class);
    public static void main(String[] args) throws Exception {
        logger.debug("generator start");
        Reader reader = ResourceUtils.getResourceAsReader("generator-config.xml");
        try {
            RabbitGeneratorBuilder builder = new RabbitGeneratorBuilder();
            RabbitGenerator rabbitGenerator = builder.build(reader);
            rabbitGenerator.generator();
        } finally {
            reader.close();
        }
        logger.debug("generator end");
    }
}
