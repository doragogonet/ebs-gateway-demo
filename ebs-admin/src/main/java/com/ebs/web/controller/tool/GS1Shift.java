package com.ebs.web.controller.tool;

import com.alibaba.fastjson.JSON;
import com.ebs.common.utils.StringUtils;
import com.ebs.rfid.GS1Item;
import com.ebs.rfid.TagQuery;
import org.epctagcoder.option.CPI.CPIFilterValue;
import org.epctagcoder.option.CPI.CPITagSize;
import org.epctagcoder.option.GDTI.GDTIFilterValue;
import org.epctagcoder.option.GDTI.GDTITagSize;
import org.epctagcoder.option.GIAI.GIAIFilterValue;
import org.epctagcoder.option.GIAI.GIAITagSize;
import org.epctagcoder.option.GRAI.GRAIFilterValue;
import org.epctagcoder.option.GRAI.GRAITagSize;
import org.epctagcoder.option.GSRN.GSRNFilterValue;
import org.epctagcoder.option.GSRN.GSRNTagSize;
import org.epctagcoder.option.GSRNP.GSRNPFilterValue;
import org.epctagcoder.option.GSRNP.GSRNPTagSize;
import org.epctagcoder.option.SGLN.SGLNFilterValue;
import org.epctagcoder.option.SGLN.SGLNTagSize;
import org.epctagcoder.option.SGTIN.SGTINExtensionDigit;
import org.epctagcoder.option.SGTIN.SGTINFilterValue;
import org.epctagcoder.option.SGTIN.SGTINTagSize;
import org.epctagcoder.option.SSCC.SSCCExtensionDigit;
import org.epctagcoder.option.SSCC.SSCCFilterValue;
import org.epctagcoder.option.SSCC.SSCCTagSize;
import org.epctagcoder.parse.CPI.ParseCPI;
import org.epctagcoder.parse.GDTI.ParseGDTI;
import org.epctagcoder.parse.GIAI.ParseGIAI;
import org.epctagcoder.parse.GRAI.ParseGRAI;
import org.epctagcoder.parse.GSRN.ParseGSRN;
import org.epctagcoder.parse.GSRNP.ParseGSRNP;
import org.epctagcoder.parse.SGLN.ParseSGLN;
import org.epctagcoder.parse.SGTIN.ParseSGTIN;
import org.epctagcoder.parse.SSCC.ParseSSCC;
import org.epctagcoder.result.*;

public class GS1Shift {

    private final static String GDTI_96 = "gdti";
    private final static byte GDTI_96_HEADER = 0B00101100;
    private final static String GSRN_96 = "gsrn";
    private final static byte GSRN_96_HEADER = 0B00101101;
    private final static String SSCC_96 = "sscc";
    private final static byte SSCC_96_HEADER = 0B00110001;
    private final static String SGTIN_96 = "sgtin";
    private final static byte SGTIN_96_HEADER = 0B00110000;
    private final static String SGLN_96 = "sgln";
    private final static byte SGLN_96_HEADER = 0B00110010;
    private final static String GRAI_96 = "grai";
    private final static byte GRAI_96_HEADER = 0B00110011;
    private final static String GIAI_96 = "giai";
    private final static byte GIAI_96_HEADER = 0B00110100;
    private final static String CPI_96 = "cpi";
    private final static byte CPI_96_HEADER = 0B00111100;
    private final static String GSRNP_96 = "gsrnp";
    private final static byte GSRNP_96_HEADER = 0B00101110;
    private final static String GDTI_174 = "gdti_174";
    private final static byte GDTI_174_HEADER = 0B00111110;
    private final static String SGTIN_198 = "sgtin_198";
    private final static byte SGTIN_198_HEADER = 0B00110110;
    private final static String SGLN_195 = "sgln_195";
    private final static byte SGLN_195_HEADER = 0B00111001;
    private final static String GRAI_170 = "grai_170";
    private final static byte GRAI_170_HEADER = 0B00110111;
    private final static String GIAI_202 = "giai_202";
    private final static byte GIAI_202_HEADER = 0B00111000;

//    public static String decodeEpc(String epcScheme, String rfidTag) throws Exception {
//
//        String gs1JsonStr = "";
//        try {
//            switch (epcScheme) {
//                case GDTI_96:
//                    ParseGDTI parseGDTI = ParseGDTI.Builder()
//                            .withRFIDTag(rfidTag)
//                            .build();
//                    GDTI gdti = parseGDTI.getGDTI();
//                    gs1JsonStr = gdti.toString();
//                    break;
//                case GSRN_96:
//                    ParseGSRN parseGSRN = ParseGSRN.Builder()
//                            .withRFIDTag(rfidTag)
//                            .build();
//                    GSRN gsrn = parseGSRN.getGSRN();
//                    gs1JsonStr = gsrn.toString();
//                    break;
//                case GSRNP_96:
//                    ParseGSRNP parseGSRNP = ParseGSRNP.Builder()
//                            .withRFIDTag(rfidTag)
//                            .build();
//                    GSRNP gsrnp = parseGSRNP.getGSRNP();
//                    gs1JsonStr = gsrnp.toString();
//                    break;
//                case SGTIN_96:
//                    ParseSGTIN parseSGTIN = ParseSGTIN.Builder()
//                            .withRFIDTag(rfidTag)
//                            .build();
//                    SGTIN sgtin = parseSGTIN.getSGTIN();
//                    gs1JsonStr = sgtin.toString();
//                    break;
//                case SSCC_96:
//                    ParseSSCC parseSSCC = ParseSSCC.Builder()
//                            .withRFIDTag(rfidTag)
//                            .build();
//                    SSCC sscc = parseSSCC.getSSCC();
//                    gs1JsonStr = sscc.toString();
//                    break;
//                case SGLN_96:
//                    ParseSGLN parseSGLN = ParseSGLN.Builder()
//                            .withRFIDTag(rfidTag)
//                            .build();
//                    SGLN sgln = parseSGLN.getSGLN();
//                    gs1JsonStr = sgln.toString();
//                    break;
//                case GRAI_96:
//                    ParseGRAI parseGRAI = ParseGRAI.Builder()
//                            .withRFIDTag(rfidTag)
//                            .build();
//                    GRAI grai = parseGRAI.getGRAI();
//                    gs1JsonStr = grai.toString();
//                    break;
//                case GIAI_96:
//                    ParseGIAI parseGIAI = ParseGIAI.Builder()
//                            .withRFIDTag(rfidTag)
//                            .build();
//                    GIAI giai = parseGIAI.getGIAI();
//                    gs1JsonStr = giai.toString();
//                    break;
//                case CPI_96:
//                    ParseCPI parseCPI = ParseCPI.Builder()
//                            .withRFIDTag(rfidTag)
//                            .build();
//                    CPI cpi = parseCPI.getCPI();
//                    gs1JsonStr = cpi.toString();
//                    break;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            gs1JsonStr = null;
//        }
//
//        return gs1JsonStr;
//    }

    public static String encodeEpc(GS1Item gs1) throws Exception {

        String rfidTag = "";

        try {
            String code1 = gs1.getCode1();
            String code2 = gs1.getCode2();
            String code3 = gs1.getCode3();
            String filter = gs1.getFilter();
            String extension = gs1.getExtension();
            switch (gs1.getGs1Type()) {
                case GDTI_96:
                    ParseGDTI parseGDTI = ParseGDTI.Builder()
                            .withCompanyPrefix(code1)
                            .withDocType(code2)
                            .withserial(code3)
                            .withTagSize(GDTITagSize.BITS_96)
                            .withFilterValue(GDTIFilterValue.valueOf(filter))
                            .build();
                    GDTI gdti = parseGDTI.getGDTI();
                    System.out.println("parseGDTI:" + gdti.toString());
                    rfidTag = gdti.getRfidTag();
                    break;
                case GSRN_96:
                    ParseGSRN parseGSRN = ParseGSRN.Builder()
                            .withCompanyPrefix(code1)
                            .withServiceReference(code2)
                            .withTagSize(GSRNTagSize.BITS_96)
                            .withFilterValue(GSRNFilterValue.valueOf(filter))
                            .build();
                    GSRN gsrn = parseGSRN.getGSRN();
                    System.out.println("parseGSRN:" + gsrn.toString());
                    rfidTag = gsrn.getRfidTag();
                    break;
                case SSCC_96:
                    ParseSSCC parseSSCC = ParseSSCC.Builder()
                            .withCompanyPrefix(code1)
                            .withExtensionDigit(SSCCExtensionDigit.forCode(Integer.parseInt(extension)))
                            .withSerial(code3)
                            .withTagSize(SSCCTagSize.BITS_96)
                            .withFilterValue(SSCCFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    SSCC sscc = parseSSCC.getSSCC();
                    System.out.println("parseSSCC:" + sscc.toString());
                    rfidTag = sscc.getRfidTag();
                    break;
                case SGTIN_96:
                    ParseSGTIN parseSgtin = ParseSGTIN.Builder()
                            .withCompanyPrefix(code1)
                            .withExtensionDigit(SGTINExtensionDigit.forCode(Integer.parseInt(extension)))
                            .withItemReference(code2)
                            .withSerial(code3)
                            .withTagSize(SGTINTagSize.BITS_96)
                            .withFilterValue(SGTINFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    SGTIN sgtin = parseSgtin.getSGTIN();
                    System.out.println("parseSGTIN:" + sgtin.toString());
                    rfidTag = sgtin.getRfidTag();
                    break;

                case SGLN_96:
                    ParseSGLN parseSgln = ParseSGLN.Builder()
                            .withCompanyPrefix(code1)
                            .withLocationReference(code3)
                            .withExtension(String.valueOf(extension))
                            .withTagSize(SGLNTagSize.BITS_96)
                            .withFilterValue(SGLNFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    SGLN sgln = parseSgln.getSGLN();
                    System.out.println("parseSGLN:" + sgln.toString());
                    rfidTag = sgln.getRfidTag();
                    break;
                case GRAI_96:
                    ParseGRAI parseGrai = ParseGRAI.Builder()
                            .withCompanyPrefix(code1)
                            .withAssetType(code2)
                            .withserial(code3)
                            .withTagSize(GRAITagSize.BITS_96)
                            .withFilterValue(GRAIFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    GRAI grai = parseGrai.getGRAI();
                    System.out.println("parseGrai:" + grai.toString());
                    rfidTag = grai.getRfidTag();
                    break;
                case GIAI_96:
                    ParseGIAI parseGiai = ParseGIAI.Builder()
                            .withCompanyPrefix(code1)
                            .withIndividualAssetReference(code2)
                            .withTagSize(GIAITagSize.BITS_96)
                            .withFilterValue(GIAIFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    GIAI giai = parseGiai.getGIAI();
                    System.out.println("parseGiai:" + giai.toString());
                    rfidTag = giai.getRfidTag();
                    break;
                case CPI_96:
                    ParseCPI parseCpi = ParseCPI.Builder()
                            .withCompanyPrefix(code1)
                            .withComponentPartReference(code2)
                            .withSerial(code3)
                            .withTagSize(CPITagSize.BITS_96)
                            .withFilterValue(CPIFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    CPI cpi = parseCpi.getCPI();
                    System.out.println("parseCpi:" + cpi.toString());
                    rfidTag = cpi.getRfidTag();
                    break;
                case GSRNP_96:
                    ParseGSRNP parseGsrnp = ParseGSRNP.Builder()
                            .withCompanyPrefix(code1)
                            .withServiceReference(code3)
                            .withTagSize(GSRNPTagSize.BITS_96)
                            .withFilterValue(GSRNPFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    GSRNP gsrnp = parseGsrnp.getGSRNP();
                    System.out.println("parseCpi:" + gsrnp.toString());
                    rfidTag = gsrnp.getRfidTag();
                    break;
                case GDTI_174:
                    ParseGDTI parseGDTI_174 = ParseGDTI.Builder()
                            .withCompanyPrefix(code1)
                            .withDocType(code2)
                            .withserial(code3)
                            .withTagSize(GDTITagSize.BITS_174)
                            .withFilterValue(GDTIFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    GDTI gdti_174 = parseGDTI_174.getGDTI();
                    System.out.println("parseGDTI_174:" + gdti_174.toString());
                    rfidTag = gdti_174.getRfidTag();
                    break;
                case SGTIN_198:
                    ParseSGTIN parseSgtin_198 = ParseSGTIN.Builder()
                            .withCompanyPrefix(code1)
                            .withExtensionDigit(SGTINExtensionDigit.forCode(Integer.parseInt(extension)))
                            .withItemReference(code2)
                            .withSerial(code3)
                            .withTagSize(SGTINTagSize.BITS_198)
                            .withFilterValue(SGTINFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    SGTIN sgtin_198 = parseSgtin_198.getSGTIN();
                    System.out.println("parseSGTIN:" + sgtin_198.toString());
                    rfidTag = sgtin_198.getRfidTag();
                    break;
                case SGLN_195:
                    ParseSGLN parseSgln_195 = ParseSGLN.Builder()
                            .withCompanyPrefix(code1)
                            .withLocationReference(code3)
                            .withExtension(String.valueOf(extension))
                            .withTagSize(SGLNTagSize.BITS_195)
                            .withFilterValue(SGLNFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    SGLN sgln_195 = parseSgln_195.getSGLN();
                    System.out.println("parseSgln_195:" + sgln_195.toString());
                    rfidTag = sgln_195.getRfidTag();
                    break;
                case GRAI_170:
                    ParseGRAI parseGrai_170 = ParseGRAI.Builder()
                            .withCompanyPrefix(code1)
                            .withAssetType(code2)
                            .withserial(code3)
                            .withTagSize(GRAITagSize.BITS_170)
                            .withFilterValue(GRAIFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    GRAI grai_170 = parseGrai_170.getGRAI();
                    System.out.println("parseGrai_170:" + grai_170.toString());
                    rfidTag = grai_170.getRfidTag();
                    break;
                case GIAI_202:
                    ParseGIAI parseGiai_202 = ParseGIAI.Builder()
                            .withCompanyPrefix(code1)
                            .withIndividualAssetReference(code2)
                            .withTagSize(GIAITagSize.BITS_202)
                            .withFilterValue(GIAIFilterValue.forCode(Integer.parseInt(filter)))
                            .build();
                    GIAI giai_202 = parseGiai_202.getGIAI();
                    System.out.println("parseGiai_202:" + giai_202.toString());
                    rfidTag = giai_202.getRfidTag();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            rfidTag = null;
        }

        return rfidTag;
    }

    public static boolean checkEpc(TagQuery query, String epc) throws Exception {
        System.out.println("isGS1:"+query.getIsGS1());
        if (!query.getIsGS1()) {
            return true;
        }
        GS1Item gs1 = decodeEpc(epc);
        System.out.println(JSON.toJSONString(gs1));
        if (gs1 != null) {
            if (StringUtils.isNotEmpty(query.getTagGs1())) {
                if (!gs1.getGs1Type().equals(query.getTagGs1())) {
                    return false;
                }
            }
            if (StringUtils.isNotEmpty(query.getEpcCode1())) {
                if (!gs1.getCode1().equals(query.getEpcCode1())) {
                    return false;
                }
            }
            if (StringUtils.isNotEmpty(query.getEpcCode2())) {
                if (!gs1.getCode2().equals(query.getEpcCode2())) {
                    return false;
                }
            }
            if (StringUtils.isNotEmpty(query.getEpcCode3())) {
                if (!gs1.getCode3().equals(query.getEpcCode3())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static GS1Item decodeEpc(String epc) {
        System.out.println("decodeEpc:"+epc);
        GS1Item gs1 = null;
        try {
            String epcBin = hexToBinary(epc);
            if (epcBin.length() >= 8) {
                String gs1Header = epcBin.substring(0, 8);
                gs1 = new GS1Item();
                switch (Integer.parseInt(gs1Header, 2)) {
                    case GDTI_96_HEADER:
                        ParseGDTI parseGDTI = ParseGDTI.Builder()
                                .withRFIDTag(epc)
                                .build();
                        GDTI gdti = parseGDTI.getGDTI();
                        gs1.setCode1(gdti.getCompanyPrefix());
                        gs1.setCode3(gdti.getSerial());
                        gs1.setGs1Type("gdti");
                        gs1.setJsonStr(gdti.toString());
                        break;
                    case GSRN_96_HEADER:
                        ParseGSRN parseGSRN = ParseGSRN.Builder()
                                .withRFIDTag(epc)
                                .build();
                        GSRN gsrn = parseGSRN.getGSRN();
                        gs1.setCode1(gsrn.getCompanyPrefix());
                        gs1.setCode3(gsrn.getServiceReference());
                        gs1.setGs1Type("gsrn");
                        gs1.setJsonStr(gsrn.toString());
                        break;
                    case GSRNP_96_HEADER:
                        ParseGSRNP parseGSRNP = ParseGSRNP.Builder()
                                .withRFIDTag(epc)
                                .build();
                        GSRNP gsrnp = parseGSRNP.getGSRNP();
                        gs1.setCode1(gsrnp.getCompanyPrefix());
                        gs1.setCode3(gsrnp.getServiceReference());
                        gs1.setGs1Type("gsrpn");
                        gs1.setJsonStr(gsrnp.toString());
                        break;
                    case SGTIN_96_HEADER:
                        ParseSGTIN parseSGTIN = ParseSGTIN.Builder()
                                .withRFIDTag(epc)
                                .build();
                        SGTIN sgtin = parseSGTIN.getSGTIN();
                        gs1.setCode1(sgtin.getCompanyPrefix());
                        gs1.setCode2(sgtin.getItemReference());
                        gs1.setCode3(sgtin.getSerial());
                        gs1.setGs1Type("sgtin");
                        gs1.setJsonStr(sgtin.toString());
                        break;
                    case SSCC_96_HEADER:
                        ParseSSCC parseSSCC = ParseSSCC.Builder()
                                .withRFIDTag(epc)
                                .build();
                        SSCC sscc = parseSSCC.getSSCC();
                        gs1.setCode1(sscc.getCompanyPrefix());
                        gs1.setCode3(sscc.getSerial());
                        gs1.setGs1Type("sscc");
                        gs1.setJsonStr(sscc.toString());
                        break;
                    case SGLN_96_HEADER:
                        ParseSGLN parseSGLN = ParseSGLN.Builder()
                                .withRFIDTag(epc)
                                .build();
                        SGLN sgln = parseSGLN.getSGLN();
                        gs1.setCode1(sgln.getCompanyPrefix());
                        gs1.setCode3(sgln.getLocationReference());
                        gs1.setGs1Type("sgln");
                        gs1.setJsonStr(sgln.toString());
                        break;
                    case GRAI_96_HEADER:
                        ParseGRAI parseGRAI = ParseGRAI.Builder()
                                .withRFIDTag(epc)
                                .build();
                        GRAI grai = parseGRAI.getGRAI();
                        gs1.setCode1(grai.getCompanyPrefix());
                        gs1.setCode2(grai.getAssetType());
                        gs1.setCode3(grai.getSerial());
                        gs1.setGs1Type("grai");
                        gs1.setJsonStr(grai.toString());
                        break;
                    case GIAI_96_HEADER:
                        ParseGIAI parseGIAI = ParseGIAI.Builder()
                                .withRFIDTag(epc)
                                .build();
                        GIAI giai = parseGIAI.getGIAI();
                        gs1.setCode1(giai.getCompanyPrefix());
                        gs1.setCode2(giai.getIndividualAssetReference());
                        gs1.setGs1Type("giai");
                        gs1.setJsonStr(giai.toString());
                        break;
                    case SGTIN_198_HEADER:
                        ParseSGTIN parseSGTIN_198 = ParseSGTIN.Builder()
                                .withRFIDTag(epc)
                                .build();
                        SGTIN sgtin_198 = parseSGTIN_198.getSGTIN();
                        gs1.setCode1(sgtin_198.getCompanyPrefix());
                        gs1.setCode2(sgtin_198.getItemReference());
                        gs1.setCode3(sgtin_198.getSerial());
                        gs1.setGs1Type("sgtin_198");
                        gs1.setJsonStr(sgtin_198.toString());
                        break;
                    case GRAI_170_HEADER:
                        ParseGRAI parseGRAI_170 = ParseGRAI.Builder()
                                .withRFIDTag(epc)
                                .build();
                        GRAI grai_170 = parseGRAI_170.getGRAI();
                        gs1.setCode1(grai_170.getCompanyPrefix());
                        gs1.setCode3(grai_170.getSerial());
                        gs1.setGs1Type("grai_170");
                        gs1.setJsonStr(grai_170.toString());
                        break;
                    case GIAI_202_HEADER:
                        ParseGIAI parseGIAI_202 = ParseGIAI.Builder()
                                .withRFIDTag(epc)
                                .build();
                        GIAI giai_202 = parseGIAI_202.getGIAI();
                        gs1.setCode1(giai_202.getCompanyPrefix());
                        gs1.setCode3(giai_202.getIndividualAssetReference());
                        gs1.setGs1Type("giai_202");
                        gs1.setJsonStr(giai_202.toString());
                        break;
                    case SGLN_195_HEADER:
                        ParseSGLN parseSGLN_195 = ParseSGLN.Builder()
                                .withRFIDTag(epc)
                                .build();
                        SGLN sgln_195 = parseSGLN_195.getSGLN();
                        gs1.setCode1(sgln_195.getCompanyPrefix());
                        gs1.setCode3(sgln_195.getLocationReference());
                        gs1.setGs1Type("sgln_195");
                        gs1.setJsonStr(sgln_195.toString());
                        break;
                    case GDTI_174_HEADER:
                        ParseGDTI parseGDTI_174 = ParseGDTI.Builder()
                                .withRFIDTag(epc)
                                .build();
                        GDTI gdti_174 = parseGDTI_174.getGDTI();
                        gs1.setCode1(gdti_174.getCompanyPrefix());
                        gs1.setCode3(gdti_174.getSerial());
                        gs1.setGs1Type("gdti_174");
                        gs1.setJsonStr(gdti_174.toString());
                        break;
                    case CPI_96_HEADER:
                        ParseCPI parseCPI = ParseCPI.Builder()
                                .withRFIDTag(epc)
                                .build();
                        CPI cpi = parseCPI.getCPI();
                        gs1.setCode1(cpi.getCompanyPrefix());
                        gs1.setCode2(cpi.getComponentPartReference());
                        gs1.setCode3(cpi.getSerial());
                        gs1.setGs1Type("cpi");
                        gs1.setJsonStr(cpi.toString());
                        break;
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return gs1;
    }

    public static String hexToBinary(String hexString) {
        StringBuilder binaryBuilder = new StringBuilder();
        for (int i = 0; i < hexString.length(); i++) {
            String binary = Integer.toBinaryString(Integer.parseInt(hexString.charAt(i) + "", 16));
            binaryBuilder.append(String.format("%4s", binary).replace(' ', '0')); // 4ビットのバイナリに書式設定
        }
        return binaryBuilder.toString();
    }

}
