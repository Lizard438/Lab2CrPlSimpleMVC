import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller {
    private static final String[] DATA_ARRAY = { "Jane Doe", "John Smith", "Kathy Green" };
    private final Model model;
    private final View view;
    public ModelAdapter stringListModel;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        stringListModel = new ModelAdapter();
        fillModel();
        initView();
    }

    public void fillModel(){
        for (String s : DATA_ARRAY) {
            stringListModel.add(s);
        }
    }

    public void initView(){
        view.setFireListener(new FireListener());
        view.setHireListener(new HireListener());
        view.setNameFieldListener(new NameFieldListener());
        view.setListModel(stringListModel);
        PopupListener edit = new PopupListener();
        view.setupPopupMenuItem("Edit", edit);
        view.setListMouseListener(edit);
    }

    public static void main(String[] args) {
        new Controller(new Model(), new View());
    }

    class FireListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = view.getSelectedIndex();

            stringListModel.remove(view.getSelected());

            int size = stringListModel.getSize();
            if (size == 0) { //Nobody's left, disable firing.
                view.disableFireButton();
            } else {
                if (index == size) {
                    index--;
                }
                view.setSelection(index);
            }
        }
    }

    class HireListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getTextFieldValue().strip();

            if (name.isBlank()|| (!stringListModel.add(name))) {
                view.textFieldWarning();
            }else{
                int index = stringListModel.getIndexOf(name);
                view.setSelection(index);
                view.resetTextField();
            }
        }
    }

    class NameFieldListener implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            view.enableHireButton();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyField(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if(!handleEmptyField(e)){
                view.enableHireButton();
            }
        }

        private boolean handleEmptyField(DocumentEvent e){
            if(e.getDocument().getLength() <= 0){
                view.disableHireButton();
                return true;
            }else
                return false;
        }
    }

    class PopupListener implements MouseListener, ActionListener {

        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                view.showPopupMenu(e);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }


        @Override
        public void actionPerformed(ActionEvent e) {
            String oldName = view.getSelected().strip();
            String newName = view.requestName(oldName).strip();
            if (newName.equals("") || (!stringListModel.edit(oldName, newName))) {
                view.optionWarning("Enter unique name.");
            }else{
                view.setSelection(stringListModel.getIndexOf(newName));
            }
        }
    }

    class ModelAdapter extends AbstractListModel<String> {

        @Override
        public String getElementAt(int index) {
            return model.getEmployeeAt(index);
        }

        @Override
        public int getSize() {
            return model.getSize();
        }

        public int getIndexOf(String t) {
            return model.getIndexOf(t);
        }

        public boolean add(String t) {
            boolean result = model.addEmployee(t);
            if (result) {
                int index = getIndexOf(t);
                fireIntervalAdded(this, index, index + 1);
            }
            return result;
        }

        public boolean remove(String t) {
            int index = getIndexOf(t);
            boolean result = model.removeEmployee(t);
            if(result){
                fireIntervalRemoved(this, index, index + 1);
            }
            return result;
        }

        public boolean edit(String oldName, String newName){
            int oldIndex = getIndexOf(oldName);
            if(model.editEmployee(oldName, newName)){
                int newIndex = getIndexOf(newName);
                fireIntervalRemoved(this, oldIndex, oldIndex + 1);
                fireIntervalAdded(this, newIndex, newIndex + 1);
                return true;
            }else{
                return false;
            }
        }

    }
}
