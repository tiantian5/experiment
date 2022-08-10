package com.experiment.core.service.exportfile;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: tzw
 * @Date: 2022/07/22/11:01 上午
 * @Description:
 */

@Data
@ToString
public class SolderExportDTO {

    /**
     * 工厂名称
     */
    private String factoryName;
    /**
     * 工厂Logo
     */
    private String factoryLogo;
    /**
     * 报告名称
     */
    private String reportName;
    /**
     * 第一列名称：锡膏编码/红胶编码
     */
    private String solderTypeName;
    /**
     * 锡膏编码/红胶编码
     */
    private String solderPasteCode;
    /**
     * 型号
     */
    private String model;
    /**
     * 规格
     */
    private String specification;
    /**
     * 批次
     */
    private String batchNo;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 保质期天数
     */
    private String shelfLife;
    /**
     * 生产日期
     */
    private String productionDate;
    /**
     * 入库时间
     */
    private String storageTime;
    /**
     * 导出人
     */
    private String exporter;
    /**
     * 导出时间
     */
    private String exportTime;

    /**
     * 使用记录列表
     */
    private List<RecordInfoDTO> recordInfoDTO;

    @Data
    public static class RecordInfoDTO {

        /**
         * 序号
         */
        private Integer step;

        private Integer stepType;

        /**
         * 使用流程
         */
        private String useDesc;
        /**
         * 扫码时间
         */
        private String useTimeDesc;

        private Long useTime;
        /**
         * 持续时间
         */
        private String timeDesc;

        /**
         * 扫码人
         */
        private String person;
    }

}
