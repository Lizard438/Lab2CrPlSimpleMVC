import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleController {
    Model model;
    ConsoleView view;
    BufferedReader console;

    public ConsoleController(Model model, ConsoleView view){
        this.model = model;
        this.view = view;
    }

    public void create() throws IOException {
        view.nameRequest();
        String name = console.readLine();
        if(!model.addEmployee(name)){
            view.notUniqueName();
        }else{
            view.added();
        }
    }

    public void update() throws IOException {
        view.update(true);
        String oldName = console.readLine();
        if(model.getIndexOf(oldName) == -1){
            view.employeeNotFound();
        }else{
            view.update(false);
            String newName = console.readLine();
            if(!model.editEmployee(oldName, newName)){
                view.notUniqueName();
            }else
                view.updated();

        }
    }

    public void delete() throws IOException {
        view.nameRequest();
        String name = console.readLine();
        if(!model.removeEmployee(name)){
            view.employeeNotFound();
        }else{
            view.removed();
        }

    }

    public void processCommands(){
        try(BufferedReader console = new BufferedReader(new InputStreamReader(System.in))){
            this.console = console;
            while (true){
                String command = console.readLine();
                switch (command.toLowerCase()){
                    case "c":
                        create();
                        break;
                    case "r":
                        view.read();
                        break;
                    case "u":
                        update();
                        break;
                    case "d":
                        delete();
                        break;
                    case "e":
                        return;
                    default:
                        view.commandNotFound();
                        break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Model m = new Model();
        ConsoleController controller = new ConsoleController(m, new ConsoleView(m));
        controller.processCommands();

    }

}
