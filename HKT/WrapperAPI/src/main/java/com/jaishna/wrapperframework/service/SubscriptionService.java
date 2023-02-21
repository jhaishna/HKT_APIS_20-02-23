package com.jaishna.wrapperframework.service;

import com.jaishna.wrapperframework.customfields.MRBStringFld;
import com.jaishna.wrapperframework.messages.SubscriptionAccountMessages;
import com.jaishna.wrapperframework.model.subscriptions.*;
import com.jaishna.wrapperframework.utils.ContextUtils;
import com.jaishna.wrapperframework.utils.ConversionUtils;
import com.portal.pcm.FList;
import com.portal.pcm.PortalContext;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;

@org.springframework.stereotype.Service
public class SubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);

    public SubscriptionResponse createSubscriptionAccount(SubscriptionAccount subscriptionAccount) {
        SubscriptionResponse response = new SubscriptionResponse();
        LOGGER.info("createSubrscriptionAccount.."+subscriptionAccount);

        if(validateInput(subscriptionAccount,response)) {
            try {
                PortalContext fContext = ContextUtils.createContext();
                LOGGER.info("created context with BRM DB..." + fContext);
                if (fContext != null) {
                    FList flistSubscriptionAccount = new FList();
                    Long dbNumber = fContext.getCurrentDB();
                    LOGGER.info("Input FList being sent before calling opcode:" + flistSubscriptionAccount);
                    flistSubscriptionAccount.set(FldPoid.getInst(), ConversionUtils.convertToPOID(dbNumber,new Long(-1),"/plan"));
                    flistSubscriptionAccount.set(FldEndT.getInst(), ConversionUtils.convertToTimeStamp(null));
                    flistSubscriptionAccount.set(FldAccountNo.getInst(), subscriptionAccount.getAccountNo());
                    flistSubscriptionAccount.set(FldSubordInfo.getInst(), createSubordInfo(subscriptionAccount, dbNumber));
                    LOGGER.info("Input FList being sent before calling opcode:" + flistSubscriptionAccount);
                    FList flistOut = fContext.opcode(19445, flistSubscriptionAccount);
                    response.setRequestStatus("1");
                    response.setAccountNo(subscriptionAccount.getAccountNo());
                    response.setMSISDN(subscriptionAccount.getLineId());
                    response = prepareResponse(flistOut, response);
                    flistOut.dump();
                    LOGGER.info("FList Output from BRM DB..." + flistOut);
                    fContext.close(true);
                }
            } catch (Exception ex) {
                response.setErrorCode("910000");
                response.setErrorDescr(ex.getMessage());
                ex.printStackTrace();
                LOGGER.info("Exception calling the Flist OpCode.19445..." + ex);
            }
        }
            return response;
    }

    private boolean validateInput(SubscriptionAccount subscriptionAccount, SubscriptionResponse response) {
        boolean isValid = false;
        if(!StringUtils.hasLength(subscriptionAccount.getAccountNo())) {
            response.setErrorDescr(SubscriptionAccountMessages.ACCOUNT_NO_MISSING);
        } else if(!StringUtils.hasLength(subscriptionAccount.getLineId())) {
            response.setErrorDescr(SubscriptionAccountMessages.LINE_ID_MISSING);
        } else if(subscriptionAccount.getProfiles() == null || subscriptionAccount.getProfiles().isEmpty()) {
            response.setErrorDescr(SubscriptionAccountMessages.PROFILES_ARRAY_MISSING);
        } else if(subscriptionAccount.getProfiles() != null && !subscriptionAccount.getProfiles().isEmpty()) {
            for(Profile profile : subscriptionAccount.getProfiles()) {
                for(Subscription subscription : profile.getSubscriptions()) {
                    if (!StringUtils.hasLength(subscription.getTitle())) {
                        response.setErrorDescr(SubscriptionAccountMessages.TITLE_MISSING);
                    }
                }
            }
        } else if(subscriptionAccount.getServices() != null && !subscriptionAccount.getServices().isEmpty()) {
            for(Service service : subscriptionAccount.getServices()) {
                for(Alias alias : service.getAliasList()) {
                    if (!StringUtils.hasLength(alias.getName())) {
                        response.setErrorDescr(SubscriptionAccountMessages.ALIAS_NAME_MISSING);
                    }
                }
            }
        }
        if(!StringUtils.hasLength(response.getErrorDescr())) {
            isValid = true;
        }
        else {
            response.setErrorCode("400");
        }
        return isValid;
    }

    private SparseArray createSubordInfo(SubscriptionAccount subscriptionAccount, Long dbNumber) {
        SparseArray subordArray = new SparseArray();
        FList subOrdElem = new FList();
        subOrdElem.set(FldMsid.getInst(),subscriptionAccount.getLineId());
        SparseArray profilesArray = new SparseArray();
        for(Profile profile : subscriptionAccount.getProfiles()) {
            FList profilesElem = new FList();
            profilesElem.set(FldInheritedInfo.getInst(), createSubscriptionInheritedInfo(profile,dbNumber));
            profilesArray.add(profilesElem);
        }
        subOrdElem.set(FldProfiles.getInst(),profilesArray);
        subOrdElem.set(FldServices.getInst(),createServices(subscriptionAccount.getServices(),dbNumber));
        subordArray.add(subOrdElem);
        return subordArray;
    }

    private SparseArray createServices(List<Service> services, Long dbNumber) {
        SparseArray servicesArray = new SparseArray();
        for(Service service : services) {
            FList serviceElem = new FList();
            serviceElem.set(FldAliasList.getInst(),createAliasList(service.getAliasList()));
            servicesArray.add(serviceElem);
        }
        return servicesArray;
    }

    private SparseArray createAliasList(List<Alias> aliasList) {
        SparseArray aliasArray = new SparseArray();
        for(Alias alias : aliasList) {
            FList aliasElem = new FList();
            aliasElem.set(FldName.getInst(),alias.getName());
            aliasArray.add(aliasElem);
        }
        return aliasArray;
    }

    private FList createSubscriptionInheritedInfo(Profile profile, Long dbNumber) {
        FList subscriptionInheritedInfoSubstruct = new FList();
        subscriptionInheritedInfoSubstruct.set(FldAttributeInfo.getInst(), createAttributeInfo(profile.getSubscriptions(),dbNumber));
        return subscriptionInheritedInfoSubstruct;
    }

    private FList createAttributeInfo(List<Subscription> subscriptions,Long dbNumber) {
        FList attributeInfoSubstruct = new FList();
        for(Subscription subscription : subscriptions) {
            attributeInfoSubstruct.set(FldLastName.getInst(),subscription.getLastName());
            attributeInfoSubstruct.set(FldFirstName.getInst(),subscription.getFirstName());
            attributeInfoSubstruct.set(FldTitle.getInst(),subscription.getTitle());
            if(subscription.getStaffCcc()!=null) {
                attributeInfoSubstruct.set(MRBStringFld.getInst(100018, "MRB_FLD_STAFF_CCC"), subscription.getStaffCcc());
            }
            if(subscription.getCompany()!=null) {
                attributeInfoSubstruct.set(FldCompany.getInst(), subscription.getCompany());
            }
            if(subscription.getIdType()!=null) {
                attributeInfoSubstruct.set(MRBStringFld.getInst(100008, "MRB_FLD_ID_TYPE"), subscription.getIdType());
            }
            if(subscription.getLocalGprsOptOut()!=null) {
                attributeInfoSubstruct.set(FldOperatorStr.getInst(), subscription.getLocalGprsOptOut());
            }
            if(subscription.getRoamGprsOptOut()!=null) {
                attributeInfoSubstruct.set(FldStatusStr.getInst(), subscription.getRoamGprsOptOut());
            }
            if(subscription.geteGprsOptOut()!=null) {
                attributeInfoSubstruct.set(FldString.getInst(), subscription.geteGprsOptOut());
            }
            if(subscription.getIddRoamoutIn()!=null) {
                attributeInfoSubstruct.set(FldStatusMsg.getInst(), subscription.getIddRoamoutIn());
            }
            if(subscription.getIddRoamThreshold()!=null) {
                attributeInfoSubstruct.set(FldStatusFlags.getInst(), ConversionUtils.getInteger(subscription.getIddRoamThreshold()));
            }
            if(subscription.getJocCity()!=null) {
                attributeInfoSubstruct.set(FldDestinationNetwork.getInst(), subscription.getJocCity());
            }
            if(subscription.getOptOutPccw()!=null) {
                attributeInfoSubstruct.set(MRBStringFld.getInst(100023, "MRB_FLD_DELIVERY_DESCR4"), subscription.getOptOutPccw());
            }
            if(subscription.getOptOutSms()!=null) {
                attributeInfoSubstruct.set(MRBStringFld.getInst(100022, "MRB_FLD_DELIVERY_DESCR3"), subscription.getOptOutSms());
            }
            if(subscription.getOptOutEmail()!=null) {
                attributeInfoSubstruct.set(MRBStringFld.getInst(100021, "MRB_FLD_DELIVERY_DESCR2"), subscription.getOptOutEmail());
            }
            if(subscription.getOptOutDm()!=null) {
                attributeInfoSubstruct.set(MRBStringFld.getInst(100020, "MRB_FLD_DELIVERY_DESCR1"), subscription.getOptOutDm());
            }
            attributeInfoSubstruct.set(FldProfileObj.getInst(),ConversionUtils.convertToPOID(dbNumber,new Long(-1),"/profile/subscription"));
        }
        return attributeInfoSubstruct;
    }

    private SubscriptionResponse prepareResponse(FList flistOut, SubscriptionResponse response) {
        return response;
    }
}
