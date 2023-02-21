package com.jaishna.wrapperframework.service;

import com.jaishna.wrapperframework.messages.PurchaseOffersMessages;
import com.jaishna.wrapperframework.model.purchaseoffers.Bundle;
import com.jaishna.wrapperframework.model.purchaseoffers.PurchaseOffers;
import com.jaishna.wrapperframework.model.purchaseoffers.PurchaseOffersResponse;
import com.jaishna.wrapperframework.utils.ContextUtils;
import com.jaishna.wrapperframework.utils.ConversionUtils;
import com.portal.pcm.FList;
import com.portal.pcm.PortalContext;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PurchaseOffersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOffersService.class);

    public PurchaseOffersResponse getPurchaseOffers(PurchaseOffers purchaseOffers) {
        LOGGER.info("getPurchaseOffers.."+purchaseOffers);
        PurchaseOffersResponse response = new PurchaseOffersResponse();
        if(validateInput(purchaseOffers,response)) {
            try {
                PortalContext fContext = ContextUtils.createContext();
                LOGGER.info("created context with BRM DB..." + fContext);
                if (fContext != null) {
                    FList flistPurchaseOffers = new FList();
                    Long dbNumber = fContext.getCurrentDB();
                    flistPurchaseOffers.set(FldPoid.getInst(), ConversionUtils.convertToPOID(dbNumber,ConversionUtils.getLong(purchaseOffers.getAccountNumber()), "/account"));
                    flistPurchaseOffers.set(FldMsid.getInst(), purchaseOffers.getLineId());
                    flistPurchaseOffers.set(FldEndT.getInst(), ConversionUtils.convertToTimeStamp(purchaseOffers.getEndT()));
                    flistPurchaseOffers.set(FldDeals.getInst(), createDeals(purchaseOffers.getBundles()));
                    LOGGER.info("Input FList being sent before calling opcode:" + flistPurchaseOffers);
                    FList flistOut = fContext.opcode(14785, flistPurchaseOffers);
                    response.setAccountNo(purchaseOffers.getAccountNumber());
                    response.setMSISDN(purchaseOffers.getLineId());
                    response = prepareResponse(flistOut, response);
                    flistOut.dump();
                    LOGGER.info("FList Output from BRM DB..." + flistOut);
                    fContext.close(true);
                }
            } catch (NumberFormatException ex) {
                response.setErrorCode("400");
                response.setErrorDescr(ex.getMessage());
                LOGGER.info("Number Format Exception Occured::"+ex);
                response.setRequestStatus("0");
            } catch (Exception ex) {
                response.setErrorCode("910000");
                response.setErrorDescr(ex.getMessage());
                response.setRequestStatus("0");
                LOGGER.info("Exception calling the Flist OpCode.14785..." + ex);
            }
        }
        return response;
    }

    private SparseArray createDeals(List<Bundle> bundles) {
        SparseArray dealsArray = new SparseArray();
        for(Bundle bundle : bundles) {
            FList dealElem = new FList();
            dealElem.set(FldDealName.getInst(),bundle.getBundleName());
            dealElem.set(FldDealCode.getInst(),bundle.getBundleId());
            dealsArray.add(dealElem);
        }
        return dealsArray;
    }

    private boolean validateInput(PurchaseOffers purchaseOffers,PurchaseOffersResponse response) {
        boolean isValid = false;
        if(!StringUtils.hasLength(purchaseOffers.getAccountNumber())) {
            response.setErrorDescr(PurchaseOffersMessages.ACCOUNT_NO_MISSING);
        } else if(!StringUtils.hasLength(purchaseOffers.getLineId())) {
            response.setErrorDescr(PurchaseOffersMessages.LINE_ID_MISSING);
        } else if(purchaseOffers.getBundles() == null || purchaseOffers.getBundles().isEmpty()) {
            response.setErrorDescr(PurchaseOffersMessages.BUNDLES_ARRAY_MISSING);
        } else if(purchaseOffers.getBundles() != null && !purchaseOffers.getBundles().isEmpty()) {
            for(Bundle bundle : purchaseOffers.getBundles()) {
                if(!StringUtils.hasLength(bundle.getBundleName())) {
                    response.setErrorDescr(PurchaseOffersMessages.BUNDLE_NAME_MISSING);
                }
                else if(!StringUtils.hasLength(bundle.getBundleId())) {
                    response.setErrorDescr(PurchaseOffersMessages.BUNDLE_ID_MISSING);
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

    private PurchaseOffersResponse prepareResponse(FList flistOut,PurchaseOffersResponse response) {
        if(flistOut !=null) {
            response.setRequestStatus("1");
        }
        return response;
    }

}
