package org.atzberger.mango.windowing;

import java.awt.Component;
import javax.swing.JTabbedPane;

/**
 *
 * Provides tabbed window panes that can hold panels with closeable and windowing
 * functionality.  Dockable frames are comprised of this container type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JTabbedPane_Closeable extends JTabbedPane {

  public JTabbedPane_Closeable() {
    super();
  }
  
  @Override
  public void addTab(String name, Component component) {

    /* WARNING: should also override other add methods */

    super.addTab(name, component);

    super.setSelectedComponent(component); /* select the current component */
    int index = super.getSelectedIndex();

    /* add the closable button component */
    setTabComponentAt(index, new JTabbedComponent_CloseButton(this, component));
    //setTabComponentAt(index, new JTabbedComponent_Manageable(this, name));
    
  }


}
