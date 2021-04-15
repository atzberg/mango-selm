package org.atzberger.application.selm_builder;

import java.util.HashMap;

/**
 *
 * Handles response to data change events for hierarchical hash map.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_Struct_DataChangeListener_MainData implements Atz_Struct_DataChangeListener {

  JTable_MainData jTable_mainData;
  String          message;

  boolean         flagHandlerListening = true;  /* used to control listening to avoid infinite
                                                 * loops when this handler makes a setData call
                                                 */

  Atz_Struct_DataChangeListener_MainData() {
    message = "Test Listener Fired";
  }

  Atz_Struct_DataChangeListener_MainData(JTable_MainData jTable_mainData_in) {
    jTable_mainData = jTable_mainData_in;
    message = "Test Listener Fired";
  }

  Atz_Struct_DataChangeListener_MainData(String message_in) {
    message = message_in;
  }

  public void handleDataChange(Atz_Struct_DataChangeEvent e) {

    if (flagHandlerListening) {

      Atz_Struct_DataContainer dataContainer = (Atz_Struct_DataContainer) e.getSource();

      if (e.getDataChangeType().equals(Atz_Struct_DataChangeEvent.EVENT_TYPE_SET)) {
        HashMap<String,Object> extras = (HashMap<String,Object>) e.getDataChangeExtraInfo();
        String relSearchStr           = (String) extras.get("relativeSearchStr");
        String structName             = dataContainer.getName();
        
        //System.out.println("Atz_Struct_DataChangeListener_MainData: Struct Name = " + structName);
        //System.out.println("Atz_Struct_DataChangeListener_MainData: Setting Field = " + relSearchStr);
        ////System.out.println("Atz_Struct_DataChangeListener_MainData: message = " + message);

        /* determine if field should be used to set table data */
        if (jTable_mainData != null) {

          /* Generic call to set new value, if in table */
          Object data_new     = extras.get("data_new");
          Object sourceCaller = extras.get("sourceCaller");

          /* Update the table data, provided this was not the source of the call.
           * This test avoids an infinite loop of events.
           */
          if (TableModel_MainData.class.isInstance(sourceCaller) == false) {
            jTable_mainData.setValueByFieldName(relSearchStr, data_new);
          }
          
//          if (relSearchStr.equals(TableModel_MainData.nsTag_BaseFilename)) {
//
//            String data_new = (String) extras.get("data_new");
//
//            Object sourceCaller = extras.get("sourceCaller");
//
//            if (sourceCaller != null) {
//              //System.out.println("sourceCaller = " + sourceCaller.getClass().getName());
//            } else {
//              //System.out.println("sourceCaller = " + "(null)");
//            }
//
//            //System.out.println("!!!!!!!!!!!!!.....Base filename changed....!!!!!!!!!!!!!!!!!!!!!!");
//
//              /* Update the table data, provided this was not the source of the call.
//               * This test avoids an infinite loop of events.
//               */
//            if (TableModel_MainData.class.isInstance(sourceCaller) == false) {
//              //jTable_mainData.setBaseFilename(data_new);
//              jTable_mainData.setValueByFieldName(relSearchStr, data_new);
//              //System.out.println("Updating the table since setData originated elsewhere.");
//            } else {
//              //System.out.println("Skipping table update, since table was the source!");
//            }
//          }

        } /* end jTable_mainData != null */

      } /* end SET event */

    } /* flagHandlerListening */
    
  }

  public void setHandlerListening(boolean flagHandlerListening_in) {
    flagHandlerListening = flagHandlerListening_in;
  }

  public boolean isHandlerListening() {
    return flagHandlerListening;
  }

}
