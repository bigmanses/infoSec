package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * Дискретная модель политики безопасности
 * @author mismailov
 */
public class DiscreteSecurityModel {

    /** Имена объектов */
    private final String[] objects = new String[] {"test.txt", "study.bin", "Main.java", "plan.txt"};
    /** Имена субъектов */
    private final String[] usersName = new String[]{"Mansur", "Alexandr", "Konstantin", "Andrey", "Nikita", "Danya", "Mihail", "Valera", "Ivan", "Artur"};
    /** Список возможных команды в двоичном представлении */
    private final String[] keyCommands = new String[]{"000", "001", "010", "011", "100", "101", "110", "111"};
    /** Имя админа */
    String admin = initializationAdmin();
    /** Пользователи с правами доступа к объектам */
    private final HashMap<String, HashMap<String, String>> users;
    /** Мапа, где ключ  keyCommand, а значение - расшифровка двоичного представления */
    private final HashMap<String, String[]> commands;
    /** Ридер для чтения */
    private final BufferedReader reader;
    /** Меню */
    private final String[] helpMenu = new String[]{"help - print all available commands",
            "grant - grant another user with your rights to file",
            "read - read file",
            "write - write to file",
            "quit - quit the user",
            "exit - exit the system",
            "matrix - shows matrix (admin)"};
    /** Имя активнго пользователя */
    private String nameUser;


    public DiscreteSecurityModel(BufferedReader reader){
        this.commands = initializationCommands();
        initializationAdmin();
        this.users = initializationUsers();
        this.reader = reader;
    }

    /**
     * Метод инициализации наших команд
     * @return возвраащет наши всевозможные операции
     */
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

    /**
     * Метод инициализации наших пользоватей
     * @return возвраащет пользоватлей
     */
    private HashMap<String, HashMap<String, String>> initializationUsers(){
        HashMap<String, HashMap<String, String>> users = new HashMap<>();
        for(String name: usersName){
            if(name.equals(this.admin)) {
                users.put(name, createFileAccess(true));
                continue;
            }
            users.put(name, createFileAccess(false));

        }
        return users;
    }

    /**
     * Метод инициализации администратора
     * @return возвраащет имя рандомного пользователя
     */
    private String initializationAdmin(){
        return usersName[(int) (Math.random() * 9)];
    }

    /**
     * Метод создания прав доступа к объектам
     * @return наши права досутпа
     */
    private  HashMap<String, String> createFileAccess(boolean admin){
        HashMap<String, String> fileAccess = new HashMap<>();
        for (String object: objects){
            fileAccess.put(object, keyCommands[!admin ? (int) (Math.random() * 7) : 7]);
        }
        return fileAccess;
    }

    /**
     * Метод вывода матрицы пользователей
     */
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

    /**
     * Метод проверки на наличия пользователя
     * @param name - имя возможного пользователя
     * @return true, если такой есть, иначе - false
     */
    public boolean isUser(String name){
        return users.get(name) != null;
    }

    /**
     * Метод связи с пользователем
     * @param user - имя пользователя
     * @return  -1, если пользователь хочет выйти из аккаунта; 0, если хочет выйти из программы
     */
    public int menu(String user) throws IOException {
        HashMap<String, String> access = users.get(user);
        this.nameUser = user;
        printAccess(access);
        while (true){
            System.out.print("Жду ваших указаний > ");
            String command = reader.readLine();
            int flagExit = checkCommand(command, access);
            System.out.println();
            if(flagExit < 1) return flagExit;
        }
    }

    /**
     * Метод печати прав доступа
     * @param access - права доступа к объектам
     */
    private void printAccess(HashMap<String, String> access){
        for(String nameFile: access.keySet()){
            System.out.print(nameFile + ":" + Arrays.toString(commands.get(access.get(nameFile))));
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Метод првоерки на корректность ввода команды
     * @param command - команда, которую ввел пользователь
     * @param access - права доступа к объектам
     * @return -1, если пользователь хочет выйти из аккаунта; 0, если хочет выйти из программы; иначе 1
     */
    private int checkCommand(String command, HashMap<String, String> access) throws IOException {
        try {
            switch (command) {
                case "read":
                    printAnswer(Objects.equals(access.get(objects[Integer.parseInt(getNumObj()) - 1]).charAt(0), '1'));
                    break;
                case "write":
                    printAnswer(Objects.equals(access.get(objects[Integer.parseInt(getNumObj()) - 1]).charAt(1), '1'));
                    break;
                case "grant":
                    String objNum = getNumObj();
                    checkGrant(Objects.equals(access.get(objects[Integer.parseInt(objNum) - 1]).charAt(2), '1'), objNum);
                    break;
                case "help":
                    printMenu();
                    break;
                case "quit":
                    return -1;
                case "exit":
                    return 0;
                case "matrix":
                    if(nameUser.equals(admin)){
                        printMatrix();
                    } else { printAnswer(false);}
                    break;
                default:
                    System.out.println("Такой команды нет. Введите команду 'help' для ознакомления");
                    break;
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println("Объекта с номером " + getNumObj() + " не существует");
        }
        return 1;
    }

    /**
     * Метод печати ответа
     * @param allow - право на изменения
     */
    private void printAnswer(boolean allow){
        if(allow){
        System.out.println("Операция выполнена");
        } else {
            System.out.println("Отказ в выполнении операции. У Вас нет прав для ее осуществления");
        }
    }

    /**
     * Метод получения номера объекта от пользователя
     * @return  номер объекта
     */
    private String getNumObj() throws IOException {
        System.out.print("Выберите объект:");
        return reader.readLine();
    }

    /**
     * Метод печати вспомогательного меню
     */
    private void printMenu(){
        for(String item: helpMenu){
            System.out.println(item);
        }
    }

    /**
     * Метод првоерки на корректность ввода команды Grant
     * @param allow - право на изменения
     * @param objNum -  номер объекта
     */
    private void checkGrant(boolean allow, String objNum) throws IOException {
        if(allow){
            String[] value = inputLawOrUser();
            HashMap<String, String> chooseUser = users.get(value[1]);
            if(chooseUser == null) {
                System.out.println("Такого пользователя нет");
            } else {
                StringBuilder laws = new StringBuilder(chooseUser.get(objects[Integer.parseInt(objNum) - 1]));
                if(Objects.equals(value[0], "read")){
                    laws.setCharAt(0, '1');
                } else if(value[0].equals("write")){
                    laws.setCharAt(1, '1');
                } else if(value[0].equals("grant")){
                    laws.setCharAt(2, '1');
                } else {
                    System.out.println("Такой команды нет");
                    return;
                }
                chooseUser.put(objects[Integer.parseInt(objNum) - 1], laws.toString());
                System.out.println("Операция прошла успешно");
            }
        } else {
            System.out.println("У вас нет доступа к этой команде");
        }
    }

    /**
     * Метод получения права, которое пользователь хочет передать и имя - кому передать
     * @return право и имя пользователя
     */
    private String[] inputLawOrUser() throws IOException {
        System.out.print("Какое право передается? >");
        String law = reader.readLine();
        System.out.print("Какому пользователю передается право? >");
        String userName = reader.readLine();
        return new String[] {law, userName};
    }
}
