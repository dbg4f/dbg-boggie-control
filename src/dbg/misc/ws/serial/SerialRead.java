package dbg.misc.ws.serial;

import dbg.misc.ws.MessageConsumer;
import dbg.misc.ws.MessageFlowMediator;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SerialRead implements MessageConsumer {

  private static Logger log = LoggerFactory.getLogger(SerialRead.class);

  static SerialPort serialPort;

  public static void launch() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          main(new String[0]);
        }
        catch (Exception e) {
          log.error("Failed to launch serial port reader");
        }
      }
    }).start();
  }

  public static void main(String[] args) throws InterruptedException, SerialPortException {
    new SerialRead().mainCycle();
  }

  private void mainCycle() throws InterruptedException, SerialPortException {

    serialPort = new SerialPort("/dev/ttyACM0");

    while (!Thread.currentThread().isInterrupted()) {

      waitForConnectionOk();

      registerAsConsumer();

      configurePort();

      StringBuilder text = new StringBuilder();

      while (!Thread.currentThread().isInterrupted()) {

        try {
          byte[] buf = serialPort.readBytes();

          text = parseAndForward(text, buf);

        }
        catch (Exception e) {
          log.error("Failed to read and forward serial data: " + e.getMessage(), e);
          deregisterConsumer();
        }

      }

    }

  }

  private void deregisterConsumer() {
    MessageFlowMediator.getInstance().deregisterConsumer(this);
  }

  private void registerAsConsumer() {
    MessageFlowMediator.getInstance().registerConsumer(this);
  }

  private StringBuilder parseAndForward(StringBuilder text, byte[] buf) {

    if (buf != null) {

      for (byte bt : buf) {

        text.append((char)bt);

        if (bt == '}') {
          MessageFlowMediator.getInstance().broadcast(text.toString());
          text = new StringBuilder();
        }

      }

    }
    return text;
  }

  private void configurePort() throws SerialPortException {
    serialPort.setParams(115200, 8, 1, 0);//Set params
    int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
    serialPort.setEventsMask(mask);//Set mask
    //serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener

    log.info("Serial connected");
  }

  private void waitForConnectionOk() throws InterruptedException {

    int i=0;

    while (!Thread.currentThread().isInterrupted()) {

      try {
        serialPort.openPort();//Open port
        break;
      }
      catch (Exception e) {

        if (i % 10 == 0) {
          log.error("Failed to open serial port: " + e.getMessage(), e);
        }

        Thread.sleep(5000);
        i++;
      }


    }
  }

  @Override
  public void onMessage(String message) throws IOException {

    if (serialPort != null && serialPort.isOpened()) {
      try {
        serialPort.writeBytes(message.getBytes());
      }
      catch (Exception e) {
        log.error("Failed to send message to serial: " + e.getMessage(), e);
      }
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
      if (event.isRXCHAR()) {//If data is available
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
        }
        catch (SerialPortException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
      }
      else if (event.isCTS()) {//If CTS line has changed state
        if (event.getEventValue() == 1) {//If line is ON
          System.out.println("CTS - ON");
        }
        else {
          System.out.println("CTS - OFF");
        }
      }
      else if (event.isDSR()) {///If DSR line has changed state
        if (event.getEventValue() == 1) {//If line is ON
          System.out.println("DSR - ON");
        }
        else {
          System.out.println("DSR - OFF");
        }
      }
    }
  }
}