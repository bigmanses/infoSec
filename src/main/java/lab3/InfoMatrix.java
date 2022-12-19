package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class InfoMatrix {

    private final String[] objects = new String[] {"test.txt", "study.bin", "Main.java", "plan.txt"};
    private final String[] usersName = new String[]{"Mansur", "Alexandr", "Konstantin", "Andrey", "Nikita", "Danya", "Mihail", "Valera", "Ivan", "Artur"};
    private  HashMap<String, HashMap<String, String>> users;
    private final String[] keyCommands = new String[]{"000", "001", "010", "011", "100", "101", "110", "111"};
    private  HashMap<String, String[]> commands;

    public InfoMatrix(){
        this.commands = initializationCommands();
        this.users = initializationUsers();
    }

    private  HashMap<String, String[]> initializationCommands(){
        HashMap<String, String[]> commands = new HashMap<>();
        commands.put(keyCommands[0], new String[]{"Запрет"});
        commands.put(keyCommands[1], new String[]{"Передача прав"});
        commands.put(keyCommands[2],new String[]{"Запись"} );
        commands.put(keyCommands[3],new String[]{"Запись", "Передача прав"});
        commands.put(keyCommands[4],new String[]{"Чтение"} );
        commands.put(keyCommands[5],new String[]{"Чтение", "Передача прав"} );
        commands.put(keyCommands[6],new String[]{"Чтение", "Запись"} );
        commands.put(keyCommands[7],new String[]{"Полный доступ"} );
        return commands;
    }

    private HashMap<String, HashMap<String, String>> initializationUsers(){
        HashMap<String, HashMap<String, String>> users = new HashMap<>();
        for(String name: usersName){
            users.put(name, createFileAccess());
        }
        return users;
    }

    private  HashMap<String, String> createFileAccess(){
        HashMap<String, String> fileAccess = new HashMap<>();
        for (String object: objects){
            fileAccess.put(object, keyCommands[(int) (Math.random() * 7)]);
        }
        return fileAccess;
    }

    public void printMatrix(){
        System.out.format("%17s", "Объекты/Субъекты");
        for(String object: objects){
            System.out.format("%17s", object);
        }
        System.out.println();
        for(String userName: users.keySet()){
            System.out.format("%17s", userName);
            HashMap<String, String> fileAccess = users.get(userName);
            for(String file: fileAccess.keySet()){
                System.out.format("%17s", fileAccess.get(file));
            }
            System.out.println();
        }
    }

    public boolean isUser(String name){
        if(users.get(name) == null){
            return false;
        }
        return true;
    }
    public void menu(String user,  BufferedReader reader) throws IOException {
        HashMap<String, String> access = users.get(user);
        printAccess(access);
        while (true){
            System.out.print("Жду ваших указаний:");
            String command = reader.readLine();
            System.out.print("Выберите объект:");
            String objNum = reader.readLine();
            checkCommand(command, objNum, access);
        }
    }

    private void printAccess(HashMap<String, String> access){
        for(String nameFile: access.keySet()){
            System.out.print(nameFile + ":" + Arrays.toString(commands.get(access.get(nameFile))));
            System.out.println();
        }
    }

    private void checkCommand(String command, String objNum, HashMap<String, String> access){
        if(command.equals("read")){
            if((Objects.equals(access.get(objects[Integer.parseInt(objNum) - 1]).charAt(0), '1'))){
                System.out.println("Операция выполнена");
            } else {
                System.out.println("Отказ в выполнении операции. У Вас нет прав для ее осуществления");
            }
        }
    }

}
