package com.gciaws.contract.contract_parser_service.controller;

import com.gciaws.contract.contract_parser_service.model.ContractClauseData;
import com.gciaws.contract.contract_parser_service.parser.ParserEngine;
import com.gciaws.contract.contract_parser_service.service.ClauseExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/contracts")
public class ContractUploadController {

    @Autowired
    @Qualifier("tikaParserEngine")
    private ParserEngine parserEngine;

    @Autowired
    private Map<String, ParserEngine> parserEngines;

    @Autowired
    private ClauseExtractorService clauseExtractorService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadContract(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "engine", defaultValue = "tika") String engine) {
        try {
            ParserEngine activeEngine = parserEngines.getOrDefault(
                    engine.equalsIgnoreCase("docai") ? "documentAIParserEngine" : "tikaParserEngine",
                    parserEngine // fallback to tika
            );

            String rawText = activeEngine.extractText(file.getBytes());
            System.out.println("🔍 Extracted Text:\n" + rawText);

            ContractClauseData clauseData = clauseExtractorService.extractClauses(rawText);

            Map<String, Object> response = new HashMap<>();
            response.put("engineUsed", engine);
            response.put("clauseData", clauseData);
            response.put("rawText", rawText);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Something went wrong during document parsing.");
            error.put("message", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}
