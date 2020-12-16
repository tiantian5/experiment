package com.experiment.core.service.factory.factoryfive;

import com.experiment.core.service.factory.factoryfive.instance.DeliveryNotify;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tzw
 * @description
 * @create 2020-12-16 11:28 上午
 **/

@Slf4j
public enum EnumInterfaceNotifyAction implements InterfaceNotifyAction {

    /**
     * 处理一
     */
    UPDATE_TYPE_ONE(1, 2, 3) {

        @Override
        public void notifyAction(NotifyCallBackParam notifyCallBackParam) {
            // 若是逻辑复杂可以继续套用工厂模式
            DeliveryNotify.getInstance().realAction(notifyCallBackParam);
        }

    },

    /**
     * 处理二
     */
    UPDATE_TYPE_TWO(2, 3, 4) {

        @Override
        public void notifyAction(NotifyCallBackParam notifyCallBackParam) {
            log.info("do 处理二");
        }

    },

    /**
     * 处理三
     */
    UPDATE_TYPE_THREE(3, 4, 5) {

        @Override
        public void notifyAction(NotifyCallBackParam notifyCallBackParam) {
            log.info("do 处理三");
        }

    }
    ;

    EnumInterfaceNotifyAction(Integer updateType, Integer oldStatus, Integer newStatus) {
        this.updateType = updateType;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    private Integer updateType;
    private Integer oldStatus;
    private Integer newStatus;

    private Integer getUpdateType() {
        return updateType;
    }
    private Integer getOldStatus() {
        return oldStatus;
    }

    private Integer getNewStatus() {
        return newStatus;
    }

    /**
     * 业务判断
     *
     * @param updateType updateType
     * @param oldStatus oldStatus
     * @param newStatus newStatus
     * @return EnumInterfaceNotifyAction
     */
    public static EnumInterfaceNotifyAction getInstance(Integer updateType, Integer oldStatus, Integer newStatus) {
        for (EnumInterfaceNotifyAction iEnum: values()) {
            if (iEnum.getUpdateType().equals(updateType) && iEnum.getOldStatus().equals(oldStatus)
                    && iEnum.getNewStatus().equals(newStatus)) {
                return iEnum;
            }
        }
        return null;
    }

    @Override
    public void notifyAction(NotifyCallBackParam notifyCallBackParam) {

    }
}