package com.jaishna.wrapperframework.service;

import com.jaishna.wrapperframework.customfields.MRBEnumFld;
import com.jaishna.wrapperframework.customfields.MRBIntFld;
import com.jaishna.wrapperframework.customfields.MRBStringFld;
import com.jaishna.wrapperframework.model.*;
import com.jaishna.wrapperframework.model.errorhandling.ErrorResponse;
import com.jaishna.wrapperframework.model.purchaseoffers.PurchaseOffers;
import com.jaishna.wrapperframework.model.purchaseoffers.PurchaseOffersResponse;
import com.jaishna.wrapperframework.utils.ContextUtils;
import com.jaishna.wrapperframework.utils.ConversionUtils;
import com.portal.pcm.PortalContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.portal.pcm.*;
import com.portal.pcm.fields.*;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BillingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingService.class);

    public BillingAccountResponse createBillingAccount(BillingAccount billingAccount) {
        LOGGER.info("createBillingAccount.."+billingAccount);
        BillingAccountResponse response = new BillingAccountResponse();
        if(validateInput(billingAccount,response)) {
            try {
                PortalContext fContext = ContextUtils.createContext();
                LOGGER.info("created context with BRM DB..." + fContext);
                if (fContext != null) {
                    FList flistCommitCustomer = new FList();
                    Long dbNumber = fContext.getCurrentDB();
                    flistCommitCustomer.set(FldPoid.getInst(), ConversionUtils.strToPoid(billingAccount.getId(), dbNumber));
                    flistCommitCustomer.set(FldDealObj.getInst(), ConversionUtils.strToPoid(billingAccount.getDealObj(), dbNumber));
                    flistCommitCustomer.set(FldName.getInst(), billingAccount.getName());
                    flistCommitCustomer.set(FldFlags.getInst(), ConversionUtils.getInteger(billingAccount.getFlags()));
                    flistCommitCustomer.set(FldLocales.getInst(), createLocale(billingAccount.getLocales()));
                    flistCommitCustomer.set(FldPayinfo.getInst(), createPayInfo(billingAccount.getPayInfo(), dbNumber));
                    flistCommitCustomer.set(FldNameinfo.getInst(), createNameInfo(billingAccount.getNameInfo()));
                    flistCommitCustomer.set(FldProfiles.getInst(), createProfiles(billingAccount.getProfiles(), dbNumber));
                    flistCommitCustomer.set(FldDescr.getInst(), billingAccount.getDescr());
                    flistCommitCustomer.set(FldAcctinfo.getInst(), createAccountInfo(billingAccount.getAcctInfo(), dbNumber));
                    if (StringUtils.hasLength(billingAccount.getEndT())) {
                        flistCommitCustomer.set(FldEndT.getInst(), ConversionUtils.convertToTimeStamp(billingAccount.getEndT()));
                    }
                    flistCommitCustomer.set(FldBillinfo.getInst(), createBillInfo(billingAccount.getBillInfo(), dbNumber));
                    flistCommitCustomer.set(FldBalInfo.getInst(), createBalanceInfo(billingAccount.getBalInfo(), dbNumber));
                    LOGGER.info("Input being sent before calling opcode:" + flistCommitCustomer);
                    FList flistOut = fContext.opcode(19444, flistCommitCustomer);
                    response = prepareResponse(flistOut, response);
                    flistOut.dump();
                    LOGGER.info("FList Output from BRM DB..." + flistOut);
                    fContext.close(true);
                }
            } catch (Exception ex) {
                ErrorResponse error = new ErrorResponse();
                error.setCode("910000");
                error.setMessage(ex.getMessage());
                error.setException(ex);
                response.setErrorDetails(error);
                ex.printStackTrace();
                LOGGER.info("Exception calling the Flist OpCode.CUST_COMMIT_CUSTOMER..." + ex);
            }
        }
        return response;
    }

        private boolean validateInput(BillingAccount billingAccount, BillingAccountResponse response) {
            if(StringUtils.hasLength(billingAccount.getId()) && StringUtils.hasLength(billingAccount.getDealObj()) && billingAccount.getLocales()!=null && !billingAccount.getLocales().isEmpty() && billingAccount.getPayInfo()!=null && !billingAccount.getPayInfo().isEmpty()
                    && billingAccount.getNameInfo()!=null && !billingAccount.getNameInfo().isEmpty() && billingAccount.getProfiles()!=null && !billingAccount.getProfiles().isEmpty()
                    && billingAccount.getAcctInfo()!=null && !billingAccount.getAcctInfo().isEmpty()
                    && billingAccount.getBillInfo()!=null && !billingAccount.getBillInfo().isEmpty()) {
                return true;
            }
            else {
                ErrorResponse error = new ErrorResponse();
                error.setCode("400");
                error.setMessage("Input Validation Failed.Missing input Attributes.");
                response.setErrorDetails(error);
            }
            return false;
        }

    public SparseArray createLocale(List<Locale> locales) {
        SparseArray localesArray = new SparseArray();
        for(Locale locale : locales) {
            FList localeElem = new FList();
            localeElem.set(FldLocale.getInst(),locale.getLocale());
            localesArray.add(localeElem);
        }
        return localesArray;
    }

    private BillingAccountResponse prepareResponse(FList flistOut,BillingAccountResponse response) {
        response.setResult(flistOut);
        return response;
    }

    private SparseArray createBillInfo(List<BillInfo> billInfos,Long dbNumber) throws EBufException {
        SparseArray billInfoArray = new SparseArray();
        for(BillInfo billInfo : billInfos) {
            FList billInfoElem = new FList();
            billInfoElem.set(FldPoid.getInst(), ConversionUtils.strToPoid(billInfo.getId(),dbNumber));
            billInfoElem.set(FldActgFutureDom.getInst(), ConversionUtils.getInteger(billInfo.getActgFutureDom()));
            billInfoElem.set(FldBillinfoId.getInst(),billInfo.getBillinfoId());
            billInfoElem.set(FldBillWhen.getInst(),ConversionUtils.getInteger(billInfo.getBillWhen()));
            billInfoElem.set(FldPayType.getInst(), ConversionUtils.getInteger(billInfo.getPayType()));
            if(billInfo.getPayinfo()!=null) {
                billInfoElem.set(FldPayinfo.getInst(),createPayInfo(billInfo.getPayinfo(),dbNumber));
            }
            if(billInfo.getBalInfo()!=null) {
                billInfoElem.set(FldBalInfo.getInst(),createBalanceInfo(billInfo.getBalInfo(),dbNumber));
            }
            billInfoArray.add(billInfoElem);
        }
        return billInfoArray;
    }

    private SparseArray createBalanceInfo(List<BalanceInfo> balanceInfos,Long dbNumber) throws EBufException {
        SparseArray balanceInfoArray = new SparseArray();
        if(balanceInfos!=null && !balanceInfos.isEmpty()) {
            for(BalanceInfo balanceInfo : balanceInfos) {
                FList ballInfoElem = new FList();
                ballInfoElem.set(FldPoid.getInst(), ConversionUtils.strToPoid(balanceInfo.getId(),dbNumber));
                ballInfoElem.set(FldName.getInst(), balanceInfo.getName());
                if(balanceInfo.getBillInfo()!=null) {
                    ballInfoElem.set(FldBillinfo.getInst(),createBillInfo(balanceInfo.getBillInfo(),dbNumber));
                }
                balanceInfoArray.add(ballInfoElem);
            }
        } else {
            FList balanceInfoElem = new FList();
            balanceInfoArray.add(balanceInfoElem);
        }
        return balanceInfoArray;
    }

    private SparseArray createAccountInfo(List<AccountInfo> acctInfos,Long dbNumber) throws EBufException {
        SparseArray accountInfoArray = new SparseArray();
        for(AccountInfo accountInfo : acctInfos) {
            FList accountInfoElem = new FList();
            accountInfoElem.set(FldPoid.getInst(), ConversionUtils.strToPoid(accountInfo.getId(),dbNumber));
            accountInfoElem.set(FldAccountNo.getInst(), accountInfo.getAccountNo());
            accountInfoElem.set(FldGlSegment.getInst(), accountInfo.getGlSegment());
            accountInfoElem.set(FldActgType.getInst(), ConversionUtils.getInteger(accountInfo.getActgType()));
            if(accountInfo.getBalInfo() != null) {
                accountInfoElem.set(FldBalInfo.getInst(),createBalanceInfo(accountInfo.getBalInfo(),dbNumber));
            }
            accountInfoElem.set(FldCurrency.getInst(), ConversionUtils.getInteger(accountInfo.getCurrency()));
            accountInfoElem.set(FldBusinessType.getInst(), ConversionUtils.getInteger(accountInfo.getBusinessType()));
            accountInfoArray.add(accountInfoElem);
        }
        return accountInfoArray;
    }

    private SparseArray createPayInfo(List<PayInfo> payInfos,Long dbNumber) throws EBufException {
        // Create payInfo array and add the payInfos flist to it
        SparseArray payInfoArray = new SparseArray();
        if(payInfos!=null && !payInfos.isEmpty()) {
            for (PayInfo payInfo : payInfos) {
                FList payInfoElem = new FList();
                payInfoElem.set(FldPoid.getInst(), ConversionUtils.strToPoid(payInfo.getId(), dbNumber));
                payInfoElem.set(FldFlags.getInst(), ConversionUtils.getInteger(payInfo.getFlags()));
                payInfoElem.set(FldPayType.getInst(), ConversionUtils.getInteger(payInfo.getPayType()));
                payInfoElem.set(FldName.getInst(), payInfo.getName());
                payInfoElem.set(FldInheritedInfo.getInst(), createInteritedInfo(payInfo.getInheritedInfo()));
                payInfoArray.add(payInfoElem);
            }
        }else {
            FList payInfoElem = new FList();
            payInfoArray.add(payInfoElem);
        }
        return payInfoArray;
    }

    private FList createInteritedInfo(InheritedInfo inheritedInfo) throws EBufException {
        FList inheritedInfoSubstruct = new FList();
        if(inheritedInfo.getInvInfo()!=null) {
            inheritedInfoSubstruct.set(FldInvInfo.getInst(), createInvoiceInfo(inheritedInfo.getInvInfo()));
        }
        if(inheritedInfo.getCustomerCareInfo() != null) {
            inheritedInfoSubstruct.set(FldCustomerCareInfo.getInst(),createCustomerCareInfo(inheritedInfo.getCustomerCareInfo()));
        }
        if(inheritedInfo.getBillingInfo() != null) {
            inheritedInfoSubstruct.set(FldBillingInfo.getInst(),createBillingInfo(inheritedInfo.getBillingInfo()));
        }
        if(inheritedInfo.getAttributes() != null) {
            inheritedInfoSubstruct.set(FldAttributes.getInst(),createAttributes(inheritedInfo.getAttributes()));
        }
        if(inheritedInfo.getAccount() != null) {
            inheritedInfoSubstruct.set(FldAccount.getInst(),createAccountObject(inheritedInfo.getAccount()));
        }
        return inheritedInfoSubstruct;
    }

    private FList createAccountObject(Account account) {
        FList accountSubstruct = new FList();
        if(account.getCustomerSegment() != null) {
            accountSubstruct.set(MRBStringFld.getInst(100029,"MRB_FLD_CUSTOMER_SEGMENT"), account.getCustomerSegment());
        }
        accountSubstruct.set(MRBIntFld.getInst(100026,"MRB_FLD_PAY_METHOD_KEY"), ConversionUtils.getInteger(account.getPayMethodKey()));
        accountSubstruct.set(MRBEnumFld.getInst(100009,"MRB_FLD_CREDIT_STATUS"), ConversionUtils.getInteger(account.getCreditStatus()));
        return accountSubstruct;
    }

    private SparseArray createInvoiceInfo(List<InvoiceInfo> invoiceInfos) throws EBufException {
        SparseArray invInfoArray = new SparseArray();
        if(invoiceInfos!=null && !invoiceInfos.isEmpty()) {
            for (InvoiceInfo invoiceInfo : invoiceInfos) {
                FList invoiceInfoElem = new FList();
                invoiceInfoElem.set(FldAddress.getInst(), invoiceInfo.getAddress());
                invInfoArray.add(invoiceInfoElem);
            }
        }
        return invInfoArray;
    }

    private SparseArray createNameInfo(List<NameInfo> nameInfos) throws EBufException {
        // Create NameInfo array and add the NameInfos Array to it
        SparseArray nameInfoArray = new SparseArray();
        if(nameInfos!=null && !nameInfos.isEmpty()) {
            for (NameInfo nameInfo : nameInfos) {
                FList nameInfoElem = new FList();
                nameInfoElem.set(FldTitle.getInst(), nameInfo.getTitle());
                nameInfoElem.set(FldSalutation.getInst(), nameInfo.getSalutation());
                nameInfoElem.set(FldFirstName.getInst(), nameInfo.getFirstName());
                nameInfoElem.set(FldLastName.getInst(), nameInfo.getLastName());
                nameInfoElem.set(FldMiddleName.getInst(), nameInfo.getMiddleName());
                nameInfoElem.set(FldContactType.getInst(), nameInfo.getContactType());
                nameInfoElem.set(FldEmailAddr.getInst(), nameInfo.getEmailAddr());
                nameInfoElem.set(FldCountry.getInst(), nameInfo.getCountry());
                nameInfoElem.set(FldCity.getInst(), nameInfo.getCity());
                nameInfoElem.set(FldAddress.getInst(), nameInfo.getAddress());
                nameInfoElem.set(FldCompany.getInst(), nameInfo.getCompany());
                nameInfoArray.add(1,nameInfoElem);
            }
        }
        return nameInfoArray;
    }

    private SparseArray createProfiles(List<Profile> profiles,Long dbNumber) throws EBufException {
        // Create profiles array and add the profiles flist to it
        SparseArray profilesArray = new SparseArray();
        if(profiles!=null && !profiles.isEmpty()) {
            for (Profile profile : profiles) {
                FList profileElem = new FList();
                profileElem.set(FldProfileObj.getInst(), ConversionUtils.strToPoid(profile.getId(),dbNumber));
                profileElem.set(FldInheritedInfo.getInst(), createInteritedInfo(profile.getInheritedInfo()));
                profilesArray.add(profileElem);
            }
        }
        return profilesArray;
    }

    private FList createCustomerCareInfo(CustomerCareInfo customerCareInfo) {
        FList customerCareInfoSubstruct = new FList();
        customerCareInfoSubstruct.set(FldCustomerType.getInst(),ConversionUtils.getInteger(customerCareInfo.getCustomerType()));
        return customerCareInfoSubstruct;
    }

    private FList createBillingInfo(BillInfo billingInfo) {
        FList billingInfoSubstruct = new FList();
        billingInfoSubstruct.set(MRBStringFld.getInst(100019,"MRB_FLD_INTERCOM_CCC"),billingInfo.getIntercomCcc());
        billingInfoSubstruct.set(MRBStringFld.getInst(100001,"MRB_FLD_RECEIPT_ACCOUNT_NO"),billingInfo.getReceiptAccountNo());
        billingInfoSubstruct.set(MRBStringFld.getInst(100002,"MRB_FLD_PAYMENT_NO"),billingInfo.getPaymentNo());
        billingInfoSubstruct.set(MRBStringFld.getInst(100000,"MRB_FLD_CCC"),billingInfo.getCcc());
        billingInfoSubstruct.set(MRBStringFld.getInst(100003,"MRB_FLD_BILL_DIVERT_LOCATION"),billingInfo.getBillDivertLocation());
        billingInfoSubstruct.set(MRBIntFld.getInst(100004,"MRB_FLD_BILL_DIVERT_RMN_CYC"),ConversionUtils.getInteger(billingInfo.getBillDivertRmnCyc()));
        billingInfoSubstruct.set(MRBIntFld.getInst(100005,"MRB_FLD_BILL_WHOLD_RMN_CYC"),ConversionUtils.getInteger(billingInfo.getBillWholdRmnCyc()));
        billingInfoSubstruct.set(MRBIntFld.getInst(100006,"MRB_FLD_ITEMIZED_BILL_RMN_CYC"),ConversionUtils.getInteger(billingInfo.getItemizedBillRmnCyc()));
        if(billingInfo.getAttributes() != null) {
            billingInfoSubstruct.set(FldAttributes.getInst(), createAttributes(billingInfo.getAttributes()));
        }
        return billingInfoSubstruct;
    }

    private SparseArray createAttributes(List<Attribute> attributes) {
        SparseArray attributesArray = new SparseArray();
        if(attributes!=null && !attributes.isEmpty()) {
            for (Attribute attribute : attributes) {
                FList attributeElem = new FList();
                attributeElem.set(FldStatus.getInst(), ConversionUtils.getInteger(attribute.getStatus()));
                attributeElem.set(FldLocale.getInst(), attribute.getLocale());
                attributeElem.set(FldDeliveryDescr.getInst(),attribute.getDeliveryDescr1());
                attributesArray.add(attributeElem);
            }
        }
        return attributesArray;
    }

}
