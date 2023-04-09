import java.time.LocalDate;

public class Task {
    String description;
    LocalDate dueDate;

    public Task(String description, LocalDate dueDate){
        this.description = description;
        this.dueDate = dueDate;
    }
    public String getDescription(){
        return this.description;
    }
    public LocalDate getdueDate(){
        return this.dueDate;
    }

    @Override
    public String toString(){
        return description+"\n"+"(Due: "+this.dueDate.toString()+")";
    }
}
