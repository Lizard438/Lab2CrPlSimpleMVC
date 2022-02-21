import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class View extends JFrame implements ListSelectionListener {
    private static final String fireString = "Fire";
    private static final String hireString = "Hire";
    private final JButton fireButton;
    private final JButton hireButton;
    private final JTextField employeeName;
    private final JList<String> jList;
    private final JPopupMenu popupMenu;
    private final JPanel contentPane;

    public View() {
        contentPane = new JPanel(new BorderLayout());

        jList = new JList<>();
        jList.addListSelectionListener(this);

        popupMenu = new JPopupMenu();

        fireButton = new JButton(fireString);
        fireButton.setActionCommand(fireString);

        hireButton = new JButton(hireString);
        hireButton.setActionCommand(hireString);
        hireButton.setEnabled(false);

        employeeName = new JTextField(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(fireButton);
        buttonPane.add(employeeName);
        buttonPane.add(hireButton);

        contentPane.add(new JScrollPane(jList), BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);
        contentPane.setOpaque(true);
        getContentPane().add(contentPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 200);

        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    public void setListModel(ListModel<String> model){
        jList.setModel(model);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setSelectedIndex(0);
    }

    public void setFireListener(ActionListener l){
        fireButton.addActionListener(l);
    }

    public void disableFireButton(){
        fireButton.setEnabled(false);
    }

    public void enableHireButton(){
        hireButton.setEnabled(true);
    }

    public void disableHireButton(){
        hireButton.setEnabled(false);
    }

    public void setHireListener(ActionListener l){
        hireButton.addActionListener(l);
    }

    public void setNameFieldListener(DocumentListener l){
        employeeName.getDocument().addDocumentListener(l);
    }

    public String getTextFieldValue(){
        return employeeName.getText();
    }

    public void textFieldWarning(){
        Toolkit.getDefaultToolkit().beep();
        employeeName.requestFocusInWindow();
        employeeName.selectAll();
    }

    public void resetTextField(){
        employeeName.requestFocusInWindow();
        employeeName.setText("");
    }

    public String requestName(String initialValue){
        return JOptionPane.showInputDialog(contentPane,
                "Edit record", initialValue);
    }

    public void optionWarning(String message){
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(contentPane, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public String getSelected(){
        return jList.getSelectedValue();
    }

    public int getSelectedIndex(){
        return jList.getSelectedIndex();
    }

    public void setSelection(int index){
        jList.setSelectedIndex(index);
        jList.ensureIndexIsVisible(index);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            fireButton.setEnabled(jList.getSelectedIndex() != -1);
        }
    }

    public void setupPopupMenuItem(String item, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(item);
        menuItem.addActionListener(listener);
        popupMenu.add(menuItem);
    }

    public void setListMouseListener(MouseListener l){
        jList.addMouseListener(l);
    }

    public void showPopupMenu(MouseEvent e){
        int index = jList.locationToIndex(e.getPoint());
        if(index == getSelectedIndex())
            popupMenu.show(e.getComponent(),
                    e.getX(), e.getY());
    }




}


