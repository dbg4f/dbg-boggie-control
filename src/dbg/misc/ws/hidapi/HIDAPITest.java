package dbg.misc.ws.hidapi;

import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDDeviceInfo;
import com.codeminders.hidapi.HIDManager;
import dbg.misc.ws.JettyWebSocketHandler;

import java.io.IOException;
import java.util.Date;


/**
 * This class demonstrates enumeration, reading and getting
 * notifications when a HID device is connected/disconnected.
 */
public class HIDAPITest
{
    private static final long READ_UPDATE_DELAY_MS = 100L;

    static
    {
        //ClassPathLibraryLoader.loadNativeHIDLibrary();
//        System.setProperty("java.library.path", "native\\win");
//        System.loadLibrary("hidapi-jni-64");
    }

    //static final int VENDOR_ID = 1411;
    //static final int PRODUCT_ID = 40971;
    static final int VENDOR_ID = 4607;
    static final int PRODUCT_ID = 13105;
    private static final int BUFSIZE = 2048;

    /**
     * @param args input strings value.
     */
    public static void main(String[] args) throws IOException
    {

        System.setProperty("java.library.path", "native\\win");
        System.loadLibrary("hidapi-jni-64");

        listDevices();
        readDevice();
    }


    public static void launch() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    main(new String[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    /**
     * Static function to read an input report to a HID device.
     */
    private static void readDevice()
    {

        String currentReport= "";

        HIDDevice dev;
        try
        {
            HIDManager hid_mgr = HIDManager.getInstance();
            dev = hid_mgr.openById(VENDOR_ID, PRODUCT_ID, null);
            System.err.print("Manufacturer: " + dev.getManufacturerString() + "\n");
            System.err.print("Product: " + dev.getProductString() + "\n");
            System.err.print("Serial Number: " + dev.getSerialNumberString() + "\n");
            try
            {
                byte[] buf = new byte[BUFSIZE];
                dev.enableBlocking();
                while(true)
                {
                    int n = dev.read(buf);

                    StringBuffer hidReportBuf = new StringBuffer();

                    for(int i=0; i<n; i++)
                    {
                        int v = buf[i];
                        if (v<0) v = v+256;
                        String hs = Integer.toHexString(v);

                        if (i == 2) {
                            double d = v / 2.55;
                            hidReportBuf.append((int)d);
                        }

                        //if (v<16)
                        //    hidReportBuf.append("0");
                        //hidReportBuf.append(hs + " ");
                    }

                    String hidReport = hidReportBuf.toString();
                    if (!hidReport.equalsIgnoreCase(currentReport)) {
                        currentReport = hidReport;
                        String hidReportWithDate = hidReport + " " + new Date();
                        System.out.println("hidReport = " + hidReportWithDate);

                        JettyWebSocketHandler.sendToClient(hidReport);

                    }

                    try
                    {
                        Thread.sleep(READ_UPDATE_DELAY_MS);
                    } catch(InterruptedException e)
                    {
                        //Ignore
                        e.printStackTrace();
                    }
                }
            } finally
            {
                dev.close();
                hid_mgr.release();
                System.gc();
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Static function to find the list of all the HID devices
     * attached to the system.
     */
    private static void listDevices()
    {
        String property = System.getProperty("java.library.path");
        System.err.println(property);
        try
        {

            HIDManager manager = HIDManager.getInstance();
            HIDDeviceInfo[] devs = manager.listDevices();
            System.err.println("Devices:\n\n");
            for(int i=0;i<devs.length;i++)
            {
                System.err.println(""+i+".\t"+devs[i]);
                System.err.println("---------------------------------------------\n");
            }
            System.gc();
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
