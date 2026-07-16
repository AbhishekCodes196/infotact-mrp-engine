package com.example.demo.model;

public class MrpResult {
    private Long partId;
    private String partName;
    private Integer grossRequirement;
    private Integer currentStock;
    private Integer netRequirement;
    private Integer leadTimeDays;

    public MrpResult(Long partId, String partName, Integer grossRequirement, 
                     Integer currentStock, Integer netRequirement, Integer leadTimeDays) {
        this.partId = partId;
        this.partName = partName;
        this.grossRequirement = grossRequirement;
        this.currentStock = currentStock;
        this.netRequirement = netRequirement;
        this.leadTimeDays = leadTimeDays;
    }

    public Long getPartId() { return partId; }
    public String getPartName() { return partName; }
    public Integer getGrossRequirement() { return grossRequirement; }
    public Integer getCurrentStock() { return currentStock; }
    public Integer getNetRequirement() { return netRequirement; }
    public Integer getLeadTimeDays() { return leadTimeDays; }
}
