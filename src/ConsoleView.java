public class ConsoleView {
    Model model;

    public ConsoleView(Model model){
        this.model = model;
        System.out.println("CRUD, C - Create, R - Read, U - Update, D - Delete, E - exit");
    }

    public void nameRequest(){
        System.out.println("Enter name:");
    }

    public void added(){
        System.out.println("Employee added");
        read();
    }

    public void updated(){
        System.out.println("Record edited");
        read();
    }

    public void removed(){
        System.out.println("Record removed");
        read();
    }

    public void update(boolean old){
        if(old)
            System.out.println("Enter old name:");
        else
            System.out.println("Enter new name:");
    }


    public void employeeNotFound(){
        System.out.println("Employee not found.");
    }

    public void notUniqueName(){
        System.out.println("Name must be unique.");
    }

    public void commandNotFound(){
        System.out.println("No such command.");
    }

    public void read(){
        System.out.println(model.getEmployees());
    }


}
