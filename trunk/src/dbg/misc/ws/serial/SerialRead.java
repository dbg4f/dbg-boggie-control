package dbg.misc.ws.serial;

import dbg.misc.ws.MessageFlowMediator;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialRead {

    static SerialPort serialPort;

    public static void launch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                main(new String[0]);
            }
        }).start();
    }

    public static void main(String[] args) {
        serialPort = new SerialPort("/dev/ttyACM0");
        try {
            serialPort.openPort();//Open port
            serialPort.setParams(115200, 8, 1, 0);//Set params
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            serialPort.setEventsMask(mask);//Set mask
            //serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener

            System.out.println("serialPort = " + serialPort);

            StringBuilder text = new StringBuilder();

            while(!Thread.currentThread().isInterrupted()) {

                byte[] buf = serialPort.readBytes();

                if (buf != null){
                    //System.out.print(new String(buf));

                    for (byte bt : buf){

                        text.append((char)bt);

                        if (bt == '}') {
                            MessageFlowMediator.getInstance().broadcast(text.toString());
                            text = new StringBuilder();
                        }

                    }


                }


                //serialPort.getInputBufferBytesCount()


            }


        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    /*
     * In this class must implement the method serialEvent, through it we learn about 
     * events that happened to our port. But we will not report on all events but only 
     * those that we put in the mask. In this case the arrival of the data and change the 
     * status lines CTS and DSR
     */
    static class SerialPortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR()){//If data is available
                /*
                if(event.getEventValue() == 10){//Check bytes count in the input buffer
                    //Read data, if 10 bytes available 
                    try {
                        byte buffer[] = serialPort.readBytes(10);
                    }
                    catch (SerialPortException ex) {
                        System.out.println(ex);
                    }
                }
                */


                try {
                    byte[] buf = serialPort.readBytes();
                    System.out.print(new String(buf));
                } catch (SerialPortException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            else if(event.isCTS()){//If CTS line has changed state
                if(event.getEventValue() == 1){//If line is ON
                    System.out.println("CTS - ON");
                }
                else {
                    System.out.println("CTS - OFF");
                }
            }
            else if(event.isDSR()){///If DSR line has changed state
                if(event.getEventValue() == 1){//If line is ON
                    System.out.println("DSR - ON");
                }
                else {
                    System.out.println("DSR - OFF");
                }
            }
        }
    }
}