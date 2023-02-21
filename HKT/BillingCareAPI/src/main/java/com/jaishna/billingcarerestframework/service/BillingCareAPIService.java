package com.jaishna.billingcarerestframework.service;

import com.jaishna.billingcarerestframework.controller.BillingCareAPIController;
import com.jaishna.billingcarerestframework.model.AdjustmentCodeRequest;
import com.jaishna.billingcarerestframework.model.AdjustmentCodeResponse;
import com.jaishna.billingcarerestframework.model.SearchResult;
import com.jaishna.billingcarerestframework.model.glCode.*;
import com.jaishna.billingcarerestframework.util.ContextUtils;
import com.jaishna.billingcarerestframework.util.ConversionUtils;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.PortalContext;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Enumeration;

@Service
public class BillingCareAPIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingCareAPIController.class);

    @Autowired
    ContextUtils contextUtils;

    public AdjustmentCodeResponse getAdjustmentCodeResults(AdjustmentCodeRequest adjustmentCodeRequest,AdjustmentCodeResponse adjustmentCodeResponse) {
        try {
            PortalContext fContext = contextUtils.createContext();
            LOGGER.info("created context with BRM DB..." + fContext);
            if (fContext != null) {
                FList flistAdjustmentCodeSearch = new FList();
                Long dbNumber = fContext.getCurrentDB();
                flistAdjustmentCodeSearch.set(FldPoid.getInst(), ConversionUtils.convertToPOID(dbNumber,new Long(-1),"/search"));
                if(StringUtils.hasLength(adjustmentCodeRequest.getReasonCode())) {
                    flistAdjustmentCodeSearch.set(FldStringId.getInst(), ConversionUtils.getInteger(adjustmentCodeRequest.getReasonCode()));
                    flistAdjustmentCodeSearch.set(FldActionMode.getInst(), 1);
                }
                if(StringUtils.hasLength(adjustmentCodeRequest.getPrefixAdjustment())) {
                    flistAdjustmentCodeSearch.set(FldReasonCode.getInst(), adjustmentCodeRequest.getPrefixAdjustment());
                    flistAdjustmentCodeSearch.set(FldActionMode.getInst(),2);
                }
                if(StringUtils.hasLength(adjustmentCodeRequest.getDescription())) {
                    flistAdjustmentCodeSearch.set(FldString.getInst(), adjustmentCodeRequest.getDescription());
                    flistAdjustmentCodeSearch.set(FldActionMode.getInst(),3);
                }
                if(StringUtils.hasLength(adjustmentCodeRequest.getGlid())) {
                    flistAdjustmentCodeSearch.set(FldGlId.getInst(), ConversionUtils.getInteger(adjustmentCodeRequest.getGlid()));
                    flistAdjustmentCodeSearch.set(FldActionMode.getInst(),4);
                }
                LOGGER.info("Input being sent before calling opcode:" + flistAdjustmentCodeSearch);
                FList flistOut = fContext.opcode(19447, flistAdjustmentCodeSearch);
                LOGGER.info("FList Output from BRM DB..." + flistOut);
                adjustmentCodeResponse.setRequestStatus("1");
                prepareResponse(flistOut, adjustmentCodeResponse);
                flistOut.dump();
                fContext.close(true);
            }
        } catch (Exception ex) {
            adjustmentCodeResponse.setRequestStatus("0");
            adjustmentCodeResponse.setErrorCode("910000");
            adjustmentCodeResponse.setErrorDescription(ex.getMessage());
            ex.printStackTrace();
            LOGGER.info("Exception calling the Flist OpCode.19447..." + ex);
        }
        return adjustmentCodeResponse;
    }

    private void prepareResponse(FList flistOut,AdjustmentCodeResponse adjustmentCodeResponse) {
        if(flistOut != null) {
            SparseArray array = null;
            try {
                array = flistOut.get(FldResults.getInst());
                LOGGER.info("Elements of the FldResults array:");
                Enumeration valueEnum = array.getValueEnumerator();
                while (valueEnum.hasMoreElements()) {
                    FList flist = (FList) valueEnum.nextElement();
                    SearchResult result = new SearchResult();
                    // retrieve the fields of the array element flist
                    result.setCreatedDate(flist.get(FldCreatedT.getInst()).toString());
                    result.setAdjustmentCode("" + flist.get(FldStringId.getInst()));
                    result.setEnglishDescription(flist.get(FldString.getInst()));
                    result.setChineseDescription(flist.get(FldReasonCode.getInst()));
                    result.setGlid("" + flist.get(FldGlId.getInst()));
                    adjustmentCodeResponse.getResults().add(result);
                }
            } catch (EBufException ebuf) {
                LOGGER.info("could not retrieve element from flist EBufException::"+ebuf);
                adjustmentCodeResponse.setRequestStatus("0");
                try {
                    adjustmentCodeResponse.setErrorCode(flistOut.get(FldErrorCode.getInst()));
                    adjustmentCodeResponse.setErrorDescription(flistOut.get(FldErrorDescr.getInst()));
                }catch (EBufException ebuff) {
                    LOGGER.info("could not retrieve Error details element from flist"+ebuff);
                }
                ebuf.printStackTrace();
                } catch (Exception ex) {
                adjustmentCodeResponse.setRequestStatus("0");
                try {
                    adjustmentCodeResponse.setErrorCode(flistOut.get(FldErrorCode.getInst()));
                    adjustmentCodeResponse.setErrorDescription(flistOut.get(FldErrorDescr.getInst()));
                }catch (EBufException ebuff) {
                    LOGGER.info("could not retrieve Error details element from flist"+ebuff);
                }
                LOGGER.info("could not retrieve element from flist Exception::"+ex);
                ex.printStackTrace();
                }
            }
    }

    private void prepareSingleResponse(FList flistOut,AdjustmentCodeResponse adjustmentCodeResponse) {
        if(flistOut != null) {
            try {
                SearchResult result = new SearchResult();
                result.setCreatedDate(flistOut.get(FldCreatedT.getInst()).toString());
                result.setAdjustmentCode("" + flistOut.get(FldStringId.getInst()));
                result.setEnglishDescription(flistOut.get(FldString.getInst()));
                result.setChineseDescription(flistOut.get(FldReasonCode.getInst()));
                result.setGlid("" + flistOut.get(FldGlId.getInst()));
                adjustmentCodeResponse.getResults().add(result);
            } catch (EBufException ebuf) {
                LOGGER.info("could not retrieve element from flist"+ebuf);
                adjustmentCodeResponse.setRequestStatus("0");
                adjustmentCodeResponse.setErrorCode("910000");
                adjustmentCodeResponse.setErrorDescription(ebuf.getMessage());
                ebuf.printStackTrace();
            } catch (Exception ex) {
                adjustmentCodeResponse.setRequestStatus("0");
                adjustmentCodeResponse.setErrorCode("910000");
                adjustmentCodeResponse.setErrorDescription(ex.getMessage());
                LOGGER.info("could not retrieve element from flist"+ex);
                ex.printStackTrace();
            }
        }
    }

    public GLCodeResponse getGLCodeResults(GLCodeRequest glCodeRequest, GLCodeResponse glCodeResponse) {
        try {
            PortalContext fContext = contextUtils.createContext();
            LOGGER.info("created context with BRM DB..." + fContext);
            if (fContext != null) {
                FList flistGLCodeSearch = new FList();
                Long dbNumber = fContext.getCurrentDB();
                flistGLCodeSearch.set(FldPoid.getInst(), ConversionUtils.convertToPOID(dbNumber,new Long(-1),"/search"));
                if(StringUtils.hasLength(glCodeRequest.getGlCode())) {
                    flistGLCodeSearch.set(FldGlAcct.getInst(), glCodeRequest.getGlCode());
                    flistGLCodeSearch.set(FldActionMode.getInst(),1);
                } else if(StringUtils.hasLength(glCodeRequest.getGlDescription())) {
                    flistGLCodeSearch.set(FldDescr.getInst(), glCodeRequest.getGlDescription());
                    flistGLCodeSearch.set(FldActionMode.getInst(),2);
                } else if(StringUtils.hasLength(glCodeRequest.getGlSegment())) {
                    flistGLCodeSearch.set(FldGlSegment.getInst(), glCodeRequest.getGlSegment());
                    flistGLCodeSearch.set(FldActionMode.getInst(),3);
                } else if(StringUtils.hasLength(glCodeRequest.getGlHistory())) {
                    flistGLCodeSearch.set(FldGlAcct.getInst(), glCodeRequest.getGlHistory());
                    flistGLCodeSearch.set(FldTimeFromStr.getInst(), glCodeRequest.getGlStartDate());
                    flistGLCodeSearch.set(FldTimeToStr.getInst(), glCodeRequest.getGlEndDate());
                    flistGLCodeSearch.set(FldActionMode.getInst(),4);
                } else if(StringUtils.hasLength(glCodeRequest.getGlPrefix())) {
                    flistGLCodeSearch.set(FldGlAcct.getInst(), glCodeRequest.getGlPrefix());
                    flistGLCodeSearch.set(FldActionMode.getInst(),5);
                }
                LOGGER.info("Input being sent before calling opcode #19433:" + flistGLCodeSearch);
                FList flistOut = fContext.opcode(19433, flistGLCodeSearch);
                LOGGER.info("FList Output from BRM DB..." + flistOut);
                glCodeResponse.setRequestStatus("1");
                prepareGLCodeResponse(flistOut, glCodeResponse);
                flistOut.dump();
                fContext.close(true);
            }
        } catch (Exception ex) {
            glCodeResponse.setRequestStatus("0");
            glCodeResponse.setErrorCode("910000");
            glCodeResponse.setErrorDescription(ex.getMessage());
            ex.printStackTrace();
            LOGGER.info("Exception calling the Flist OpCode.19433..." + ex);
        }
        return glCodeResponse;
    }

    private void prepareGLCodeResponse(FList flistOut,GLCodeResponse glCodeResponse) {
        if(flistOut != null) {
            SparseArray array = null;
            try {
                array = flistOut.get(FldResults.getInst());
                LOGGER.info("Elements of the FldResults array:");
                Enumeration valueEnum = array.getValueEnumerator();
                while (valueEnum.hasMoreElements()) {
                    FList flist = (FList) valueEnum.nextElement();
                    GLCodeSearchResult result = new GLCodeSearchResult();
                    // retrieve the fields of the array element flist
                    result.setGlSegment(flist.get(FldGlSegment.getInst()).toString());
                    result.setGlDescription(flist.get(FldDescr.getInst()).toString());
                    result.setGlAccount(flist.get(FldGlaccount.getInst()).toString());
                    result.setAttributeName(flist.get(FldAttrName.getInst()).toString());
                    result.setOrderId(flist.get(FldOrderId.getInst()).toString());
                    result.setCode(flist.get(FldCode.getInst()).toString());
                    result.setCodeString(flist.get(FldCodeStr.getInst()).toString());
                    result.setAccountCode(flist.get(FldAccountCode.getInst()).toString());
                    result.setCreatedDate(flist.get(FldCreatedT.getInst()).toString());
                    result.setModifiedDate(flist.get(FldModT.getInst()).toString());
                    result.setGlid("" + flist.get(FldGlId.getInst()));
                    result.setStatus("" + flist.get(FldStatusStr.getInst()));
                    result.setStringValue("" + flist.get(FldString.getInst()));
                    glCodeResponse.getResults().add(result);
                }
            } catch (EBufException ebuf) {
                LOGGER.info("could not retrieve element from flist EBufException::"+ebuf);
                glCodeResponse.setRequestStatus("0");
                try {
                    glCodeResponse.setErrorCode(flistOut.get(FldErrorCode.getInst()));
                    glCodeResponse.setErrorDescription(flistOut.get(FldErrorDescr.getInst()));
                }catch (EBufException ebuff) {
                    LOGGER.info("could not retrieve Error details element from flist"+ebuff);
                }
                ebuf.printStackTrace();
            } catch (Exception ex) {
                glCodeResponse.setRequestStatus("0");
                try {
                    glCodeResponse.setErrorCode(flistOut.get(FldErrorCode.getInst()));
                    glCodeResponse.setErrorDescription(flistOut.get(FldErrorDescr.getInst()));
                }catch (EBufException ebuff) {
                    LOGGER.info("could not retrieve Error details element from flist"+ebuff);
                }
                LOGGER.info("could not retrieve element from flist Exception::"+ex);
                ex.printStackTrace();
            }
        }
    }

    public GLCodeResponse getGLCodeDeleteResults(GLCodeRequest glCodeRequest, GLCodeResponse glCodeResponse) {
        try {
            PortalContext fContext = contextUtils.createContext();
            LOGGER.info("created context with BRM DB..." + fContext);
            if (fContext != null) {
                FList flistGLCodeSearch = new FList();
                Long dbNumber = fContext.getCurrentDB();
                flistGLCodeSearch.set(FldPoid.getInst(), ConversionUtils.convertToPOID(dbNumber,new Long(-1),"/search"));
                if(StringUtils.hasLength(glCodeRequest.getGlCode())) {
                    flistGLCodeSearch.set(FldGlAcct.getInst(), glCodeRequest.getGlCode());
                    flistGLCodeSearch.set(FldActionMode.getInst(),1);
                }
                LOGGER.info("Input being sent before calling opcode:" + flistGLCodeSearch);
                FList flistOut = fContext.opcode(19433, flistGLCodeSearch);
                LOGGER.info("FList Output from BRM DB..." + flistOut);
                glCodeResponse.setRequestStatus("1");
                prepareGLCodeResponse(flistOut, glCodeResponse);
                flistOut.dump();
                fContext.close(true);
            }
        } catch (Exception ex) {
            glCodeResponse.setRequestStatus("0");
            glCodeResponse.setErrorCode("910000");
            glCodeResponse.setErrorDescription(ex.getMessage());
            ex.printStackTrace();
            LOGGER.info("Exception calling the Flist OpCode.19434..." + ex);
        }
        return glCodeResponse;
    }

    private void prepareGLCodeDeleteResponse(FList flistOut, GLCodeDeleteResponse glCodeDeleteResponse) {
        if(flistOut != null) {
            SparseArray array = null;
            try {
                glCodeDeleteResponse.setGlSegment(flistOut.get(FldGlSegment.getInst()));
                array = flistOut.get(FldResults.getInst());
                LOGGER.info("Elements of the FldResults array:");
                Enumeration valueEnum = array.getValueEnumerator();
                while (valueEnum.hasMoreElements()) {
                    FList flist = (FList) valueEnum.nextElement();
                    GLCodeDeleteSearchResult result = new GLCodeDeleteSearchResult();
                    // retrieve the fields of the array element flist
                    result.setDescription(flist.get(FldDescr.getInst()).toString());
                    result.setAttribute(flist.get(FldAttrName.getInst()).toString());
                    result.setCreatedDate(flist.get(FldCreatedT.getInst()).toString());
                    result.setModifiedDate(flist.get(FldModT.getInst()).toString());
                    result.setGlid("" + flist.get(FldGlId.getInst()));
                    result.setStatus("" + flist.get(FldStatusStr.getInst()));
                    result.setGlARAccount(flist.get(FldGlArAcct.getInst()));
                    result.setGlOffSetAccount(flist.get(FldGlOffsetAcct.getInst()));
                    glCodeDeleteResponse.getResults().add(result);
                }
            } catch (EBufException ebuf) {
                LOGGER.info("could not retrieve element from flist EBufException::"+ebuf);
                glCodeDeleteResponse.setRequestStatus("0");
                try {
                    glCodeDeleteResponse.setErrorCode(flistOut.get(FldErrorCode.getInst()));
                    glCodeDeleteResponse.setErrorDescription(flistOut.get(FldErrorDescr.getInst()));
                }catch (EBufException ebuff) {
                    LOGGER.info("could not retrieve Error details element from flist"+ebuff);
                }
                ebuf.printStackTrace();
            } catch (Exception ex) {
                glCodeDeleteResponse.setRequestStatus("0");
                try {
                    glCodeDeleteResponse.setErrorCode(flistOut.get(FldErrorCode.getInst()));
                    glCodeDeleteResponse.setErrorDescription(flistOut.get(FldErrorDescr.getInst()));
                }catch (EBufException ebuff) {
                    LOGGER.info("could not retrieve Error details element from flist"+ebuff);
                }
                LOGGER.info("could not retrieve element from flist Exception::"+ex);
                ex.printStackTrace();
            }
        }
    }

    public GLDeleteResponse glDelete(GLDeleteRequest glDeleteRequest,GLDeleteResponse glDeleteResponse) {
        try {
            PortalContext fContext = contextUtils.createContext();
            LOGGER.info("created context with BRM DB..." + fContext);
            if (fContext != null) {
                FList flistGLDelete = new FList();
                Long dbNumber = fContext.getCurrentDB();
                flistGLDelete.set(FldPoid.getInst(), ConversionUtils.convertToPOID(dbNumber,new Long(-1),"/search"));
                flistGLDelete.set(FldResults.getInst(),createResultsArray(dbNumber,glDeleteRequest));
                LOGGER.info("Input being sent before calling opcode 19434:" + flistGLDelete);
                FList flistOut = fContext.opcode(19434, flistGLDelete);
                LOGGER.info("FList Output from BRM DB..." + flistOut);
                glDeleteResponse.setRequestStatus("1");
                prepareGLDeleteResponse(flistOut, glDeleteResponse);
                flistOut.dump();
                fContext.close(true);
            }
        } catch (Exception ex) {
            glDeleteResponse.setRequestStatus("0");
            glDeleteResponse.setErrorCode("910000");
            glDeleteResponse.setErrorDescription(ex.getMessage());
            ex.printStackTrace();
            LOGGER.info("Exception calling the Flist OpCode.19434..." + ex);
        }
        return glDeleteResponse;
    }

    public SparseArray createResultsArray(Long dbNumber,GLDeleteRequest glDeleteRequest) {
        SparseArray resultsArray = new SparseArray();
        int index = 1;
        for(GlDeleteInput glDeleteInput : glDeleteRequest.getGlDeleteInputs()) {
            FList resultsElem = new FList();
            resultsElem.set(FldPoid.getInst(),ConversionUtils.convertToPOID(dbNumber,new Long(-1),"/config/glid"));
            resultsElem.set(FldGlSegment.getInst(),glDeleteInput.getGlSegment());
            resultsElem.set(FldGlAcct.getInst(),glDeleteInput.getGlId());
            resultsElem.set(FldDescr.getInst(),glDeleteInput.getDescription());
            resultsElem.set(FldStatusStr.getInst(),glDeleteInput.getType());
            resultsElem.set(FldAction.getInst(),glDeleteInput.getAttribute());
            resultsElem.set(FldOrderId.getInst(),glDeleteInput.getCreditWorksOrder());
            resultsElem.set(FldCode.getInst(),glDeleteInput.getCreditCostCenterCode());
            resultsElem.set(FldGlaccount.getInst(),glDeleteInput.getCreditGLAccountCode());
            resultsElem.set(FldString.getInst(),glDeleteInput.getDebitWorksOrder());
            resultsElem.set(FldCodeStr.getInst(),glDeleteInput.getDebitCostCenterCode());
            resultsElem.set(FldAccountCode.getInst(),glDeleteInput.getDebitGLAccountCode());
            resultsArray.add(index,resultsElem);
            index++;
        }
        return resultsArray;
    }

    GLDeleteResponse prepareGLDeleteResponse(FList flistOut, GLDeleteResponse glDeleteResponse) {
        glDeleteResponse.setDeleteStatus("Deleted Successfully");
        return glDeleteResponse;
    }


}
