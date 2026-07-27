import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        calculateExecutionTime();
        functionRace();
    }

    private static void functionRace() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(2); } catch (Exception e) {}
            return "Resposta lenta";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(1); } catch (Exception e) {}
            return "Resposta rápida";
        });

        CompletableFuture.anyOf(f1, f2)
                .thenAccept(System.out::println);
        // Se Não usarmos o join, o programa pode terminar antes de imprimir a resposta
    }

    private static void calculateExecutionTime() {
        // Criando um CompletableFuture que será completado no futuro
        var futuro = CompletableFuture.supplyAsync(() -> {
            // Simulando uma operação demorada
            var inicio = System.currentTimeMillis();
            try {
                Thread.sleep(4321);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            var fim = System.currentTimeMillis();

            return fim - inicio; // Retorna o tempo de execução
        });

        /*
         * Definindo uma ação a ser executada quando
         * o CompletableFuture estiver completo
         */
        var novoFuturo = futuro.thenAccept(resultado -> {
            System.out.println("Resultado: " + resultado); // Imprime o tempo de execução
        });

        // Aguardando a conclusão (isso é opcional, dependendo do contexto)
        novoFuturo.join();
    }
}