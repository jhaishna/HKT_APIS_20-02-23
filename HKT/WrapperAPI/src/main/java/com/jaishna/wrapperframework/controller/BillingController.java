package com.jaishna.wrapperframework.controller;

import com.jaishna.wrapperframework.model.BillingAccount;
import com.jaishna.wrapperframework.model.BillingAccountResponse;
import com.jaishna.wrapperframework.model.errorhandling.ErrorResponse;
import com.jaishna.wrapperframework.model.subscriptions.SubscriptionAccount;
import com.jaishna.wrapperframework.model.subscriptions.SubscriptionResponse;
import com.jaishna.wrapperframework.model.purchaseoffers.PurchaseOffers;
import com.jaishna.wrapperframework.model.purchaseoffers.PurchaseOffersResponse;
import com.jaishna.wrapperframework.service.BillingService;
import com.jaishna.wrapperframework.service.PurchaseOffersService;
import com.jaishna.wrapperframework.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class BillingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingController.class);

    @Autowired
    private BillingService billingService;

    @Autowired
    private PurchaseOffersService purchaseOffersService;

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Method to createBillingAccount Information
     *
     * @return
     */
    @RequestMapping(value = "/createBillingAccount", method = RequestMethod.POST,consumes = "application/json",produces="application/json")
    public ResponseEntity<?> createBillingAccount(@RequestBody BillingAccount billingAccount,@RequestHeader HttpHeaders headers) {
        LOGGER.info("createBillingAccount..");
        AtomicBoolean valid = new AtomicBoolean(false);
        BillingAccountResponse billingAccountResponse = new BillingAccountResponse();
        if(headers.get("X-Ruby-CorrelationId")!= null && headers.get("X-Ruby-SystemIdentifier")!= null) {
            valid.set(true);
        }
        if(!valid.get()) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage("Validation error (SystemIdentifier or CorrelationId is missing)");
            response.setCode("800000");
            billingAccountResponse.setErrorDetails(response);
            return new ResponseEntity<>(billingAccountResponse, HttpStatus.BAD_REQUEST);
        }
        billingAccountResponse = billingService.createBillingAccount(billingAccount);
        LOGGER.info("createBillingAccount returned from BRM DB :"+billingAccountResponse);
        if(billingAccountResponse!=null && billingAccountResponse.getErrorDetails()!=null) {
            if(billingAccountResponse.getErrorDetails().getCode()!=null && "400".equals(billingAccountResponse.getErrorDetails().getCode())) {
                return new ResponseEntity<>(billingAccountResponse, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(billingAccountResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(billingAccountResponse, HttpStatus.OK);
    }

    /**
     * Method to get all purchaseOffers Information
     *
     * @return
     */
    @RequestMapping(value = "/purchaseOffers", method = RequestMethod.POST,consumes = "application/json",produces="application/json")
    public ResponseEntity<?> getPurchaseOffers(@RequestBody PurchaseOffers purchaseOffers,@RequestHeader HttpHeaders headers) {
        LOGGER.info("getPurchaseOffers..");
        AtomicBoolean valid = new AtomicBoolean(false);
        PurchaseOffersResponse purchaseOffersResponse = new PurchaseOffersResponse();
        if(headers.get("X-Ruby-CorrelationId")!= null && headers.get("X-Ruby-SystemIdentifier")!= null) {
            valid.set(true);
        }
        if(!valid.get()) {
            ErrorResponse response = new ErrorResponse();
            purchaseOffersResponse.setErrorDescr("Validation error (SystemIdentifier or CorrelationId is missing)");
            purchaseOffersResponse.setErrorCode("800000");
            return new ResponseEntity<>(purchaseOffersResponse, HttpStatus.BAD_REQUEST);
        }
        purchaseOffersResponse = purchaseOffersService.getPurchaseOffers(purchaseOffers);
        LOGGER.info("getPurchaseOffers returned from BRM DB :"+purchaseOffersResponse);
        if(purchaseOffersResponse!=null && purchaseOffersResponse.getErrorDescr()!=null) {
            if("400".equals(purchaseOffersResponse.getErrorCode())) {
                return new ResponseEntity<>(purchaseOffersResponse, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(purchaseOffersResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(purchaseOffersResponse, HttpStatus.OK);
    }

    /**
     * Method to get all createSubrscriptionAccount Information
     *
     * @return
     */
    @RequestMapping(value = "/createSubscriptionAccount", method = RequestMethod.POST,consumes = "application/json",produces="application/json")
    public ResponseEntity<?> createSubscriptionAccount(@RequestBody SubscriptionAccount subscriptionAccount,@RequestHeader HttpHeaders headers) {
        LOGGER.info("createSubscriptionAccount..");
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
        AtomicBoolean valid = new AtomicBoolean(false);
        if(headers.get("X-Ruby-CorrelationId")!= null && headers.get("X-Ruby-SystemIdentifier")!= null) {
            valid.set(true);
        }
        if(!valid.get()) {
            subscriptionResponse.setErrorDescr("Validation error (SystemIdentifier or CorrelationId is missing)");
            subscriptionResponse.setErrorCode("800000");
            return new ResponseEntity<>(subscriptionResponse, HttpStatus.BAD_REQUEST);
        }
        subscriptionResponse = subscriptionService.createSubscriptionAccount(subscriptionAccount);
        LOGGER.info("createSubscriptionAccount returned from BRM DB :"+subscriptionResponse);
        if(subscriptionResponse!=null && subscriptionResponse.getErrorDescr()!=null) {
            if(subscriptionResponse.getErrorDescr()!=null && "400".equals(subscriptionResponse.getErrorCode())) {
                return new ResponseEntity<>(subscriptionResponse, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(subscriptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(subscriptionResponse, HttpStatus.OK);
    }

}
