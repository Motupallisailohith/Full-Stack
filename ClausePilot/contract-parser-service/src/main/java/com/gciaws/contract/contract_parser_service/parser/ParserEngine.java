package com.gciaws.contract.contract_parser_service.parser;

public interface ParserEngine {
    /**
     * Extracts raw plain text from the given binary file content.
     *
     * @param fileBytes PDF or DOCX file as byte array
     * @return Raw extracted text
     * @throws Exception if parsing fails
     */
    String extractText(byte[] fileBytes) throws Exception;
}
