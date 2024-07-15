import java.util.*;

class Questionaire {
    HashMap<String, List<String>> map;
    private int points;

    public Questionaire() {
        map = new HashMap<>();
        map.put("What is the capital of India?", List.of("Mumbai", "Delhi", "Kocchi", "Kolkata", "B"));
        map.put("Where is Taj Mahal located?", List.of("Panvel", "Agra", "Indore", "America", "B"));
        map.put("Who is the current president of India (2024)?", List.of("Rahul Gandhi", "Michal Jordan", "Narendra Modi", "Smriti Irani", "C"));
        map.put("What is the national animal of India?", List.of("Peacock", "Cheetah", "Lion", "Tiger", "D"));
        points = 0;
    }

    public Iterator<String> getQuestions() {
        return map.keySet().iterator();
    }

    public boolean checkAnswer(String question, String userAnswer) {
        List<String> choices = map.get(question);
        int correctIndex = choices.size() - 1; // Index of correct answer (last element)
        return userAnswer.equalsIgnoreCase(choices.get(correctIndex));
    }

    public void addPoint() {
        points++;
    }

    public int getPoints() {
        return points;
    }

    public void processAnswer(String question, String userAnswer) {
        if (checkAnswer(question, userAnswer)) {
            System.out.println("Correct! 🎉");
            addPoint();
        } else {
            System.out.println("Incorrect. 😔");
        }
    }
}

public class QuizApp {
    private static final int QUESTION_TIMEOUT = 5000; // 15 seconds in milliseconds

    public static void main(String[] args) {
        
        Questionaire questionaire = new Questionaire();

        System.out.println("Welcome to the Quiz App!");
        Iterator<String> questions = questionaire.getQuestions();

        while (questions.hasNext()) {
            String question = questions.next();
            List<String> choices = questionaire.map.get(question);
            String options = String.join(", ", choices.subList(0, choices.size() - 1));

            System.out.println("\n" + question);
            System.out.println("Options: " + options);
            System.out.print("Your answer (A/B/C/D): ");

            Thread inputThread = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                try {
                    String userAnswer = scanner.nextLine();
                    questionaire.processAnswer(question, userAnswer);
                } catch (NoSuchElementException e) {
                    scanner.close();
                }
            });
            inputThread.start();

            try {
                inputThread.join(QUESTION_TIMEOUT);
            } catch (InterruptedException e) {
                System.out.println("No input was given, marking it as unsolved.");
                System.out.println();
            }
        }

        System.out.println("\nQuiz completed!");
        System.out.println("Total points: " + questionaire.getPoints() + "/" + questionaire.map.size());
    }
}

