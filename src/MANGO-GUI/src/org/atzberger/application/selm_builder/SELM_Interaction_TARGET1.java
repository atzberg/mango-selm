package org.atzberger.application.selm_builder;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class SELM_Interaction_TARGET1 extends SELM_Interaction {
    
  int    numPairs;
  int    numPairsAlloc;
  int    pairList_ptI1[];
  int    pairList_ptI2[];  /* assumes I2 > I1 (keeps unique) */

  double restLength[];
  double stiffnessK[];
  
  SELM_Interaction_TARGET1() {

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();
    
    InteractionName    = "Your Name Here";
    InteractionTypeStr = thisClassName.replace(superClassName + "_", "");

    numPairsAlloc = 1;
    numPairs      = 0;

    pairList_ptI1 = new int[numPairsAlloc];
    pairList_ptI2 = new int[numPairsAlloc];

    restLength    = new double[numPairsAlloc];
    stiffnessK    = new double[numPairsAlloc];

  }

  void addPair(int ptI1_in, int ptI2_in, double stiffnessK_in) {

    double restLength;

    restLength = 0.0;
    addPair(ptI1_in, ptI2_in, stiffnessK_in, restLength);

  }


  /* add a pair to the list */
  void addPair(int ptI1_in, int ptI2_in, double stiffnessK_in, double restLength_in) {

    boolean flagNewPair = true;
    
    int    i,j,k;
    int    ptI1, ptI2;

    int    pairList_ptI1_new[];
    int    pairList_ptI2_new[];

    double restLength_new[];
    double stiffnessK_new[];

    /* == make sure ptI1 <= ptI2 */
    if (ptI1_in <= ptI2_in) {
      ptI1 = ptI1_in;
      ptI2 = ptI2_in;
    } else {
      ptI1 = ptI2_in;
      ptI2 = ptI1_in;
    }

    /* == check to make sure does not already exist */
    for (k = 0; k < numPairs; k++) {
      if ((pairList_ptI1[k] == ptI1) && (pairList_ptI2[k] == ptI2)) {
        flagNewPair = false;
      }
    }

    /* == add this entry to the list */
    if (flagNewPair) {

      if (numPairs < numPairsAlloc) {

        pairList_ptI1[numPairs] = ptI1;
        pairList_ptI2[numPairs] = ptI2;
        stiffnessK[numPairs] = stiffnessK_in;
        restLength[numPairs] = restLength_in;
        numPairs++;

      } else { /* == resize arrays is necessary, before add */

        numPairsAlloc = 2 * numPairsAlloc;

        pairList_ptI1_new = new int[numPairsAlloc];
        pairList_ptI2_new = new int[numPairsAlloc];

        restLength_new = new double[numPairsAlloc];
        stiffnessK_new = new double[numPairsAlloc];

        System.arraycopy(pairList_ptI1, 0, pairList_ptI1_new, 0, numPairs);
        System.arraycopy(pairList_ptI2, 0, pairList_ptI2_new, 0, numPairs);

        System.arraycopy(restLength, 0, restLength_new, 0, numPairs);
        System.arraycopy(stiffnessK, 0, stiffnessK_new, 0, numPairs);

        pairList_ptI1 = pairList_ptI1_new;
        pairList_ptI2 = pairList_ptI2_new;

        restLength = restLength_new;
        stiffnessK = stiffnessK_new;

        /* try to add again */
        addPair(ptI1, ptI2, restLength_in, stiffnessK_in); /* can @optimize checks */

      } /* end else */
      
    } /* end newPair */
    
  } /* add interaction */

  
  /* remove and re-label all of the pairs */
  void removePtI(int ptI) {
    
    int     k;
    
    /* == relabel indices and remove entries for given ptI */
    for (k = 0; k < numPairs; k++) {

      if ((pairList_ptI1[k] == ptI) || (pairList_ptI2[k] == ptI)) {        
        pairList_ptI1[k] = -1; /* mark for removal */
        pairList_ptI2[k] = -1;
      } else { /* otherwise reduce index by one for all indices larger than ptI */
        if (pairList_ptI1[k] > ptI) {
          pairList_ptI1[k]--;
        }
        if (pairList_ptI2[k] > ptI) {
          pairList_ptI2[k]--;
        }
      }
      
    } /* end k loop */

    /* removes all entries with ptI1 or ptI2 == -1 */
    removeInteraction();

  }




  /* remove interaction */
  void removeInteraction(int ptI1_in, int ptI2_in) {

    int k;
    int ptI1, ptI2;

    boolean flagFound   = false;
    int     removeIndex = -1;

    /* == make sure ptI1 <= ptI2 */
    if (ptI1_in <= ptI2_in) {
      ptI1 = ptI1_in;
      ptI2 = ptI2_in;
    } else {
      ptI1 = ptI2_in;
      ptI2 = ptI1_in;
    }

    /* == find index of entry to remove */
    for (k = 0; k < numPairs; k++) {

      if ((pairList_ptI1[k] == ptI1) && (pairList_ptI2[k] == ptI2)) {        
        removeIndex = k;
        flagFound   = true;
      }
      
    } /* end k loop */

    /* == if instance found then remove this entry */
    if (flagFound) {
      removeInteraction(removeIndex);
    } /* end flagFound */
    
  } /* end removeInteraction */


  /* remove interaction */
  void removeInteraction(int removeIndex) {

    int k;
    int ptI1, ptI2;

    boolean flagFound = false;
    
    int pairList_ptI1_new[];
    int pairList_ptI2_new[];

    double restLength_new[];
    double stiffnessK_new[];

    /* resize array if a lot of slack */
    if (numPairs < numPairsAlloc / 2) {
      numPairsAlloc = numPairsAlloc / 2;
    }

    /* copy data to smaller array with entry deleted */
    pairList_ptI1_new = new int[numPairsAlloc];
    pairList_ptI2_new = new int[numPairsAlloc];

    restLength_new = new double[numPairsAlloc];
    stiffnessK_new = new double[numPairsAlloc];

    /* copy first part of the array */
    if (removeIndex >= 0) {
      System.arraycopy(pairList_ptI1, 0, pairList_ptI1_new, 0, removeIndex);
      System.arraycopy(pairList_ptI2, 0, pairList_ptI2_new, 0, removeIndex);

      System.arraycopy(restLength, 0, restLength_new, 0, removeIndex);
      System.arraycopy(stiffnessK, 0, stiffnessK_new, 0, removeIndex);
    }

    /* copy second part of the array */
    System.arraycopy(pairList_ptI1, removeIndex + 1, pairList_ptI1_new, removeIndex, numPairs - removeIndex - 1);
    System.arraycopy(pairList_ptI2, removeIndex + 1, pairList_ptI2_new, removeIndex, numPairs - removeIndex - 1);

    System.arraycopy(restLength, removeIndex + 1, restLength_new, removeIndex, numPairs - removeIndex - 1);
    System.arraycopy(stiffnessK, removeIndex + 1, stiffnessK_new, removeIndex, numPairs - removeIndex - 1);

    pairList_ptI1 = pairList_ptI1_new;
    pairList_ptI2 = pairList_ptI2_new;

    restLength = restLength_new;
    stiffnessK = stiffnessK_new;
  }


  /* removes all entries with ptI == -1 */
  void removeInteraction() {

    int k;
    int ptI1, ptI2;

    int numPairs_new = 0;

    boolean flagFound = false;

    int pairList_ptI1_new[];
    int pairList_ptI2_new[];

    double restLength_new[];
    double stiffnessK_new[];

    /* copy data to smaller array with entry deleted */
    pairList_ptI1_new = new int[numPairsAlloc];
    pairList_ptI2_new = new int[numPairsAlloc];

    restLength_new = new double[numPairsAlloc];
    stiffnessK_new = new double[numPairsAlloc];

    /* copy only the entries not having ptI == -1 */
    for (k = 0; k < numPairs; k++) {

      if ((pairList_ptI1[k] != -1) && (pairList_ptI2[k] != -1)) {
        pairList_ptI1_new[numPairs_new] = pairList_ptI1[k];
        pairList_ptI2_new[numPairs_new] = pairList_ptI2[k];
        restLength_new[numPairs_new]    = restLength[k];
        stiffnessK_new[numPairs_new]    = stiffnessK[k];
        numPairs_new++;
      }

    } /* end k loop */

  } /* removeInteraction */
  
}
