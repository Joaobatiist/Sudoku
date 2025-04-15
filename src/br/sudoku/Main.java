package br.sudoku;

import br.sudoku.model.Board;
import br.sudoku.model.Space;
import br.sudoku.util.BoardTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.sudoku.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);

    private static Board board;

    private final static int Board_LIMIT = 9;


    public static void main(String[] args) {


        final var positions = Stream.of(args).
                collect(Collectors.toMap(
                k -> k.split(";")[0],
                v -> v.split(";")[1]));

        var option = -1;
        while (true){
            System.out.println("--------Selecione uma das opções a seguir--------");
            System.out.println("1- Iniciar um novo jogo");
            System.out.println("2- Colocar um novo número");
            System.out.println("3- remover número");
            System.out.println("4- Visualizar jogo atual");
            System.out.println("5- Verificar status do jogo");
            System.out.println("6- limpar jogo");
            System.out.println("7- Finalizar jogo");
            System.out.println("8- Sair");

            option = scanner.nextInt();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNUmber();
                case 4 -> showCurrentGamer();
                case 5-> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Posição invalida");
            }
        }
    }


    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)){
            System.out.println("O jogo já foi iniciado");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < Board_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < Board_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space( expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("O jogo está pronto para começar");
    }

    private static void inputNumber() {
        if (isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o número sera inserido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o número sera inserido");
        var row = runUntilGetValidNumber(0, 8);

        System.out.printf("Informe o numero que vai entrar na posicao [%s, %s]\n", col, row);
        var value = runUntilGetValidNumber(1, 9);

        if (!board.changeValeu(col, row, value)){
            System.out.printf("A posição [%s, %s] ja possui um valor fixo\n", col, row);
            return;
        }
    }
    private static void removeNUmber() {
        if (isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o número sera inserido");
        var col = runUntilGetValidNumber(0, 8);
        System.out.println("Informe a linha em que o número sera inserido");
        var row = runUntilGetValidNumber(0, 8);

        System.out.printf("Informe o numero que vai entrar na posicao [%s, %s]\n", col, row);
        var value = runUntilGetValidNumber(1, 9);

        if (!board.clearValeu(col, row)){
            System.out.printf("A posição [%s, %s] ja possui um valor fixo\n", col, row);
            return;
        }
    }
    private static void showCurrentGamer() {
        if (isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < Board_LIMIT; i++) {
            for (var col: board.getSpaces()){
                args[argPos ++] = " " + ((isNull(col.get(i).getActualValue())) ? " " : col.get(i).getActualValue());
            }
        }
        System.out.println("Seu jogo se encontra da seguinte forma");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }
    private static void showGameStatus() {
        if (isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        System.out.printf("O jogo encontra se no status %s\n", board.getGameStatus().getLabel());
        if (board.hasErros()){
            System.out.println("O jogo encontrou erros");
        }else{
            System.out.println("O jogo não contém erro");
        }
    }

    private static void clearGame() {
        if (isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        System.out.println("Tem certeza que deseja limpar o jogo? (S/N)");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("s") && !confirm.equalsIgnoreCase("n")){
          confirm = scanner.next();
        }
        if (confirm.equalsIgnoreCase("s")){
            board.reset();
        }
    }

    private static void finishGame() {
        if (isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        if (board.gameIsFinished()){
            System.out.println("O jogo foi finalizado com sucesso");
            showCurrentGamer();
            board = null;
        } else if (board.hasErros()){
            System.out.println("O jogo contém erros, verifique seu board e ajuste:");
        } else {
            System.out.println("precisa preencher o board");
        }
    }


private static int runUntilGetValidNumber(final int min, final int max){
        var current = scanner.nextInt();
        while(current < min || current > max){
            System.out.println("Informe um numero entre %d e %d".formatted(min, max));
            current = scanner.nextInt();
        }
        return current;
}



}