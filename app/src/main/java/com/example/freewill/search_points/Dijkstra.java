package com.example.freewill.search_points;

import java.util.ArrayList;
import java.util.Arrays;


class Graph {
    final private int[][] matrix; // Матриця суміжності
    private VisitedVertex vv; // Клас, для зберігання даних при реалізації алгоритму Дейкстри
    private final int inf = Short.MAX_VALUE; // Імітація нескінченності
    private Root root;


    public Graph(int countVertex) { // Конструктор лише для кількості вершн
        matrix = new int[countVertex][countVertex];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = inf;
                }
            }
        }
    }

    public Graph(Root root) { // Конструктор для JSON обєкта
        this.root = root;
        matrix = new int[root.getDisplayFromId().size()][root.getDisplayFromId().size()];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = inf;
                }
            }
        }

        for (Line l : root.getLines()) {
            addVertex(l.getP1().getId(), l.getP2().getId(), l.weight(), true);
        }

    }

    // Додавання вершин орієнтованого графа
    public void addVertex(int startVertex, int endVertex, int weight, boolean isRevers) {
        matrix[startVertex][endVertex] = weight;
        if (isRevers) {
            matrix[endVertex][startVertex] = weight;
        }
    }

    //Передається вершина старту
    public void dsj(int startVertex) {
        vv = new VisitedVertex(matrix.length, startVertex);
        update(startVertex); // Оновлюємо відстань від вершини до сусідніх вершин
        for (int j = 1; j < matrix.length; j++) {
            startVertex = vv.updateArr(); // Повертаємось до нової вершини доступу
            update(startVertex);
        }
    }

    // Оновлюємо відстань від вершини до сусідніх вершин і попереднього вузла оточуючих вершин
    public void update(int vertex) {
        for (int i = 0; i < matrix[vertex].length; i++) {
            // len - це сума відстані від початкової вершини до поточної + вістань від поточної вершини до вершини i
            int len = vv.getDis(vertex) + matrix[vertex][i];
            // Якщо вершина i не була відвідана, а довжина len менша, ніж відстань від початкової вершини до вершини і, то її необхідно оновити
            if (!vv.in(i) && len < vv.getDis(i)) {
                vv.updatePre(i, vertex);
                vv.updateDis(i, len);
            }
        }
    }

    // Метод, для одержання шляху
    public ArrayList<Integer> getWay(int endVertex) {
        int[] way = getWay();
        ArrayList<Integer> result = new ArrayList<>();
        int tempVertex = endVertex;

        do {
            result.add(tempVertex);
            tempVertex = way[tempVertex];
        } while (tempVertex != 0);

        if (way[0] == 0) {
            result.add(tempVertex);
        }

        return result;
    }

    // Метод, який повертає результуючий вектор
    public int[] getWay() {
        return vv.pre_visited;
    }

    // Метод, який повертає масив координат для ліній
    public ArrayList<Integer> getCoords(int endVertex) {
        ArrayList<Integer> vectorResult = getWay(endVertex);
        ArrayList<Integer> coords = new ArrayList<>();

        for (Integer integer : vectorResult) {
            coords.add(root.getPointById(integer).getX());
            coords.add(root.getPointById(integer).getY());
        }
        return coords;
    }

}

class VisitedVertex {
    public int[] already_arr; // Масив відвіданих вершин
    public int[] pre_visited; // Попередники відвіданих вершин
    public int[] dis; // Масив, для запису ваг


    // length - кількість вершин
    // index - початкова точка відвідин
    public VisitedVertex(int length, int index) {
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];

        Arrays.fill(dis, Short.MAX_VALUE);

        this.already_arr[index] = 1;
        this.dis[index] = 0;
    }

    // Метод, який повертає true після відвідин вершини
    public boolean in(int index) {
        return already_arr[index] == 1;
    }

    //Метод, який оновлює відстань від початкової вершини до поточної
    public void updateDis(int index, int len) {
        dis[index] = len;
    }

    // Оновлює список відвіданих вершин
    public void updatePre(int pre, int index) {
        pre_visited[pre] = index;
    }


    // Метод, що повертає відстань від початкової точки до поточної
    public int getDis(int index) {
        return dis[index];
    }

    // Метод, який ітерується по графу
    public int updateArr() {
        int min = Short.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < already_arr.length; i++) {
            if (already_arr[i] == 0 && dis[i] < min) {
                min = dis[i];
                index = i;
            }
        }
        already_arr[index] = 1;
        return index;
    }

}

class Dijkstra {
    public static ArrayList<Integer> Calculate(String startVertex, String endVertex) {
        GsonParser parser = new GsonParser();
        Root root = parser.parse();

        int start = root.getDisplayFromId().get(startVertex);
        int end = root.getDisplayFromId().get(endVertex);

        Graph graph = new Graph(root);
        graph.dsj(start);

        return graph.getCoords(end);
    }
}