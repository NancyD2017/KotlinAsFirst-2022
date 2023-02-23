package src.main.java.org.example;

import java.io.*;
import java.util.*;

public class Program {
    public static void main(String[] args) //уточнить про стринг аргс
    throws IOException {
        System.out.print("""
                Добро пожаловать в редактор создания графов!

                Для создания вершины нажмите 1
                Для удаления вершины нажмите 2
                Для создания дуги нажмите 3
                Для удаления дуги нажмите 4
                Для изменения имени дуги нажмите 5
                Для изменения веса дуги нажмите 6

                Чтобы получить список исходящих из вершин дуг нажмите 7
                Чтобы получить список входящих в вершину дуг нажмите 8

                Выйти - нажмите 0
                """);
        BufferedReader toRead = new BufferedReader(new InputStreamReader(System.in));

        String number = toRead.readLine(); //попробовать изменить на интеджер
        Map<String, Map<String, String>> arc = new HashMap();
        Set<String> graph = new HashSet();
        while (!Objects.equals(number, "0")) {
            switch(number)
            {
                case "1" :
                    System.out.print("Введите имя вершины: ");
                    graph.add(toRead.readLine());
                    System.out.print("Новая вершина успешно добавлена! ");
                    break; //можно ли без брейка каждый раз
                case "2" :
                    if (graph.size() > 0) {
                        System.out.print("Список вершин, доступных для удаления:" + graph + "\nВведите имя вершины:"); //изменить вывод графа
                        graph.remove(toRead.readLine());
                        System.out.print("Вершина успешно удалена! ");
                    }
                    else  System.out.print("Список вершин пуст.\nПопробуйте сначала добавить вершину");
                    break;
                case "3" :
                    System.out.print("Введите имя дуги: ");
                    String arcName = toRead.readLine();
                    System.out.println("Введите направление дуги\n(Например, если она направлена из вершины А в В, просто запишите: A B):");
                    String arcDirection = toRead.readLine();
                    String[] summits = arcDirection.split(" ");
                    if (graph.containsAll(List.of(summits)) && summits.length == 2 && !summits[1].equals(summits[0])) {
                        System.out.println("Введите вес дуги:");
                        String arcWeight = toRead.readLine(); //изменить на интеджер
                        arc.put(arcName, Map.of(summits[0] + " -> " + summits[1], arcWeight));
                        System.out.print("Новая дуга успешно добавлена! ");
                        break;
                    }
                    else System.out.print("Новая дуга не добавлена. Неправильное направление дуги");
                    break;
                case "4" :
                    if (arc.size() > 0) {
                        System.out.print("Список дуг, доступных для удаления:" + arc.keySet() + "\nВведите имя дуги:"); //изменить вывод дуг
                        arc.remove(toRead.readLine());
                        System.out.print("Дуга успешно удалена! ");
                    }
                    else  System.out.print("Список дуг пуст.\nПопробуйте сначала добавить дугу");
                    break;
                case "5" :
                    if (arc.size() > 0) {
                        System.out.print("Список дуг для изменения:" + arc.keySet() + "\nВведите старое имя дуги: "); //изменить вывод дуг
                        String changement = toRead.readLine();
                        System.out.print("Введите новое имя дуги: ");
                        arc.put(toRead.readLine(), arc.get(changement));
                        arc.remove(changement);
                        System.out.print("Дуга успешно изменена! ");
                    }
                    else  System.out.print("Список дуг пуст.\nПопробуйте сначала добавить дугу");
                    break;
                case "6" :
                    if (arc.size() > 0) {
                        System.out.print("Список дуг для изменения:" + arc.keySet() + "\nВведите имя изменяемой дуги: "); //изменить вывод дуг
                        String newWeight = toRead.readLine();
                        System.out.print("Введите новый вес дуги: ");
                        arc.put(newWeight, Map.of(arc.get(newWeight).keySet().toString(), toRead.readLine()));
                        System.out.print("Дуга успешно изменена! ");
                    }
                    else  System.out.print("Список дуг пуст.\nПопробуйте сначала добавить дугу");
                    break;
                case "7" :
                    if (arc.size() > 0) {
                        System.out.print("Список вершин:" + graph + "\nВведите имя интересующей вершины: "); //изменить вывод дуг
                        String name = toRead.readLine();

                    }
                    else  System.out.print("Список дуг пуст.\nПопробуйте сначала добавить дугу");
                    break;
                case "8" :
                    if (arc.size() > 0) {
                        System.out.print("Список дуг для изменения:" + arc.keySet() + "\nВведите имя изменяемой дуги: "); //изменить вывод дуг
                        String newWeight = toRead.readLine();
                        System.out.print("Введите новый вес дуги: ");
                        arc.put(newWeight, Map.of(arc.get(newWeight).keySet().toString(), toRead.readLine()));
                        System.out.print("Дуга успешно изменена! ");
                    }
                    else  System.out.print("Список дуг пуст.\nПопробуйте сначала добавить дугу");
                    break;
                default :
                    System.out.println("Неверное действие. Для работы программы введите цифры 1,2,3,4,5,6,7,8 или 0");
            }
            System.out.print("Введите новую команду: ");
            number = toRead.readLine();
        }
        System.out.println(graph);
        System.out.println(arc);
        System.out.println(arc);
    }
}