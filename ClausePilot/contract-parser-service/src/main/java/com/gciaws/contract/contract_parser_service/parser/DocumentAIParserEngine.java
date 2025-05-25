package com.gciaws.contract.contract_parser_service.parser;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.documentai.v1.*;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component("documentAIParserEngine")
public class DocumentAIParserEngine implements ParserEngine {

    @Value("${google.docai.processor-id}")
    private String processorId;

    @Override
    public String extractText(byte[] fileBytes) throws IOException {
        // 🔍 Check if the credential file is accessible and log file size
        String credentialPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        System.out.println("GOOGLE_APPLICATION_CREDENTIALS = " + credentialPath);

        try {
            byte[] jsonBytes = Files.readAllBytes(Paths.get(credentialPath));
            System.out.println("✅ Credential file is readable. Size: " + jsonBytes.length + " bytes");
        } catch (IOException ioEx) {
            System.err.println("❌ Failed to read credentials file: " + ioEx.getMessage());
            throw ioEx;
        }

        // 🔐 Manually load credentials from the file
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialPath));

        DocumentProcessorServiceSettings settings = DocumentProcessorServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        try (DocumentProcessorServiceClient client = DocumentProcessorServiceClient.create(settings)) {
            RawDocument rawDocument = RawDocument.newBuilder()
                    .setContent(ByteString.copyFrom(fileBytes))
                    .setMimeType("application/pdf")  // or application/vnd.openxmlformats-officedocument.wordprocessingml.document
                    .build();

            ProcessRequest request = ProcessRequest.newBuilder()
                    .setName(processorId)
                    .setRawDocument(rawDocument)
                    .build();

            ProcessResponse response = client.processDocument(request);
            return response.getDocument().getText();
        }
    }
}
