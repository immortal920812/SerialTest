package PosTransaction;

public class ConstantCommon {
    public static final  String STX = "02";
    public static final  String ETX = "03";
    public static final String ACK = "06";
    public static final String NAK = "15";
    public static final  String HEADER = "60000000001120";
    // transaction approve response code
    public static final  String RESPONSE_CODE_SUCCESS = "00";
    // transaction not approve response code
    public static final String RESPONSE_CODE_FAIL = "ND";
    // Message Type Transaction Request
    public static final String MESSAGE_TYPE_FOR_TRANSACTION_REQUEST = "0";
    // Message Type Terminal Status
    public static final String MESSAGE_TYPE_FOR_TERMINAL_STATUS = "1";
    // Message Type Balance Inquiry
    public static final String MESSAGE_TYPE_FOR_BALANCE_INQUIRY = "2";
    // Message Type Message To Host
    public static final String MESSAGE_TYPE_FOR_MESSAGE_TO_HOST = "3";
    // Message Type Transaction Inquiry
    public static final String MESSAGE_TYPE_FOR_TRANSACTION_INQUIRY  = "4";
    // Message Type Tap Card Event  (Vending Machine Only)
    public static final String MESSAGE_TYPE_FOR_TAB_CARD_EVENT  = "6";
    // Message Type Cancel Transaction Request
    public static final String MESSAGE_TYPE_FOR_CANCEL_TRANSACTION_REQUEST  = "7";
    // Message Type Settlement Request
    public static final String MESSAGE_TYPE_FOR_SETTLEMENT_RWEQUEST = "8";
    // Message Type Report Status (Vending Machine Only)
    public static final String MESSAGE_TYPE_FOR_REPORT_STATUS  = "9";
    public static final  String FIELD_SEPARATOR = "1C";
    public static final  String ADDITIONAL_FIELD_SEPARATOR = "1Ch";
    // Field Type for POS to Terminal
    public static final  String FIELD_TYPE_FOR_PRODUCT_CODE = "33";
    public static final  int PRODUCT_CODE_LENGTH = 20;
    public static final  String FIELD_TYPE_FOR_PAYMENT_TYPE = "34";
    public static final  String PT_FIELD_TYPE_FOR_TRANSACTION_AMOUNT = "40";
    public static final  String FIELD_TYPE_FOR_INVOICE_NO = "72";
    public static final  int INVOICE_NO_LENGTH = 6;
    public static final  String FIELD_TYPE_FOR_QR_OR_BARCODE_DATA = "75";
    public static final  int QR_OR_BARCODE_DATA_LENGTH = 50;
    // Field Type for Terminal to POS
    public static final  String TP_FIELD_TYPE_FOR_APPROVAL_CODE = "01";
    public static final  String TP_FIELD_TYPE_FOR_CARD_NUMBER = "30";
    public static final  String TP_FIELD_TYPE_FOR_EXPIRY_DATE = "31";
    public static final  String TP_FIELD_TYPE_FOR_ISSUER_NAME = "32";
    public static final  String TP_FIELD_TYPE_FOR_CARD_HOLDER_INFORMATION = "35";
    public static final  String TP_FIELD_TYPE_FOR_TRANSCATION_AMOUNT = "41";
    public static final  String TP_FIELD_TYPE_FOR_DISCOUNT_AMOUNT = "42";
    public static final  String TP_FIELD_TYPE_FOR_CARD_BALANCE_AMOUNT = "43";
    public static final  String TP_FIELD_TYPE_FOR_RESPONSE_FAILED = "50";
    public static final  String TP_FIELD_TYPE_FOR_PAYMENT_TYPE = "52";
    public static final  String TP_FIELD_TYPE_FOR_TRACE_NUMBER = "65";
    public static final  String TP_FIELD_TYPE_FOR_DATE_TIME = "66";
    public static final  String TP_FIELD_TYPE_FOR_CARD_TYPE = "67";
    public static final  String TP_FIELD_TYPE_FOR_ENTRY_MODE = "68";
    public static final  String TP_FIELD_TYPE_FOR_PIN_NUMBER = "69";
    public static final  String TP_FIELD_TYPE_FOR_MID = "70";
    public static final  String TP_FIELD_TYPE_FOR_TID = "71";
    public static final  String TP_FIELD_TYPE_FOR_INVOICE_NO = "72";
    public static final  String TP_FIELD_TYPE_FOR_RRN = "73";
    public static final  String TP_FIELD_TYPE_FOR_BATCH_NO = "74";
    public static final  String TP_FIELD_TYPE_FOR_STAN_NO = "75";
    //public static final  String TP_FIELD_TYPE_FOR_APPROVAL_CODE = "76";
    public static final  String TP_FIELD_TYPE_FOR_TOTAL_AMOUNT = "80";
    public static final  String TP_FIELD_TYPE_FOR_AID = "81";
    public static final  String TP_FIELD_TYPE_FOR_CARD_LABEL = "82";
    public static final  String TP_FIELD_TYPE_FOR_TRANSACTION_CERTIFICATE_TC = "83";
    public static final  String TP_FIELD_TYPE_FOR_TVR = "84";
    public static final  String TP_FIELD_TYPE_FOR_TSI = "85";
    // Only for Nets
    public static final  String TP_FIELD_TYPE_FOR_ACCOUNT_TYPE = "86";
    public static final  String TP_FIELD_TYPE_FOR_DEBIT_BANK = "87";
    // Only for Cepas
    public static final  String TP_FIELD_TYPE_FOR_PREVIOUS_BALANCE = "88";
    public static final  String TP_FIELD_FOR_CURRENT_BALANCE = "89";
    public static final  String TP_FIELD_TYPE_FOR_SIGNATURE_REQUIRED = "90";

    // Field Value for Error (Field Type or Tag 50)
    public static final  String FIELD_VALUE_FOR_READ_CARD_ERROR = "51";
    public static final  String FIELD_VALUE_FOR_NO_CARD_BALANCE = "52";
    public static final  String FIELD_VALUE_FOR_INVALID_CARD = "53";
    public static final  String FIELD_VALUE_FOR_INSUFFICIENT_BALANCE = "54";
    public static final  String FIELD_VALUE_FOR_CANCEL_TRANSCATION = "55";
    public static final  String FIELD_VALUE_FOR_CARD_READER_TIME_OUT = "56";
    public static final  String FIELD_VALUE_FOR_CARD_READER_NO_READY = "57";
    public static final  String FIELD_VALUE_FOR_TERMINAL_NO_READY = "58";
    public static final  String FIELD_VALUE_FOR_TERMINAL_NO_READY_DUE_TO_SETTLEMENT = "59";
    public static final  String FIELD_VALUE_FOR_UNABLE_TO_LOCATE_RECORD = "25";
    public static final  String FIELD_VALUE_FOR_USER_CALCEL_TRANSCATION = "55";
    public static final  String FIELD_VALUE_FOR_TRANSACTION_TIME_OUT = "60";
    public static final  String FIELD_VALUE_FOR_TERMINAL_TIME_OUT = "61";
    // Field Value for Signature Required (TP_FIELD_TYPE_SIGNATURE_REQUIRED)
    public static final  String YES_FOR_SIGNATURE_REQUIRED = "01";
    public static final  String NO_FOR_SIGNATURE_REQUIRED = "02";

    // Terminal or Application Status Request (Echo)
    public static final String PT_FIELD_TYPE_FOR_DATE_TIME_STAMP = "80h";
    public static final String PT_FIELD_TYPE_FOR_CHECK_APPLICATION = "34";
    // Field Value (Prefered Payment Type Code) for CHECK APPLICATION
    public static final String CEPAS_PAYMENT_TYPE_CODE = "01";
    public static final String CREDIT_CONTACTLESS_PAYMENT_TYPE_CODE = "02";
    public static final String CREDIT_CARD_PAYMENT_TYPE_CODE = "03";
    public static final String CUP_PAYMENT_TYPE_CODE = "04";
    public static final String ALIPAY_QR_SPOT_PAYMENT_TYPE_CODE = "05";
    public static final String WECHAT_QR_PAYMENT_TYPE_CODE = "06";
    // Tap card event request fields from TERMINAL to POS
    // Field Value for Response Failed
    public static final String TERMINAL_NO_READY = "58";
    public static final String TERMINAL_NO_READY_DUE_TO_SETTLEMENT_ERROR = "59";
    public static final String APPLICATION_NO_READY_OR_NO_AVAILABLE = "62";
    // Host message upload request
    public static final String FIELD_TYPE_FOR_IP_ADDRESS = "90";
    public static final String FIELD_TYPE_FOR_SEND_DATA_TO_HOST = "91";
    public static final String FIELD_TYPE_FOR_DISCONNECT_WITH_HOST = "92";
    // Field Value for Disconnect with host
    public static final String DISCONNECT = "01";
    public static final String MESSAGE_TO_FOLLOW = "02";
    // Host message upload response
    public static final String FIELD_TYPE_FOR_CONNECT_TO_HOST_RESPONSE = "60";
    // Field Values for Connect to Host of Terminal to POS
    public static final String CONNECT_TO_HOST_FAILED = "FD";
    public static final String CONNECT_TO_HOST_SUCCESS = "OK";
    // Field Type for Data Receive from Host
    public static final String FIELD_TYPE_FOR_DATA_RECEIVE_FROM_HOST = "61";
    public static final String FIELD_TYPE_FOR_REASON_FAIL_TO_CONNECT = "62";
    // Field value for reasons to fail to connect to Host
    public static final String UNABLE_TO_SEND = "01";
    public static final String SENT_BUT_TIME_OUT = "02";
    public static final String CONNECTION_DROPPED = "03";
    // Tap card event request fields from TERMINAL to POS
    public static final String FIELD_TYPE_FOR_CARD_NUMBER = "30";
    public static final String FIELD_TYPE_FOR_APPLICATION_CHECK = "34";
    public static final String FIELD_TYPE_FOR_CARD_BALANCE_AMOUNT = "43";
    // Tap card event request fields from POS to TERMINAL
    public static final String FIELD_TYPE_FOR_GET_ERROR_LIST = "70";
    public static final int ERROR_LIST_LENGTH = 2;
    // Field Value for GETTING ERROR LIST
    public static final String CANCEL_CODE = "70";
    public static final String SELL_COMPLETE_USING_COIN = "72";
    public static final String SELL_CANCEL = "73";
    public static final String SELL_FAIL_DUE_TO_VENDING_ERROR = "74";
    public static final String TECHNICAL_PROBLEM = "75";
    public static final String ALL_ITEM_SOLD_OUT = "76";
    // Amount Data Length
    public static final int AMOUNT_DATA_LENGTH = 12;
    public static final int PAYMENT_TYPE_DATA_LENGTH = 2;
    // Date Time Stamp (yyyyMMddHHmmss)
    public static final String PT_DATE_TIME = "80h";
    // Payment Type for checking Terminal Status from POS to Terminal
    public static final String PT_TSR_PAYMENT_TYPE = "34";
    // Payment Type "CEPAS" for Terminal Status Request from POS to Terminal
    public static final String PT_TSR_PAYMENT_TYPE_CEPAS = "01";
    public static final String PT_TSR_PAYMENT_TYPE_CREDIT_CONTACTLESS ="02";
    public static final String PT_TSR_PAYMENT_TYPE_CREDIT_CARD = "03";
    public static final String PT_TSR_PAYMENT_TYPE_CUP = "04";
    public static final String PT_TSR_PAYMENT_TYPE_ALIPAY_QR_SPOT_PAYMENT = "05";
    public static final String PT_TSR_PAYMENT_TYPE_WECHAT_QR = "06";
    // Tap Card Event from Terminal To POS
    // Tag Name or Field Type for Response Fail
    public static final String TAG_FOR_RESPONSE_FAIL = "50";
    // Terminal no ready for checking Terminal Status
    public static final String TP_TSR_TERMINAL_NO_READY = "58";
    // Terminal no ready due to settlement for checking terminal status
    public static final String TP_TSR_TERMINAL_TERMINAL_NO_READY_DUE_TO_SETTLEMENT = "59";
    // Application no ready or not available for checking terminal status
    public static final String TP_TSR_APPLICATION_NOT_AVAILABLE = "62";
    // Connection to Host for message to host
    public static final String TYPE_FOR_CONNECTION_TO_HOST = "90";
    // Send data to Host for message to Host
    public static final String TYPE_FOR_SEND_TO_HOST = "91";
    // Disconnect with Host
    public static final String TYPE_FOR_DISCONNECT_WITH_HOST = "92";
    // Disconnect
    public static final String DISCONNECT_WITH_HOST = "01";
    // Message to follow
    public static final String MESSAGE_TO_FLOW = "02";
    // Connect to Host for Host Message Upload Response
    public static final String TYPE_CONNECT_TO_HOST = "60";
    // data length
    public static final int LENGTH_BYTES = 2;
    public static final int DATE_TIME_LENGTH = 14;
    public static final int IP_ADDRESS_LENGTH = 18;
    public static final int DISCONNECT_WITH_HOST_LENGTH = 1;
    public static final String TP_PAYMENT_TYPE_CODE = "52";
    public static final String TP_CREDIT_CARD_PAYMENT_TYPE_CODE = "02";
    public static final String TP_NETS_PAYMENT_TYPE_CODE = "03";
    public static final String FIELD_TYPE_FOR_APPROVAL_CODE = "76";
    public static final int WRITE_WAIT_MILLIS = 4000;
    public static final int READ_WAIT_MILLIS = 4000;
}
