package com.tcps.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tcps.common.core.domain.BaseEntity;
import com.tcps.common.core.domain.model.TenantEntity;
import com.tcps.common.xss.Xss;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 通知公告表 sys_notice
 *
 * @author Tao Guang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_notice")
public class SysNotice extends TenantEntity {

    /**
     * 公告ID
     */
    @TableId(value = "notice_id")
    private Long noticeId;

    /**
     * 公告标题
     */
    @Xss(message = "公告标题不能包含脚本字符")
    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过{max}个字符")
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    @NotBlank(message = "公告类型不能为空")
    private String noticeType;

    /**
     * 附件
     */
    private String files;

    /**
     * 公告状态（0发布 1草稿）
     */
    private String status;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 账套
     */
    private String accountId;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 备注
     */
    private String remarks;

}
