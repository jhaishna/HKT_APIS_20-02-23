package com.samplecompany.modules;

import com.oracle.communications.brm.cc.common.BRMUtility;
import com.oracle.communications.brm.cc.exceptions.ApplicationException;
import com.oracle.communications.brm.cc.model.Payment;
import com.oracle.communications.brm.cc.modules.pcm.PCMPaymentModule;
import com.oracle.communications.brm.cc.modules.pcm.workers.PCMBaseOps;
import com.oracle.communications.brm.cc.modules.pcm.workers.PaymentWorker;
import com.portal.pcm.FList;
import com.portal.pcm.PortalContext;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.FldChannelId;
import com.portal.pcm.fields.FldCharges;
import java.util.Enumeration;
import java.util.LinkedHashMap;
/**
 *
 * @author user
 */
public class CustomPaymentChannelModule extends PCMPaymentModule {
    
//REST Customization
@Override
public String makePayment(Payment payment, boolean savePaymentType) {
PortalContext ctx = null;
        String paymentId = null;
        try {
            ctx = BRMUtility.getConnection();
            PCMBaseOps baseOps = new PCMBaseOps(ctx);
            PaymentWorker paymentWorker = new PaymentWorker();
            paymentWorker.setBaseOps(baseOps);
            paymentWorker.setUserContext(getUserContext());
            FList inputFlist = paymentWorker.convertToInputFListForMakePayment(payment,10001);
            LinkedHashMap map = (LinkedHashMap) payment.getExtension();
            String paymentChannelId = (String) map.get("paymentChannel");
            Integer channelId = null;
            try {
            channelId = Integer.parseInt(paymentChannelId);
            } catch(Exception e) {
              System.out.println("Exception while converting ChannelId .." + paymentChannelId);
              channelId = 61;
            }   
            
            System.out.println("inputFlist from Payment payload .." + inputFlist);
            if (inputFlist.hasField(FldCharges.getInst())) {
                SparseArray chargesArray = inputFlist.get(FldCharges.getInst());
                System.out.println("chargesArray Payment payload .." + chargesArray);
                Enumeration<FList> resEnum = chargesArray.getValueEnumerator();
                while (resEnum.hasMoreElements()) {
                    FList chargesArrayFlist = resEnum.nextElement();
                    System.out.println("setting channelId to payment payload manually.. " + chargesArrayFlist);
                    chargesArrayFlist.set(FldChannelId.getInst(), channelId);
                }
            }
            System.out.println("calling invokeMakePayment after editing the Flist..." + inputFlist);
            paymentId = paymentWorker.invokeMakePayment(inputFlist);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (paymentId == null) {
                System.out.println("Unable to makePayment " + ex);
                throw new ApplicationException(ex);
            }
        } finally {
            if (ctx != null) {
                BRMUtility.releaseConnection(ctx);
            }
        }
        return paymentId;
    }
    
}
