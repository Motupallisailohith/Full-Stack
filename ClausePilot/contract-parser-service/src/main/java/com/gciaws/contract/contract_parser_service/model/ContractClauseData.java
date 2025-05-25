package com.gciaws.contract.contract_parser_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractClauseData {
    private Double customPayPlanFee;
    private Double balanceTransferFee;
    private Integer gracePeriodDays;
    private String policyStartDate;
    private String expiryDate;
    private Double refundAmount;
    private boolean coverageLost;
    private String surrenderConditions;
}
