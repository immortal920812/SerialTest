package PosTransaction;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WireCardMethods {

	private static DecimalFormat twoDecimalFormat = new DecimalFormat("#0.00");

    private static String getFieldDataByCardType(String cardType){
        String fieldType = "";
        switch (cardType){
            case "Contactless Card":
                fieldType = "00";
                break;
            case "CEPAS":
                fieldType = "01";
                break;
            case "Credit Contactless":
                fieldType = "02";
                break;
            case "Credit Card":
                fieldType = "03";
                break;
            case "CUP":
                fieldType  = "04";
                break;
            case "Alipay QR Spot Payment":
                fieldType = "05";
                break;
            case "Wechat QR":
                fieldType = "05";
                break;
        }
        return fieldType;
    }

    private static String getPaymentTypeByCode(String paymentTypeCode){
        String paymentTypeStr = "";
        switch (paymentTypeCode){
            case "01":
                paymentTypeStr = "CEPAS";
                break;
            case "02":
                paymentTypeStr = "Credit Contactless";
                break;
            case "03":
                paymentTypeStr = "Credit Card";
                break;
            case "04":
                paymentTypeStr = "CUP";
                break;
            case "05":
                paymentTypeStr  = "Alipay QR Spot Payment";
                break;
            case "06":
                paymentTypeStr = "Wechat QR";
                break;

        }
        return paymentTypeStr;
    }

    public static String transactionRequest(String productCode, String paymentType, double amount, String invoiceNo, String qrOrBarcode){
        StringBuffer requestString = new StringBuffer();
        StringBuffer stringForLRC = new StringBuffer();
        String fieldData = buildFieldDataForTransactionRequest(productCode, paymentType, amount, invoiceNo, qrOrBarcode);
        String dataHeader = CommonMethods.ASCIItoHEX(ConstantCommon.HEADER);
        String responseCode = CommonMethods.ASCIItoHEX(ConstantCommon.RESPONSE_CODE_SUCCESS);
        String indicator = CommonMethods.ASCIItoHEX(ConstantCommon.MESSAGE_TYPE_FOR_TRANSACTION_REQUEST);
        int fieldDataLength = fieldData.length() ;
        int dataLength = (dataHeader.length()+responseCode.length()+indicator.length()+ConstantCommon.FIELD_SEPARATOR.length()+fieldDataLength) / 2;
        String formattedFeildDataLength = CommonMethods.formatDataLength(dataLength);
        stringForLRC.append(formattedFeildDataLength);
        stringForLRC.append(dataHeader);
        stringForLRC.append(responseCode);
        stringForLRC.append(indicator);
        stringForLRC.append(ConstantCommon.FIELD_SEPARATOR);
        stringForLRC.append(fieldData);
        stringForLRC.append(ConstantCommon.ETX);
        String lrc = getLRCValueByCalculating(stringForLRC.toString());
        requestString.append(ConstantCommon.STX).append(stringForLRC).append(lrc);
        return requestString.toString();
    }

    private static String buildFieldDataForTransactionRequest(String productCode, String paymentType, double amount, String invoiceNo, String qrOrBarcode) {
        StringBuffer fieldData = new StringBuffer();
        String productCodeHexString = "";
        String paymentTypeHexString = "";
        String amountHexString = "";
        String invoiceNoHexString = "";
        String qrOrBarcodeHexString = "";
        if(!productCode.isEmpty() && productCode != null){
            productCodeHexString = buildDataForProductCode(productCode);
        }
        if(!paymentType.isEmpty() && paymentType != null) {
            paymentTypeHexString = buildDataForPaymentType(paymentType);
        }
        if(amount != 0) {
            amountHexString = buildDataForAmount(amount);
        }
        if(!invoiceNo.isEmpty() && invoiceNo != null ){
            invoiceNoHexString = buildDataForInvoiceNo(invoiceNo);
        }
        if(!qrOrBarcode.isEmpty() && qrOrBarcode != null ){
            qrOrBarcodeHexString = buildDataForQrOrBarcode(qrOrBarcode);
        }


        fieldData.append(productCodeHexString)
                .append(paymentTypeHexString)
                .append(amountHexString)
                .append(invoiceNoHexString)
                .append(qrOrBarcodeHexString);
        return fieldData.toString();
    }

    private static String buildDataForProductCode(String productCode) {
        StringBuffer stringBuffer = new StringBuffer();
        String type = CommonMethods.ASCIItoHEX(ConstantCommon.FIELD_TYPE_FOR_PRODUCT_CODE);
        String data = CommonMethods.ASCIItoHEX(productCode, ConstantCommon.PRODUCT_CODE_LENGTH);
        String length = CommonMethods.formatDataLength(ConstantCommon.PRODUCT_CODE_LENGTH);
        stringBuffer.append(type).append(length).append(data).append(ConstantCommon.FIELD_SEPARATOR);
        return stringBuffer.toString();
    }

    private static String buildDataForPaymentType(String paymentType) {
        StringBuffer stringBuffer = new StringBuffer();
        String type = CommonMethods.ASCIItoHEX(ConstantCommon.FIELD_TYPE_FOR_PAYMENT_TYPE);
        String length = CommonMethods.formatDataLength(ConstantCommon.PAYMENT_TYPE_DATA_LENGTH);
        String data = CommonMethods.ASCIItoHEX(getFieldDataByCardType(paymentType), ConstantCommon.PAYMENT_TYPE_DATA_LENGTH);
        stringBuffer.append(type).append(length).append(data).append(ConstantCommon.FIELD_SEPARATOR);
        return stringBuffer.toString();
    }

    private static String buildDataForAmount(double amount) {
        StringBuffer stringBuffer = new StringBuffer();
        String amountStr = new DecimalFormat("#0.00").format(amount);
        amountStr = amountStr.substring(0,amountStr.indexOf("."))+amountStr.substring(amountStr.indexOf(".")+1);
        String type = CommonMethods.ASCIItoHEX(ConstantCommon.PT_FIELD_TYPE_FOR_TRANSACTION_AMOUNT);
        String length = CommonMethods.formatDataLength(ConstantCommon.AMOUNT_DATA_LENGTH);
        String data = CommonMethods.ASCIItoHEX(amountStr, ConstantCommon.AMOUNT_DATA_LENGTH);
        stringBuffer.append(type).append(length).append(data).append(ConstantCommon.FIELD_SEPARATOR);
        return stringBuffer.toString();
    }

    private static String buildDataForInvoiceNo(String invoiceNo) {
        StringBuffer stringBuffer = new StringBuffer();
        String type = CommonMethods.ASCIItoHEX(ConstantCommon.FIELD_TYPE_FOR_INVOICE_NO);
        String length = CommonMethods.formatDataLength(ConstantCommon.INVOICE_NO_LENGTH);
        String data = CommonMethods.ASCIItoHEX(invoiceNo, ConstantCommon.INVOICE_NO_LENGTH);
        stringBuffer.append(type).append(length).append(data).append(ConstantCommon.FIELD_SEPARATOR);
        return stringBuffer.toString();
    }

    private static String buildDataForQrOrBarcode(String qrOrBarcode) {
        StringBuffer stringBuffer = new StringBuffer();
        String type = CommonMethods.ASCIItoHEX(ConstantCommon.FIELD_TYPE_FOR_QR_OR_BARCODE_DATA);
        String length = CommonMethods.formatDataLength(ConstantCommon.QR_OR_BARCODE_DATA_LENGTH);
        String data = CommonMethods.ASCIItoHEX(qrOrBarcode, ConstantCommon.QR_OR_BARCODE_DATA_LENGTH);
        stringBuffer.append(type).append(length).append(data).append(ConstantCommon.FIELD_SEPARATOR);
        return stringBuffer.toString();
    }

    private static String getLRCValueByCalculating(String dataValue){
        int result = 0;
        for(int i = 0 ; i < dataValue.length() ; i+=2){
            result ^= Integer.parseInt(dataValue.substring(i,i+2), 16);
        }
        String lrc = Integer.toHexString(result);
        return lrc;
    }

    public static String terminalOrApplicationStatusRequest(long dateTime, String paymentType){
        DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuffer requestString = new StringBuffer();
        StringBuffer stringForLRC = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        String dateTimeStr = formatter.format(calendar);
        String paymentTypeCode = getFieldDataByCardType(paymentType);
        String fieldData = buildFieldDataForTerminalStatus(dateTimeStr, paymentTypeCode);
        String dataHeader = CommonMethods.ASCIItoHEX(ConstantCommon.HEADER);
        String responseCode = CommonMethods.ASCIItoHEX(ConstantCommon.RESPONSE_CODE_SUCCESS);
        String indicator = CommonMethods.ASCIItoHEX(ConstantCommon.MESSAGE_TYPE_FOR_TERMINAL_STATUS);
        int fieldDataLength = fieldData.length() ;
        int dataLength = (dataHeader.length()+responseCode.length()+indicator.length()+ConstantCommon.FIELD_SEPARATOR.length()+fieldDataLength) / 2;
        String formattedFeildDataLength = CommonMethods.formatDataLength(dataLength);
        stringForLRC.append(formattedFeildDataLength);
        stringForLRC.append(dataHeader);
        stringForLRC.append(responseCode);
        stringForLRC.append(indicator);
        stringForLRC.append(ConstantCommon.FIELD_SEPARATOR);
        stringForLRC.append(fieldData);
        stringForLRC.append(ConstantCommon.ETX);
        String lrc = getLRCValueByCalculating(stringForLRC.toString());
        requestString.append(ConstantCommon.STX).append(stringForLRC).append(lrc);
        return requestString.toString();
    }

    private static String buildFieldDataForTerminalStatus(String dateTimeStr, String paymentTypeCode) {
        StringBuffer fieldData = new StringBuffer();
        String dateTimeStrHexString = "";
        String paymentTypeCodeHexString = "";
        if(!dateTimeStrHexString.isEmpty() && dateTimeStrHexString != null){
            dateTimeStrHexString = buildDataForDateTime(dateTimeStr);
        }
        if(!paymentTypeCodeHexString.isEmpty() && paymentTypeCodeHexString != null) {
            paymentTypeCodeHexString = buildDataForPaymentType(paymentTypeCode);
        }
        fieldData.append(dateTimeStrHexString)
                .append(paymentTypeCodeHexString);
        return fieldData.toString();
    }

    private static String buildDataForDateTime(String dateTimeStr) {
        StringBuffer stringBuffer = new StringBuffer();
        String type = CommonMethods.ASCIItoHEX(ConstantCommon.PT_FIELD_TYPE_FOR_DATE_TIME_STAMP);
        String length = CommonMethods.formatDataLength(ConstantCommon.DATE_TIME_LENGTH);
        String data = CommonMethods.ASCIItoHEX(dateTimeStr);
        stringBuffer.append(type).append(length).append(data).append(ConstantCommon.FIELD_SEPARATOR);
        return stringBuffer.toString();
    }

    public static String messgeToHostRequest(String url, String data, String disconnectWithHost){
        StringBuffer requestString = new StringBuffer();
        StringBuffer stringForLRC = new StringBuffer();
        String fieldData = buildFieldDataForMessageToHost(url, data, disconnectWithHost);
        String dataHeader = CommonMethods.ASCIItoHEX(ConstantCommon.HEADER);
        String responseCode = CommonMethods.ASCIItoHEX(ConstantCommon.RESPONSE_CODE_SUCCESS);
        String indicator = CommonMethods.ASCIItoHEX(ConstantCommon.MESSAGE_TYPE_FOR_MESSAGE_TO_HOST);
        int fieldDataLength = fieldData.length() ;
        int dataLength = (dataHeader.length()+responseCode.length()+indicator.length()+ConstantCommon.FIELD_SEPARATOR.length()+fieldDataLength) / 2;
        String formattedFeildDataLength = CommonMethods.formatDataLength(dataLength);
        stringForLRC.append(formattedFeildDataLength);
        stringForLRC.append(dataHeader);
        stringForLRC.append(responseCode);
        stringForLRC.append(indicator);
        stringForLRC.append(ConstantCommon.FIELD_SEPARATOR);
        stringForLRC.append(fieldData);
        stringForLRC.append(ConstantCommon.ETX);
        String lrc = getLRCValueByCalculating(stringForLRC.toString());
        requestString.append(ConstantCommon.STX).append(stringForLRC).append(lrc);
        return requestString.toString();
    }

    private static String buildFieldDataForMessageToHost(String url, String data, String disconnectWithHost) {

        StringBuffer fieldData = new StringBuffer();
        String urlHexString = "";
        String dataHexString = "";
        String disconnectWithHostHexString = "";
        if(!url.isEmpty() && url != null){
            urlHexString = buildDataForURL(url);
        }
        if(!data.isEmpty() && data != null) {
            dataHexString = buildDataForData(data);
        }
        if(!disconnectWithHost.isEmpty() && disconnectWithHost != null) {
            disconnectWithHostHexString = buildDataForDisconnectWithHost(disconnectWithHost);
        }
        fieldData.append(urlHexString)
                .append(dataHexString)
                .append(disconnectWithHostHexString);
        return fieldData.toString();

    }

    private static String buildDataForURL(String url) {
        StringBuffer stringBuffer = new StringBuffer();
        String type = CommonMethods.ASCIItoHEX(ConstantCommon.FIELD_TYPE_FOR_IP_ADDRESS);
        String length = CommonMethods.formatDataLength(ConstantCommon.IP_ADDRESS_LENGTH);
        String data = CommonMethods.formatIPAddress(url);
        stringBuffer.append(type).append(length).append(data).append(ConstantCommon.FIELD_SEPARATOR);
        return stringBuffer.toString();
    }


    private static String buildDataForData(String dataStr) {
        StringBuffer stringBuffer = new StringBuffer();
        String type = CommonMethods.ASCIItoHEX(ConstantCommon.FIELD_TYPE_FOR_SEND_DATA_TO_HOST);
        String data = CommonMethods.ASCIItoHEX(dataStr);
        String length = CommonMethods.formatDataLength(dataStr.length());
        stringBuffer.append(type).append(length).append(data).append(ConstantCommon.FIELD_SEPARATOR);
        return stringBuffer.toString();
    }


    private static String buildDataForDisconnectWithHost(String disconnectWithHost) {
        StringBuffer stringBuffer = new StringBuffer();
        String type = CommonMethods.ASCIItoHEX(ConstantCommon.FIELD_TYPE_FOR_DISCONNECT_WITH_HOST);
        String length = CommonMethods.formatDataLength(ConstantCommon.DISCONNECT_WITH_HOST_LENGTH);
        String data = CommonMethods.ASCIItoHEX(disconnectWithHost);
        stringBuffer.append(type).append(length).append(data).append(ConstantCommon.FIELD_SEPARATOR);
        return stringBuffer.toString();
    }

    public static String cancelTransactionRequest(String errorCode){
        StringBuffer requestString = new StringBuffer();
        StringBuffer stringForLRC = new StringBuffer();
        String fieldData = buildFieldDataForCancelTransactionRequest(errorCode);
        String dataHeader = CommonMethods.ASCIItoHEX(ConstantCommon.HEADER);
        String responseCode = CommonMethods.ASCIItoHEX(ConstantCommon.RESPONSE_CODE_SUCCESS);
        String indicator = CommonMethods.ASCIItoHEX(ConstantCommon.MESSAGE_TYPE_FOR_CANCEL_TRANSACTION_REQUEST);
        int fieldDataLength = fieldData.length() ;
        int dataLength = (dataHeader.length()+responseCode.length()+indicator.length()+ConstantCommon.FIELD_SEPARATOR.length()+fieldDataLength) / 2;
        String formattedFeildDataLength = CommonMethods.formatDataLength(dataLength);
        stringForLRC.append(formattedFeildDataLength);
        stringForLRC.append(dataHeader);
        stringForLRC.append(responseCode);
        stringForLRC.append(indicator);
        stringForLRC.append(ConstantCommon.FIELD_SEPARATOR);
        stringForLRC.append(fieldData);
        stringForLRC.append(ConstantCommon.ETX);
        String lrc = getLRCValueByCalculating(stringForLRC.toString());
        requestString.append(ConstantCommon.STX).append(stringForLRC).append(lrc);
        return requestString.toString();
    }

    private static String buildFieldDataForCancelTransactionRequest(String errorCode) {

        StringBuffer fieldData = new StringBuffer();
        String errorCodeHexString = "";
        if(!errorCode.isEmpty() && errorCode != null) {
            errorCodeHexString = buildDataForErrorCode(errorCode);
        }
        fieldData.append(errorCodeHexString);
        return fieldData.toString();
    }

    private static String buildDataForErrorCode(String errorCode) {
        StringBuffer stringBuffer = new StringBuffer();
        String type = CommonMethods.ASCIItoHEX(ConstantCommon.FIELD_TYPE_FOR_GET_ERROR_LIST);
        String length = CommonMethods.formatDataLength(ConstantCommon.ERROR_LIST_LENGTH);
        String data = CommonMethods.formatIPAddress(errorCode);
        stringBuffer.append(type).append(length).append(data).append(ConstantCommon.FIELD_SEPARATOR);
        return stringBuffer.toString();
    }

    public static Map transactionResponse(String response){
        Map<String, String> responseMap = new HashMap<>();
        List<String> fieldList = new ArrayList<>();
        String responseCode = response.substring(18,20);
        responseMap.put("Response Code", responseCode);
        String messageType = response.substring(20);
        responseMap.put("Message Type",messageType);
        String fieldStr = response.substring(response.indexOf(ConstantCommon.FIELD_SEPARATOR)+1,response.lastIndexOf(ConstantCommon.FIELD_SEPARATOR));
        if(!fieldStr.isEmpty()){
            int fieldCount = getFieldSeparatorCount(fieldStr, ConstantCommon.FIELD_SEPARATOR);
            for(int i = 0 ; i < fieldCount ; i++){
                fieldList.add(fieldStr.substring(0, fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)));
                fieldStr = fieldStr.substring(fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)+1);
            }
        }
        if(fieldList.size() > 0){
            for(String fieldData : fieldList) {
                String hexType = fieldData.substring(0,5);
                String type = CommonMethods.HEXtoASCII(hexType);
                if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_APPROVAL_CODE)){
                    responseMap.put("Approval Code", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if (type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_CARD_NUMBER)){
                    responseMap.put("Card Number", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if (type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_EXPIRY_DATE)){
                    responseMap.put("Expiry Date", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if (type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_ISSUER_NAME)){
                    responseMap.put("Issuer Name", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if (type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_CARD_HOLDER_INFORMATION)){
                    responseMap.put("Card Holder Information", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if (type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_TRANSCATION_AMOUNT)){
                    responseMap.put("Transaction Amount", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if (type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_DISCOUNT_AMOUNT)){
                    responseMap.put("Discount Amount", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if (type.equals(ConstantCommon.FIELD_TYPE_FOR_CARD_BALANCE_AMOUNT)){
                    responseMap.put("Card Balance Amount", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if (type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_RESPONSE_FAILED)){
                    String errorCode = CommonMethods.HEXtoASCII(fieldData.substring(8));
                    if(errorCode.equals(ConstantCommon.FIELD_VALUE_FOR_READ_CARD_ERROR)){
                        responseMap.put("Error", "Read Card Error");
                    } else if(errorCode.equals(ConstantCommon.FIELD_VALUE_FOR_NO_CARD_BALANCE)){
                        responseMap.put("Error", "Card Balance has no value");
                    } else if(errorCode.equals(ConstantCommon.FIELD_VALUE_FOR_INVALID_CARD)){
                        responseMap.put("Error", "Invalid card");
                    } else if(errorCode.equals(ConstantCommon.FIELD_VALUE_FOR_INSUFFICIENT_BALANCE)){
                        responseMap.put("Error", "Card has insufficient balance");
                    } else if(errorCode.equals(ConstantCommon.FIELD_VALUE_FOR_CANCEL_TRANSCATION)){
                        responseMap.put("Error", "Cancel Transaction");
                    } else if(errorCode.equals(ConstantCommon.FIELD_VALUE_FOR_CARD_READER_TIME_OUT)){
                        responseMap.put("Error", "Card reader time out when reading card");
                    } else if(errorCode.equals(ConstantCommon.FIELD_VALUE_FOR_CARD_READER_NO_READY)){
                        responseMap.put("Error", "Card Reader no ready");
                    } else if(errorCode.equals(ConstantCommon.TERMINAL_NO_READY)){
                        responseMap.put("Error", "Terminal no ready");
                    } else if(errorCode.equals(ConstantCommon.TERMINAL_NO_READY_DUE_TO_SETTLEMENT_ERROR)){
                        responseMap.put("Error", "Terminal no ready due to settlement error");
                    } else if(errorCode.equals(ConstantCommon.FIELD_VALUE_FOR_UNABLE_TO_LOCATE_RECORD)){
                        responseMap.put("Error", "Unable to Locate the Record");
                    } else if(errorCode.equals(ConstantCommon.FIELD_VALUE_FOR_USER_CALCEL_TRANSCATION)){
                        responseMap.put("Error", "User cancel transaction");
                    } else if(errorCode.equals(ConstantCommon.FIELD_VALUE_FOR_TRANSACTION_TIME_OUT)){
                        responseMap.put("Error", "Transaction Time Out");
                    } else if(errorCode.equals(ConstantCommon.FIELD_VALUE_FOR_TERMINAL_TIME_OUT)){
                        responseMap.put("Time Out", "Terminal Time Out");
                    }
                } else if(type.equals(ConstantCommon.TP_PAYMENT_TYPE_CODE)) {
                    String paymentTypeCode = CommonMethods.HEXtoASCII(fieldData.substring(8));
                    if(paymentTypeCode.equals(ConstantCommon.CEPAS_PAYMENT_TYPE_CODE)){
                        responseMap.put("Payment Type", "CEPAS");
                    } else if(paymentTypeCode.equals(ConstantCommon.TP_CREDIT_CARD_PAYMENT_TYPE_CODE)) {
                        responseMap.put("Payment Type", "Credit Card");
                    } else if(paymentTypeCode.equals(ConstantCommon.TP_NETS_PAYMENT_TYPE_CODE)) {
                        responseMap.put("Payment Type", "NETS");
                    } else if(paymentTypeCode.equals(ConstantCommon.CUP_PAYMENT_TYPE_CODE)) {
                        responseMap.put("Payment Type", "CUP");
                    } else if(paymentTypeCode.equals(ConstantCommon.ALIPAY_QR_SPOT_PAYMENT_TYPE_CODE)) {
                        responseMap.put("Payment Type", "Alipay QR Spot Payment");
                    } else if(paymentTypeCode.equals(ConstantCommon.WECHAT_QR_PAYMENT_TYPE_CODE)) {
                        responseMap.put("Payment Type", "Wechat QR");
                    }
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_TRACE_NUMBER)){
                    responseMap.put("Trace Number", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_DATE_TIME)){
                    responseMap.put("Date Time", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_CARD_TYPE)){
                    responseMap.put("Card Type", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_ENTRY_MODE)){
                    responseMap.put("Entry Mode", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_PIN_NUMBER)){
                    responseMap.put("PIN Number", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_MID)){
                    responseMap.put("MID", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_TID)){
                    responseMap.put("TID", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_INVOICE_NO)){
                    responseMap.put("Invoice No", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_RRN)){
                    responseMap.put("RRN", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_BATCH_NO)){
                    responseMap.put("Batch No", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_STAN_NO)){
                    responseMap.put("Stan No", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.FIELD_TYPE_FOR_APPROVAL_CODE)){
                    responseMap.put("Approval Code", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_TOTAL_AMOUNT)){
                    responseMap.put("Total Amount", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_AID)){
                    responseMap.put("AID", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_CARD_LABEL)){
                    responseMap.put("Card Label", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_TRANSACTION_CERTIFICATE_TC)){
                    responseMap.put("Transaction Certificate TC", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_TVR)){
                    responseMap.put("TVR", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_TSI)){
                    responseMap.put("TSI", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_ACCOUNT_TYPE)){
                    responseMap.put("Account Type", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_DEBIT_BANK)){
                    responseMap.put("Debit Bank", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_PREVIOUS_BALANCE)){
                    responseMap.put("Previous Balance", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_FOR_CURRENT_BALANCE)){
                    responseMap.put("Current Balance", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_SIGNATURE_REQUIRED)){
                    String required = CommonMethods.HEXtoASCII(fieldData.substring(8));
                    if(required.equals("01"))
                        responseMap.put("Signature Required", "Yes");
                    else
                        responseMap.put("Signature Required", "No");
                }
            }
        }
        return responseMap;
    }

    private static int getFieldSeparatorCount(String findStr, String str) {
        int count = 0;
        int lastIndex = 0;
        while(lastIndex != -1){
            lastIndex = str.indexOf(findStr,lastIndex);
            if(lastIndex != -1){
                count ++;
                lastIndex += findStr.length();
            }
        }
        return count;
    }

    public static Map terminalOrApplicationStatusResponse(String response){
        Map<String, String> responseMap = new HashMap<>();
        String responseCode = response.substring(18,20);
        responseMap.put("Response Code", responseCode);
        String messageType = response.substring(20);
        responseMap.put("Message Type",messageType);
        List<String> fieldList = new ArrayList<>();
        String fieldStr = response.substring(response.indexOf(ConstantCommon.FIELD_SEPARATOR)+1,response.lastIndexOf(ConstantCommon.FIELD_SEPARATOR));
        if(!fieldStr.isEmpty()){
            int fieldCount = getFieldSeparatorCount(fieldStr, ConstantCommon.FIELD_SEPARATOR);
            for(int i = 0 ; i < fieldCount ; i++){
                fieldList.add(fieldStr.substring(0, fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)));
                fieldStr = fieldStr.substring(fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)+1);
            }
        }
        if(fieldList.size() > 0){
            for(String fieldData : fieldList) {
                String hexType = fieldData.substring(0,5);
                String type = CommonMethods.HEXtoASCII(hexType);
                if(type.equals(ConstantCommon.TAG_FOR_RESPONSE_FAIL)){
                    String errorResponseCode = CommonMethods.HEXtoASCII(fieldData.substring(8));
                    if(errorResponseCode.equals(ConstantCommon.TERMINAL_NO_READY))
                        responseMap.put("Error", "Terminal no ready");
                    else if(errorResponseCode.equals(ConstantCommon.TERMINAL_NO_READY_DUE_TO_SETTLEMENT_ERROR))
                        responseMap.put("Error", "Terminal no ready due to settlement error");
                    else if(errorResponseCode.equals(ConstantCommon.TP_TSR_APPLICATION_NOT_AVAILABLE))
                        responseMap.put("Error", "Application not ready/not available");
                }
            }
        }
        return responseMap;
    }

    public static Map messgeToHostResponse(String response){
        Map responseMap = new HashMap<>();
        String responseCode = response.substring(18,20);
        responseMap.put("Response Code", responseCode);
        String messageType = response.substring(20);
        responseMap.put("Message Type",messageType);
        List<String> fieldList = new ArrayList<>();
        String fieldStr = response.substring(response.indexOf(ConstantCommon.FIELD_SEPARATOR)+1,response.lastIndexOf(ConstantCommon.FIELD_SEPARATOR));
        if(!fieldStr.isEmpty()){
            int fieldCount = getFieldSeparatorCount(fieldStr, ConstantCommon.FIELD_SEPARATOR);
            for(int i = 0 ; i < fieldCount ; i++){
                fieldList.add(fieldStr.substring(0, fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)));
                fieldStr = fieldStr.substring(fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)+1);
            }
        }
        if(fieldList.size() > 0){
            for(String fieldData : fieldList) {
                String hexType = fieldData.substring(0,5);
                String type = CommonMethods.HEXtoASCII(hexType);
                if(type.equals(ConstantCommon.FIELD_TYPE_FOR_CONNECT_TO_HOST_RESPONSE)){
                    String responseData = CommonMethods.HEXtoASCII(fieldData.substring(8));
                    if(responseCode.equals(ConstantCommon.CONNECT_TO_HOST_FAILED))
                        responseMap.put("Response", "Fail");
                    else if(responseCode.equals(ConstantCommon.CONNECT_TO_HOST_SUCCESS))
                        responseMap.put("Response", "Success");
                } else if(type.equals(ConstantCommon.FIELD_TYPE_FOR_DATA_RECEIVE_FROM_HOST)){
                        responseMap.put("ResponseData", CommonMethods.HEXtoASCII(fieldData.substring(8)));
                } else if(type.equals(ConstantCommon.FIELD_TYPE_FOR_REASON_FAIL_TO_CONNECT)){
                    String reason = CommonMethods.HEXtoASCII(fieldData.substring(8));
                    if(reason.equals(ConstantCommon.UNABLE_TO_SEND))
                        responseMap.put("Reason Fail to connect", "Unable to send");
                    else if(reason.equals(ConstantCommon.SENT_BUT_TIME_OUT))
                        responseMap.put("Reason Fail to connect", "Sent , But Time out");
                    else if(reason.equals(ConstantCommon.CONNECTION_DROPPED))
                        responseMap.put("Reason Fail to connect", "sent ,but unable to get response due to connection dropped");
                }
            }
        }
        return responseMap;
    }

    public static Map tapCardEventRequest(String request){
        Map<String, String> requestMap = new HashMap<>();
        String responseCode = request.substring(18,20);
        requestMap.put("Response Code", responseCode);
        String messageType = request.substring(20);
        requestMap.put("Message Type",messageType);
        List<String> fieldList = new ArrayList<>();
        String fieldStr = request.substring(request.indexOf(ConstantCommon.FIELD_SEPARATOR)+1,request.lastIndexOf(ConstantCommon.FIELD_SEPARATOR));
        if(!fieldStr.isEmpty()){
            int fieldCount = getFieldSeparatorCount(fieldStr, ConstantCommon.FIELD_SEPARATOR);
            for(int i = 0 ; i < fieldCount ; i++){
                fieldList.add(fieldStr.substring(0, fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)));
                fieldStr = fieldStr.substring(fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)+1);
            }
        }
        if(fieldList.size() > 0){
            for(String fieldData : fieldList) {
                String hexType = fieldData.substring(0,5);
                String type = CommonMethods.HEXtoASCII(hexType);
                if(type.equals(ConstantCommon.TAG_FOR_RESPONSE_FAIL)){
                    String data = CommonMethods.HEXtoASCII(fieldData.substring(8));
                    if(type.equals(ConstantCommon.FIELD_TYPE_FOR_CARD_NUMBER))
                        requestMap.put("Card Number", data);
                    else if(type.equals(ConstantCommon.FIELD_TYPE_FOR_PAYMENT_TYPE)) {
                        requestMap.put("Payment Type", getPaymentTypeByCode(data));
                     }
                    else if(type.equals(ConstantCommon.FIELD_TYPE_FOR_CARD_BALANCE_AMOUNT))
                        requestMap.put("Card Balance Amount", data);
                    else if(type.equals(ConstantCommon.TP_FIELD_TYPE_FOR_RESPONSE_FAILED))
                        requestMap.put("Error", data);
                }
            }
        }
        return requestMap;
    }

    public static Map cancelTransactionResponse(String response){
        Map<String, String> responseMap = new HashMap<>();
        String responseCode = response.substring(18,20);
        responseMap.put("Response Code", responseCode);
        String messageType = response.substring(20);
        responseMap.put("Message Type",messageType);
        List<String> fieldList = new ArrayList<>();
        String fieldStr = response.substring(response.indexOf(ConstantCommon.FIELD_SEPARATOR)+1,response.lastIndexOf(ConstantCommon.FIELD_SEPARATOR));
        if(!fieldStr.isEmpty()){
            int fieldCount = getFieldSeparatorCount(fieldStr, ConstantCommon.FIELD_SEPARATOR);
            for(int i = 0 ; i < fieldCount ; i++){
                fieldList.add(fieldStr.substring(0, fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)));
                fieldStr = fieldStr.substring(fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)+1);
            }
        }
        if(fieldList.size() > 0){
            for(String fieldData : fieldList) {
                String hexType = fieldData.substring(0,5);
                String type = CommonMethods.HEXtoASCII(hexType);
                if(type.equals(ConstantCommon.TAG_FOR_RESPONSE_FAIL)){
                    responseMap.put("Error", "User Cancel Transaction");
                }
            }
        }
        return responseMap;
    }

    public static Map settlementResponse(String response){
        Map<String, String> responseMap = new HashMap<>();
        String responseCode = response.substring(18,20);
        responseMap.put("Response Code", responseCode);
        String messageType = response.substring(20);
        responseMap.put("Message Type",messageType);
        List<String> fieldList = new ArrayList<>();
        String fieldStr = response.substring(response.indexOf(ConstantCommon.FIELD_SEPARATOR)+1,response.lastIndexOf(ConstantCommon.FIELD_SEPARATOR));
        if(!fieldStr.isEmpty()){
            int fieldCount = getFieldSeparatorCount(fieldStr, ConstantCommon.FIELD_SEPARATOR);
            for(int i = 0 ; i < fieldCount ; i++){
                fieldList.add(fieldStr.substring(0, fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)));
                fieldStr = fieldStr.substring(fieldStr.indexOf(ConstantCommon.FIELD_SEPARATOR)+1);
            }
        }
        if(fieldList.size() > 0){
            for(String fieldData : fieldList) {
                String hexType = fieldData.substring(0,5);
                String type = CommonMethods.HEXtoASCII(hexType);
                if(type.equals(ConstantCommon.PT_FIELD_TYPE_FOR_DATE_TIME_STAMP)){
                    String data = CommonMethods.HEXtoASCII(fieldData.substring(8));
                    responseMap.put("Settlement Time Stamp", data);
                }
            }
        }
        return responseMap;
    }

    public static String transactionInquiryRequest(String invoiceNo){
        StringBuffer requestString = new StringBuffer();
        StringBuffer stringForLRC = new StringBuffer();
        String fieldData = buildFieldDataForTransactionInquiryRequest(invoiceNo);
        String dataHeader = CommonMethods.ASCIItoHEX(ConstantCommon.HEADER);
        String responseCode = CommonMethods.ASCIItoHEX(ConstantCommon.RESPONSE_CODE_SUCCESS);
        String indicator = CommonMethods.ASCIItoHEX(ConstantCommon.MESSAGE_TYPE_FOR_CANCEL_TRANSACTION_REQUEST);
        int fieldDataLength = fieldData.length() ;
        int dataLength = (dataHeader.length()+responseCode.length()+indicator.length()+ConstantCommon.FIELD_SEPARATOR.length()+fieldDataLength) / 2;
        String formattedFeildDataLength = CommonMethods.formatDataLength(dataLength);
        stringForLRC.append(formattedFeildDataLength);
        stringForLRC.append(dataHeader);
        stringForLRC.append(responseCode);
        stringForLRC.append(indicator);
        stringForLRC.append(ConstantCommon.FIELD_SEPARATOR);
        stringForLRC.append(fieldData);
        stringForLRC.append(ConstantCommon.ETX);
        String lrc = getLRCValueByCalculating(stringForLRC.toString());
        requestString.append(ConstantCommon.STX).append(stringForLRC).append(lrc);
        return requestString.toString();
    }

    private static String buildFieldDataForTransactionInquiryRequest(String invoiceNo) {
        StringBuffer fieldData = new StringBuffer();
        String invoiceNoHexString = "";
        if(!invoiceNo.isEmpty() && invoiceNo != null ){
            invoiceNoHexString = buildDataForInvoiceNo(invoiceNo);
        }
        fieldData
                .append(invoiceNoHexString);
        return fieldData.toString();
    }
}
