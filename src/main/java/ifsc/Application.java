package ifsc;

import ifsc.dao.AlunoDAO;
import ifsc.model.RelatorioHoras;
import ifsc.service.CalculadoraHorasService;

import java.util.Scanner;

/**
 * Encapsula toda a lógica de execução e interação com o usuário da aplicação de console.
 */
public class Application {

    private final AlunoDAO alunoDAO;
    private final CalculadoraHorasService calculadoraService;
    private static final int CARGA_HORARIA_TOTAL_CURSO = 3200; // Valor de exemplo

    public Application(AlunoDAO alunoDAO, CalculadoraHorasService calculadoraService) {
        this.alunoDAO = alunoDAO;
        this.calculadoraService = calculadoraService;
    }

    /**
     * Inicia a execução principal da aplicação.
     */
    public void run() {
        System.out.println("======================================================");
        System.out.println("== Sistema de Cálculo de Horas Complementares - IFSC ==");
        System.out.println("======================================================");

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Digite o ID do aluno para gerar o relatório: ");
            int alunoId = Integer.parseInt(scanner.nextLine());

            // A lógica de buscar o aluno e gerar o relatório é orquestrada aqui.
            alunoDAO.buscarPorId(alunoId).ifPresentOrElse(
                    aluno -> {
                        System.out.println("\nGerando relatório para o aluno: " + aluno.nome());
                        System.out.println("Carga horária total do curso (base para cálculo): " + CARGA_HORARIA_TOTAL_CURSO + "h\n");
                        RelatorioHoras relatorio = calculadoraService.gerarRelatorio(aluno, CARGA_HORARIA_TOTAL_CURSO);
                        imprimirRelatorio(relatorio);
                    },
                    () -> System.out.println("ERRO: Aluno com ID " + alunoId + " não encontrado.")
            );

        } catch (NumberFormatException e) {
            System.out.println("ERRO: ID do aluno inválido.");
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método auxiliar responsável apenas pela formatação da saída do relatório.
     * @param relatorio O objeto contendo os dados do relatório a ser impresso.
     */
    private void imprimirRelatorio(RelatorioHoras relatorio) {
        System.out.println("--------- RELATÓRIO DE HORAS COMPLEMENTARES ---------");
        relatorio.horasPorModalidade().forEach((modalidade, horas) ->
                System.out.printf("Modalidade: %-25s | Horas Válidas: %3d h\n", modalidade.nome(), horas)
        );
        System.out.println("-----------------------------------------------------");
        System.out.printf("TOTAL DE HORAS COMPLEMENTARES VÁLIDAS: %d h\n", relatorio.totalHorasValidadas());
        System.out.println("-----------------------------------------------------");
    }
}