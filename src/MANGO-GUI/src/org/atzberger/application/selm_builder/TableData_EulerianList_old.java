package org.atzberger.application.selm_builder;

/**
 *
 * Customised data represention for display and editing within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableData_EulerianList_old {

  SELM_Interaction[] interactionList;  /* list of interaction objects */

  TableData_EulerianList_old() {

    SELM_Interaction_PAIRS_HARMONIC harmonic;
    double                     stiffnessK;

    interactionList = new SELM_Interaction[3];

    harmonic   = new SELM_Interaction_PAIRS_HARMONIC();
    stiffnessK = 1.1;
    harmonic.InteractionName = "struts";
    harmonic.addPair(null, 0, null, 1, stiffnessK);
    harmonic.addPair(null, 0, null, 2, stiffnessK);
    harmonic.addPair(null, 1, null, 2, stiffnessK);

    interactionList[0] = harmonic;

    harmonic   = new SELM_Interaction_PAIRS_HARMONIC();
    stiffnessK = 2.2;
    harmonic.InteractionName = "springs";
    harmonic.addPair(null, 0, null, 1, stiffnessK);
    harmonic.addPair(null, 0, null, 2, stiffnessK);
    harmonic.addPair(null, 1, null, 2, stiffnessK);

    interactionList[1] = harmonic;

    harmonic   = new SELM_Interaction_PAIRS_HARMONIC();
    stiffnessK = 3.3;
    harmonic.InteractionName = "braces";
    harmonic.addPair(null, 0, null, 1, stiffnessK);
    harmonic.addPair(null, 0, null, 2, stiffnessK);
    harmonic.addPair(null, 1, null, 2, stiffnessK);

    interactionList[2] = harmonic;


  }
  
}
