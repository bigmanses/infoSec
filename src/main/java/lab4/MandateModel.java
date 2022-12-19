package lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class MandateModel {
    /** Имена объектов */
    private final String[] objects = new String[] {"test.txt", "study.bin", "Main.java", "plan.txt"};
    /** Имена субъектов */
    private final String[] usersName = new String[]{"Mansur", "Alexandr", "Konstantin", "Andrey", "Nikita", "Danya", "Mihail", "Valera", "Ivan", "Artur"};
    private final String[] access = new String[]{"free access", "secret", "super secret"};
    private final HashMap<String, Integer> weight = new HashMap<>();
    private final HashMap<String, String> objectsAccess;
    private final HashMap<String,String> subjectsAccess;
    private String userName;
    /** Ридер для чтения */
    private final BufferedReader reader;

    /** Меню */
    private final String[] helpMenu = new String[]{"help - print all available commands",
            "quit - quit the user",
            "exit - exit the system",
            "request - request object"};
    /** Имя активнго пользователя */

    public MandateModel(BufferedReader reader){
        initializationWeight();
        objectsAccess = initializationObjects();
        subjectsAccess = initializationSubjects();
        this.reader = reader;
    }

    private void initializationWeight(){
        weight.put(access[0], 0);
        weight.put(access[1], 1);
        weight.put(access[2], 2);
    }
    /**
     * Метод инициализации субъектов
     * @return субъекты
     */
    private HashMap<String, String> initializationSubjects(){
        HashMap<String, String> subjectsAccess = new HashMap<>();
        for(String name: usersName){
            subjectsAccess.put(name, access[(int) (Math.random() * 3)]);
        }
        return subjectsAccess;
    }

    private HashMap<String, String> initializationObjects(){
        HashMap<String, String> objectsAccess = new HashMap<>();
        for(String file: objects){
            objectsAccess.put(file, access[(int) (Math.random() * 3)]);
        }
        return objectsAccess;
    }

    /**
     * Метод связи с пользователем
     * @param user - имя пользователя
     * @return  -1, если пользователь хочет выйти из аккаунта; 0, если хочет выйти из программы
     */
    public int menu(String user) throws IOException {
        userName = user;
        printAccess();
        while (true){
            System.out.print("Жду ваших указаний > ");
            String command = reader.readLine();
            int flagExit = checkCommand(command);
            System.out.println();
            if(flagExit < 1) return flagExit;
        }
    }

    /**
     * Метод печати прав доступа
     */
    private void printAccess(){
        System.out.print("Перчень доступных объектов: ");
        for(String object: objectsAccess.keySet()){
            if(weight.get(subjectsAccess.get(userName)) >= weight.get(objectsAccess.get(object))){
                System.out.print(object + ", ");
            }
        }
        System.out.println();
    }

    /**
     * Метод првоерки на корректность ввода команды
     * @param command - команда, которую ввел пользователь
     * @return -1, если пользователь хочет выйти из аккаунта; 0, если хочет выйти из программы; иначе 1
     */
    private int checkCommand(String command) throws IOException {
            switch (command) {
                case "request":
                    checkRequest();
                    break;
                case "help":
                    printMenu();
                    break;
                case "quit":
                    return -1;
                case "exit":
                    return 0;
                default:
                    System.out.println("Такой команды нет. Введите команду 'help' для ознакомления");
                    break;
            }
        return 1;
    }

    /**
     * Метод печати вспомогательного меню
     */
    private void printMenu(){
        for(String item: helpMenu){
            System.out.println(item);
        }
    }

    private void checkRequest() throws IOException {
        System.out.print("К какому объекту хотите осуществить доступ? >");
        String chooseObj = reader.readLine();
        try {
            if(weight.get(subjectsAccess.get(userName)) >= weight.get(objectsAccess.get(objects[Integer.parseInt(chooseObj) - 1]))){
                System.out.println("Операция прошла успешно");
            } else {
                System.out.println("Отказ в выполнении операции. Недостаточно прав.");
            }
        } catch (IndexOutOfBoundsException ex){
            System.out.println("Объекта с номером " + chooseObj  + " не существует");
        }
    }

    public void printObjects(){
        System.out.println();
        System.out.println("List objects:");
        for(String file: objectsAccess.keySet()){
            System.out.println(file + ": " + objectsAccess.get(file));
        }
        System.out.println();
    }

    public void printSubjects(){
        System.out.println();
        System.out.println("List subjects:");
        for(String name: subjectsAccess.keySet()){
            System.out.println(name + ": " + subjectsAccess.get(name));
        }
        System.out.println();
    }

    /**
     * Метод проверки на наличия пользователя
     * @param name - имя возможного пользователя
     * @return true, если такой есть, иначе - false
     */
    public boolean isUser(String name){
        return subjectsAccess.get(name) != null;
    }
}
