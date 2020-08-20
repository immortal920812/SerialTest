package rs232Conn;

import PosTransaction.WireCardMethods;
import gnu.io.CommPortIdentifier;  
import gnu.io.SerialPort;  
   
public class RS232Example {  
     
    public void connect(String portName) throws Exception {  
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);  
   
        if (portIdentifier.isCurrentlyOwned()) {  
            System.out.println("Port in use!");  
        } else {  
            // points who owns the port and connection timeout  
            SerialPort serialPort = (SerialPort) portIdentifier.open("RS232Example", 2000);  
              
            // setup connection parameters  
            serialPort.setSerialPortParams(  
                9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);  
   
            // setup serial port writer  
            CommPortSender.setWriterStream(serialPort.getOutputStream());  
              
            // setup serial port reader  
            new CommPortReceiver(serialPort.getInputStream()).start();  
        }  
    }  
      
    public static void main(String[] args) throws Exception {  
          
        // connects to the port which name (e.g. COM1) is in the first argument  
        new RS232Example().connect("COM2");
        
        //productCode, paymentType, amount, invoiceNo, qrOrBarcode
        //WireCardMethods.transactionRequest(productCode, paymentType, amount, invoiceNo, qrOrBarcode);
        
        //   30 30 30 30 30 31 31 32 30 30 30 30 1C 33 34 00 02 30 33 1C 34 30 00 12 30 30 30 30 30 30 30 30 30 30 30 31 1C 03 78
        byte[] Testingdata= {0x02,0x00,0x42,0x36,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x31,0x31,0x32,0x30,0x30,0x30,0x30,0x1C,0x33,0x34,0x00,0x02,0x30,0x33,0x1C,0x34,0x30,0x00,0x12,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x31,0x1C,0x03,0x78};
          
        // send HELO message through serial port using protocol implementation  
        //CommPortSender.send(new ProtocolImpl().getMessage("HELO"));  
        CommPortSender.send(Testingdata); 
    }  
}  

