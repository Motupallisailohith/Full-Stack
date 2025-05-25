package com.gciaws.contract.contract_parser_service.parser;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

@Component("tikaParserEngine")
public class TikaParserEngine implements ParserEngine{
   private final Tika tika = new Tika();

    @Override
    public String extractText(byte[] fileBytes) throws Exception {
        return tika.parseToString(new java.io.ByteArrayInputStream(fileBytes));
    }
}
