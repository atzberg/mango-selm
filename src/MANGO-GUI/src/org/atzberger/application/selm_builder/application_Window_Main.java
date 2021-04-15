/**
 * @author Paul J. Atzberger
 *
 *
 */
package org.atzberger.application.selm_builder;

import org.atzberger.mango.windowing.JTextPane_Output_ErrorStream;
import org.atzberger.mango.windowing.JTextPane_Output_OutputStream;
import org.atzberger.mango.windowing.JTextPane_Output_InputStream;
import org.atzberger.mango.windowing.JTextPane_Output;
import org.atzberger.mango.windowing.JPanel_Output_Messages;
import org.atzberger.mango.atz3d.JPanel_Model_View_RenderPanel;
import org.atzberger.mango.atz3d.JPanel_Model_View_Composite;
import org.atzberger.mango.windowing.JTabbedPane_Closeable;
import org.atzberger.mango.jython.JPanel_Editor_Jython;
import org.atzberger.mango.units.Atz_UnitsRef;
import org.atzberger.mango.table.JDialog_Edit_Array_Simple_New;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;



import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


/**
 * Handles the applications main frame.  This includes the pull-down menus, docking panels, and tool bars.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 * 
 */
public class application_Window_Main extends FrameView {

    private int splash_numSetupSteps  = 13;
    private int splash_setupStepIndex = 0;

    private application_Main applicationMain;

    private JFrame_SplashProgress jFrame_Splash = null;

    /**
     *
     * Constructs the MANGO interface panels, performs layout, and sets up
     * all data structures.  
     *
     * @param app The main application frame holding the interface.
     */
    public application_Window_Main(SingleFrameApplication app) {
      super(app);

      /* record reference to main application */
      applicationMain = (application_Main)app;  
      
      /* == setup splash screen the monitor progress */
      jFrame_Splash = new JFrame_SplashProgress();      
      jFrame_Splash.setNumSetupSteps(splash_numSetupSteps);                        
      jFrame_Splash.setVisible(true);      
      setframeicon(jFrame_Splash, "PJA_IconForFrame");
      
      /* perform the main window setup using a separte thread */
      (new application_Window_Main_SetupThread(this)).start();
      
      // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        //statusAnimationLabel.setIcon(idleIcon);
        //progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    
    }

    /**
     * Peforms setup of the main application window and related data structures.
     */
    public void setupApplicationWindowMain() {

      splash_setupStepIndex++;
      jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Initializing application components.");

      initComponents();

      /* setup a progress bar and run the initialization on a separate thread */
      
//
//      JPanel[] jPanelList = null;
//
//      /* show splash / progress screen (run on its own thread) */
//      application_Window_Main_SetupThread jFrame_Splash_Thread = new application_Window_Main_SetupThread();
//
//      jFrame_Splash_Thread.start();
//
         
//
//      setframeicon(jFrame_Splash, "PJA_IconForFrame");
//
//      jFrame_Splash.setNumSetupSteps(splash_numSetupSteps);

      /* PJA: modify the existing component to add custom values */
      modifyComponents();

      /*
      JDialog_WindowPanelHolder jDialog_test = new JDialog_WindowPanelHolder(this.getFrame(), false, this.jPanel_Editor_Jython);

      jDialog_test.setPanel(this.jPanel_Editor_Jython);

      this.jPanel_Editor_Jython.setVisible(true);
    
      jDialog_test.setVisible(true);
       */

      /* redirect the standard out */
//      try {
//        stdout_new = new PrintStream(new FileOutputStream("Redirect.out"));
//      } catch (Exception e) {
//        // Sigh.  Couldn't open the file.
//        System.out.println("Redirect:  Unable to open output file!");
//        System.exit(1);
//      }
        

    /* PJA: Application Icon set */
    //idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
    //statusAnimationLabel.setIcon(idleIcon);
    //jFrame_Main.
    //FrameView mainView = application_Main.getApplication().getMainView();
    //JFrame mainFrame = mainView.getFrame();
        
    //mainFrame.setIconImage(null);
    //mainFrame.getIconImage();

      /* show the main application window */
      applicationMain.show(this);

      /* close the splash screen */
      jFrame_Splash.setVisible(false);
      jFrame_Splash = null;

        /* set the icon frame again (should be freed from splash now */
        setframeicon(this.getFrame(), "PJA_IconForFrame");

//        // Run ls command
//        try {
//            String cmd = "/usr/bin/xterm &";
//            Runtime.getRuntime().exec(cmd);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        /* @@@@@@@@ Shows how to call and interact with Python sub-process via Java,  could make interactive shell this way @@@@@@ */
//        /* Basic idea would be to launch application using Python from a Java consolve (JPanel) that uses below to re-route input / output
//         * then this panel could be embedded above.  Would allow Python / Jython control of the GUI in self-contained way.   This would
//         * allow for SciPy and other conventional LAMMPS stuff to be used from out GUI.  Just one idea...
//         */
//        try {
//            //Process p = Runtime.getRuntime().exec("cmd /C dir");//Windows command, use "ls -oa" for UNIX
//            //Process p = Runtime.getRuntime().exec("ls -oa"); // for UNIX
//            Process p = Runtime.getRuntime().exec("python -vEi"); // for UNIX
//            p.getOutputStream().write("import os; os.getcwd(); print('test message'); exit() \n".getBytes());
//            p.getOutputStream().flush();
//            p.waitFor();  /* very important to allow for the commands to execute */
//            Scanner sc2 = new Scanner(p.getErrorStream());
//            while (sc2.hasNext()) {
//                System.err.println(sc2.nextLine());
//            }
//            Scanner sc = new Scanner(p.getInputStream());
//            while (sc.hasNext()) {
//                System.out.println(sc.nextLine());
//            }
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }


    }

    /**
     *
     * @param window            Frame whose icon is to be set
     * @param iconResourceName  Name of the icon resource to be used
     */
    public void setframeicon(JFrame window, String iconResourceName){
    try{
        org.jdesktop.application.ResourceMap resourceMap
          = org.jdesktop.application.Application.getInstance(org.atzberger.application.selm_builder.application_Main.class).getContext().getResourceMap(application_Window_Main.class);

        //String tmp = resourceMap.getString("PJA_IconForFrame");        
        ImageIcon myImg = resourceMap.getImageIcon(iconResourceName);
        
        //InputStream imgStream = resourceMap.get getResourceAsStream(icon);
        //BufferedImage bi = ImageIO.read(imgStream);
        //ImageIcon myImg = new ImageIcon(bi);

        window.setIconImage(myImg.getImage());
        
        }catch(Exception e){
          System.out.println(e);
        }
    }

    /**
     * Shows the about box information.
     */
    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = application_Main.getApplication().getMainFrame();
            aboutBox = new application_Window_About(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        application_Main.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton_newProject = new javax.swing.JButton();
        jButton_openProject = new javax.swing.JButton();
        jButton_saveProject = new javax.swing.JButton();
        jButton_generate_LAMMPS_USER_SELM = new javax.swing.JButton();
        jButton_aboutInformation = new javax.swing.JButton();
        jSplitPane_Main_Horizontal = new javax.swing.JSplitPane();
        jSplitPane_Main_Right_Vertical = new javax.swing.JSplitPane();
        jPanel_Dockable_Windows_LowerRight = new javax.swing.JPanel();
        jTabbedPane_Docksite_Windows_LowerRight = new JTabbedPane_Closeable();
        jPanel_Model_View_Composite = new javax.swing.JPanel();
        jTabbedPane_Docksite_Windows_UpperRight = new JTabbedPane_Closeable();
        jSplitPane_Main_Left_Vertical = new javax.swing.JSplitPane();
        jPanelTabbed_Main = new javax.swing.JTabbedPane();
        jPanel_Main = new javax.swing.JPanel();
        jPanel_MainData = new javax.swing.JPanel();
        jScrollPane_Table_Main = new javax.swing.JScrollPane();
        jTable_Main = new org.atzberger.application.selm_builder.JTable_MainData();
        jPanel_Lagrangian_DOF = new javax.swing.JPanel();
        jPanel_Eulerian_DOF = new javax.swing.JPanel();
        jPanel_CouplingOp = new javax.swing.JPanel();
        jPanel_Interactions = new javax.swing.JPanel();
        jPanel_Integrator = new javax.swing.JPanel();
        jPanel_Preferences = new javax.swing.JPanel();
        jComboBox_Preferences = new javax.swing.JComboBox();
        jPanel_Preferences_Changable = new javax.swing.JPanel();
        jPanel_Preferences_Rendering = new javax.swing.JPanel();
        jScrollPane_Table_Preferences_Rendering = new javax.swing.JScrollPane();
        jTable_Preferences_Rendering = (JTable) new org.atzberger.application.selm_builder.JTable_Preferences_Rendering();
        jPanel_Preferences_TableDisplay = new javax.swing.JPanel();
        jScrollPane_Table_Preferences_TableDisplay = new javax.swing.JScrollPane();
        jTable_Preferences_TableDisplay = (JTable) new org.atzberger.application.selm_builder.JTable_Preferences_TableDisplay();
        jPanel_Preferences_Other = new javax.swing.JPanel();
        jScrollPane_Table_Preferences_Other = new javax.swing.JScrollPane();
        jTable_Preferences_Other = (JTable) new org.atzberger.application.selm_builder.JTable_Preferences_Rendering();
        jLabel30 = new javax.swing.JLabel();
        jPanel_Dockable_Windows_LowerLeft = new javax.swing.JPanel();
        jTabbedPane_Docksite_Windows_LowerLeft = new JTabbedPane_Closeable();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItem_ProjectNew = new javax.swing.JMenuItem();
        jMenuItem_ProjectOpen = new javax.swing.JMenuItem();
        jMenu_OpenRecentFiles = new javax.swing.JMenu();
        jMenuItem_Empty = new javax.swing.JMenuItem();
        jMenuItem_ProjectSave = new javax.swing.JMenuItem();
        jMenuItem_Generate_LAMMPS = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu_Windows = new javax.swing.JMenu();
        jMenuItem_Window_InteractionEditor = new javax.swing.JMenuItem();
        jMenuItem_LagrangianEditor = new javax.swing.JMenuItem();
        jMenuItem_CouplingOpEditor = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem_renderView = new javax.swing.JMenuItem();
        jMenuItem_messages = new javax.swing.JMenuItem();
        jMenuItem_Jython = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        jMenuItem_Help = new javax.swing.JMenuItem();
        jMenuItem_CheckForUpdates = new javax.swing.JMenuItem();
        jMenuItem_About = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jButtonGroup_ControlPtsViewport = new javax.swing.ButtonGroup();
        jDialog_Edit_SimpleArray = new javax.swing.JDialog();
        label_parameter_name = new java.awt.Label();
        jButton_Cancel = new javax.swing.JButton();
        jButton_OK = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel_space_filler = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(400, 401));

        jToolBar1.setRollover(true);
        jToolBar1.setName("jToolBar1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.atzberger.application.selm_builder.application_Main.class).getContext().getResourceMap(application_Window_Main.class);
        jButton_newProject.setIcon(resourceMap.getIcon("jButton_newProject.icon")); // NOI18N
        jButton_newProject.setToolTipText(resourceMap.getString("jButton_newProject.toolTipText")); // NOI18N
        jButton_newProject.setBorder(null);
        jButton_newProject.setFocusable(false);
        jButton_newProject.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_newProject.setIconTextGap(0);
        jButton_newProject.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton_newProject.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton_newProject.setName("jButton_newProject"); // NOI18N
        jButton_newProject.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton_newProject.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_newProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_newProjectActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_newProject);

        jButton_openProject.setIcon(resourceMap.getIcon("jButton_openProject.icon")); // NOI18N
        jButton_openProject.setText(resourceMap.getString("jButton_openProject.text")); // NOI18N
        jButton_openProject.setToolTipText(resourceMap.getString("jButton_openProject.toolTipText")); // NOI18N
        jButton_openProject.setBorder(null);
        jButton_openProject.setFocusable(false);
        jButton_openProject.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_openProject.setIconTextGap(0);
        jButton_openProject.setName("jButton_openProject"); // NOI18N
        jButton_openProject.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_openProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_openProjectActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_openProject);

        jButton_saveProject.setIcon(resourceMap.getIcon("jButton_saveProject.icon")); // NOI18N
        jButton_saveProject.setText(resourceMap.getString("jButton_saveProject.text")); // NOI18N
        jButton_saveProject.setToolTipText(resourceMap.getString("jButton_saveProject.toolTipText")); // NOI18N
        jButton_saveProject.setBorder(null);
        jButton_saveProject.setFocusable(false);
        jButton_saveProject.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_saveProject.setIconTextGap(0);
        jButton_saveProject.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton_saveProject.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton_saveProject.setName("jButton_saveProject"); // NOI18N
        jButton_saveProject.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton_saveProject.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_saveProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_saveProjectActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_saveProject);

        jButton_generate_LAMMPS_USER_SELM.setIcon(resourceMap.getIcon("jButton_generate_LAMMPS_USER_SELM.icon")); // NOI18N
        jButton_generate_LAMMPS_USER_SELM.setText(resourceMap.getString("jButton_generate_LAMMPS_USER_SELM.text")); // NOI18N
        jButton_generate_LAMMPS_USER_SELM.setToolTipText(resourceMap.getString("jButton_generate_LAMMPS_USER_SELM.toolTipText")); // NOI18N
        jButton_generate_LAMMPS_USER_SELM.setBorder(null);
        jButton_generate_LAMMPS_USER_SELM.setFocusable(false);
        jButton_generate_LAMMPS_USER_SELM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_generate_LAMMPS_USER_SELM.setIconTextGap(0);
        jButton_generate_LAMMPS_USER_SELM.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton_generate_LAMMPS_USER_SELM.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton_generate_LAMMPS_USER_SELM.setName("jButton_generate_LAMMPS_USER_SELM"); // NOI18N
        jButton_generate_LAMMPS_USER_SELM.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_generate_LAMMPS_USER_SELM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_generate_LAMMPS_USER_SELMActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_generate_LAMMPS_USER_SELM);

        jButton_aboutInformation.setIcon(resourceMap.getIcon("jButton_aboutInformation.icon")); // NOI18N
        jButton_aboutInformation.setText(resourceMap.getString("jButton_aboutInformation.text")); // NOI18N
        jButton_aboutInformation.setToolTipText(resourceMap.getString("jButton_aboutInformation.toolTipText")); // NOI18N
        jButton_aboutInformation.setBorder(null);
        jButton_aboutInformation.setFocusable(false);
        jButton_aboutInformation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_aboutInformation.setIconTextGap(0);
        jButton_aboutInformation.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton_aboutInformation.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton_aboutInformation.setName("jButton_aboutInformation"); // NOI18N
        jButton_aboutInformation.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton_aboutInformation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_aboutInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aboutInformationActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_aboutInformation);

        jSplitPane_Main_Horizontal.setDividerLocation(500);
        jSplitPane_Main_Horizontal.setName("jSplitPane_Main_Horizontal"); // NOI18N

        jSplitPane_Main_Right_Vertical.setDividerLocation(250);
        jSplitPane_Main_Right_Vertical.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane_Main_Right_Vertical.setName("jSplitPane_Main_Right_Vertical"); // NOI18N

        jPanel_Dockable_Windows_LowerRight.setName("jPanel_Dockable_Windows_LowerRight"); // NOI18N
        jPanel_Dockable_Windows_LowerRight.setLayout(new javax.swing.BoxLayout(jPanel_Dockable_Windows_LowerRight, javax.swing.BoxLayout.LINE_AXIS));

        jTabbedPane_Docksite_Windows_LowerRight.setBackground(resourceMap.getColor("jTabbedPane_Docksite_Windows_LowerRight.background")); // NOI18N
        jTabbedPane_Docksite_Windows_LowerRight.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane_Docksite_Windows_LowerRight.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane_Docksite_Windows_LowerRight.setName("jTabbedPane_Docksite_Windows_LowerRight"); // NOI18N
        jPanel_Dockable_Windows_LowerRight.add(jTabbedPane_Docksite_Windows_LowerRight);

        jSplitPane_Main_Right_Vertical.setRightComponent(jPanel_Dockable_Windows_LowerRight);

        jPanel_Model_View_Composite.setBackground(resourceMap.getColor("jPanel_Model_View_Composite.background")); // NOI18N
        jPanel_Model_View_Composite.setName("jPanel_Model_View_Composite"); // NOI18N
        jPanel_Model_View_Composite.setLayout(new javax.swing.BoxLayout(jPanel_Model_View_Composite, javax.swing.BoxLayout.LINE_AXIS));

        jTabbedPane_Docksite_Windows_UpperRight.setBackground(resourceMap.getColor("jTabbedPane_Docksite_Windows_UpperRight.background")); // NOI18N
        jTabbedPane_Docksite_Windows_UpperRight.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane_Docksite_Windows_UpperRight.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane_Docksite_Windows_UpperRight.setName("jTabbedPane_Docksite_Windows_UpperRight"); // NOI18N
        jPanel_Model_View_Composite.add(jTabbedPane_Docksite_Windows_UpperRight);

        jSplitPane_Main_Right_Vertical.setLeftComponent(jPanel_Model_View_Composite);

        jSplitPane_Main_Horizontal.setRightComponent(jSplitPane_Main_Right_Vertical);

        jSplitPane_Main_Left_Vertical.setDividerLocation(250);
        jSplitPane_Main_Left_Vertical.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane_Main_Left_Vertical.setName("jSplitPane_Main_Left_Vertical"); // NOI18N

        jPanelTabbed_Main.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelTabbed_Main.setName("jPanelTabbed_Main"); // NOI18N
        jPanelTabbed_Main.setPreferredSize(new java.awt.Dimension(400, 361));

        jPanel_Main.setName("jPanel_Main"); // NOI18N

        jPanel_MainData.setName("jPanel_MainData"); // NOI18N

        jScrollPane_Table_Main.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane_Table_Main.setName("jScrollPane_Table_Main"); // NOI18N

        jTable_Main.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("jTable_Main.border.lineColor"))); // NOI18N
        jTable_Main.setModel(new org.atzberger.application.selm_builder.TableModel_MainData());
        jTable_Main.setName("jTable_Main"); // NOI18N
        jScrollPane_Table_Main.setViewportView(jTable_Main);

        javax.swing.GroupLayout jPanel_MainDataLayout = new javax.swing.GroupLayout(jPanel_MainData);
        jPanel_MainData.setLayout(jPanel_MainDataLayout);
        jPanel_MainDataLayout.setHorizontalGroup(
            jPanel_MainDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_MainDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Table_Main, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_MainDataLayout.setVerticalGroup(
            jPanel_MainDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_MainDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Table_Main, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel_MainLayout = new javax.swing.GroupLayout(jPanel_Main);
        jPanel_Main.setLayout(jPanel_MainLayout);
        jPanel_MainLayout.setHorizontalGroup(
            jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_MainData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_MainLayout.setVerticalGroup(
            jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_MainLayout.createSequentialGroup()
                .addComponent(jPanel_MainData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelTabbed_Main.addTab(resourceMap.getString("jPanel_Main.TabConstraints.tabTitle"), jPanel_Main); // NOI18N

        jPanel_Lagrangian_DOF.setMaximumSize(new java.awt.Dimension(5000, 5000));
        jPanel_Lagrangian_DOF.setName("jPanel_Lagrangian_DOF"); // NOI18N
        jPanel_Lagrangian_DOF.setPreferredSize(new java.awt.Dimension(300, 250));
        jPanel_Lagrangian_DOF.setLayout(new java.awt.BorderLayout());
        jPanelTabbed_Main.addTab(resourceMap.getString("jPanel_Lagrangian_DOF.TabConstraints.tabTitle"), jPanel_Lagrangian_DOF); // NOI18N

        jPanel_Eulerian_DOF.setName("jPanel_Eulerian_DOF"); // NOI18N
        jPanel_Eulerian_DOF.setLayout(new javax.swing.BoxLayout(jPanel_Eulerian_DOF, javax.swing.BoxLayout.LINE_AXIS));
        jPanelTabbed_Main.addTab(resourceMap.getString("jPanel_Eulerian_DOF.TabConstraints.tabTitle"), jPanel_Eulerian_DOF); // NOI18N

        jPanel_CouplingOp.setName("jPanel_CouplingOp"); // NOI18N
        jPanel_CouplingOp.setLayout(new java.awt.BorderLayout());
        jPanelTabbed_Main.addTab(resourceMap.getString("jPanel_CouplingOp.TabConstraints.tabTitle"), jPanel_CouplingOp); // NOI18N

        jPanel_Interactions.setName("jPanel_Interactions"); // NOI18N
        jPanel_Interactions.setLayout(new java.awt.BorderLayout());
        jPanelTabbed_Main.addTab(resourceMap.getString("jPanel_Interactions.TabConstraints.tabTitle"), jPanel_Interactions); // NOI18N

        jPanel_Integrator.setName("jPanel_Integrator"); // NOI18N
        jPanel_Integrator.setLayout(new javax.swing.BoxLayout(jPanel_Integrator, javax.swing.BoxLayout.LINE_AXIS));
        jPanelTabbed_Main.addTab(resourceMap.getString("jPanel_Integrator.TabConstraints.tabTitle"), jPanel_Integrator); // NOI18N

        jPanel_Preferences.setName("jPanel_Preferences"); // NOI18N

        jComboBox_Preferences.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rendering", "Table Display", "Other" }));
        jComboBox_Preferences.setName("jComboBox_Preferences"); // NOI18N
        jComboBox_Preferences.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_PreferencesItemStateChanged(evt);
            }
        });

        jPanel_Preferences_Changable.setName("jPanel_Preferences_Changable"); // NOI18N
        jPanel_Preferences_Changable.setLayout(new java.awt.CardLayout());

        jPanel_Preferences_Rendering.setName("jPanel_Preferences_Rendering"); // NOI18N

        jScrollPane_Table_Preferences_Rendering.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane_Table_Preferences_Rendering.setName("jScrollPane_Table_Preferences_Rendering"); // NOI18N

        jTable_Preferences_Rendering.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("jTable_Preferences_Rendering.border.lineColor"))); // NOI18N
        jTable_Preferences_Rendering.setModel(new org.atzberger.application.selm_builder.TableModel_Preferences_Rendering());
        jTable_Preferences_Rendering.setName("jTable_Preferences_Rendering"); // NOI18N
        jTable_Preferences_Rendering.setRowSelectionAllowed(false);
        jScrollPane_Table_Preferences_Rendering.setViewportView(jTable_Preferences_Rendering);

        javax.swing.GroupLayout jPanel_Preferences_RenderingLayout = new javax.swing.GroupLayout(jPanel_Preferences_Rendering);
        jPanel_Preferences_Rendering.setLayout(jPanel_Preferences_RenderingLayout);
        jPanel_Preferences_RenderingLayout.setHorizontalGroup(
            jPanel_Preferences_RenderingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Preferences_RenderingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Table_Preferences_Rendering, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_Preferences_RenderingLayout.setVerticalGroup(
            jPanel_Preferences_RenderingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Preferences_RenderingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Table_Preferences_Rendering, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel_Preferences_Changable.add(jPanel_Preferences_Rendering, "Rendering");

        jPanel_Preferences_TableDisplay.setName("jPanel_Preferences_TableDisplay"); // NOI18N

        jScrollPane_Table_Preferences_TableDisplay.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane_Table_Preferences_TableDisplay.setName("jScrollPane_Table_Preferences_TableDisplay"); // NOI18N

        jTable_Preferences_TableDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("jTable_Preferences_TableDisplay.border.lineColor"))); // NOI18N
        jTable_Preferences_TableDisplay.setModel(new org.atzberger.application.selm_builder.TableModel_Preferences_TableDisplay());
        jTable_Preferences_TableDisplay.setName("jTable_Preferences_TableDisplay"); // NOI18N
        jTable_Preferences_TableDisplay.setRowSelectionAllowed(false);
        jScrollPane_Table_Preferences_TableDisplay.setViewportView(jTable_Preferences_TableDisplay);

        javax.swing.GroupLayout jPanel_Preferences_TableDisplayLayout = new javax.swing.GroupLayout(jPanel_Preferences_TableDisplay);
        jPanel_Preferences_TableDisplay.setLayout(jPanel_Preferences_TableDisplayLayout);
        jPanel_Preferences_TableDisplayLayout.setHorizontalGroup(
            jPanel_Preferences_TableDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Preferences_TableDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Table_Preferences_TableDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_Preferences_TableDisplayLayout.setVerticalGroup(
            jPanel_Preferences_TableDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Preferences_TableDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Table_Preferences_TableDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel_Preferences_Changable.add(jPanel_Preferences_TableDisplay, "TableDisplay");

        jPanel_Preferences_Other.setName("jPanel_Preferences_Other"); // NOI18N

        jScrollPane_Table_Preferences_Other.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane_Table_Preferences_Other.setName("jScrollPane_Table_Preferences_Other"); // NOI18N

        jTable_Preferences_Other.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("jTable_Preferences_Other.border.lineColor"))); // NOI18N
        jTable_Preferences_Other.setModel(new org.atzberger.application.selm_builder.TableModel_Preferences_Other());
        jTable_Preferences_Other.setName("jTable_Preferences_Other"); // NOI18N
        jTable_Preferences_Other.setRowSelectionAllowed(false);
        jScrollPane_Table_Preferences_Other.setViewportView(jTable_Preferences_Other);

        javax.swing.GroupLayout jPanel_Preferences_OtherLayout = new javax.swing.GroupLayout(jPanel_Preferences_Other);
        jPanel_Preferences_Other.setLayout(jPanel_Preferences_OtherLayout);
        jPanel_Preferences_OtherLayout.setHorizontalGroup(
            jPanel_Preferences_OtherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Preferences_OtherLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Table_Preferences_Other, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_Preferences_OtherLayout.setVerticalGroup(
            jPanel_Preferences_OtherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Preferences_OtherLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Table_Preferences_Other, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel_Preferences_Changable.add(jPanel_Preferences_Other, "Other");

        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N

        javax.swing.GroupLayout jPanel_PreferencesLayout = new javax.swing.GroupLayout(jPanel_Preferences);
        jPanel_Preferences.setLayout(jPanel_PreferencesLayout);
        jPanel_PreferencesLayout.setHorizontalGroup(
            jPanel_PreferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_PreferencesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox_Preferences, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
            .addComponent(jPanel_Preferences_Changable, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
        );
        jPanel_PreferencesLayout.setVerticalGroup(
            jPanel_PreferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_PreferencesLayout.createSequentialGroup()
                .addGroup(jPanel_PreferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_Preferences, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_Preferences_Changable, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
        );

        jPanelTabbed_Main.addTab(resourceMap.getString("jPanel_Preferences.TabConstraints.tabTitle"), jPanel_Preferences); // NOI18N

        jSplitPane_Main_Left_Vertical.setTopComponent(jPanelTabbed_Main);

        jPanel_Dockable_Windows_LowerLeft.setName("jPanel_Dockable_Windows_LowerLeft"); // NOI18N
        jPanel_Dockable_Windows_LowerLeft.setLayout(new javax.swing.BoxLayout(jPanel_Dockable_Windows_LowerLeft, javax.swing.BoxLayout.LINE_AXIS));

        jTabbedPane_Docksite_Windows_LowerLeft.setBackground(resourceMap.getColor("jTabbedPane_Docksite_Windows_LowerLeft.background")); // NOI18N
        jTabbedPane_Docksite_Windows_LowerLeft.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane_Docksite_Windows_LowerLeft.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane_Docksite_Windows_LowerLeft.setName("jTabbedPane_Docksite_Windows_LowerLeft"); // NOI18N
        jPanel_Dockable_Windows_LowerLeft.add(jTabbedPane_Docksite_Windows_LowerLeft);

        jSplitPane_Main_Left_Vertical.setRightComponent(jPanel_Dockable_Windows_LowerLeft);

        jSplitPane_Main_Horizontal.setLeftComponent(jSplitPane_Main_Left_Vertical);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1157, Short.MAX_VALUE)
            .addComponent(jSplitPane_Main_Horizontal, javax.swing.GroupLayout.DEFAULT_SIZE, 1157, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane_Main_Horizontal, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N
        fileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuActionPerformed(evt);
            }
        });

        jMenuItem_ProjectNew.setText(resourceMap.getString("jMenuItem_ProjectNew.text")); // NOI18N
        jMenuItem_ProjectNew.setName("jMenuItem_ProjectNew"); // NOI18N
        jMenuItem_ProjectNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ProjectNewActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_ProjectNew);

        jMenuItem_ProjectOpen.setText(resourceMap.getString("jMenuItem_ProjectOpen.text")); // NOI18N
        jMenuItem_ProjectOpen.setName("jMenuItem_ProjectOpen"); // NOI18N
        jMenuItem_ProjectOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ProjectOpenActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_ProjectOpen);

        jMenu_OpenRecentFiles.setText(resourceMap.getString("jMenu_OpenRecentFiles.text")); // NOI18N
        jMenu_OpenRecentFiles.setName("jMenu_OpenRecentFiles"); // NOI18N

        jMenuItem_Empty.setText(resourceMap.getString("jMenuItem_Empty.text")); // NOI18N
        jMenuItem_Empty.setName("jMenuItem_Empty"); // NOI18N
        jMenu_OpenRecentFiles.add(jMenuItem_Empty);

        fileMenu.add(jMenu_OpenRecentFiles);

        jMenuItem_ProjectSave.setText(resourceMap.getString("jMenuItem_ProjectSave.text")); // NOI18N
        jMenuItem_ProjectSave.setName("jMenuItem_ProjectSave"); // NOI18N
        jMenuItem_ProjectSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ProjectSaveActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_ProjectSave);

        jMenuItem_Generate_LAMMPS.setText(resourceMap.getString("jMenuItem_Generate_LAMMPS.text")); // NOI18N
        jMenuItem_Generate_LAMMPS.setName("jMenuItem_Generate_LAMMPS"); // NOI18N
        jMenuItem_Generate_LAMMPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_Generate_LAMMPSActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem_Generate_LAMMPS);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.atzberger.application.selm_builder.application_Main.class).getContext().getActionMap(application_Window_Main.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu_Windows.setText(resourceMap.getString("jMenu_Windows.text")); // NOI18N
        jMenu_Windows.setName("jMenu_Windows"); // NOI18N

        jMenuItem_Window_InteractionEditor.setText(resourceMap.getString("jMenuItem_Window_InteractionEditor.text")); // NOI18N
        jMenuItem_Window_InteractionEditor.setName("jMenuItem_Window_InteractionEditor"); // NOI18N
        jMenuItem_Window_InteractionEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_Window_InteractionEditorActionPerformed(evt);
            }
        });
        jMenu_Windows.add(jMenuItem_Window_InteractionEditor);

        jMenuItem_LagrangianEditor.setText(resourceMap.getString("jMenuItem_LagrangianEditor.text")); // NOI18N
        jMenuItem_LagrangianEditor.setName("jMenuItem_LagrangianEditor"); // NOI18N
        jMenuItem_LagrangianEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_LagrangianEditorActionPerformed(evt);
            }
        });
        jMenu_Windows.add(jMenuItem_LagrangianEditor);

        jMenuItem_CouplingOpEditor.setText(resourceMap.getString("jMenuItem_CouplingOpEditor.text")); // NOI18N
        jMenuItem_CouplingOpEditor.setName("jMenuItem_CouplingOpEditor"); // NOI18N
        jMenuItem_CouplingOpEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_CouplingOpEditorActionPerformed(evt);
            }
        });
        jMenu_Windows.add(jMenuItem_CouplingOpEditor);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jMenu_Windows.add(jSeparator1);

        jMenuItem_renderView.setText(resourceMap.getString("jMenuItem_renderView.text")); // NOI18N
        jMenuItem_renderView.setName("jMenuItem_renderView"); // NOI18N
        jMenuItem_renderView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_renderViewActionPerformed(evt);
            }
        });
        jMenu_Windows.add(jMenuItem_renderView);

        jMenuItem_messages.setText(resourceMap.getString("jMenuItem_messages.text")); // NOI18N
        jMenuItem_messages.setName("jMenuItem_messages"); // NOI18N
        jMenuItem_messages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_messagesActionPerformed(evt);
            }
        });
        jMenu_Windows.add(jMenuItem_messages);

        jMenuItem_Jython.setText(resourceMap.getString("jMenuItem_Jython.text")); // NOI18N
        jMenuItem_Jython.setName("jMenuItem_Jython"); // NOI18N
        jMenuItem_Jython.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_JythonActionPerformed(evt);
            }
        });
        jMenu_Windows.add(jMenuItem_Jython);

        menuBar.add(jMenu_Windows);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        jMenuItem_Help.setText(resourceMap.getString("jMenuItem_Help.text")); // NOI18N
        jMenuItem_Help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_HelpActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem_Help);

        jMenuItem_CheckForUpdates.setText(resourceMap.getString("jMenuItem_CheckForUpdates.text")); // NOI18N
        jMenuItem_CheckForUpdates.setName("jMenuItem_CheckForUpdates"); // NOI18N
        jMenuItem_CheckForUpdates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_CheckForUpdatesActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem_CheckForUpdates);

        jMenuItem_About.setText(resourceMap.getString("jMenuItem_About.text")); // NOI18N
        jMenuItem_About.setName("jMenuItem_About"); // NOI18N
        jMenuItem_About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_AboutActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem_About);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1157, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 973, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jDialog_Edit_SimpleArray.setTitle(resourceMap.getString("label_parameter_name.text")); // NOI18N
        jDialog_Edit_SimpleArray.setName("jDialog_Edit_String"); // NOI18N

        label_parameter_name.setFont(resourceMap.getFont("label_parameter_name.font")); // NOI18N
        label_parameter_name.setName("label_parameter_name"); // NOI18N
        label_parameter_name.setText(resourceMap.getString("label_parameter_name.text")); // NOI18N

        jButton_Cancel.setText(resourceMap.getString("jButton_Cancel.text")); // NOI18N
        jButton_Cancel.setName("jButton_Cancel"); // NOI18N

        jButton_OK.setText(resourceMap.getString("jButton_OK.text")); // NOI18N
        jButton_OK.setName("jButton_OK"); // NOI18N
        jButton_OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_OKActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jTable2.setName("jTable_dataArray"); // NOI18N
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable2);

        javax.swing.GroupLayout jDialog_Edit_SimpleArrayLayout = new javax.swing.GroupLayout(jDialog_Edit_SimpleArray.getContentPane());
        jDialog_Edit_SimpleArray.getContentPane().setLayout(jDialog_Edit_SimpleArrayLayout);
        jDialog_Edit_SimpleArrayLayout.setHorizontalGroup(
            jDialog_Edit_SimpleArrayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_Edit_SimpleArrayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog_Edit_SimpleArrayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_Edit_SimpleArrayLayout.createSequentialGroup()
                        .addComponent(jButton_OK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_Cancel))
                    .addComponent(label_parameter_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(204, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_Edit_SimpleArrayLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jDialog_Edit_SimpleArrayLayout.setVerticalGroup(
            jDialog_Edit_SimpleArrayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_Edit_SimpleArrayLayout.createSequentialGroup()
                .addGroup(jDialog_Edit_SimpleArrayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_OK)
                    .addComponent(jButton_Cancel))
                .addGap(2, 2, 2)
                .addComponent(label_parameter_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_space_filler.setName("jPanel_space_filler"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout jPanel_space_fillerLayout = new javax.swing.GroupLayout(jPanel_space_filler);
        jPanel_space_filler.setLayout(jPanel_space_fillerLayout);
        jPanel_space_fillerLayout.setHorizontalGroup(
            jPanel_space_fillerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_space_fillerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(507, Short.MAX_VALUE))
        );
        jPanel_space_fillerLayout.setVerticalGroup(
            jPanel_space_fillerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_space_fillerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(179, Short.MAX_VALUE))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents


//  public void actionPerformed(ActionEvent e) {

    /* handles actions */

    /*
    if ("disable".equals(e.getActionCommand())) {
      b2.setEnabled(false);
      b1.setEnabled(false);
      b3.setEnabled(true);
    } else {
      b2.setEnabled(true);
      b1.setEnabled(true);
      b3.setEnabled(false);
    }
     */
 // }




    private void jButton_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OKActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_jButton_OKActionPerformed
    
    private void jComboBox_PreferencesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_PreferencesItemStateChanged
      CardLayout cl = (CardLayout)(jPanel_Preferences_Changable.getLayout());
      cl.next(jPanel_Preferences_Changable);

      //evt.getStateChange()
      //System.out.println("State Changed Combo: " + (String)evt.getItem());
      cl.show(jPanel_Preferences_Changable, (String)evt.getItem());
    }//GEN-LAST:event_jComboBox_PreferencesItemStateChanged

    private void jMenuItem_Window_InteractionEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_Window_InteractionEditorActionPerformed
      openInDockableWindowPanel(jPanel_Editor_Interaction, this.jTabbedPane_Docksite_Windows_LowerLeft);
    }//GEN-LAST:event_jMenuItem_Window_InteractionEditorActionPerformed


  /**
   * Hanldes saving the project data.
   */
  private void project_saveProject() {

    int returnVal;

    JFileChooser jFileChooser;

    jFileChooser = new JFileChooser();

    jFileChooser.setDialogTitle("Save Project");
    jFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
    jFileChooser.setApproveButtonToolTipText("Save Project");
    jFileChooser.setControlButtonsAreShown(true);
    jFileChooser.resetChoosableFileFilters();
    jFileChooser.setFileFilter(new Atz_FileFilter(".SELM_Builder_Project", "SELM Builder Project Files"));

    if (lastDirectorySelected != null) {
      jFileChooser.setCurrentDirectory(lastDirectorySelected);
    }

    returnVal = jFileChooser.showDialog(mainPanel, "Save Project");

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = jFileChooser.getSelectedFile();

      lastDirectorySelected = file;

      //System.out.println("File select was:" + file.getAbsolutePath());

      String filename = file.getAbsolutePath();
      
      if (filename.contains(".") == false) {
        filename = filename + ".SELM_Builder_Project";
      }
      
      /* add this file to the recently opened files */
      addRecentOpenFile(filename);

      /* Save all of the data to a project file */
      saveCurrentProject(filename, applSharedData);
      
    }

  }

  /**
   *
   * Save the current project to a file.
   *
   * @param filename         Filename to use for the project XML files.
   * @param applSharedData   Application data structure that holds all project-related data.
   */
  public void saveCurrentProject(String filename, application_SharedData applSharedData) {
    application_Project_Atz_XML_DataHandler_SELM_Builder.writeProjectToXLMFile(filename, applSharedData);
  }

  /**
   * Export the project to LAMMPS.  This handles writing LAMMPS scripts and XML data file for SELM.
   */
  private void project_exportToLAMMPS_USER_SELM() {

    applSharedData.atz_File_Generator.generateFilesWithUserInput();
        
  }

 
    private void jMenuItem_ProjectSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ProjectSaveActionPerformed

      project_saveProject();
      
    }//GEN-LAST:event_jMenuItem_ProjectSaveActionPerformed

    private void fileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuActionPerformed
      
    }//GEN-LAST:event_fileMenuActionPerformed


    private void project_openProject() {
      int returnVal;

      JFileChooser jFileChooser = new JFileChooser();

      jFileChooser.setDialogTitle("Open Project");
      jFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
      jFileChooser.resetChoosableFileFilters();
      //jFileChooser.addChoosableFileFilter(new Atz_FileFilter(".SELM_Builder_Project", "SELM Builder Project Files"));
      jFileChooser.setFileFilter(new Atz_FileFilter(".SELM_Builder_Project", "SELM Builder Project Files"));
      
      if (lastDirectorySelected != null) {
        jFileChooser.setCurrentDirectory(lastDirectorySelected);
      }

      returnVal = jFileChooser.showDialog(mainPanel, "Open Project");

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = jFileChooser.getSelectedFile();

        lastDirectorySelected = file;

        System.out.println("File select was:" + file.getAbsolutePath());

        String filename = file.getAbsolutePath();

        if (filename.contains(".") == false) {
          filename = filename + ".SELM_Builder_Project";
        }
        
        /* add this file to the recently opened files */
        addRecentOpenFile(filename);
        
        /* make new project ?? @@@@ PJA new add... maybe not necessary?????*/
        project_makeNewProject();

        /* Open all of the data to a project file */
        openNewProject(filename, applSharedData);

      }
    }

    public void openNewProject(String filename, application_SharedData applSharedData) {
      application_Project_Atz_XML_DataHandler_SELM_Builder.readProjectFromXLMFile(filename, applSharedData);
    }

    private void jMenuItem_ProjectOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ProjectOpenActionPerformed
      
      project_openProject();

    }//GEN-LAST:event_jMenuItem_ProjectOpenActionPerformed

    private void project_makeNewProjectAction() {

      //Custom button text
      Object[] options = {"Yes", "No"};

      int n = JOptionPane.showOptionDialog(this.getFrame(),
              "By creating a new project any unsaved data will be lost.  Do you want to proceed?",
              "New Project",
              JOptionPane.YES_NO_OPTION,
              JOptionPane.WARNING_MESSAGE,
              null,
              options,
              options[1]);

      if (n == 0) {
        project_makeNewProject();
      }

    }


    private void project_makeNewProject() {

      /* clear all project specific data, (keep the preferences the same?) */

      /* clear data from the jMainData */
      applSharedData.jTable_MainData.setBaseFilename("Model");
      applSharedData.jTable_MainData.setDescription("Put description here.");
      applSharedData.jTable_MainData.setLagrangianDOF(new SELM_Lagrangian[0]);
      applSharedData.jTable_MainData.setCouplingOpList(new SELM_CouplingOperator[0]);
      applSharedData.jTable_MainData.setInteractionList(new SELM_Interaction[0]);
            
      applSharedData.atz_File_Generator = new Atz_File_Generator_LAMMPS_USER_SELM1(applSharedData); 
                  
      /* create one of each kind of eulerian known */
      init_eulerianList();

      /* create one of each kind of integrator known */
      init_integratorList();
      
      /* reinitialize each entry and update appropriate panel */
      SELM_Eulerian[] eulerianList = applSharedData.jTable_MainData.getEulerianList();
      for (int k = 0; k < eulerianList.length; k++) {
        try {
          eulerianList[k]              = (eulerianList[k]).getClass().newInstance();
          eulerianList[k].EulerianName = eulerianList[k].EulerianTypeStr;
          applSharedData.jPanel_Eulerian_DOF_list[k].setData(eulerianList[k]);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } /* end of k loop */

      /* reinitialize each entry and update appropriate panel */
      SELM_Integrator[] IntegratorList = applSharedData.jTable_MainData.getIntegratorList();
      for (int k = 0; k < IntegratorList.length; k++) {
        try {
          IntegratorList[k]              = (IntegratorList[k]).getClass().newInstance();
          IntegratorList[k].IntegratorName = IntegratorList[k].IntegratorTypeStr;
          applSharedData.jPanel_Integrator_list[k].setData(IntegratorList[k]);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } /* end of k loop */

      /* reinitialize each entry and update appropriate panel */
      SELM_Interaction[] InteractionList = applSharedData.jTable_MainData.getInteractionList();
      for (int k = 0; k < InteractionList.length; k++) {
        try {
          InteractionList[k]                 = (InteractionList[k]).getClass().newInstance();
          InteractionList[k].InteractionName = InteractionList[k].InteractionTypeStr;
          applSharedData.jPanel_Interaction_list[k].setData(InteractionList[k]);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } /* end of k loop */

      ((JPanel_Editor_Interaction)jPanel_Editor_Interaction).setInteractionData(new SELM_Interaction_NULL());

      /* reinitialize each entry and update appropriate panel */
      SELM_Lagrangian[] LagrangianList = applSharedData.jTable_MainData.getLagrangianList();
      for (int k = 0; k < LagrangianList.length; k++) {
        try {
          LagrangianList[k]                = (LagrangianList[k]).getClass().newInstance();
          LagrangianList[k].LagrangianName = LagrangianList[k].LagrangianTypeStr;
          applSharedData.jPanel_Lagrangian_DOF_list[k].setData(LagrangianList[k]);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } /* end of k loop */

      ((JPanel_Editor_Lagrangian_DOF)jPanel_Editor_Lagrangian_DOF).setLagrangianData(new SELM_Lagrangian_NULL());

      /* reinitialize each entry and update appropriate panel */
      SELM_CouplingOperator[] CouplingOpList = applSharedData.jTable_MainData.getCouplingOpList();
      for (int k = 0; k < CouplingOpList.length; k++) {
        try {
          CouplingOpList[k]                = (CouplingOpList[k]).getClass().newInstance();
          CouplingOpList[k].CouplingOpName = CouplingOpList[k].CouplingOpTypeStr;
          applSharedData.jPanel_CouplingOp_list[k].setData(CouplingOpList[k]);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } /* end of k loop */

      ((JPanel_Editor_CouplingOperator)jPanel_Editor_CouplingOp).setCouplingOpData(new SELM_CouplingOperator_NULL());

      /* reinitialize the render view and preferences*/
      ((JTable_Preferences_Rendering)applSharedData.jTable_Preferences_Rendering).setToDefaultValues();
      ((JTable_Preferences_TableDisplay)applSharedData.jTable_Preferences_TableDisplay).setToDefaultValues();
      
      /* update the editor panels */
      //((JPanel_Editor_Interaction)jPanel_Editor_Interaction)

    }

    private void jMenuItem_ProjectNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ProjectNewActionPerformed

      project_makeNewProjectAction();

    }//GEN-LAST:event_jMenuItem_ProjectNewActionPerformed

    private void jButton_newProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_newProjectActionPerformed

      project_makeNewProjectAction();

    }//GEN-LAST:event_jButton_newProjectActionPerformed

    private void jButton_openProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_openProjectActionPerformed
      project_openProject();
    }//GEN-LAST:event_jButton_openProjectActionPerformed

    private void jMenuItem_ProjectOpenRecent(java.awt.event.ActionEvent evt) {
      JMenuItem jMenuItem = (JMenuItem) evt.getSource();
      String filename = jMenuItem.getText(); /* the filename is the same as the text (for now) */     
      /* Open all of the data to a project file @@@@ PJA use common sub-routine to do the open. */
      openNewProject(filename, applSharedData);
      //application_Project_Atz_XML_DataHandler_SELM_Builder.readProjectFromXLMFile(filename, applSharedData);
    }

    private void jButton_saveProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_saveProjectActionPerformed
      project_saveProject();
    }//GEN-LAST:event_jButton_saveProjectActionPerformed

    private void jMenuItem_JythonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_JythonActionPerformed
      openInDockableWindowPanel(this.jPanel_Editor_Jython, this.jTabbedPane_Docksite_Windows_LowerRight);
    }//GEN-LAST:event_jMenuItem_JythonActionPerformed

    private void jMenuItem_CouplingOpEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_CouplingOpEditorActionPerformed
      openInDockableWindowPanel(this.jPanel_Editor_CouplingOp, this.jTabbedPane_Docksite_Windows_LowerLeft);
    }//GEN-LAST:event_jMenuItem_CouplingOpEditorActionPerformed

    private void jMenuItem_LagrangianEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_LagrangianEditorActionPerformed
      openInDockableWindowPanel(this.jPanel_Editor_Lagrangian_DOF, this.jTabbedPane_Docksite_Windows_LowerLeft);
    }//GEN-LAST:event_jMenuItem_LagrangianEditorActionPerformed

    private void jButton_generate_LAMMPS_USER_SELMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_generate_LAMMPS_USER_SELMActionPerformed
     project_exportToLAMMPS_USER_SELM();
    }//GEN-LAST:event_jButton_generate_LAMMPS_USER_SELMActionPerformed

    private void jMenuItem_HelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_HelpActionPerformed
      showOnlineHelp();
    }//GEN-LAST:event_jMenuItem_HelpActionPerformed

    public void showOnlineHelp() {

      /* launch web browser when clicking link */
      String url = "http://www.mango-selm.org/doc/";
      try {
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
      } catch (IOException ex) {
        Logger.getLogger(application_Window_About.class.getName()).log(Level.SEVERE, null, ex);
      }

    }

    public void showOnlineUpdates() {

      /* launch web browser when clicking link */
      String url = "http://www.mango-selm.org/download/";
      try {
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
      } catch (IOException ex) {
        Logger.getLogger(application_Window_About.class.getName()).log(Level.SEVERE, null, ex);
      }
      
    }

    public void showOnlineAbout() {

      /* launch web browser when clicking link */
      String url = "http://www.mango-selm.org/about/";
      try {
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
      } catch (IOException ex) {
        Logger.getLogger(application_Window_About.class.getName()).log(Level.SEVERE, null, ex);
      }

    }

    public void showOnlineHome() {

      /* launch web browser when clicking link */
      String url = "http://www.mango-selm.org/";
      try {
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
      } catch (IOException ex) {
        Logger.getLogger(application_Window_About.class.getName()).log(Level.SEVERE, null, ex);
      }

    }

    private void jMenuItem_CheckForUpdatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_CheckForUpdatesActionPerformed

      showOnlineUpdates();
      
    }//GEN-LAST:event_jMenuItem_CheckForUpdatesActionPerformed

    private void jMenuItem_Generate_LAMMPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_Generate_LAMMPSActionPerformed
      project_exportToLAMMPS_USER_SELM();
    }//GEN-LAST:event_jMenuItem_Generate_LAMMPSActionPerformed

    private void jButton_aboutInformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aboutInformationActionPerformed
      showAboutBox();
    }//GEN-LAST:event_jButton_aboutInformationActionPerformed

    private void jMenuItem_AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_AboutActionPerformed
      showAboutBox();
    }//GEN-LAST:event_jMenuItem_AboutActionPerformed

    private void jMenuItem_messagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_messagesActionPerformed
      openInDockableWindowPanel(this.jPanel_Output_Messages, this.jTabbedPane_Docksite_Windows_LowerRight);
    }//GEN-LAST:event_jMenuItem_messagesActionPerformed

    private void jMenuItem_renderViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_renderViewActionPerformed
      openInDockableWindowPanel(applSharedData.jPanel_Model_View_Composite, jTabbedPane_Docksite_Windows_UpperRight);
    }//GEN-LAST:event_jMenuItem_renderViewActionPerformed

  
  public void jTable_Main_tableChanged(TableModelEvent e)  {

    TableModel_MainData model = (TableModel_MainData)applSharedData.jTable_MainData.getModel();

    /* rebuild the panel information depending on the entries changed */
    if ((e.getFirstRow() <= model.paramIndex_InteractionList) && (e.getLastRow() >= model.paramIndex_InteractionList)) {
      interactionList_build_lists();
    }

    if ((e.getFirstRow() <= model.paramIndex_CouplingOpList) && (e.getLastRow() >= model.paramIndex_CouplingOpList)) {
      couplingOpList_build_lists();
    }

    if ((e.getFirstRow() <= model.paramIndex_Lagrangian_DOF) && (e.getLastRow() >= model.paramIndex_Lagrangian_DOF)) {
      lagrangianList_build_lists();
    }
    
    if ((e.getFirstRow() <= model.paramIndex_Eulerian_DOF) && (e.getLastRow() >= model.paramIndex_Eulerian_DOF)) {
      eulerianList_build_lists();
    }
    
    if ((e.getFirstRow() <= model.paramIndex_Integrator) && (e.getLastRow() >= model.paramIndex_Integrator)) {
      integratorList_build_lists();
    }
   
  }

  public void interactionList_build_lists()  {

    /* setup the table of Interactions from main data */
    SELM_Interaction[] interactionList = ((JTable_MainData)jTable_Main).getInteractionList();
    
    jPanel_Edit_InteractionList.setTableFromInteractionList(interactionList);

    /* setup for rendering */
    applSharedData.jPanel_Model_View_RenderPanel.removeAllRenderView(SELM_Interaction.atz3D_RENDER_TAG_INTERACTION);
    for (int k = 0; k < interactionList.length; k++) {
      applSharedData.jPanel_Model_View_RenderPanel.addRenderView((SELM_RenderView)interactionList[k]);
    }

    applSharedData.jPanel_Model_View_RenderPanel.repaint();
  }

  public void couplingOpList_build_lists()  {

    /* setup the table of Interactions from main data */
    SELM_CouplingOperator[] couplingOpList = ((JTable_MainData)jTable_Main).getCouplingOpList();

    jPanel_Edit_CouplingOpList.setTableFromCouplingOpList(couplingOpList);

    /* setup for rendering */
//    applSharedData.jPanel_Model_View_RenderPanel.removeAllRenderView(SELM_CouplingOperator.atz3D_RENDER_TAG_COUPLING_OPERATOR);
//    for (int k = 0; k < couplingOpList.length; k++) {
//      applSharedData.jPanel_Model_View_RenderPanel.addRenderView((SELM_RenderView)couplingOpList[k]);
//    }
//
//    applSharedData.jPanel_Model_View_RenderPanel.repaint();

  }

  public void lagrangianList_build_lists()  {
        
    /* setup the table of Lagrangian DOF from main data */
    SELM_Lagrangian[] lagrangianList = ((JTable_MainData)jTable_Main).getLagrangianList();

    jPanel_Edit_LagrangianList.setTableFromLagrangianList(lagrangianList);

    //TableModel_LagrangianList model  = (TableModel_LagrangianList) jTable_LagrangianList.getModel();
    /*
    TableModel_LagrangianList model  = (TableModel_LagrangianList) jPanel_Edit_LagrangianList.jTable_LagrangianList.getModel();

    jPanel_Edit_LagrangianList.jTable_LagrangianList_validForUse = false; // indicates rebuilding table
    model.removeAllEntries();
    for (int k = 0; k < lagrangianList.length; k++) {
      model.setValueAt((lagrangianList[k]).LagrangianName, k, 0, TableModel_LagrangianList.EDITABLE);
      model.setValueAt((lagrangianList[k]).LagrangianTypeStr, k, 1, TableModel_LagrangianList.NOT_EDITABLE);

      model.fireTableCellUpdated(k, 0);
      model.fireTableCellUpdated(k, 1);
    }
    jPanel_Edit_LagrangianList.jTable_LagrangianList_validForUse = true;
    */
    
    /* setup for rendering */
    applSharedData.jPanel_Model_View_RenderPanel.removeAllRenderView(SELM_Lagrangian.atz3D_RENDER_TAG_LAGRANGIAN);
    for (int k = 0; k < lagrangianList.length; k++) {
      applSharedData.jPanel_Model_View_RenderPanel.addRenderView((SELM_RenderView)lagrangianList[k]);
    }

    applSharedData.jPanel_Model_View_RenderPanel.repaint();

  }


  public void init_eulerianList() {

    JPanel[] jPanelList = null;
    int N, I, count;

    /* == Build the Eulerian DOF panels */
    jPanelList = Atz_ClassLoader.loadAndInstantiatePanelClassesStartingWith(JPanel_Eulerian.class.getSimpleName() + "_", this.getClass().getPackage().getName(), this.getClass().getClassLoader(), applSharedData);
    applSharedData.jPanel_Eulerian_DOF_list = new JPanel_Eulerian[jPanelList.length];
    //applSharedData.ApplNamespace.setData("applSharedData.jPanel_Eulerian_DOF_list", applSharedData.jPanel_Eulerian_DOF_list);
    for (int k = 0; k < jPanelList.length; k++) {
      applSharedData.jPanel_Eulerian_DOF_list[k] = (JPanel_Eulerian)jPanelList[k];
    }

    /* add one eulerian type for each type of panel found */
    N                    = applSharedData.jPanel_Eulerian_DOF_list.length;
    SELM_Eulerian[] list = new SELM_Eulerian[N];
    count = 0;
    for (int k = 0; k < N; k++) {
      Atz_Object_Factory objFactory = applSharedData.jPanel_Eulerian_DOF_list[k].getObjectFactory();
      if (objFactory != null) {
        list[k] = (SELM_Eulerian) objFactory.getNewInstance();
        list[k].EulerianName = list[k].EulerianTypeStr;  /* make the name same as the type */
        count++;
      }
    }
    
    I = 0;
    SELM_Eulerian[] eulerianList = new SELM_Eulerian[count];
    for (int k = 0; k < count; k++) {
      if (list[k] != null) {
        eulerianList[I] = list[k];
        I++;
      }
    }
    
    /* build the SELM_Eulerian[], comboBox, pane, etc..for type selection */

    /* eulerianList */
//    int N = applSharedData.jPanel_Eulerian_DOF_list.length;
//    SELM_Eulerian[] eulerianList = new SELM_Eulerian[N];
//    for (int k = 0; k < N; k ++) {
//      String startsWithStr   = "SELM_Eulerian_";
//      String EulerianTypeStr = (applSharedData.jPanel_Eulerian_DOF_list[k]).getName();
//      String packageName     = this.getClass().getPackage().getName(); //"pja_desktopapplication1";
//      String className       = packageName + "." + startsWithStr + EulerianTypeStr;
//      eulerianList[k]        = (SELM_Eulerian) Atz_ClassLoader.loadAndInstantiateClass(className,this.getClass().getClassLoader());
//      if (eulerianList[k] != null) {
//        eulerianList[k].EulerianName = EulerianTypeStr; /* make type and name match */
//        ((JPanel_Eulerian)applSharedData.jPanel_Eulerian_DOF_list[k]).setData(eulerianList[k]);
//      }
//    }
    applSharedData.jTable_MainData.setEulerianDOF(eulerianList);

    applSharedData.jTable_MainData.setEulerian_selected(0);
    applSharedData.jTable_MainData.setEulerian_selectedByName("NULL");
    
  }

  public void eulerianList_build_lists()  {
   
    /* refresh the eulerian degrees of freedom of the editor */
    SELM_Eulerian[] eulerianList          = ((JTable_MainData)jTable_Main).getEulerianList();

    //jPanel_Editor_Eulerian_DOF.setTableFromLagrangianList(lagrangianList);

//    /* == Build the Eulerian DOF panels */
//    //jPanelList = Atz_ClassLoader.loadAndInstantiatePanelClassesStartingWith(JPanel_Eulerian.class.getSimpleName() + "_", this.getClass().getPackage().getName(), this.getClass().getClassLoader(), applSharedData);
//    applSharedData.jPanel_Eulerian_DOF_list = new JPanel_Eulerian[eulerianList.length];  /* make a separate panel for each name */
//    for (int k = 0; k < eulerianList.length; k++) {  /* for each eulerian name, instantiate panel of appropriate type */
//      JPanel_Eulerian jPanel;
//      String className = eulerianList[k].getClass().getSimpleName();
//      className        = className.replace("SELM_Eulerian_","");
//      className        = "JPanel_Eulerian_" + className;
//      className        = eulerianList[k].getClass().getPackage().getName() + "." + className;
//      jPanel           = (JPanel_Eulerian)Atz_ClassLoader.loadAndInstantiateClass(className, this.applSharedData, this.getClass().getClassLoader());
//      jPanel.setData(eulerianList[k]);
//      applSharedData.jPanel_Eulerian_DOF_list[k] = (JPanel_Eulerian)jPanel;
//    }

//    applSharedData.ApplNamespace.setData("applSharedData.jPanel_Eulerian_DOF_list", applSharedData.jPanel_Eulerian_DOF_list);

//    /* == Build the Eulerian chooser menu */
//    /* combo list */
//    jComboBox_Type_Eulerian_DOF.removeAllItems();
//    for (int k = 0; k < applSharedData.jPanel_Eulerian_DOF_list.length; k ++) {
//      jComboBox_Type_Eulerian_DOF.addItem(eulerianList[k].getName());
//      jPanel_Eulerian_Changeable.add(applSharedData.jPanel_Eulerian_DOF_list[k], eulerianList[k].getName());
//    }
       
    /* == Reset the render view of the eulerian DOF selected */
    int             eulerianIndexSelected = ((JTable_MainData)jTable_Main).getEulerianIndexSelected();
    if (eulerianIndexSelected >= eulerianList.length) {
      eulerianIndexSelected = 0;
    }
    SELM_Eulerian   eulerian              = eulerianList[eulerianIndexSelected];
    applSharedData.jPanel_Model_View_RenderPanel.removeAllRenderView(SELM_Eulerian.atz3D_RENDER_TAG_EULERIAN);
    applSharedData.jPanel_Model_View_RenderPanel.addRenderView((SELM_RenderView)eulerian);
    
    /* setup for rendering */
    /*
    applSharedData.jPanel_Model_View_RenderPanel.removeAllRenderView(SELM_Eulerian.atz3D_RENDER_TAG_Eulerian);
    for (int k = 0; k < EulerianList.length; k++) {
      applSharedData.jPanel_Model_View_RenderPanel.addRenderView((SELM_RenderView)EulerianList[k]);
    }

    applSharedData.jPanel_Model_View_RenderPanel.repaint();
     */

  }

  public void init_integratorList() {

    JPanel[] jPanelList = null;

    int I,N,count;

    /* == Build the Integrator panels */
    jPanelList = Atz_ClassLoader.loadAndInstantiatePanelClassesStartingWith(JPanel_Integrator.class.getSimpleName() + "_", this.getClass().getPackage().getName(), this.getClass().getClassLoader(), applSharedData);
    applSharedData.jPanel_Integrator_list = new JPanel_Integrator[jPanelList.length];
    //applSharedData.ApplNamespace.setData("applSharedData.jPanel_Integrator_list", applSharedData.jPanel_Integrator_list);
    for (int k = 0; k < jPanelList.length; k++) {
      applSharedData.jPanel_Integrator_list[k] = (JPanel_Integrator)jPanelList[k];
    }

    /* add one integrator type for each type of panel found */
    N                    = applSharedData.jPanel_Integrator_list.length;
    SELM_Integrator[] list = new SELM_Integrator[N];
    count = 0;
    for (int k = 0; k < N; k++) {
      Atz_Object_Factory objFactory = applSharedData.jPanel_Integrator_list[k].getObjectFactory();
      if (objFactory != null) {
        list[k] = (SELM_Integrator) objFactory.getNewInstance();
        list[k].IntegratorName = list[k].IntegratorTypeStr;  /* make the name same as the type */
        count++;
      }
    }

    I = 0;
    SELM_Integrator[] integratorList = new SELM_Integrator[count];
    for (int k = 0; k < count; k++) {
      if (list[k] != null) {
        integratorList[I] = list[k];
        I++;
      }
    }

//    /* build the SELM_Integrator[], comboBox, pane, etc..for type selection */
//
//    /* integratorList */
//    int N = applSharedData.jPanel_Integrator_list.length;
//    SELM_Integrator[] integratorList = new SELM_Integrator[N];
//    for (int k = 0; k < N; k ++) {
//      String startsWithStr     = "SELM_Integrator_";
//      String IntegratorTypeStr = (applSharedData.jPanel_Integrator_list[k]).getName();
//      String packageName       = this.getClass().getPackage().getName(); //"pja_desktopapplication1";
//      String className         = packageName + "." + startsWithStr + IntegratorTypeStr;
//      integratorList[k]        = (SELM_Integrator) Atz_ClassLoader.loadAndInstantiateClass(className, this.getClass().getClassLoader());
//      if (integratorList[k] != null) {
//        integratorList[k].IntegratorName = IntegratorTypeStr; /* make type and name match */
//        ((JPanel_Integrator)applSharedData.jPanel_Integrator_list[k]).setData(integratorList[k]);
//      }
//    }

    applSharedData.jTable_MainData.setIntegratorList(integratorList);
    
    applSharedData.jTable_MainData.setIntegrator_selected(0);
    applSharedData.jTable_MainData.setIntegrator_selectedByName("NULL");

  }

  public void integratorList_build_lists()  {

    /* refresh the integrator degrees of freedom of the editor */
    SELM_Integrator[] integratorList          = ((JTable_MainData)jTable_Main).getIntegratorList();

    //jPanel_Editor_Integrator_DOF.setTableFromLagrangianList(lagrangianList);

//    /* == Build the Integrator DOF panels */
//    //jPanelList = Atz_ClassLoader.loadAndInstantiatePanelClassesStartingWith(JPanel_Integrator.class.getSimpleName() + "_", this.getClass().getPackage().getName(), this.getClass().getClassLoader(), applSharedData);
//    applSharedData.jPanel_Integrator_DOF_list = new JPanel_Integrator[integratorList.length];  /* make a separate panel for each name */
//    for (int k = 0; k < integratorList.length; k++) {  /* for each integrator name, instantiate panel of appropriate type */
//      JPanel_Integrator jPanel;
//      String className = integratorList[k].getClass().getSimpleName();
//      className        = className.replace("SELM_Integrator_","");
//      className        = "JPanel_Integrator_" + className;
//      className        = integratorList[k].getClass().getPackage().getName() + "." + className;
//      jPanel           = (JPanel_Integrator)Atz_ClassLoader.loadAndInstantiateClass(className, this.applSharedData, this.getClass().getClassLoader());
//      jPanel.setData(integratorList[k]);
//      applSharedData.jPanel_Integrator_DOF_list[k] = (JPanel_Integrator)jPanel;
//    }

//    applSharedData.ApplNamespace.setData("applSharedData.jPanel_Integrator_DOF_list", applSharedData.jPanel_Integrator_DOF_list);

//    /* == Build the Integrator chooser menu */
//    /* combo list */
//    jComboBox_Type_Integrator_DOF.removeAllItems();
//    for (int k = 0; k < applSharedData.jPanel_Integrator_DOF_list.length; k ++) {
//      jComboBox_Type_Integrator_DOF.addItem(integratorList[k].getName());
//      jPanel_Integrator_Changeable.add(applSharedData.jPanel_Integrator_DOF_list[k], integratorList[k].getName());
//    }

    /* == Reset the render view of the integrator DOF selected */
    int             integratorIndexSelected = ((JTable_MainData)jTable_Main).getIntegratorIndexSelected();
    if (integratorIndexSelected >= integratorList.length) {
      integratorIndexSelected = 0;
    }
    SELM_Integrator   integrator              = integratorList[integratorIndexSelected];
    applSharedData.jPanel_Model_View_RenderPanel.removeAllRenderView(SELM_Integrator.atz3D_RENDER_TAG_INTEGRATOR);
    applSharedData.jPanel_Model_View_RenderPanel.addRenderView((SELM_RenderView)integrator);

    /* setup for rendering */
    /*
    applSharedData.jPanel_Model_View_RenderPanel.removeAllRenderView(SELM_Integrator.atz3D_RENDER_TAG_Integrator);
    for (int k = 0; k < IntegratorList.length; k++) {
      applSharedData.jPanel_Model_View_RenderPanel.addRenderView((SELM_RenderView)IntegratorList[k]);
    }

    applSharedData.jPanel_Model_View_RenderPanel.repaint();
     */

  }



  private void modifyComponents() {  /* PJA: modifies components to customize */

    int N;

    JPanel[] jPanelList;
        
    InputStream inStrm;
    
    PrintStream outStrm;
    PrintStream errStrm;
        
    boolean     autoFlush = true;
    
    /* create the output messages panel */
    jPanel_Output_Messages = new JPanel_Output_Messages();        
    openInDockableWindowPanel(jPanel_Output_Messages, jTabbedPane_Docksite_Windows_LowerRight);
                            
    /* redirect the standard streams to the output JTextPane */
    jTextPane_Output_InputStream   = new JTextPane_Output_InputStream(jPanel_Output_Messages.getTextPane());
    inStrm                         = jTextPane_Output_InputStream;
    jTextPane_Output_OutputStream  = new JTextPane_Output_OutputStream(jPanel_Output_Messages.getTextPane());
    outStrm                        = new PrintStream(jTextPane_Output_OutputStream, autoFlush);
    jTextPane_Output_ErrorStream   = new JTextPane_Output_ErrorStream(jPanel_Output_Messages.getTextPane());
    errStrm                        = new PrintStream(jTextPane_Output_ErrorStream, autoFlush);

    /* save the old system out and err (and mirror any output to the standard locations) */
    jPanel_Output_Messages.getTextPane().stdErr = System.err;
    jPanel_Output_Messages.getTextPane().stdOut = System.out;

    /* redirect the standard input and output */
    System.setIn(inStrm);
    System.setOut(outStrm);
    System.setErr(errStrm);
    
    /* report the splash setup steps */                        
    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Setting up shared application variables.");

    //setframeicon(this.getFrame(), "PJA_IconForFrame");
    
    /* setup the application icon */
    setframeicon(this.getFrame(), "PJA_IconForFrame");
    
    /* setup the reference units */
    applSharedData                              = new application_SharedData();
    //applSharedData.ApplNamespace                = new Atz_Struct_DataContainer("DataNamespace");
    //applSharedData.ApplNamespace                = null; /* disabled this feature */
    applSharedData.FrameView_Application_Main   = this;
    applSharedData.jTextPane_Messages           = jPanel_Output_Messages.getTextPane();

    applSharedData.atz_UnitsRef                 = ((JTable_MainData)jTable_Main).getUnitsRef();

    ((JTable_MainData)jTable_Main).setApplSharedData(applSharedData);
    applSharedData.jTable_MainData                 = (JTable_MainData)jTable_Main;
    //applSharedData.jTable_MainData.syncDataWithApplNamespaceAll();
    
    applSharedData.jTable_Preferences_Rendering    = (JTable)jTable_Preferences_Rendering;
    applSharedData.jTable_Preferences_TableDisplay = (JTable)jTable_Preferences_TableDisplay;
    applSharedData.jTable_Preferences_Other        = (JTable)jTable_Preferences_Other;

    applSharedData.jPanel_Model_View_Composite   = new JPanel_Model_View_Composite();
    //applSharedData.jPanel_Model_View_Composite.setName("RenderView");
    applSharedData.jPanel_Model_View_RenderPanel = (JPanel_Model_View_RenderPanel)applSharedData.jPanel_Model_View_Composite.getJPanel_Model_View_RenderPanel();
    openInDockableWindowPanel(applSharedData.jPanel_Model_View_Composite, jTabbedPane_Docksite_Windows_UpperRight);
    
    //applSharedData.jPanel_Model_View_Composite   = (JPanel_Model_View_Composite) jPanel_Model_View_Composite;
    //applSharedData.jPanel_Model_View_RenderPanel = (JPanel_Model_View_RenderPanel)applSharedData.jPanel_Model_View_Composite.getJPanel_Model_View_RenderPanel();
    
    applSharedData.jPanel_Tabbed_Main            = jPanelTabbed_Main;

//    applSharedData.ApplNamespace.setData("applSharedData", applSharedData);
//    applSharedData.ApplNamespace.setData("applSharedData.atz_UnitsRef",    applSharedData.atz_UnitsRef);
//    applSharedData.ApplNamespace.setData("applSharedData.jTable_MainData", applSharedData.jTable_MainData);
//    applSharedData.ApplNamespace.setData("applSharedData.jTable_Preferences_Rendering", applSharedData.jTable_Preferences_Rendering);
//    applSharedData.ApplNamespace.setData("applSharedData.jTable_Preferences_TableDisplay", applSharedData.jTable_Preferences_TableDisplay);
//    applSharedData.ApplNamespace.setData("applSharedData.jTable_Preferences_Other", applSharedData.jTable_Preferences_Other);
//    applSharedData.ApplNamespace.setData("applSharedData.jPanel_Model_View_Composite", applSharedData.jPanel_Model_View_Composite);
//    applSharedData.ApplNamespace.setData("applSharedData.jPanel_Model_View_RenderPanel", applSharedData.jPanel_Model_View_RenderPanel);
//
//    applSharedData.ApplNamespace.setData("applSharedData.jPanel_Model_View_RenderPanel", applSharedData.jPanel_Model_View_RenderPanel);
        
    applSharedData.atz_File_Generator = (Atz_File_Generator) new Atz_File_Generator_LAMMPS_USER_SELM1(applSharedData);

    //applSharedData.jTable_MainData.getEulerianList()
          
    JPanel_Model_View_RenderPanel renderControlPts = applSharedData.jPanel_Model_View_RenderPanel;

    /* == Build the Lagrangian DOF panels */
    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Setting up lagrangian Panels.");

    jPanelList = Atz_ClassLoader.loadAndInstantiatePanelClassesStartingWith(JPanel_Lagrangian.class.getSimpleName() + "_", this.getClass().getPackage().getName(), this.getClass().getClassLoader(), applSharedData);
    applSharedData.jPanel_Lagrangian_DOF_list = new JPanel_Lagrangian[jPanelList.length];
    //applSharedData.ApplNamespace.setData("applSharedData.jPanel_Lagrangian_DOF_list", applSharedData.jPanel_Lagrangian_DOF_list);
    for (int k = 0; k < jPanelList.length; k++) {
      applSharedData.jPanel_Lagrangian_DOF_list[k] = (JPanel_Lagrangian)jPanelList[k];
    }
        
    N = applSharedData.jPanel_Lagrangian_DOF_list.length;
    for (int k = 0; k < N; k++) {      
      /* reference ControlPts_BASIC1 for current implementation purposes */
      //if (JPanel_Lagrangian_CONTROL_PTS_BASIC1.class.isInstance(applSharedData.jPanel_Lagrangian_DOF_list[k])) {
      //  jPanel_Lagrangian_ControlPts_BASIC1 = (JPanel_Lagrangian_CONTROL_PTS_BASIC1)applSharedData.jPanel_Lagrangian_DOF_list[k];
      //}
      /* reference LAMMPS_ATOM for current implementation purposes */
      //if (JPanel_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE.class.isInstance(applSharedData.jPanel_Lagrangian_DOF_list[k])) {
      //  jPanel_Lagrangian_LAMMPS_ATOM_angle_style = (JPanel_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE)applSharedData.jPanel_Lagrangian_DOF_list[k];
      //}
    } /* end of k loop for panel class loading */

    /*
    jPanel_Lagrangian_ControlPts_BASIC1 = new JPanel_Lagrangian_CONTROL_PTS_BASIC1(applSharedData);
    applSharedData.jPanel_Lagrangian_DOF_list[0] = jPanel_Lagrangian_ControlPts_BASIC1;

    jPanel_Lagrangian_ControlPts_FAXEN1 = new JPanel_Lagrangian_CONTROL_PTS_FAXEN1(applSharedData);
    applSharedData.jPanel_Lagrangian_DOF_list[1] = jPanel_Lagrangian_ControlPts_FAXEN1;
    */


    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Setting up eulerian panels.");

    init_eulerianList();
        
    jPanel_Editor_Eulerian_DOF = new JPanel_Editor_Eulerian_DOF(applSharedData);
    jPanel_Eulerian_DOF.add(jPanel_Editor_Eulerian_DOF);

    //((JPanel_Editor_Eulerian_DOF)jPanel_Eulerian_DOF).setApplSharedData(applSharedData);
    
    eulerianList_build_lists();

//    /* combo list */
//    jComboBox_Type_Eulerian_DOF.removeAllItems();
//    for (int k = 0; k < applSharedData.jPanel_Eulerian_DOF_list.length; k ++) {
//      jComboBox_Type_Eulerian_DOF.addItem((applSharedData.jPanel_Eulerian_DOF_list[k]).getName());
//      jPanel_Eulerian_Changeable.add(applSharedData.jPanel_Eulerian_DOF_list[k], (applSharedData.jPanel_Eulerian_DOF_list[k]).getName());
//    }


    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Setting up integrator panels.");

    init_integratorList();
    
//    /* combo box */
//    jComboBox_Type_Integrator.removeAllItems();
//    for (int k = 0; k < applSharedData.jPanel_Integrator_list.length; k++) {
//      jComboBox_Type_Integrator.addItem((applSharedData.jPanel_Integrator_list[k]).getName());
//      jPanel_Integrator_Changeable.add(applSharedData.jPanel_Integrator_list[k], (applSharedData.jPanel_Integrator_list[k]).getName());
//    }


    jPanel_Editor_Integrator = new JPanel_Editor_Integrator(applSharedData);
    jPanel_Integrator.add(jPanel_Editor_Integrator);

    //((JPanel_Editor_Integrator)jPanel_Integrator).setApplSharedData(applSharedData);

    integratorList_build_lists();

    /* == Build the coupling operator panels */
    jPanelList = Atz_ClassLoader.loadAndInstantiatePanelClassesStartingWith(JPanel_CouplingOperator.class.getSimpleName() + "_", this.getClass().getPackage().getName(), this.getClass().getClassLoader(), applSharedData);
    applSharedData.jPanel_CouplingOp_list = new JPanel_CouplingOperator[jPanelList.length];
    //applSharedData.ApplNamespace.setData("applSharedData.jPanel_CouplingOp_list", applSharedData.jPanel_CouplingOp_list);
    for (int k = 0; k < jPanelList.length; k++) {
      applSharedData.jPanel_CouplingOp_list[k] = (JPanel_CouplingOperator)jPanelList[k];
    }

    /* j combo box */
    //jComboBox_Add_CouplingOp.removeAllItems();
    //jComboBox_Add_CouplingOp.addItem("Add Coupling Operator");
    //for (int k = 0; k < applSharedData.jPanel_CouplingOp_list.length; k ++) {
//      jComboBox_Add_CouplingOp.addItem((applSharedData.jPanel_CouplingOp_list[k]).getName());
//    }

    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Setting up coupling operator panels.");

    /* use the panels to build the add list for Lagrangian DOF */
    /* build the comboBox, pane, etc..for type selection */
    jPanel_Edit_CouplingOpList = new JPanel_Edit_CouplingOpList(applSharedData);
    jPanel_CouplingOp.add(jPanel_Edit_CouplingOpList);

    String[] listCouplingOpTypesStr = new String[applSharedData.jPanel_CouplingOp_list.length];
    for (int k = 0; k < applSharedData.jPanel_CouplingOp_list.length; k++) {
      listCouplingOpTypesStr[k] = applSharedData.jPanel_CouplingOp_list[k].getName();
    }

    jPanel_Edit_CouplingOpList.setCouplingOpKnownTypes(listCouplingOpTypesStr);

    /* == Build the Interaction panels */
    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Setting up interactions panels.");

    JPanel tmp = new JPanel_Interaction_PAIRS_HARMONIC(applSharedData);    
    jPanelList = Atz_ClassLoader.loadAndInstantiatePanelClassesStartingWith(JPanel_Interaction.class.getSimpleName() + "_", this.getClass().getPackage().getName(), this.getClass().getClassLoader(), applSharedData);
    applSharedData.jPanel_Interaction_list = new JPanel_Interaction[jPanelList.length];
    for (int k = 0; k < jPanelList.length; k++) {
      applSharedData.jPanel_Interaction_list[k] = (JPanel_Interaction)jPanelList[k];
    }

    jPanel_Edit_InteractionList = new JPanel_Edit_InteractionList(applSharedData);
    jPanel_Interactions.add(jPanel_Edit_InteractionList);

    N = applSharedData.jPanel_Interaction_list.length;
    String[] listInteractionTypesStr = new String[N];
    for (int k = 0; k < N; k++) {
      listInteractionTypesStr[k] = (applSharedData.jPanel_Interaction_list[k]).getName();
    }

    jPanel_Edit_InteractionList.setInteractionKnownTypes(listInteractionTypesStr);

//    jComboBox_Add_Interaction.removeAllItems();
//    jComboBox_Add_Interaction.addItem("Add Interaction");
//    for (int k = 0; k < applSharedData.jPanel_Interaction_list.length; k ++) {
//      jComboBox_Add_Interaction.addItem((applSharedData.jPanel_Interaction_list[k]).getName());
//    }

    interactionList_build_lists();


    /* setup table listeners */
    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Setting up main table.");
    
    jTable_Main.getModel().addTableModelListener(new TableModelListener() {
      public void tableChanged(TableModelEvent e)  {
        jTable_Main_tableChanged(e);
      }
    });
        
    /* == Setup the editor main panels */
    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Setting up editors.");

    /* Lagrangian lists table */    
    jPanel_Edit_LagrangianList = new JPanel_Edit_LagrangianList(applSharedData);
    jPanel_Lagrangian_DOF.add(jPanel_Edit_LagrangianList);

    /* use the panels to build the add list for Lagrangian DOF */
    /* build the comboBox, pane, etc..for type selection */
    String[] listLagrangianTypesStr = new String[applSharedData.jPanel_Lagrangian_DOF_list.length];
    for (int k = 0; k < applSharedData.jPanel_Lagrangian_DOF_list.length; k ++) {
      listLagrangianTypesStr[k] = applSharedData.jPanel_Lagrangian_DOF_list[k].getName();
    }

    jPanel_Edit_LagrangianList.setLagrangianKnownTypes(listLagrangianTypesStr);
    
    /* setup the table of Lagrangian DOF from main data */
    SELM_Lagrangian[] lagrangianList = ((JTable_MainData)jTable_Main).getLagrangianList();

    jPanel_Edit_LagrangianList.setTableFromLagrangianList(lagrangianList);
    
    /* setup the table of Coupling Operators from main data */
    SELM_CouplingOperator[] couplingOpList = ((JTable_MainData)jTable_Main).getCouplingOpList();

    jPanel_Edit_CouplingOpList.setTableFromCouplingOpList(couplingOpList);
    
    /*
    TableModel_LagrangianList model  = (TableModel_LagrangianList) jTable_LagrangianList.getModel();
    model.setValueAt(model, N, N)
     */
   
    /* == Setup the interactions and editors */
    /* get the ControlPts_BASIC1 data */
    //jTable_Lagrangian_ControlPts_BASIC1 = (JTable_Lagrangian_ControlPts_BASIC1) jPanel_Lagrangian_ControlPts_BASIC1.getJTable_Lagrangian_ControlPts_BASIC1();

    /* buid the editors */
    //jPanel_test1 = new JPanel_Editor_Test1();
    //openInDockableWindowPanel(jPanel_test1,this.jTabbedPane_Docksite_Windows_LowerRight);

    /* buid the editors */
    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Setting up Jython.");

    Atz_Application_Data_Communication.setApplSharedData(applSharedData); /* set static pointer to shared application data for Jython to use */
    jPanel_Editor_Jython = new JPanel_Editor_Jython();
    openInDockableWindowPanel(jPanel_Editor_Jython, this.jTabbedPane_Docksite_Windows_LowerRight);
    
    jPanel_Editor_Interaction = new JPanel_Editor_Interaction(applSharedData);
    //jPanel_Editor_Interaction.setJPanelRenderModelView(renderControlPts);
    //jPanel_Editor_Interaction.setJTable_Lagrangian_ControlPts_BASIC1((JTable_Lagrangian_ControlPts_BASIC1)jTable_Lagrangian_ControlPts_BASIC1);
    //jPanel_Editor_Interaction.setRenderRelatedButtonGroup(jButtonGroup_ControlPtsViewport);
    openInDockableWindowPanel(jPanel_Editor_Interaction, this.jTabbedPane_Docksite_Windows_LowerLeft);

    jPanel_Editor_Lagrangian_DOF = new JPanel_Editor_Lagrangian_DOF(applSharedData);
    openInDockableWindowPanel(jPanel_Editor_Lagrangian_DOF, this.jTabbedPane_Docksite_Windows_LowerLeft);

    jPanel_Editor_CouplingOp = new JPanel_Editor_CouplingOperator(applSharedData);
    openInDockableWindowPanel(jPanel_Editor_CouplingOp, this.jTabbedPane_Docksite_Windows_LowerLeft);
        
    /* add a listener to the ControlPts_BASIC1 table so that the
     * Comobo Box and the jTable_Interaction model list is updated
     * when there are additions or removals of interactions
     */    
    //jTable_Lagrangian_ControlPts_BASIC1.getModel().addTableModelListener(new javax.swing.event.TableModelListener() {
    //    public void tableChanged(TableModelEvent e) {
    //      // System.out.println("model_ControlPts_BASIC1 Changed ");
    //      jTable_ControlPts_BASIC1_ModelDataChanged(e);
    //    }
    //  });
    
    /* -- Add additional file filters to the Import Data File Chooser */
    //jFileChooser.addChoosableFileFilter(new Atz_FileFilter(".txt", "Test Import Data"));
        
    /* -- Setup rendering space for the control points display */
    //jPanel_Render_Pane = new JPanel_Model_View_Composite();

    /* jPanel_Render_Pane.setBackground(jPanel_ControlPts_RenderCanvas.getBackground());  */
    //jPanel_Render_Pane.setDisplayPanel(jPanel_ControlPts_RenderCanvas);
    //renderControlPts.setBackground(renderControlPts.getParent().getBackground());
    //renderControlPts.setPopupMenu(jPopupMenu_ControlPts_Render);

    //jPanel_Render_Pane.setBackground(jPanel_ControlPts_RenderCanvas.getBackground());
    //jPanel_Render_Pane.setMaximumSize(jPanel_ControlPts_RenderCanvas.getMaximumSize());
    //jPanel_Render_Pane.setMinimumSize(jPanel_ControlPts_RenderCanvas.getMinimumSize());

    //Dimension minSize = jPanel_ControlPts_RenderCanvas.getMinimumSize();
    //jPanel_Render_Pane.setBounds(0, 0, (int) minSize.getWidth(), (int) minSize.getHeight());
    //jPanel_Render_Pane.setName("jPanel_Render_Pane");

    //jPanel_ControlPts_RenderCanvas.add(jPanel_Render_Pane);

    //renderControlPts.addMouseListener(renderControlPts); /* listens to itself */
    //renderControlPts.addMouseMotionListener(renderControlPts); /* listens to itself */
    //renderControlPts.addMouseWheelListener(renderControlPts); /* listens to itself */

    /* Link the ControlPts table model to the rendering panel */
    //TableModel_CouplingOperator_TABLE1_tmp tableModel_ControlPts_BASIC1;
    //tableModel_ControlPts_BASIC1 = (TableModel_CouplingOperator_TABLE1_tmp) jTable_Lagrangian_ControlPts_BASIC1.getModel();
    //tableModel_ControlPts_BASIC1.setControlPtsRenderPanel(renderControlPts);

    /*
    gridBagConstraints         = new java.awt.GridBagConstraints();
    gridBagConstraints.fill    = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.25;
    gridBagConstraints.weighty = 0.25;
    jPanel_ControlPts_RenderCanvas.add(jPanel_Render_Pane, gridBagConstraints);


    p.add("Center", painter);
     */

    /* ControlPts_Renderer new ControlPts_Renderer_Class(jPanel_ControlPts_RenderCanvas.getGraphics());*/
    /*
    jPanel_ControlPts_Display.getGraphics();

    canvas3D = new Canvas3D(config);
    canvas3D.setMinimumSize(new Dimension(300,300));
    canvas3D.setVisible(true);

    canvas3D.setBackground(new java.awt.Color(0, 1, 0));
    canvas3D.setMinimumSize(new java.awt.Dimension(300, 300));
    canvas3D.setPreferredSize(new java.awt.Dimension(300, 300));

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.25;
    gridBagConstraints.weighty = 0.25;
    displayPanel.add(canvas3D, gridBagConstraints);
     */

    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Building the main table.");

    //((JTable_MainData)jTable_Main).setApplSharedData(applSharedData);
    ((JTable_MainData)jTable_Main).rebuildTable();


    /* setup the preference tables */
    ((JTable_Preferences_Rendering)jTable_Preferences_Rendering).setApplSharedData(applSharedData);
    ((JTable_Preferences_TableDisplay)jTable_Preferences_TableDisplay).setApplSharedData(applSharedData);
        
    /* any preliminary output messages */

    /* setup frame to enforce modal behaviors for this class */
    JDialog_Edit_Array_Simple_New.baseFrame = this.getFrame();


    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Reading user preferences.");

    setupUserPreferences();

    /* make new project */
    project_makeNewProject();

    splash_setupStepIndex++;
    jFrame_Splash.setSetupStepIndex(splash_setupStepIndex,"Done with setup.");
   
    //jFrame_Splash.setVisible(false);  /* hide the splash progress screen */
    //jFrame_Splash = null;
        
  }

  void setupUserPreferences() {

    /* setup the recent files */
    numRecentOpenFilesTotal = 5;
    recentOpenFilesList     = new String[5];
    recentOpenFileIndex     = 0;

    // Retrieve the user preference node for the package com.mycompany
    Preferences prefs = Preferences.userNodeForPackage(application_Window_Main.class);

    // Preference key name
    final String PREF_NAME = "recentOpenFilesList";

    // Get the value of the preference
    for (int k = 0; k < recentOpenFilesList.length; k++) {
      String defaultVal      = "";
      String val             = prefs.get(PREF_NAME + "[" + k + "]", defaultVal);
      recentOpenFilesList[k] = val;
    }

    rebuildOpenRecentFileList();
        
  }


  public void openInDockableWindowPanel(JPanel jPanel_toOpen) {
    openInDockableWindowPanel(jPanel_toOpen, jTabbedPane_Docksite_Windows_LowerLeft); /* default docking location */
  }

  public void openInDockableWindowPanel(JPanel jPanel_toOpen, JTabbedPane jTabbedPane_Editors_toUse) {

    /* get the tabName from the panel name */
    String tabName = jPanel_toOpen.getName();
    if (tabName == null) {
      tabName = "";
    }

    openInDockableWindowPanel(tabName, jPanel_toOpen, jTabbedPane_Editors_toUse);
  }

  public void openInDockableWindowPanel(String tabName, JPanel jPanel_toOpen, JTabbedPane jTabbedPane_Editors_toUse) {
    
    /* WARNING: Make sure Holder has box layout or something similar
     * so added components are not of size [0,0]  (BoxLayout worked)
     */

    /* panel name is null then set it equal to the tabName */
    String curName = jPanel_toOpen.getName();
    if (curName == null) {
      jPanel_toOpen.setName(tabName);
    }

    /* try adding the panel */
    try {
      /* try to just select the given component */
      jTabbedPane_Editors_toUse.setSelectedComponent(jPanel_toOpen);
      
    } catch (IllegalArgumentException e) {

      /* if the component was not found, then add it */
      
      jTabbedPane_Editors_toUse.addTab(tabName, jPanel_toOpen);
      jPanel_toOpen.setVisible(true);

      /* set panel just added as the selected one */
      jTabbedPane_Editors_toUse.setSelectedComponent(jPanel_toOpen);
          
    } catch (Exception err) {
      err.printStackTrace();
    }

    /* refresh the tab */
    jTabbedPane_Editors_toUse.validate();
    jTabbedPane_Editors_toUse.repaint();
                
  }
 
  void closeInEditorPanel(JPanel jPanel_toClose) {
    closeInEditorPanel(jPanel_toClose, jTabbedPane_Docksite_Windows_LowerLeft); /* default docking location */
  }

  void closeInEditorPanel(JPanel jPanel_toClose, JTabbedPane jTabbedPane_Editors_toUse) {
    jTabbedPane_Editors_toUse.remove(jPanel_toClose);
    jPanel_toClose.setVisible(false);
    jTabbedPane_Editors_toUse.validate();
    jTabbedPane_Editors_toUse.repaint();
  }

  public void recreateJythonEditorWindow() {
    
    closeInEditorPanel(jPanel_Editor_Jython, this.jTabbedPane_Docksite_Windows_LowerRight);

    Atz_Application_Data_Communication.setApplSharedData(applSharedData); /* set static pointer to shared application data for Jython to use */
    jPanel_Editor_Jython = new JPanel_Editor_Jython();  /* create new Jython editor */
    openInDockableWindowPanel(jPanel_Editor_Jython, this.jTabbedPane_Docksite_Windows_LowerRight);
  }

  public void restartJythonEditor() {
    jPanel_Editor_Jython.restartJythonEditor();
  }

  public void addRecentOpenFile(String filename) {

    if (recentOpenFilesList != null) {
      recentOpenFilesList[recentOpenFileIndex] = filename;
      recentOpenFileIndex++;
      recentOpenFileIndex = recentOpenFileIndex % numRecentOpenFilesTotal;

      // Retrieve the user preference node for the package com.mycompany
      Preferences prefs = Preferences.userNodeForPackage(application_Window_Main.class);

      // Preference key name
      final String PREF_NAME = "recentOpenFilesList";

      // Set the value of the preference
      //String newValue = "a string";
      for (int k = 0; k < recentOpenFilesList.length; k++) {
        prefs.put(PREF_NAME + "[" + k + "]", recentOpenFilesList[k]);
      }

      try {
        prefs.flush();
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    rebuildOpenRecentFileList();
  }

  public void rebuildOpenRecentFileList() {

    if ((jMenu_OpenRecentFiles != null) & (recentOpenFilesList != null)) {
      jMenu_OpenRecentFiles.removeAll();

      for (int k = 0; k < numRecentOpenFilesTotal; k++) {
        JMenuItem jMenuItem = new JMenuItem(recentOpenFilesList[numRecentOpenFilesTotal - k - 1]); /* reverse the order */

        jMenuItem.addActionListener(new java.awt.event.ActionListener() {

          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem_ProjectOpenRecent(evt);
          }
          
        });

        jMenu_OpenRecentFiles.add(jMenuItem);
      }

    }

  }


  public void issueUserErrorMessageWindow(Component baseForModalWindow, String message, String title) {

    /* issue error message */
    Object[] options = {"OK"};

    int n = JOptionPane.showOptionDialog(baseForModalWindow,
      message,
      title,
      JOptionPane.OK_OPTION,
      JOptionPane.ERROR_MESSAGE,
      null,
      options,
      options[0]);
  }
  
  public void issueOutputMessage(String message) {
    issueOutputMessage(message, JTextPane_Output.TEXT_MODE_OUTPUT);
  }

  public void issueOutputErrorMessage(String message) {    
    issueOutputMessage("ERROR: " + message, JTextPane_Output.TEXT_MODE_ERROR);
  }

  public void issueOutputMessage(String message, int textMode) {
    showOutputMessagePanel();
    applSharedData.jTextPane_Messages.setTextMode(textMode);
    applSharedData.jTextPane_Messages.appendTextAtEnd(message);
  }

  public void showOutputMessagePanel() {   
    applSharedData.FrameView_Application_Main.openInDockableWindowPanel(applSharedData.FrameView_Application_Main.jPanel_Output_Messages, applSharedData.FrameView_Application_Main.jTabbedPane_Docksite_Windows_LowerRight);
  }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup jButtonGroup_ControlPtsViewport;
    private javax.swing.JButton jButton_Cancel;
    private javax.swing.JButton jButton_OK;
    private javax.swing.JButton jButton_aboutInformation;
    private javax.swing.JButton jButton_generate_LAMMPS_USER_SELM;
    private javax.swing.JButton jButton_newProject;
    private javax.swing.JButton jButton_openProject;
    private javax.swing.JButton jButton_saveProject;
    private javax.swing.JComboBox jComboBox_Preferences;
    private javax.swing.JDialog jDialog_Edit_SimpleArray;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem_About;
    private javax.swing.JMenuItem jMenuItem_CheckForUpdates;
    private javax.swing.JMenuItem jMenuItem_CouplingOpEditor;
    private javax.swing.JMenuItem jMenuItem_Empty;
    private javax.swing.JMenuItem jMenuItem_Generate_LAMMPS;
    private javax.swing.JMenuItem jMenuItem_Help;
    private javax.swing.JMenuItem jMenuItem_Jython;
    private javax.swing.JMenuItem jMenuItem_LagrangianEditor;
    private javax.swing.JMenuItem jMenuItem_ProjectNew;
    private javax.swing.JMenuItem jMenuItem_ProjectOpen;
    private javax.swing.JMenuItem jMenuItem_ProjectSave;
    private javax.swing.JMenuItem jMenuItem_Window_InteractionEditor;
    private javax.swing.JMenuItem jMenuItem_messages;
    private javax.swing.JMenuItem jMenuItem_renderView;
    private javax.swing.JMenu jMenu_OpenRecentFiles;
    private javax.swing.JMenu jMenu_Windows;
    private javax.swing.JTabbedPane jPanelTabbed_Main;
    private javax.swing.JPanel jPanel_CouplingOp;
    public javax.swing.JPanel jPanel_Dockable_Windows_LowerLeft;
    private javax.swing.JPanel jPanel_Dockable_Windows_LowerRight;
    private javax.swing.JPanel jPanel_Eulerian_DOF;
    private javax.swing.JPanel jPanel_Integrator;
    private javax.swing.JPanel jPanel_Interactions;
    private javax.swing.JPanel jPanel_Lagrangian_DOF;
    private javax.swing.JPanel jPanel_Main;
    private javax.swing.JPanel jPanel_MainData;
    private javax.swing.JPanel jPanel_Model_View_Composite;
    private javax.swing.JPanel jPanel_Preferences;
    private javax.swing.JPanel jPanel_Preferences_Changable;
    private javax.swing.JPanel jPanel_Preferences_Other;
    private javax.swing.JPanel jPanel_Preferences_Rendering;
    private javax.swing.JPanel jPanel_Preferences_TableDisplay;
    private javax.swing.JPanel jPanel_space_filler;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane_Table_Main;
    private javax.swing.JScrollPane jScrollPane_Table_Preferences_Other;
    private javax.swing.JScrollPane jScrollPane_Table_Preferences_Rendering;
    private javax.swing.JScrollPane jScrollPane_Table_Preferences_TableDisplay;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane_Main_Horizontal;
    private javax.swing.JSplitPane jSplitPane_Main_Left_Vertical;
    private javax.swing.JSplitPane jSplitPane_Main_Right_Vertical;
    public javax.swing.JTabbedPane jTabbedPane_Docksite_Windows_LowerLeft;
    public javax.swing.JTabbedPane jTabbedPane_Docksite_Windows_LowerRight;
    public javax.swing.JTabbedPane jTabbedPane_Docksite_Windows_UpperRight;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable_Main;
    private javax.swing.JTable jTable_Preferences_Other;
    private javax.swing.JTable jTable_Preferences_Rendering;
    private javax.swing.JTable jTable_Preferences_TableDisplay;
    private javax.swing.JToolBar jToolBar1;
    private java.awt.Label label_parameter_name;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    
  JPanel_Output_Messages        jPanel_Output_Messages;
    
  JTextPane_Output_OutputStream jTextPane_Output_OutputStream; 
  JTextPane_Output_InputStream  jTextPane_Output_InputStream; 
  JTextPane_Output_ErrorStream  jTextPane_Output_ErrorStream; 
    
  JPanel_Edit_LagrangianList  jPanel_Edit_LagrangianList;
  JPanel_Edit_CouplingOpList  jPanel_Edit_CouplingOpList;
  JPanel_Edit_InteractionList jPanel_Edit_InteractionList;

  private final Timer messageTimer;
  private final Timer busyIconTimer;
  private final Icon idleIcon;
  private final Icon[] busyIcons = new Icon[15];
  private int busyIconIndex = 0;
  private JDialog aboutBox;

  public  int numRecentOpenFilesTotal  = 0;
  public  String[] recentOpenFilesList = null;
  public  int recentOpenFileIndex      = 0;
  
  JPanel               jPanel_test1;
  JPanel_Editor_Jython jPanel_Editor_Jython;
  
  application_SharedData applSharedData;  /* ultimately fold below into this class */
  
  JPanel_Editor_Interaction                 jPanel_Editor_Interaction;
  JPanel_Editor_Lagrangian_DOF              jPanel_Editor_Lagrangian_DOF;
  JPanel_Editor_Eulerian_DOF                jPanel_Editor_Eulerian_DOF;
  JPanel_Editor_CouplingOperator            jPanel_Editor_CouplingOp;
  JPanel_Editor_Integrator                  jPanel_Editor_Integrator;
      
  JTable_Lagrangian_ControlPts_BASIC1       jTable_Lagrangian_ControlPts_BASIC1;
  //JTable_Lagrangian_LAMMPS_ATOM_angle_style jTable_Lagrangian_LAMMPS_ATOM_angle_style;
  
  //JPanel_Lagrangian_CONTROL_PTS_BASIC1      jPanel_Lagrangian_ControlPts_BASIC1;
  //JPanel_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE jPanel_Lagrangian_LAMMPS_ATOM_angle_style;

  boolean jTable_InteractionList_validForUse = false;
   
  File    lastDirectorySelected = null;

  /*
  JPanel[]                            jPanel_Lagrangian_DOF_list;
  JPanel[]                            jPanel_Eulerian_DOF_list;
  JPanel[]                            jPanel_Integrator_list;
   */

  Atz_UnitsRef                        atz_UnitsRef; /* reference units for all quantities */


    
}









/**
 *
 * Provides file filter for selective display of file lists during file choosing.
 *
 * @author Paul J. Atzberger
 */
class FileFilter_Import_Data_SELM_Params extends javax.swing.filechooser.FileFilter {

  @Override
  public boolean accept(File file) {

    // Allow only directories, or files with ".txt" extension
    return file.isDirectory() || file.getAbsolutePath().endsWith(".SELM_Params");
    
  }

  @Override
  public String getDescription() {
    // This description will be displayed in the dialog,
    // hard-coded = ugly, should be done via I18N
    return "SELM Parameter File (*.SELM_Params)";
  }
}


