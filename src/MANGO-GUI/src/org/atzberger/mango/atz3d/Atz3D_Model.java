/*
 * Model for the 3D scene representing the SELM model.
 */

package org.atzberger.mango.atz3d;

/**
 *
 * Provides representation of a 3D model.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz3D_Model {

  int       numElements = 0;
  int       numElementsAlloc = 0;
  protected Atz3D_Element[] z3D_ElementList = null;
  protected int[]         keyList = null;
  int                     lastKey = -1;

  public Atz3D_Model() {
    numElementsAlloc = 100;
    numElements      = 0;
    z3D_ElementList  = new Atz3D_Element[numElementsAlloc];
    keyList          = new int[numElementsAlloc];
  }

  public Atz3D_Element[] getElementList() {
    return makeSubArray(z3D_ElementList, numElements);
  }

  public int[] addElements(Atz3D_Element[] atz3D_Elements) {

    int N           = atz3D_Elements.length;
    int[] list_keys = new int[N];

    for (int k = 0; k < N; k++) {
      list_keys[k] = addElement(atz3D_Elements[k]);
    }

    return list_keys;
  }

  public int addElement(Atz3D_Element z3D_Element) {
    int key = -1;

    if (numElements < numElementsAlloc) {
      z3D_ElementList[numElements] = z3D_Element;
      key                          = lastKey + 1;
      lastKey                      = key;
      keyList[numElements]         = key;
      numElements++;
    } else {
      resizeLists(2*numElementsAlloc);
      addElement(z3D_Element);
    }

    return key;
  }

  public void removeElement(int key) {

    /* find the element with given key and remove */
    int index = -1;
    for (int k = 0; k < numElements; k++) {
      if (keyList[k] == key) {
        index = k;
      }
    }

    /* overwrite the entry with last one and decrease list size */
    z3D_ElementList[index] = z3D_ElementList[numElements];
    keyList[index]         = keyList[numElements];
    numElements--;

  }

  public void resizeLists(int numElementsAlloc_new) {

    Atz3D_Element[] z3D_ElementList_new = new Atz3D_Element[numElementsAlloc_new];
    int[]         keyList_new         = new int[numElementsAlloc_new];

     for (int k = 0; k < numElements; k++) {
       z3D_ElementList_new[k] = z3D_ElementList[k];
       keyList_new[k]         = keyList[k];
     }

    z3D_ElementList = z3D_ElementList_new;
    keyList         = keyList_new;

  }


  private Atz3D_Element[] makeSubArray(Atz3D_Element[] array, int numItems) {

    Atz3D_Element[] subArray = new Atz3D_Element[numItems];

    System.arraycopy(array, 0, subArray, 0, numItems);

    return subArray;

  }


}
