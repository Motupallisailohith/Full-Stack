package com.gciaws.contract.contract_parser_service.service;

import com.gciaws.contract.contract_parser_service.model.ContractClauseData;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClauseExtractorService {

    public ContractClauseData extractClauses(String text) {
        ContractClauseData data = new ContractClauseData();

        // Extract Custom Pay Plan Fee (e.g., "1.72%")
        Pattern payPlanPattern = Pattern.compile("(\\d+(\\.\\d+)?)%\\s*custom pay plan", Pattern.CASE_INSENSITIVE);
        Matcher m1 = payPlanPattern.matcher(text);
        if (m1.find()) {
            data.setCustomPayPlanFee(Double.parseDouble(m1.group(1)));
        }

        // Extract Balance Transfer Fee (e.g., "4% balance transfer")
        Pattern transferPattern = Pattern.compile("(\\d+(\\.\\d+)?)%\\s*balance transfer", Pattern.CASE_INSENSITIVE);
        Matcher m2 = transferPattern.matcher(text);
        if (m2.find()) {
            data.setBalanceTransferFee(Double.parseDouble(m2.group(1)));
        }

        // Grace Period (e.g., "25-day grace period")
        Pattern gracePattern = Pattern.compile("(\\d+)\\s*-?\\s*day\\s*grace", Pattern.CASE_INSENSITIVE);
        Matcher m3 = gracePattern.matcher(text);
        if (m3.find()) {
            data.setGracePeriodDays(Integer.parseInt(m3.group(1)));
        }

        // You can add more rules for dates, refund amounts, etc.
        return data;
    }
}
