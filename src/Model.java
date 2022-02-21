import java.util.Iterator;
import java.util.TreeSet;

public class Model {

    private final TreeSet<String> employees = new TreeSet<>();

    public boolean addEmployee(String employee){
        return employees.add(employee);
    }

    public boolean removeEmployee(String employee){
        return employees.remove(employee);
    }

    public boolean editEmployee(String employee, String newEmployee){
        if((!employees.contains(employee))||(employees.contains(newEmployee))){
            return false;
        }else{
            employees.remove(employee);
            employees.add(newEmployee);
            return true;
        }
    }

    public TreeSet<String> getEmployees() {
        return employees;
    }

    public String getEmployeeAt(int index){
        if (index < 0 || index >= getSize()) {
            String s = "index, " + index + ", is out of bounds for getSize() = "
                    + getSize();
            throw new IllegalArgumentException(s);
        }
        Iterator<String> iterator = employees.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            String t = iterator.next();
            if (index == count) {
                return t;
            }
            count++;
        }
        // out of index. return null. will probably never reach this
        throw new IllegalArgumentException();
    }

    public int getIndexOf(String name){
        if(!employees.contains(name)){
            return -1;
        }else{
            return employees.headSet(name).size();
        }
    }

    public int getSize() {
        return employees.size();
    }
}
