package org.atzberger.application.selm_builder;

/**
 *
 * Handles response to data change events for hierarchical hash map.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_Struct_DataChangeListener_Test1 implements Atz_Struct_DataChangeListener {

  String message;

  Atz_Struct_DataChangeListener_Test1() {
    message = "Test Listener Fired";
  }

  Atz_Struct_DataChangeListener_Test1(String message_in) {
    message = message_in;
  }

  public void handleDataChange(Atz_Struct_DataChangeEvent e) {
    System.out.println("Atz_Struct_DataChangeListener_Test1: message = " + message);
  }

}

