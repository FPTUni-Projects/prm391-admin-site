package com.storeapp.api.common.constant;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Constant
 *
 * @author KhaL
 * @since 2020/09/29
 */
@Getter
@Component
@PropertySource(value = "classpath:constant-${spring.profiles.active}.properties", encoding = "utf-8")
public class Constant {

    /**
     * Directory
     */
    @Value("${DIR.ROOT}")
    private String rootDir;

    @Value("${DIR.STATIC}")
    private String staticDir;

    @Value("${DIR.HOUSE_IMG}")
    private String houseImgDir;

    @Value("${DIR.USER_IMG}")
    private String userImgDir;

    @Value("${DIR.USER_IDENTITIES}")
    private String userIdentitiesDir;

    @Value("${DIR.DOC_COMMON}")
    private String docCommonDir;

    @Value("${DIR.DOC_DESIGN}")
    private String docDesignDir;

    @Value("${DIR.DOC_REPORT}")
    private String docReportDir;

    @Value("${DIR.TEMP}")
    private String tempDir;

    /**
     * Common
     */
    @Value("${COMMON.PAGING.LIMIT}")
    private Integer comPagingLimit;

    @Value("${COMMON.PAGING.ITEM_PER_PAGE}")
    private Integer comPagingItemPerPage;

    /**
     * Authentication
     */
    @Value("${AUTH.EXPIRATION}")
    private int expirationAuth;

    @Value("${AUTH.USER_ID}")
    private String userIdAuth;

    @Value("${AUTH.UID}")
    private String uidAuth;

    @Value("${AUTH.SESSION_ID}")
    private String sessionIdAuth;

    @Value("${AUTH.ROLE_ID}")
    private String roleIdAuth;

    /**
     * Role
     */
    @Value("${ROLE_KEY}")
    private String roleKey;

    @Value("${ROLE.ADMIN}")
    private String adminRole;

    @Value("${ROLE.MANAGER}")
    private String managerRole;

    @Value("${ROLE.SALE}")
    private String saleRole;

    @Value("${ROLE.SECRETARY}")
    private String secretaryRole;

    /**
     * Field
     */
    @Value("${FIELD_KEY}")
    private String fieldKey;

    @Value("${FIELD.HIRE}")
    private String fieldHire;

    @Value("${FIELD.SELL}")
    private String fieldSell;

    @Value("${FIELD.BOTH}")
    private String fieldBoth;

    /**
     * User
     */
    @Value("${USER_STATUS_KEY}")
    private String userStatusKey;

    @Value("${USER_STATUS.ACTIVE}")
    private String userStatusActive;

    @Value("${USER_STATUS.BLOCK}")
    private String userStatusBlock;

    @Value("${USER_STATUS.WAITING}")
    private String userStatusWaiting;

    /**
     * Customer type
     */
    @Value("${CUSTOMER_TYPE_KEY}")
    private String customerTypeKey;

    @Value("${CUSTOMER_TYPE.PROCEED}")
    private String customerTypeProceed;

    @Value("${CUSTOMER_TYPE.PROCESSING}")
    private String customerTypeProcessing;

    /**
     * House Type
     */
    @Value("${HOUSE_TYPE_KEY}")
    private String houseTypeKey;

    @Value("${HOUSE_TYPE.TOWNHOUSE}")
    private String houseTypeTownhouse;

    @Value("${HOUSE_TYPE.BUILDING}")
    private String houseTypeBuilding;

    /**
     * House Linked Flg
     */
    @Value("${HOUSE_LINKED_FLG_KEY}")
    private String houseLinkedFlgKey;

    @Value("${HOUSE_LINKED_FLG.DEFAULT_HOME_PAGE}")
    private String hlfDefaultHomePage;

    @Value("${HOUSE_LINKED_FLG.DATA_HOUSE_PAGE}")
    private String hlfDataHousePage;

    @Value("${HOUSE_LINKED_FLG.PROCEED_PAGE}")
    private String hlfProceedPage;

    @Value("${HOUSE_LINKED_FLG.NOTED}")
    private String hlfNoted;

    /**
     * View type
     */
    @Value("${VIEW_TYPE_KEY}")
    private String viewTypeKey;

    @Value("${VIEW_TYPE.MENU}")
    private String viewTypeMenu;

    @Value("${VIEW_TYPE.SCREEN}")
    private String viewTypeScreen;

    @Value("${VIEW_TYPE.API}")
    private String viewTypeApi;

    /**
     * View zone
     */
    @Value("${VIEW_ZONE_KEY}")
    private String viewZoneKey;

    @Value("${VIEW_ZONE.AUTHORIZED}")
    private String viewZoneAuthorized;

    @Value("${VIEW_ZONE.UNAUTHORIZED")
    private String viewZoneUnauthorized;

}
