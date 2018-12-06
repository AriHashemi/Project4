import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Graphic extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    
    
    public static void startUI() 
    {
        
        //Initializing the File Chooser
        JFileChooser fileChooser = new JFileChooser();
        
        
        
        
        
        //Initializing the frame and setting its name, size, and location
        final JFrame frame = new JFrame("Oklahoma Mesonet - Statistics Calculator");
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setLocation(300, 300);
        
        
        //Components required for the popup File menu
        final JToolBar toolBar = new JToolBar();
        final JPopupMenu popup = new JPopupMenu();
        
        
        
        //Components for the Statistics panel
        final JPanel statisticsPanel = new JPanel(new GridLayout());
        String statPanelTitle = "Statistics";
        Border statPanelBorder = BorderFactory.createTitledBorder(statPanelTitle);
        statisticsPanel.setBorder(statPanelBorder);
        final JRadioButton tairButton = new JRadioButton("TAIR");
        final JRadioButton ta9mButton = new JRadioButton("TA9M");
        final JRadioButton sradButton = new JRadioButton("SRAD");
        final JRadioButton wspdButton = new JRadioButton("WSPD");
        final JRadioButton presButton = new JRadioButton("PRES");
        
        
        //Components for the Parameter panel
        final JPanel parameterPanel = new JPanel(new GridLayout());
        String paramPanelTitle = "Parameters";
        Border paramPanelBorder = BorderFactory.createTitledBorder(paramPanelTitle);
        parameterPanel.setBorder(paramPanelBorder);
        final JRadioButton minButton = new JRadioButton("MINIMUM");
        final JRadioButton avgButton = new JRadioButton("AVERAGE");
        final JRadioButton maxButton = new JRadioButton("MAXIMUM");
        
       
        final JPanel tablePanel = new JPanel();
        JTable table = new JTable();
        final JScrollPane tableContainer = new JScrollPane(table);
        
        final JPanel bottomPanel = new JPanel(); 
        
        final JPanel holdPlease = new JPanel();
        
        final JPanel labelAndPopup = new JPanel();
        
        final JPanel infoLabelPanel = new JPanel();
        
        final JLabel infoLabel = new JLabel("Mesonet - We don't set records, we report them!");
        
        Color lightBrown = new Color(210, 180, 140);
        
        //Thank you to Connor Buck for giving me the numbers to make a tan color
        Color tan =  new Color(203, 201, 175);
        
       
        //Action Listener for "Open Data File" menu item option
        popup.add(new JMenuItem(new AbstractAction("Open Data File") 
        {   
            public void actionPerformed(ActionEvent e) 
            {
                fileChooser.showOpenDialog(frame);    
                
            }
        }
        ));
        
        //Action Listener for "Exit" menu item option
        popup.add(new JMenuItem(new AbstractAction("Exit")
        {
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0);
            }
        }
        ));

        //Creates the "File" popup button
        final JButton fileButton = new JButton("File");
        fileButton.addMouseListener(new MouseAdapter() 
        {
            public void mousePressed(MouseEvent e) 
            {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
        );
        
        //Creates the "Calculate" button on the bottom panel
        final JButton calculateButton = new JButton("Calculate");
        calculateButton.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                
            }
        }
        );
        
        //Creates the "Exit" button on the bottom panel
        final JButton exitButton = new JButton("Exit");
        exitButton.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                System.exit(0);
            }
        }
        );
        
        //Setting the preferred size for the buttons that go on the Statistics panel
        tairButton.setPreferredSize(new Dimension(100, 100));
        ta9mButton.setPreferredSize(new Dimension(100, 100));
        sradButton.setPreferredSize(new Dimension(100, 100));
        wspdButton.setPreferredSize(new Dimension(100, 100));
        presButton.setPreferredSize(new Dimension(100, 100));
        
        //Setting the layout for the Statistics panel and adding the buttons to it
        statisticsPanel.setLayout(new BoxLayout(statisticsPanel, BoxLayout.Y_AXIS));
        statisticsPanel.add(tairButton);
        statisticsPanel.add(ta9mButton);
        statisticsPanel.add(sradButton);
        statisticsPanel.add(wspdButton);
        statisticsPanel.add(presButton);
        statisticsPanel.setPreferredSize(new Dimension(80, 80));
        statisticsPanel.setMaximumSize(statisticsPanel.getPreferredSize());
        statisticsPanel.setMinimumSize(statisticsPanel.getPreferredSize());
        
        
        //Changing the background color of the buttons so they don't clash with the panel they're on
        tairButton.setBackground(tan);
        ta9mButton.setBackground(tan);
        sradButton.setBackground(tan);
        wspdButton.setBackground(tan);
        presButton.setBackground(tan);
        
        statisticsPanel.setBackground(tan);
        //statisticsPanel.setBounds(STATS_LOCATION_X, STATS_LOCATION_Y, STATS_SIZE_X, STATS_SIZE_Y);
        
        //Setting the proper sizes for the buttons on the Parameter panel
        minButton.setPreferredSize(new Dimension(200, 200));
        avgButton.setPreferredSize(new Dimension(200, 200));
        maxButton.setPreferredSize(new Dimension(200, 200));
        
        
        //Setting the correct layout for the Parameter panel and adding the buttons
        parameterPanel.setLayout(new BoxLayout(parameterPanel, BoxLayout.Y_AXIS));
        parameterPanel.add(minButton);
        parameterPanel.add(avgButton);
        parameterPanel.add(maxButton);
        parameterPanel.setPreferredSize(new Dimension(100, 100));
        parameterPanel.setMaximumSize(parameterPanel.getPreferredSize());
        parameterPanel.setMinimumSize(parameterPanel.getPreferredSize());
        parameterPanel.setBackground(tan);
        
        //Setting background color of buttons so they don't clash with the panel they're on
        minButton.setBackground(tan);
        avgButton.setBackground(tan);
        maxButton.setBackground(tan);
        
        //Setting proper layout for the infoLabelPanel 
        infoLabelPanel.setBackground(lightBrown);
        infoLabelPanel.add(infoLabel);
        infoLabelPanel.setPreferredSize(new Dimension(20, 5));
        infoLabelPanel.setMaximumSize(infoLabel.getPreferredSize());
        infoLabelPanel.setMinimumSize(infoLabel.getPreferredSize());
        
        //Setting up the bottom panel which contains the "Calculate" and "Exit" buttons
        bottomPanel.setBackground(lightBrown);
        bottomPanel.add(calculateButton, BorderLayout.WEST);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        bottomPanel.setPreferredSize(new Dimension(30, 35));
        bottomPanel.setMaximumSize(bottomPanel.getPreferredSize());
        bottomPanel.setMinimumSize(bottomPanel.getPreferredSize());
        
        
         
        
        //Setting the layout of the JTable in which data would appear
        table.setBackground(Color.WHITE);
        table.setPreferredSize(new Dimension(100, 400));
        table.setMaximumSize(tablePanel.getPreferredSize());
        table.setMinimumSize(tablePanel.getPreferredSize());
        tablePanel.add(tableContainer, BorderLayout.CENTER);
       
        
        
        //Setting up the layout for the popup menu item and the label
        labelAndPopup.setLayout(new GridLayout(2, 1));
        labelAndPopup.add(toolBar, BorderLayout.NORTH);
        labelAndPopup.add(infoLabelPanel, BorderLayout.SOUTH);
        
        holdPlease.setLayout(new GridLayout(1, 2));
        holdPlease.add(statisticsPanel);
        holdPlease.add(parameterPanel);
        
        
        
        toolBar.add(fileButton);

        frame.getContentPane().add(tablePanel);
        frame.getContentPane().add(holdPlease, BorderLayout.WEST);
        frame.getContentPane().add(labelAndPopup, BorderLayout.NORTH);
        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        
        //frame.getContentPane().add(statisticsPanel, BorderLayout.WEST);
        //frame.getContentPane().add(parameterPanel, BorderLayout.AFTER_LAST_LINE);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
    
